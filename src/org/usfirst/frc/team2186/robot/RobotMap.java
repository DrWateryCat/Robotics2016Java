package org.usfirst.frc.team2186.robot;

public class RobotMap {
	public static class DriveTrain {
		public static final int[] LEFT = {0, 1, 2};
		public static final int LEFT_FORWARD = 0;
		public static final int LEFT_REVERSE = 1;
		public static final int[] LEFT_ENCODER = {0, 1}; 
		public static final int[] RIGHT = {3, 4, 5};
		public static final int RIGHT_FORWARD = 2;
		public static final int RIGHT_REVERSE = 3;
		public static final int[] RIGHT_ENCODER = {2, 3};
		
		//Inches of robot movement per pulse
		public static final double DISTANCE_PER_PULSE = 0.043;
		
		//Degrees of wheel movement per pulse
		public static final double DEGREES_PER_PULSE = 1/(8*Math.PI)/DISTANCE_PER_PULSE/360;
		
		//Statistics
		public static final double TURNING_CIRCUMFERENCE = 28*Math.PI;
		public static final double INCHES_PER_TURN_DEGREE = TURNING_CIRCUMFERENCE/360;
		
		//How many degrees of turning movement per pulse
		public static final double TURNING_DEGREES_PER_PULSE = (1/INCHES_PER_TURN_DEGREE)*DISTANCE_PER_PULSE;
	}
	
	public static class Intake {
		public static final int ROLLERS = 6;
		public static final int LINEAR = 7;
		
		public static final int OUT_LIMIT = 4;
	}
}
