package org.usfirst.frc.team2186.robot.motionpaths;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import org.usfirst.frc.team2186.robot.Drive;

public class MoveForward extends PathComponent {
	
	private double dist;
	private double speed;
	private double time;
	private boolean timed;
	private double startTime;
	
	public MoveForward(double dist, String unit, double speed)
	{
		this.speed = speed;
		if(unit.equals("ft")) {
			this.dist = dist * 12;
			timed = false;
		}
		else if(unit.equals("in")) {
			this.dist = dist;
			timed = false;
		}
		else if(unit.equals("sec")){
			this.time = dist;
			timed = true;
		}
	}
	
	@Override
	public void init() {
		Drive.getInstance().set(-speed, -speed);
		if (timed) {
			startTime = Timer.getFPGATimestamp();
		} else {
			Drive.getInstance().resetEncoders();
		}
	}

	@Override
	public void update() {
		Drive.getInstance().set(-speed, -speed);
		System.out.println("Updating MoveForward");
	}

	@Override
	public boolean isFinished() {
		if (timed) {
			if(Timer.getFPGATimestamp() >= startTime + time) {
				System.out.println("Finished moving forward");
				return true;
			} else {
				return false;
			}
		} else {
			Encoder[] encoders = Drive.getInstance().getEncoders();
			return encoders[0].getDistance() > dist || encoders[1].getDistance() > dist;
		}
	}
}
