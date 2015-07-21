package cz.cuni.mff.d3s.jdeeco.turtlebot.simpleexchange;

import java.net.InetAddress;
import java.util.Random;

import cz.cuni.mff.d3s.deeco.logging.Log;
import cz.cuni.mff.d3s.deeco.runtime.DEECoNode;
import cz.cuni.mff.d3s.deeco.timer.WallTimeTimer;
import cz.cuni.mff.d3s.jdeeco.network.Network;
import cz.cuni.mff.d3s.jdeeco.network.l2.strategy.KnowledgeInsertingStrategy;
import cz.cuni.mff.d3s.jdeeco.publishing.DefaultKnowledgePublisher;
import cz.cuni.mff.d3s.jdeeco.ros.BeeClick;
import cz.cuni.mff.d3s.jdeeco.ros.Bumper;
import cz.cuni.mff.d3s.jdeeco.ros.Buttons;
import cz.cuni.mff.d3s.jdeeco.ros.DockIR;
import cz.cuni.mff.d3s.jdeeco.ros.FloorDistance;
import cz.cuni.mff.d3s.jdeeco.ros.Info;
import cz.cuni.mff.d3s.jdeeco.ros.LEDs;
import cz.cuni.mff.d3s.jdeeco.ros.Position;
import cz.cuni.mff.d3s.jdeeco.ros.RosServices;
import cz.cuni.mff.d3s.jdeeco.ros.SHT1x;
import cz.cuni.mff.d3s.jdeeco.ros.Speeker;
import cz.cuni.mff.d3s.jdeeco.ros.Wheels;

public class Run {

	private static final String SENSE_SWITCH = "sense";
	private static final String RECEIVE_SWITCH = "receive";

	public static void main(String[] args) {
		if (args.length != 1
				|| (!SENSE_SWITCH.equals(args[0]) 
						&& !RECEIVE_SWITCH.equals(args[0]))) {
			Log.e("Turtlebot Simple Exchange run without a correct parameter.");
			System.out
					.println(String
							.format("The Run class takes one argument with a value from {\"%s\", \"%s\"}",
									SENSE_SWITCH, RECEIVE_SWITCH));
			System.exit(1);
		}

		Random rand = new Random();
		WallTimeTimer t = new WallTimeTimer();
		try {
			DEECoNode node;
			RosServices services = new RosServices(
					 System.getenv("ROS_MASTER_URI"),
					 InetAddress.getLocalHost().getHostName());
			switch (args[0]) {
			case SENSE_SWITCH:
				Bumper bumper = new Bumper();
				Buttons buttons = new Buttons();
				DockIR dockIR = new DockIR();
				FloorDistance floorDistance = new FloorDistance();
				Info info = new Info();
				LEDs leds = new LEDs();
				Position position = new Position();
				SHT1x sht1x = new SHT1x();
				Speeker speeker = new Speeker();
				Wheels wheels = new Wheels();
				
				node = new DEECoNode(rand.nextInt(), t, services, new Network(),
						new BeeClick(), new DefaultKnowledgePublisher(),
						new KnowledgeInsertingStrategy(), bumper, buttons,
						dockIR, floorDistance, info, leds,
						position, sht1x, speeker, wheels);

				SensingComponent snsComponent = new SensingComponent(
						SENSE_SWITCH, SENSE_SWITCH);
				snsComponent.bumper = bumper;
				snsComponent.buttons = buttons;
				snsComponent.dockIR = dockIR;
				snsComponent.floorDistance = floorDistance;
				snsComponent.info = info;
				snsComponent.leds = leds;
				snsComponent.position = position;
				snsComponent.sht1x = sht1x;
				snsComponent.speeker = speeker;
				snsComponent.wheels = wheels;

				node.deployComponent(snsComponent);
				
				break;
			case RECEIVE_SWITCH:
				node = new DEECoNode(rand.nextInt(), t, services, new Network(), new BeeClick(),
						new KnowledgeInsertingStrategy());

				ReceivingComponent recvComponent = new ReceivingComponent(
						RECEIVE_SWITCH, RECEIVE_SWITCH);

				node.deployComponent(recvComponent);
				node.deployEnsemble(BumperEnsemble.class);
				node.deployEnsemble(ButtonEnsemble.class);
				node.deployEnsemble(DockEnsemble.class);
				node.deployEnsemble(FloorEnsemble.class);
				node.deployEnsemble(InfoEnsemble.class);
				node.deployEnsemble(PositionEnsemble.class);
				node.deployEnsemble(SHT1xEnsemble.class);
				node.deployEnsemble(WheelEnsemble.class);
				
				break;
			default:
				Log.e(String.format("The parameter \"%s\" not recognized.",
						args[0]));
				System.out
						.println(String
								.format("The Run class takes one argument with a value from {\"%s\", \"%s\"}",
										SENSE_SWITCH, RECEIVE_SWITCH));
				System.exit(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(2);
		}

		Log.i("DEECo started.");
		t.start();
		Log.i("DEECo finished.");
	}

}
