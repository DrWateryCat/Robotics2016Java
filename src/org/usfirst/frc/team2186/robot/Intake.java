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
	
	private double m_rollerSpeed;
	
	private Intake() {
		m_linear = new VictorSP(RobotMap.Intake.LINEAR);
		//m_rollers = new VictorSP(RobotMap.Intake.ROLLERS);
		
		out_limit = new DigitalInput(RobotMap.Intake.OUT_LIMIT);
	}

	public void setRollers(int state) {
		double maxSpeed = Dashboard.getInstance().getConfig().getDouble("IntakeMaxSpeed", 1);
		if(state == 1) {
			m_linear.set(maxSpeed);
			m_rollerSpeed = maxSpeed;
		}
		else if(state == -1) {
			m_linear.set(-maxSpeed);
			m_rollerSpeed = -maxSpeed;
		}
		else {
			m_linear.set(0);
			m_rollerSpeed = 0;
		}
	}
	
	public double getRollers() {
		return m_rollerSpeed;
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
