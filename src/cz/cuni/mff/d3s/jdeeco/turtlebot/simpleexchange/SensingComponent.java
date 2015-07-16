package cz.cuni.mff.d3s.jdeeco.turtlebot.simpleexchange;

import geometry_msgs.Point;
import geometry_msgs.PoseWithCovariance;
import sensor_msgs.NavSatFix;
import cz.cuni.mff.d3s.deeco.annotations.Component;
import cz.cuni.mff.d3s.deeco.annotations.In;
import cz.cuni.mff.d3s.deeco.annotations.Local;
import cz.cuni.mff.d3s.deeco.annotations.Out;
import cz.cuni.mff.d3s.deeco.annotations.PeriodicScheduling;
import cz.cuni.mff.d3s.deeco.annotations.Process;
import cz.cuni.mff.d3s.deeco.task.ParamHolder;
import cz.cuni.mff.d3s.jdeeco.ros.Bumper;
import cz.cuni.mff.d3s.jdeeco.ros.Buttons;
import cz.cuni.mff.d3s.jdeeco.ros.DockIR;
import cz.cuni.mff.d3s.jdeeco.ros.FloorDistance;
import cz.cuni.mff.d3s.jdeeco.ros.Info;
import cz.cuni.mff.d3s.jdeeco.ros.LEDs;
import cz.cuni.mff.d3s.jdeeco.ros.Position;
import cz.cuni.mff.d3s.jdeeco.ros.SHT1x;
import cz.cuni.mff.d3s.jdeeco.ros.Speeker;
import cz.cuni.mff.d3s.jdeeco.ros.Wheels;
import cz.cuni.mff.d3s.jdeeco.ros.datatypes.BumperValue;
import cz.cuni.mff.d3s.jdeeco.ros.datatypes.ButtonID;
import cz.cuni.mff.d3s.jdeeco.ros.datatypes.ButtonState;
import cz.cuni.mff.d3s.jdeeco.ros.datatypes.DockingIRDiod;
import cz.cuni.mff.d3s.jdeeco.ros.datatypes.DockingIRSignal;
import cz.cuni.mff.d3s.jdeeco.ros.datatypes.FloorSensorID;
import cz.cuni.mff.d3s.jdeeco.ros.datatypes.FloorSensorState;
import cz.cuni.mff.d3s.jdeeco.ros.datatypes.WheelID;
import cz.cuni.mff.d3s.jdeeco.ros.datatypes.WheelState;

@Component
public class SensingComponent {

	public String id;
	
	public String sensingId;

	@Local
	public Bumper bumper;
	@Local
	public Buttons buttons;
	@Local
	public DockIR dockIR;
	@Local
	public FloorDistance floorDistance;
	@Local
	public Info info;
	@Local
	public LEDs leds;
	@Local
	public Position position;
	@Local
	public SHT1x sht1x;
	@Local
	public Speeker speeker;
	@Local
	public Wheels wheels;

	public BumperValue bumperValue;
	public ButtonState button0;
	public ButtonState button1;
	public ButtonState button2;
	public DockingIRSignal dockLeft;
	public DockingIRSignal dockCenter;
	public DockingIRSignal dockRight;
	public FloorSensorState floorLeft;
	public FloorSensorState floorCenter;
	public FloorSensorState floorRight;
	public String fwInfo;
	public String swInfo;
	public String hwInfo;
	public Double gpsLatitude;
	public Double gpsLongitude;
	public Double gpsAltitude;
	public Long gpsTime;
	public Double odoX;
	public Double odoY;
	public Double odoZ;
	public Double poseX;
	public Double poseY;
	public Double poseZ;
	public Double oriX;
	public Double oriY;
	public Double oriZ;
	public Double oriW;
	public Double temperature;
	public Double humidity;
	public WheelState wheelLeft;
	public WheelState wheelRight;

	public SensingComponent(final String id, final String sensingId) {
		this.id = id;
		this.sensingId = sensingId;
	}

