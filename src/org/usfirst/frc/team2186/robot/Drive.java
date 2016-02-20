package org.usfirst.frc.team2186.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive {
	class DriveTypes {
		public static final int TANK_DRIVE = 0;
		public static final int ARCADE_DRIVE = 1;
	}
	private static Drive _instance = null;
	public static Drive getInstance() {
		if(_instance == null) {
			_instance = new Drive();
		}
		return _instance;
	}
	
	ShiftingSpeedController m_left;
	ShiftingSpeedController m_right;
	private Drive() {
		m_left = new ShiftingSpeedController(RobotMap.DriveTrain.LEFT_FORWARD, RobotMap.DriveTrain.LEFT_REVERSE, RobotMap.DriveTrain.LEFT_ENCODER , RobotMap.DriveTrain.LEFT);
		m_right = new ShiftingSpeedController(RobotMap.DriveTrain.RIGHT_FORWARD, RobotMap.DriveTrain.RIGHT_REVERSE, RobotMap.DriveTrain.RIGHT_ENCODER, RobotMap.DriveTrain.RIGHT);
	}
	
	public void setRight(double val) {
		m_right.set(val);
	}
	
	public void setLeft(double val) {
		m_left.set(val);
	}
	
	public void shiftLeft(int gear) {
		m_left.shift(gear);
	}
	
	public void shiftRight(int gear) {
		m_right.shift(gear);
	}
	
	public void shift(int gear) {
		shiftLeft(gear);
		shiftRight(gear);
	}
	
	public void set(double left, double right) {
		setLeft(left);
		setRight(right);
	}
	
	public void goDistance(double dist) {
		double m_left_dist = 0, m_right_dist = 0;
		
		while(m_left_dist != dist || m_right_dist != dist) {
			set(0.75, 0.75);
			m_left_dist = m_left.getEncoder().getDistance();
			m_right_dist = m_right.getEncoder().getDistance();
		}
		set(0, 0);
	}
	
	//I thought this would be nice to have. --gamrguy
	public void stop()
	{
		set(0, 0);
	}
	
	public void teleop(Joystick j) {
		int driveType = (int) SmartDashboard.getNumber("DriveType", 0);
		if(driveType == DriveTypes.TANK_DRIVE) {
			double left = j.getRawAxis(0);
			double right = j.getRawAxis(2);
			
			set(left, right);
		} else {
			double x = j.getRawAxis(0);
			double y = j.getRawAxis(1);
			
			double left = y + x;
			double right = y - x;
			
			set(left, right);
		}
	}
}
