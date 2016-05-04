package org.usfirst.frc.team2186.robot.Autonomous;

import java.io.FileWriter;
import java.io.IOException;

import org.usfirst.frc.team2186.robot.Drive;
import org.usfirst.frc.team2186.robot.Intake;

import edu.wpi.first.wpilibj.Timer;

public class MacroRecord {
	FileWriter m_writer;
	
	double startTime;
	
	public MacroRecord(String autoFile) throws IOException {
		startTime = Timer.getFPGATimestamp();
		m_writer = new FileWriter(autoFile);
	}
	
	public void record(Drive drive) throws IOException {
		if (m_writer != null) {
			StringBuilder sb = new StringBuilder();
			sb.append("" + (Timer.getFPGATimestamp() - startTime));
			sb.append("," + drive.getLeft());
			sb.append("," + drive.getRight());
			sb.append("," + Intake.getInstance().getRollers() + "\n");
			m_writer.append(sb.toString());
		}
	} 
	
	public void end() throws IOException {
		if(m_writer != null) {
			m_writer.flush();
			m_writer.close();
		}
	}
}