	@Process
	@PeriodicScheduling(period = 200, offset = 0)
	public static void senseBumper(@In("bumper") Bumper bumper,
			@Out("bumperValue") ParamHolder<BumperValue> bumperValue) {
		bumperValue.value = bumper.getBumper();
		System.out.println(String.format("\nBumper: %s", bumperValue.value));
	}

	@Process
	@PeriodicScheduling(period = 200, offset = 10)
	public static void senseButtons(@In("buttons") Buttons buttons,
			@Out("button0") ParamHolder<ButtonState> button0,
			@Out("button1") ParamHolder<ButtonState> button1,
			@Out("button2") ParamHolder<ButtonState> button2) {
		button0.value = buttons.getButtonState(ButtonID.B0);
		button1.value = buttons.getButtonState(ButtonID.B1);
		button2.value = buttons.getButtonState(ButtonID.B2);
		System.out.println(String.format("Button 0: %s", button0.value));
		System.out.println(String.format("Button 1: %s", button1.value));
		System.out.println(String.format("Button 2: %s", button2.value));
	}

	@Process
	@PeriodicScheduling(period = 200, offset = 20)
	public static void senseDock(@In("dockIR") DockIR dockIR,
			@Out("dockLeft") ParamHolder<DockingIRSignal> dockLeft,
			@Out("dockCenter") ParamHolder<DockingIRSignal> dockCenter,
			@Out("dockRight") ParamHolder<DockingIRSignal> dockRight) {
		dockLeft.value = dockIR.getDockingIRSignal(DockingIRDiod.LEFT);
		dockCenter.value = dockIR.getDockingIRSignal(DockingIRDiod.CENTRAL);
		dockRight.value = dockIR.getDockingIRSignal(DockingIRDiod.RIGHT);
		System.out.println(String.format("Dock left: %s", dockLeft.value));
		System.out.println(String.format("Dock center: %s", dockCenter.value));
		System.out.println(String.format("Dock right: %s", dockRight.value));
	}

	@Process
	@PeriodicScheduling(period = 200, offset = 30)
	public static void senseFloor(
			@In("floorDistance") FloorDistance floorDistance,
			@Out("floorLeft") ParamHolder<FloorSensorState> floorLeft,
			@Out("floorCenter") ParamHolder<FloorSensorState> floorCenter,
			@Out("floorRight") ParamHolder<FloorSensorState> floorRight) {
		floorLeft.value = floorDistance.getFloorSensorState(FloorSensorID.LEFT);
		floorCenter.value = floorDistance
				.getFloorSensorState(FloorSensorID.CENTER);
		floorRight.value = floorDistance
				.getFloorSensorState(FloorSensorID.RIGHT);
		System.out.println(String.format("Floor left: %s", floorLeft.value));
		System.out
				.println(String.format("Floor center: %s", floorCenter.value));
		System.out.println(String.format("Floor right: %s", floorRight.value));
	}

	@Process
	@PeriodicScheduling(period = 10000, offset = 40)
	public static void senseInfo(@In("info") Info info,
			@Out("fwInfo") ParamHolder<String> fwInfo,
			@Out("swInfo") ParamHolder<String> swInfo,
			@Out("hwInfo") ParamHolder<String> hwInfo) {
		fwInfo.value = info.getFirmwareInfo();
		swInfo.value = info.getSoftwareInfo();
		hwInfo.value = info.getHardwareInfo();
		System.out.println(String.format("FW: %s", fwInfo.value));
		System.out.println(String.format("SW: %s", swInfo.value));
		System.out.println(String.format("HW: %s", hwInfo.value));
	}

