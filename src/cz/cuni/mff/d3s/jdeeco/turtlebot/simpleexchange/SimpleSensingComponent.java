package cz.cuni.mff.d3s.jdeeco.turtlebot.simpleexchange;

import cz.cuni.mff.d3s.deeco.annotations.Component;
import cz.cuni.mff.d3s.deeco.annotations.In;
import cz.cuni.mff.d3s.deeco.annotations.Process;
import cz.cuni.mff.d3s.deeco.annotations.Local;
import cz.cuni.mff.d3s.deeco.annotations.Out;
import cz.cuni.mff.d3s.deeco.annotations.PeriodicScheduling;
import cz.cuni.mff.d3s.deeco.task.ParamHolder;
import cz.cuni.mff.d3s.jdeeco.ros.RosServices;
import cz.cuni.mff.d3s.jdeeco.ros.datatypes.RobotState;

@Component
public class SimpleSensingComponent {
	
	public String id;
	
	public String sensingId;
	
	@Local
	public RosServices rosServices;
	
	public RobotState robotState;
	
	public SimpleSensingComponent(final String id, final String sensingId){
		this.id = id;
		this.sensingId = sensingId;
	}
	
	@Process
	@PeriodicScheduling(period = 1000)
	public static void readState(@In("rosServices") RosServices rosServices,
			@Out("robotState") ParamHolder<RobotState> robotState){
		robotState.value = new RobotState(rosServices);
		System.out.println(robotState);
	}
}
