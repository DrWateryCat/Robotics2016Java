package org.usfirst.frc.team2186.robot;

import java.util.Scanner;

import edu.wpi.first.wpilibj.Timer;

public class MotionPath {
	Scanner in;
	boolean passed = false;
	public MotionPath(String file) {
		in = new Scanner(getClass().getResourceAsStream(file));
	}
	
	public void interpret() {
		String c = in.next();
		if(!passed) {
			switch(c) {
			case "s":
				passed = false;
				break;
			case "f":
				Drive.getInstance().set(0.75, 0.75);
				Timer.delay(in.nextInt());
				Drive.getInstance().set(0, 0);
				break;
			case "h":
				Drive.getInstance().shift(in.nextInt());
				break;
			}
		}
	}
}
