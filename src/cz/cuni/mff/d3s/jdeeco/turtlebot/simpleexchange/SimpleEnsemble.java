package cz.cuni.mff.d3s.jdeeco.turtlebot.simpleexchange;

import cz.cuni.mff.d3s.deeco.annotations.Ensemble;
import cz.cuni.mff.d3s.deeco.annotations.In;
import cz.cuni.mff.d3s.deeco.annotations.KnowledgeExchange;
import cz.cuni.mff.d3s.deeco.annotations.Membership;
import cz.cuni.mff.d3s.deeco.annotations.Out;
import cz.cuni.mff.d3s.deeco.annotations.PeriodicScheduling;
import cz.cuni.mff.d3s.deeco.task.ParamHolder;
import cz.cuni.mff.d3s.jdeeco.ros.datatypes.RobotState;

@Ensemble
@PeriodicScheduling(period = 500)
public class SimpleEnsemble {
	@Membership
	public static boolean membership(@In("coord.receiverId") String receiverId,
			@In("member.sensingId") String sensingId){
		return true;
	}
	
	@KnowledgeExchange
	public static void simpleExchange(@In("member.robotState") RobotState memberState,
			@Out("coord.robotState") ParamHolder<RobotState> coordState){
		coordState.value = memberState;
	}

}
