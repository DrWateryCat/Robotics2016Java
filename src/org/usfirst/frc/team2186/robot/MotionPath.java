package org.usfirst.frc.team2186.robot;

import java.util.Scanner;

import edu.wpi.first.wpilibj.Timer;

public class MotionPath {
	Scanner in;
	boolean passed = false;
	Drive driveTrain = Drive.getInstance();
	public MotionPath(String file) {
		in = new Scanner(getClass().getResourceAsStream(file));
	}
	
	public void interpret() {
		String c = in.next();
		if(!passed) {
			switch(c) {
			case "forward":
				driveTrain.set(0.75, 0.75);
				Timer.delay(in.nextInt());
				driveTrain.set(0, 0);
				break;
			case "shift":
				driveTrain.shift(in.nextInt());
				break;
			case "reverse":
				driveTrain.set(-0.75, -0.75);
				Timer.delay(in.nextInt());
				driveTrain.set(0, 0);
				break;
			case "turn":
				if (in.next().equals("left")) driveTrain.set(-0.5, 0.5);
				else driveTrain.set(0.5, -0.5);
				
				Timer.delay(in.nextInt());
				driveTrain.set(0, 0);
				break;
			default:    //stop. Can be anything (but should probably say "stop" for clarity)
				driveTrain.set(0, 0);
				passed = true;
			}
		}
	}
}
