package cz.cuni.mff.d3s.jdeeco.turtlebot.simpleexchange;

import cz.cuni.mff.d3s.deeco.annotations.Component;
import cz.cuni.mff.d3s.deeco.annotations.In;
import cz.cuni.mff.d3s.deeco.annotations.Process;
import cz.cuni.mff.d3s.deeco.annotations.PeriodicScheduling;
import cz.cuni.mff.d3s.jdeeco.ros.datatypes.RobotState;

@Component
public class SimpleReceivingComponent {
	public String id;
	public String receiverId;
	public RobotState robotState;

	public SimpleReceivingComponent(String id, String receiverId) {
		this.id = id;
		this.receiverId = id;
	}
	
	@Process
	@PeriodicScheduling(period = 1000)
	public static void printRobotState(@In("robotState") RobotState robotState){
		System.out.println(robotState);
	}
}
