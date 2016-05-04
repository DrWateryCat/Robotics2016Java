package org.usfirst.frc.team2186.robot.Autonomous;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.usfirst.frc.team2186.robot.Drive;
import org.usfirst.frc.team2186.robot.Intake;

import edu.wpi.first.wpilibj.Timer;
public class MacroPlay {
	Scanner sc;
	
	double startTime;
	double nextDouble;
	
	boolean onTime = true;
	
	public MacroPlay(String autoFile) throws FileNotFoundException {
		sc = new Scanner(new File(autoFile));
		sc.useDelimiter(",|\\n");
		startTime = Timer.getFPGATimestamp();
	}
	
	public void play(Drive drive) {
		if((sc != null) && (sc.hasNextDouble())) {
			double t_delta;
			
			if(onTime) {
				nextDouble = sc.nextDouble();
			}
			
			t_delta = nextDouble - (Timer.getFPGATimestamp() - startTime);
			
			if(t_delta <= 0) {
				drive.setLeft(sc.nextDouble());
				drive.setRight(sc.nextDouble());
				Intake.getInstance().setRollers((int)Math.round(sc.nextDouble()));
				onTime = true;
			} else {
				onTime = false;
			}
		} else {
			end(drive);
			if(sc != null){
				sc.close();
			}
		}
	}
	
	public void end(Drive drive) {
		drive.setLeft(0);
		drive.setRight(0);
	}
}