	@Process
	@PeriodicScheduling(period = 200, offset = 50)
	public static void sensePosition(@In("position") Position position,
			@Out("gpsLatitude") ParamHolder<Double> gpsLatitude,
			@Out("gpsLongitude") ParamHolder<Double> gpsLongitude,
			@Out("gpsAltitude") ParamHolder<Double> gpsAltitude,
			@Out("gpsTime") ParamHolder<Long> gpsTime,
			@Out("odoX") ParamHolder<Double> odoX,
			@Out("odoY") ParamHolder<Double> odoY,
			@Out("odoZ") ParamHolder<Double> odoZ,
			@Out("poseX") ParamHolder<Double> poseX,
			@Out("poseY") ParamHolder<Double> poseY,
			@Out("poseZ") ParamHolder<Double> poseZ,
			@Out("oriX") ParamHolder<Double> oriX,
			@Out("oriY") ParamHolder<Double> oriY,
			@Out("oriZ") ParamHolder<Double> oriZ,
			@Out("oriW") ParamHolder<Double> oriW) {
		NavSatFix gps = position.getGpsPosition();
		if(gps != null){
			gpsLatitude.value = gps.getLatitude();
			gpsLongitude.value = gps.getLongitude();
			gpsAltitude.value = gps.getAltitude();
		}
		
		gpsTime.value = position.getGpsTime();
		
		Point odometry = position.getOdometry();
		if(odometry != null){
			odoX.value = odometry.getX();
			odoY.value = odometry.getY();
			odoZ.value = odometry.getZ();
		}
		
		PoseWithCovariance pose = position.getPosition();
		if(pose != null){
			poseX.value = pose.getPose().getPosition().getX();
			poseY.value = pose.getPose().getPosition().getY();
			poseZ.value = pose.getPose().getPosition().getZ();
			oriX.value = pose.getPose().getOrientation().getX();
			oriY.value = pose.getPose().getOrientation().getY();
			oriZ.value = pose.getPose().getOrientation().getZ();
			oriW.value = pose.getPose().getOrientation().getW();
		}
		
		if(gps != null){
			System.out.println(String.format("GPS Lat: %f Long: %f Alt: %f",
				gps.getLatitude(), gps.getLongitude(),
				gps.getAltitude()));
		} else {
			System.out.println("GPS: No data received");
		}
		
		System.out.println(String.format("GPS Time: %d", gpsTime.value));
		
		if(odometry != null){
			System.out.println(String.format("Odometry: [%f, %f, %f]",
				odometry.getX(), odometry.getY(),
				odometry.getZ()));
		} else {
			System.out.println("Odometry: No data received");
		}
		
		if(pose != null){
			System.out.println(String.format("Position: [%f, %f, %f]", 
				pose.getPose().getPosition().getX(),
				pose.getPose().getPosition().getY(),
				pose.getPose().getPosition().getZ()));
			System.out.println(String.format("Orientation: [%f, %f, %f, %f]",
				pose.getPose().getOrientation().getX(),
				pose.getPose().getOrientation().getY(),
				pose.getPose().getOrientation().getZ(),
				pose.getPose().getOrientation().getW()));
		} else {
			System.out.println("Position and Orientation: No data received");
		}
	}

	@Process
	@PeriodicScheduling(period = 200, offset = 60)
	public static void senseSHT1x(@In("sht1x") SHT1x sht1x,
			@Out("temperature") ParamHolder<Double> temperature,
			@Out("humidity") ParamHolder<Double> humidity) {
		temperature.value = sht1x.getTemperature();
		humidity.value = sht1x.getHumidity();
		System.out.println(String.format("Temperature: %f", temperature.value));
		System.out.println(String.format("Humidity: %f", humidity.value));
	}

	@Process
	@PeriodicScheduling(period = 200, offset = 70)
	public static void senseWheels(@In("wheels") Wheels wheels,
			@Out("wheelLeft") ParamHolder<WheelState> wheelLeft,
			@Out("wheelRight") ParamHolder<WheelState> wheelRight) {
		wheelLeft.value = wheels.getWheelState(WheelID.LEFT);
		wheelRight.value = wheels.getWheelState(WheelID.RIGHT);
		System.out.println(String.format("Wheel left: %s", wheelLeft.value));
		System.out.println(String.format("Wheel right: %s", wheelRight.value));
	}

}
