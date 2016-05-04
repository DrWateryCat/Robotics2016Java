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
	
	private double m_leftSpeed, m_rightSpeed;
	
	ShiftingSpeedController m_left;
	ShiftingSpeedController m_right;
	private Drive() {
		m_left = new ShiftingSpeedController(RobotMap.DriveTrain.LEFT_FORWARD, RobotMap.DriveTrain.LEFT_REVERSE, RobotMap.DriveTrain.LEFT_ENCODER , RobotMap.DriveTrain.LEFT);
		m_right = new ShiftingSpeedController(RobotMap.DriveTrain.RIGHT_FORWARD, RobotMap.DriveTrain.RIGHT_REVERSE, RobotMap.DriveTrain.RIGHT_ENCODER, RobotMap.DriveTrain.RIGHT);
	}
	
	public void setRight(double val) {
		m_right.set(-val);
		m_rightSpeed = val;
	}
	
	public void setLeft(double val) {
		m_left.set(val);
		m_leftSpeed = val;
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
		if(Dashboard.getInstance().getConfig().getBoolean("DriveEnabled", true)) {
			setLeft(left);
			setRight(right);
		}
	}
	
	public double getLeft() {
		return m_leftSpeed;
	}
	
	public double getRight() {
		return m_rightSpeed;
	}
	
	public void stop() {
		set(0, 0);
	}
	
	@Deprecated
	//Resets the encoders.
	public void reset() {
		m_left.m_encoder.reset();
		m_right.m_encoder.reset();
	}
	
	@Deprecated
	public void teleop(Joystick j) {
		double left, right, x, y;
		int driveType = (int) SmartDashboard.getNumber("DriveType", 1);
		if(driveType == DriveTypes.TANK_DRIVE) {
			left = Utils.deadzone(j.getRawAxis(1));
			right = Utils.deadzone(j.getRawAxis(3));
			
		} else {
			x = Utils.deadzone(j.getRawAxis(0));
			y = Utils.deadzone(j.getRawAxis(1));
			
			left = y + x;
			right = -(y - x);
		}
		
		double maxLeft = Dashboard.getInstance().getConfig().getDouble("MaxLeftSpeed", 0.3);
		double maxRight = Dashboard.getInstance().getConfig().getDouble("MaxRightSpeed", 0.3);
		
		left = Utils.clamp(left, -maxLeft, maxLeft);
		right = Utils.clamp(right, -maxRight, maxRight);
		
		set(left, right);
	}
	
	public void teleop(Joystick j1, Joystick j2) {
		double left, right, x, y;
		
		//TODO: Get drive type switching working
		int driveType = (int) SmartDashboard.getNumber("DriveType", 1);
		if(driveType == DriveTypes.TANK_DRIVE) {
			left = Utils.deadzone(j1.getRawAxis(1));
			right = Utils.deadzone(j2.getRawAxis(1));
			
			//set(left, -right);
		} else {
			x = Utils.deadzone(j1.getRawAxis(0));
			y = Utils.deadzone(j1.getRawAxis(1));
			
			left = y + x;
			right = y - x;
		}
		
		//Max speed capping
		/*
		if(left > MAX_SPEED && right < MAX_SPEED)
			set(MAX_SPEED, right);
		else if(right > MAX_SPEED && left < MAX_SPEED)
			set(left, MAX_SPEED);
		else if(left > MAX_SPEED && right > MAX_SPEED)
			set(MAX_SPEED, MAX_SPEED);
		else
			set(left, right);
		*/
		
		double maxLeft = Dashboard.getInstance().getConfig().getDouble("MaxLeftSpeed", 0.75);
		double maxRight = Dashboard.getInstance().getConfig().getDouble("MaxRightSpeed", 0.75);
		
		left = Utils.clamp(left, -maxLeft, maxLeft);
		right = Utils.clamp(right, -maxRight, maxRight);
		
		set(left, right);
		
	}
}
