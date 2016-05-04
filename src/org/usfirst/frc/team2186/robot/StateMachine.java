package org.usfirst.frc.team2186.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author gamrguy
 *
 *	State machine for the robot. Handles autonomous.
 */

public class StateMachine
{
	public static final int STOPPED = 0;
	public static final int FORWARD = 1;
	public static final int BACKWARD = 2;
	public static final int TURN_LEFT = 3;
	public static final int TURN_RIGHT = 4;
	
	private int currentState;
	private double distance;
	private double time;
	private double startTime;
	
	public StateMachine()
	{
		currentState = STOPPED;
		distance = 0;
		time = 0;
		startTime = 0;
	}
	
	public void update()
	{
		//Time stopper
		if(time > 0){
			time += startTime - Timer.getFPGATimestamp();
			System.out.println("Time remaining: "+time);
			startTime = Timer.getFPGATimestamp();
			if(time <= 0)
				currentState = STOPPED;
		}
		
		//Distance stopper
		if(distance > 0) {
			if((Drive.getInstance().m_left.m_encoder.getDistance()+
				Drive.getInstance().m_right.m_encoder.getDistance())/2 >= distance){
				System.out.println("Ending state, becoming STOPPED");
				currentState = STOPPED;
				distance = 0;
			}
		}
		
		//Stop stopper
		if((distance <= 0 && time <= 0) && currentState != STOPPED)
			currentState = STOPPED;
		
		final double maxSpeed = Dashboard.getInstance().getMaxSpeed();
		
		//Do things
		System.out.println("Acting on state: "+currentState);
		switch(currentState){
		case FORWARD:
			Drive.getInstance().set(-maxSpeed, -1);
			break;
		case BACKWARD:
			Drive.getInstance().set(maxSpeed, maxSpeed);
			break;
		case TURN_LEFT:
			Drive.getInstance().set(-maxSpeed, maxSpeed);
			break;
		case TURN_RIGHT:
			Drive.getInstance().set(maxSpeed, -maxSpeed);
			break;
		default:
			Drive.getInstance().stop();
			currentState = STOPPED;
		}
		
		SmartDashboard.putNumber("AutoState", currentState);
		SmartDashboard.putNumber("AutoTime", time);
	}
	
	public int getState()
	{
		return currentState;
	}
	
	public void changeState(int state)
	{
		if(state > TURN_RIGHT || state < STOPPED)
			state = STOPPED;
		currentState = state;
		distance = 0;
		time = 0;
	}
	
	//allows for complete setting of current robot state.
	//set "time" boolean to true for time-based state
	public void changeState(int state, double units, boolean time)
	{
		System.out.println("New state: "+state+" "+units+" units length, time = "+time);
		changeState(state);
		if(time)
			changeTime(units);
		else
			changeDistance(units);
	}
	
	//Input distance is in inches, according to our encoder settings
	public void changeDistance(double dist)
	{
		if(dist < 0)
			return;
		distance = dist;
		Drive.getInstance().reset();
	}
	
	//Time is in seconds.
	public void changeTime(double t){
		System.out.println("Changing time to: "+t);
		if(t < 0)
			return;
		time = t;
		startTime = Timer.getFPGATimestamp();
	}
}