package org.usfirst.frc.team2186.robot;

import java.util.ArrayList;
import java.util.Scanner;
import edu.wpi.first.wpilibj.Timer;

public class MotionPath {
	Scanner in;
	double DEFAULT_SPEED = 0.75, TURN_SPEED = 0.5;
	boolean passed = false;
	Drive driveTrain = Drive.getInstance();
	
	private String path = "stop;";
	
	private ArrayList<String[]> commands;
	
	public MotionPath(String instruct) {
		if(instruct != null && !instruct.equals("")) {
			for(String s : instruct.split(";")) {
				if(!s.equals(null))
					commands.add(s.split(" "));
			}
		}
	}
	
	public MotionPath() {
		in = new Scanner(path);
	}
	/**
	 * Call this in a loop, because this does not have a loop function embedded
	 * Or better yet, don't. Just slap it in the autonomous update and it should be fine
	 */
	public void interpret() {
		if(commands.size() == 0)
			return;
		
		String[] cmd = commands.remove(0);
		String c = cmd[0].toLowerCase();
		String extra = "";
		
		double dist = 0;
		if(cmd.length > 1 && c != "shift")
			dist = Double.parseDouble(cmd[1]);
		if(cmd.length > 2 && c != "shift")
			extra = cmd[2].toLowerCase();
		else extra = cmd[1];
		
		if(extra == "ft")
			if(c.equals("forward") || c.equals("reverse"))
				dist *= 12;
		
		switch(c) {
		case "forward":
			if(cmd.length >= 2)
				this.moveForward(dist, cmd[3], DEFAULT_SPEED);
			break;
		case "reverse":
			if(cmd.length >= 2)
				this.moveBackward(dist, cmd[3], DEFAULT_SPEED);
			break;
		case "turn":
			if(cmd.length >= 3 && extra.equals("left"))
				this.turnLeft(-dist, cmd[3], TURN_SPEED);
			else
				this.turnRight(dist, cmd[3], TURN_SPEED);
			break;
		case "unload":
			Intake.getInstance().setRollers(-1);
			Timer.delay(5);
			Intake.getInstance().setRollers(0);
			break;
		case "shift":
			if(cmd.length >= 2) {
				if(extra == "up")
					Drive.getInstance().shift(1);
				else
					Drive.getInstance().shift(0);
			}
			break;
		default:
			Drive.getInstance().stop();
		}
	}
	
	public void moveForward(double dist, String unit, double speed)
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
	
	public void moveBackward(double dist, String unit, double speed)
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
	
	public void turnRight(double dist, String unit, double speed)
	{
		if(unit.equals("sec")){
			driveTrain.set(speed, -speed);
			Timer.delay(dist);
			driveTrain.stop();}
		else
			driveTrain.turnAngle(speed, dist);
	}
	
	public void turnLeft(double dist, String unit, double speed)
	{
		if(unit.equals("sec")){
			driveTrain.set(-speed, speed);
			Timer.delay(dist);
			driveTrain.stop();}
		else
			driveTrain.turnAngle(speed, -dist);
	}
}
