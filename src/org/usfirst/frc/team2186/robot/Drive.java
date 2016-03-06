package org.usfirst.frc.team2186.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive {
	class DriveTypes {
		public static final int TANK_DRIVE = 0;
		public static final int ARCADE_DRIVE = 1;
	}
	private static Drive _instance = null;
	private static final double MAX_SPEED = .75;
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
		setRight(-right);
	}
	
	public void goDistance(double dist, double speed) {
		double m_left_dist = 0, m_right_dist = 0;
		
		if(dist < 0)
		{
			set(-speed, -speed);
			dist = -dist;
		}
		else
			set(speed, speed);
		
		m_left.getEncoder().reset();
		m_right.getEncoder().reset();
		
		while(Math.abs(m_left_dist) <= dist || Math.abs(m_right_dist) <= dist) {
			m_left_dist = m_left.getEncoder().getDistance();
			m_right_dist = m_right.getEncoder().getDistance();
		}
		stop();
	}
	
	public Encoder[] getEncoders() {
		return new Encoder[] {m_left.getEncoder(), m_right.getEncoder() };
	}
	
	public void stop()
	{
		set(0, 0);
	}
	
	public void turnAngle(double speed, double degrees)
	{
		if(degrees == 0)
			return;
		
		//Turn right on positive angle, left on negative
		if(degrees > 0)
			set(speed, -speed);
		else
		{
			degrees = -degrees;
			set(-speed, speed);
		}
		m_left.m_encoder.reset(); //Reset the encoders each time we turn
		m_right.m_encoder.reset();
		
		//TODO: Replace this with something not autistic AF
		//while();
		
		//Stop.
		stop();
	}
	
	public void resetEncoders() {
		m_left.m_encoder.reset();
		m_right.m_encoder.reset();
	}
	
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
			right = y - x;
		}
		
		if(left > MAX_SPEED && right < MAX_SPEED)
			set(MAX_SPEED, right);
		else if(right > MAX_SPEED && left < MAX_SPEED)
			set(left, MAX_SPEED);
		else if(left > MAX_SPEED && right > MAX_SPEED)
			set(MAX_SPEED, MAX_SPEED);
		else
			set(left, right);
	}
	
	public void teleop(Joystick j1, Joystick j2) {
		double left, right, x, y;
		int driveType = (int) SmartDashboard.getNumber("DriveType", 1);
		if(driveType == DriveTypes.TANK_DRIVE) {
			left = Utils.deadzone(j1.getRawAxis(1));
			right = Utils.deadzone(j2.getRawAxis(1));
			
			set(left, right);
		} else {
			x = Utils.deadzone(j1.getRawAxis(0));
			y = Utils.deadzone(j1.getRawAxis(1));
			
			left = y + x;
			right = y - x;
		}
		
		if(left > MAX_SPEED && right < MAX_SPEED)
			set(MAX_SPEED, right);
		else if(right > MAX_SPEED && left < MAX_SPEED)
			set(left, MAX_SPEED);
		else if(left > MAX_SPEED && right > MAX_SPEED)
			set(MAX_SPEED, MAX_SPEED);
		else
			set(left, right);
	}
}
