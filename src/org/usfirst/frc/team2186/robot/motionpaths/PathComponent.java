package org.usfirst.frc.team2186.robot.motionpaths;

public abstract class PathComponent {
	public abstract void init();
	public abstract void update();
	public abstract boolean isFinished();
}
