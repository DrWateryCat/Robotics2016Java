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
	}
	
	public static class Intake {
		public static final int LEFT_MOTOR = 6;
		public static final int RIGHT_MOTOR = 7;
		
		public static final int WHEELS = 8;
	}
	
	
	public static class Launcher {
		public static final int WHEELS = 9;
		public static final int[] PISTON = {4, 5}; 
	}
}
