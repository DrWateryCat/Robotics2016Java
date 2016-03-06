package org.usfirst.frc.team2186.robot.motionpaths;

import org.usfirst.frc.team2186.robot.Drive;
import org.usfirst.frc.team2186.robot.Intake;

public class Stop extends PathComponent {

	@Override
	public void init() {
		// TODO Auto-generated method stub
		Drive.getInstance().stop();
		Intake.getInstance().setRollers(0);
		System.out.println("Stopping");
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		Drive.getInstance().stop();
		Intake.getInstance().setRollers(0);
	}

	@Override
	public boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
	}

}
