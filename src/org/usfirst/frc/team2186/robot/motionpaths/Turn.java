package org.usfirst.frc.team2186.robot.motionpaths;

import org.usfirst.frc.team2186.robot.Drive;
import org.usfirst.frc.team2186.robot.RobotMap;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;

public class Turn extends PathComponent {
	private double angle;
	private double speed;
	private double time;
	private double startTime;
	
	private boolean timed;
	
	private String direction;
	
	
	
	public Turn(double angle, String unit, String dir, double speed) {
		if(unit.equals("deg")) {
			this.angle = angle;
			this.timed = false;
		} else if(unit.equals("sec")) {
			this.time = angle;
			this.timed = true;
		}
		
		this.speed = speed;
		this.direction = dir;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		if(timed) {
			if(direction.equals("left"))
				Drive.getInstance().set(-speed, speed);
			else
				Drive.getInstance().set(speed, -speed);
			
			this.startTime = Timer.getFPGATimestamp();
		} else {
			Drive.getInstance().resetEncoders();
			if(direction.equals("left"))
				Drive.getInstance().set(-speed, speed);
			else
				Drive.getInstance().set(speed, -speed);
		}
	}

	@Override
	public void update() {
		if(direction.equals("left")) {
			Drive.getInstance().set(-speed, speed);
		} else {
			Drive.getInstance().set(speed, -speed);
		}
	}

	@Override
	public boolean isFinished() {
		if(timed) {
			return startTime + time <= Timer.getFPGATimestamp();
		} else  {
			Encoder[] encoders = Drive.getInstance().getEncoders();
			if (direction.equals("right")) {
				return RobotMap.DriveTrain.TURNING_DEGREES_PER_PULSE*encoders[0].getRaw() < angle ||
					  RobotMap.DriveTrain.TURNING_DEGREES_PER_PULSE*encoders[1].getRaw() > angle;
			} else {
				return RobotMap.DriveTrain.TURNING_DEGREES_PER_PULSE*encoders[0].getRaw() > angle ||
					RobotMap.DriveTrain.TURNING_DEGREES_PER_PULSE*encoders[1].getRaw() < angle;
			}
		}
	}

}
