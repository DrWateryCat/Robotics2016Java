package org.usfirst.frc.team2186.robot;

import java.util.Scanner;
import edu.wpi.first.wpilibj.Timer;

public class MotionPath {
	Scanner in;
	double DEFAULT_SPEED = 0.75, TURN_SPEED = 0.5;
	boolean passed = false;
	Drive driveTrain = Drive.getInstance();
	
	private String path = "stop";
	
	public MotionPath(String instruct) {
		in = new Scanner(instruct);
	}
	
	public MotionPath() {
		in = new Scanner(path);
	}
	/**
	 * Call this in a loop, because this does not have a loop function embedded
	 * Or better yet, don't. Just slap it in the autonomous update and it should be fine
	 */
	public void interpret() {
		if(in.hasNext()) {
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
					if (in.next().equals("left")) 
						turnLeft(in.nextInt(), in.next(), TURN_SPEED);
					else 
						turnRight(in.nextInt(), in.next(), TURN_SPEED);
					break;
				case "unload":
					Intake.getInstance().setRollers(1);
					Timer.delay(5);
					Intake.getInstance().setRollers(0);
					break;
				default:    //stop. Can be anything (but should probably say "stop" for clarity)
					driveTrain.stop();
					passed = true;
				}
			}
		}
	}
	
	public void moveForward(int dist, String unit, double speed)
	{
		if(unit.equals("ft"))
			dist *= 12;
		if(unit.equals("sec")){
			driveTrain.set(speed, speed);
			Timer.delay(dist);
			driveTrain.stop();}
		else
			driveTrain.goDistance(dist, speed);
	}
	
	public void moveBackward(int dist, String unit, double speed)
	{
		if(unit.equals("ft"))
			dist *= 12;
		if(unit.equals("sec")){
			driveTrain.set(-speed, -speed);
			Timer.delay(dist);
			driveTrain.stop();}
		else
			driveTrain.goDistance(-dist, speed);
	}
	
	public void turnRight(int dist, String unit, double speed)
	{
		if(unit.equals("sec")){
			driveTrain.set(speed, -speed);
			Timer.delay(dist);
			driveTrain.stop();}
		else
			driveTrain.turnAngle(speed, dist);
	}
	
	public void turnLeft(int dist, String unit, double speed)
	{
		if(unit.equals("sec")){
			driveTrain.set(-speed, speed);
			Timer.delay(dist);
			driveTrain.stop();}
		else
			driveTrain.turnAngle(speed, -dist);
	}
}
