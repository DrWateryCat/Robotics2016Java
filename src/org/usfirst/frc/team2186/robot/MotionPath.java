package org.usfirst.frc.team2186.robot;

import java.util.ArrayList;
import java.util.Scanner;

import org.usfirst.frc.team2186.robot.motionpaths.*;

import edu.wpi.first.wpilibj.Timer;

public class MotionPath {
	Scanner in;
	double DEFAULT_SPEED = 0.75, TURN_SPEED = 0.5;
	boolean passed = false;
	Drive driveTrain = Drive.getInstance();
	
	private boolean started = false;
	
	private String path = "stop;";
	
	private ArrayList<PathComponent> commands;
	
	public MotionPath(String instruct) {
		commands = new ArrayList<PathComponent>();
		
		if(instruct != null && !instruct.equals("")) {
			String[] ss = instruct.split(";");
			System.out.println("Length of commands: " + ss.length);
			for(String line : ss) {
				System.out.println(line);
				String[] cmd = new String[line.length()];
				int i = 0;
				for(String word : line.split(" ")) {
					System.out.println("Word: " + word);
					if(!word.equals("") && word != null) {
						System.out.println("Isn't empty!");
						cmd[i] = word;
						i++;
					}
				}
				switch(cmd[0]) {
				case "forward":
					commands.add(new MoveForward(Double.parseDouble(cmd[1]), cmd[2], 0.5));
					break;
				case "backward":
					commands.add(new MoveBackward(Double.parseDouble(cmd[1]), cmd[2], 0.5));
					break;
				case "turn":
					commands.add(new Turn(Double.parseDouble(cmd[2]), cmd[3], cmd[1], TURN_SPEED));
					break;
				case "unload":
					commands.add(new Unload());
					break;
				case "stop":
				default:
					commands.add(new Stop());
				}
				
				//commands.add(line.split(" "));
			}
		}
	}
	
	
	public void start() {
		if (commands.size() == 0) {
			Drive.getInstance().set(0, 0);
			Intake.getInstance().setRollers(0);
		} else {
			commands.get(0).init();
		}
	}
	
	public void update() {
		if (!started) {
			start();
			started = true;
			System.out.println("Started");
		} else {
			if (commands.size() == 0) {
				Drive.getInstance().set(0, 0);
				Intake.getInstance().setRollers(0);
				System.out.println("Out of commands");
			} else {
				PathComponent command = commands.get(0);
				command.update();
				if (command.isFinished()) {
					System.out.println("Command finished");
					Drive.getInstance().set(0, 0);
					Intake.getInstance().setRollers(0);
					commands.remove(0);
					this.start();
				}
			}
		}
	}
}
