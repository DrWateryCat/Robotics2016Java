package org.usfirst.frc.team2186.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;

public class Intake {

	private VictorSP m_left; //move backwards to bring down intake
	private VictorSP m_right; // move forwards to bring down intake
	private VictorSP m_wheels; //move forwards to bring in ball
	
	private static Intake _instance;
	public static Intake getInstance() {
		if(_instance == null) {
			_instance = new Intake();
		}
		
		return _instance;
	}
	
	private Intake() {
		m_left = new VictorSP(RobotMap.Intake.LEFT_MOTOR);
		m_right = new VictorSP(RobotMap.Intake.RIGHT_MOTOR);
		
		m_wheels = new VictorSP(RobotMap.Intake.WHEELS);
	}
	
	public void start(){
		//stuff
	}
	
	public void set(int input){
		m_wheels.set(input);		
	}
	
	
}
