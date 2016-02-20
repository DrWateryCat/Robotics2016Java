package org.usfirst.frc.team2186.robot;

import java.util.Scanner;

import edu.wpi.first.wpilibj.Timer;

public class MotionPath {
	Scanner in;
	double DEFAULT_SPEED = 0.75, TURN_SPEED = 0.5;
	boolean passed = false;
	Drive driveTrain = Drive.getInstance();
	public MotionPath(String file) {
		in = new Scanner(getClass().getResourceAsStream(file));
	}
	/**
	 * Call this in a loop, because this does not have a loop function embedded
	 * Or better yet, don't. Just slap it in the autonomous update and it should be fine
	 */
	public void interpret() {
		String c = in.next();
		if(!passed) {
			switch(c) {
			case "forward":
				moveForward(in.nextInt(), in.next(), DEFAULT_SPEED);
				break;
			case "shift":
				driveTrain.shift(in.nextInt());
				break;
			case "reverse":
				moveBackward(in.nextInt(), in.next(), DEFAULT_SPEED);
				break;
			case "turn":
				if (in.next().equals("left")) turnLeft(in.nextInt(), in.next(), TURN_SPEED);
				else turnRight(in.nextInt(), in.next(), TURN_SPEED);
				break;
			default:    //stop. Can be anything (but should probably say "stop" for clarity)
				driveTrain.stop();
				passed = true;
			}
		}
	}
	
	//Begin separate methods for movement. Figured these just might come in handy --gamrguy
	public void moveForward(int time, String unit, double speed)
	{
		driveTrain.set(speed, speed);
		if(unit.equals("sec"))
			Timer.delay(time);
		//else --TODO: INCHES
		driveTrain.stop();
	}
	
	public void moveBackward(int time, String unit, double speed)
	{
		driveTrain.set(-speed, -speed);
		if(unit.equals("sec"))
			Timer.delay(time);
		//else --TODO: INCHES
		driveTrain.stop();
	}
	
	public void turnRight(int time, String unit, double speed)
	{
		driveTrain.set(speed, -speed);
		if(unit.equals("sec"))
			Timer.delay(time);
		//else --TODO: DEGREES
		driveTrain.stop();
	}
	
	public void turnLeft(int time, String unit, double speed)
	{
		driveTrain.set(-speed, speed);
		if(unit.equals("sec"))
			Timer.delay(time);
		//else --TODO: DEGREES
		driveTrain.stop();
	}
}
