package org.usfirst.frc.team2186.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.VictorSP;

public class Intake {
	private static Intake _instance;
	public static Intake getInstance() {
		if(_instance == null)
			_instance = new Intake();
		
		return _instance;
	}
	
	VictorSP m_linear;
	VictorSP m_rollers;
	
	DigitalInput out_limit;
	
	private Intake() {
		m_linear = new VictorSP(RobotMap.Intake.LINEAR);
		m_rollers = new VictorSP(RobotMap.Intake.ROLLERS);
		
		out_limit = new DigitalInput(RobotMap.Intake.OUT_LIMIT);
	}

	public void setRollers(int state) {
		if(state == 1)
			m_linear.set(0.75);
		else if(state == -1)
			m_linear.set(-0.75);
		else
			m_linear.set(0);
	}
	/**
	public void moveIntake() {
		m_rollers.set(0.5);
	}
	
	public void stopIntake() {
		m_rollers.set(0);
	}
	
	public void reverseIntake() {
		m_rollers.set(-0.25);
	}
	**/
}
