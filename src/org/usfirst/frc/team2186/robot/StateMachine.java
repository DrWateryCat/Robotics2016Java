package org.usfirst.frc.team2186.robot;

import edu.wpi.first.wpilibj.Timer;

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
	private double prevTime;
	
	public StateMachine()
	{
		currentState = STOPPED;
		distance = 0;
		time = 0;
		prevTime = 0;
	}
	
	public void update()
	{
		//Essentially creates a countdown
		if(time > 0){
			time -= Timer.getMatchTime() - prevTime;
			prevTime = Timer.getMatchTime();
		}
		
		//Distance countdown!
		if(distance > 0) {
			double avgDist = (Drive.getInstance().m_left.m_encoder.getDistance()+
							  Drive.getInstance().m_right.m_encoder.getDistance())/2;
			distance -= avgDist;
			Drive.getInstance().reset();
		}
		
		//Stop from going too far
		if((distance <= 0 || time <= 0) && currentState != STOPPED)
			currentState = STOPPED;
		
		//Actions!
		switch(currentState){
		case FORWARD:
			Drive.getInstance().set(Drive.MAX_SPEED, Drive.MAX_SPEED);
			break;
		case BACKWARD:
			Drive.getInstance().set(-Drive.MAX_SPEED, -Drive.MAX_SPEED);
			break;
		case TURN_LEFT:
			Drive.getInstance().set(-Drive.MAX_SPEED, Drive.MAX_SPEED);
			break;
		case TURN_RIGHT:
			Drive.getInstance().set(Drive.MAX_SPEED, -Drive.MAX_SPEED);
			break;
		default:
			Drive.getInstance().stop();
			currentState = STOPPED;
		}
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
	}
	
	//Time is in seconds.
	public void changeTime(double t){
		if(t < 0)
			return;
		time = t;
	}
}