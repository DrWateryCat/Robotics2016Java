package org.usfirst.frc.team2186.robot;

import edu.wpi.first.wpilibj.Timer;

public class Autonomous {
	private double startTime;
	private boolean initialized = false;
	private boolean finished = false;
	public Autonomous() {
		
	}
	
	public void init() {
		startTime = Timer.getFPGATimestamp();
		initialized = true;
	}
	
	public void update() {
		if(!finished) {
			if(!initialized) {
				init();
			}
			Drive.getInstance().set(-1, -1);
			if(Timer.getFPGATimestamp() >= startTime + 3) {
				Drive.getInstance().stop();
				finished = true;
			}
		}
	}
}
