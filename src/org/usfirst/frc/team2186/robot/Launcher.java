package org.usfirst.frc.team2186.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;

public class Launcher {
	private VictorSP m_launcher;
	private DoubleSolenoid m_piston;
	
	public boolean m_revved = false;// wheels aren't revved default
	private DoubleSolenoid.Value m_state;
	
	private static Launcher _instance;
	public static Launcher getInstance() {
		if(_instance == null) {
			_instance = new Launcher();
		}
		
		return _instance;
	}
	
	private Launcher() {
		m_launcher = new VictorSP(RobotMap.Launcher.WHEELS);
		m_piston = new DoubleSolenoid(RobotMap.Launcher.PISTON[0], RobotMap.Launcher.PISTON[1]);
		m_state = DoubleSolenoid.Value.kOff;
	}
	
	public void start() {
		m_piston.set(Value.kForward);
	}
	
	public void update() {
		if(SmartDashboard.getBoolean("Rev")) {
			m_launcher.set(-1.0);
			Timer.delay(1.5); //rev up time for launcher wheels
			m_revved = true;
		} else {
			m_launcher.set(0);
			m_revved = false; //launcher wheels aren't revved or aren't at full spin
		}
	}
	
	public void launch() {
		if(m_revved) {
			m_piston.set(Value.kReverse);
			
		    Intake.getInstance().set(1); //start intake to push ball past piston
			Timer.delay(2);
			Intake.getInstance().set(0); //stop intake
			m_piston.set(Value.kForward);
			Timer.delay(2);
		}
	}
}
