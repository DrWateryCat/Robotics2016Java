package org.usfirst.frc.team2186.robot.motionpaths;

import org.usfirst.frc.team2186.robot.Intake;

import edu.wpi.first.wpilibj.Timer;

public class Unload extends PathComponent {
	private double startTime;
	private double time;
	
	public Unload() {
		time = 1;
	}

	@Override
	public void init() {
		Intake.getInstance().setRollers(-1);
		startTime = Timer.getFPGATimestamp();
	}

	@Override
	public void update() {
		Intake.getInstance().setRollers(-1);
	}

	@Override
	public boolean isFinished() {
		// TODO Auto-generated method stub
		return (startTime + time <= Timer.getFPGATimestamp());
	}

}
