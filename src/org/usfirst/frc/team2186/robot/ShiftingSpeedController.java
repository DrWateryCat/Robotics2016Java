package org.usfirst.frc.team2186.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class ShiftingSpeedController {
	public static class Gear {
		public static final int DOWN = 0;
		public static final int UP = 1;
	}
	DoubleSolenoid m_piston;
	MultiSpeedController m_motorController;
	Value m_value = Value.kOff;
	
	public ShiftingSpeedController(int piston_port_a, int piston_port_b, int... motor_ports) {
		m_piston = new DoubleSolenoid(piston_port_a, piston_port_b);
		ArrayList<VictorSP> motors = new ArrayList<VictorSP>();
		for(int i = 0; i < motor_ports.length; i++) {
			motors.add(new VictorSP(motor_ports[i]));
		}
		VictorSP[] victors = motors.toArray(new VictorSP[motors.size()]);
		m_motorController = new MultiSpeedController(victors);
	}
	
	public void set(double value) {
		m_motorController.set(value);
	}
	
	public void shift(int gear) {
		switch(gear) {
		case Gear.DOWN:
			m_value = Value.kReverse;
			break;
		case Gear.UP:
			m_value = Value.kForward;
			break;
		}
		m_piston.set(m_value);
	}

}
