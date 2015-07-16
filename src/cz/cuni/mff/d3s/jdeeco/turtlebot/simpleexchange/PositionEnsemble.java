package cz.cuni.mff.d3s.jdeeco.turtlebot.simpleexchange;

import cz.cuni.mff.d3s.deeco.annotations.Ensemble;
import cz.cuni.mff.d3s.deeco.annotations.In;
import cz.cuni.mff.d3s.deeco.annotations.KnowledgeExchange;
import cz.cuni.mff.d3s.deeco.annotations.Membership;
import cz.cuni.mff.d3s.deeco.annotations.Out;
import cz.cuni.mff.d3s.deeco.annotations.PeriodicScheduling;
import cz.cuni.mff.d3s.deeco.task.ParamHolder;

@Ensemble
@PeriodicScheduling(period = 500, offset = 150)
public class PositionEnsemble {

	@Membership
	public static boolean membership(@In("coord.receiverId") String receiverId,
			@In("member.sensingId") String sensingId) {
		return true;
	}

	@KnowledgeExchange
	public static void bumperExchange(
			@In("member.gpsLatitude") Double memberGpsLatitude,
			@In("member.gpsLongitude") Double memberGpsLongitude,
			@In("member.gpsAltitude") Double memberGpsAltitude,
			@In("member.gpsTime") Long memberGpsTime,
			@In("member.odoX") Double memberOdoX,
			@In("member.odoY") Double memberOdoY,
			@In("member.odoZ") Double memberOdoZ,
			@In("member.poseX") Double memberPoseX,
			@In("member.poseY") Double memberPoseY,
			@In("member.poseZ") Double memberPoseZ,
			@In("member.oriX") Double memberOriX,
			@In("member.oriY") Double memberOriY,
			@In("member.oriZ") Double memberOriZ,
			@In("member.oriW") Double memberOriW,
			@Out("coord.gpsLatitude") ParamHolder<Double> coordGpsLatitude,
			@Out("coord.gpsLongitude") ParamHolder<Double> coordGpsLongitude,
			@Out("coord.gpsAltitude") ParamHolder<Double> coordGpsAltitude,
			@Out("coord.gpsTime") ParamHolder<Long> coordGpsTime,
			@Out("coord.odoX") ParamHolder<Double> coordOdoX,
			@Out("coord.odoY") ParamHolder<Double> coordOdoY,
			@Out("coord.odoZ") ParamHolder<Double> coordOdoZ,
			@Out("coord.poseX") ParamHolder<Double> coordPoseX,
			@Out("coord.poseY") ParamHolder<Double> coordPoseY,
			@Out("coord.poseZ") ParamHolder<Double> coordPoseZ,
			@Out("coord.oriX") ParamHolder<Double> coordOriX,
			@Out("coord.oriY") ParamHolder<Double> coordOriY,
			@Out("coord.oriZ") ParamHolder<Double> coordOriZ,
			@Out("coord.oriW") ParamHolder<Double> coordOriW) {
		coordGpsLatitude.value = memberGpsLatitude;
		coordGpsLongitude.value = memberGpsLongitude;
		coordGpsAltitude.value = memberGpsAltitude;
		coordGpsTime.value = memberGpsTime;
		coordOdoX.value = memberOdoX;
		coordOdoY.value = memberOdoY;
		coordOdoZ.value = memberOdoZ;
		coordPoseX.value = memberPoseX;
		coordPoseY.value = memberPoseY;
		coordPoseZ.value = memberPoseZ;
		coordOriX.value = memberOriX;
		coordOriY.value = memberOriY;
		coordOriZ.value = memberOriZ;
		coordOriW.value = memberOriW;
	}
}
