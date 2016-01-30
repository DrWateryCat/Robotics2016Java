package org.usfirst.frc.team2186.robot;

public class Drive {
	private static Drive _instance = null;
	public static Drive getInstance() {
		if(_instance == null) {
			_instance = new Drive();
		}
		return _instance;
	}
	
	ShiftingSpeedController m_left;
	ShiftingSpeedController m_right;
	private Drive() {
		m_left = new ShiftingSpeedController(0, 1, new int[]{0, 1, 2});
		m_right = new ShiftingSpeedController(2, 3, new int[]{3, 4, 5});
	}
	
	public void setRight(double val) {
		m_right.set(val);
	}
	
	public void setLeft(double val) {
		m_left.set(val);
	}
	
	public void shiftLeft(int gear) {
		m_left.shift(gear);
	}
	
	public void shiftRight(int gear) {
		m_right.shift(gear);
	}
	
	public void shift(int gear) {
		shiftLeft(gear);
		shiftRight(gear);
	}
	
	public void set(double left, double right) {
		setLeft(left);
		setRight(right);
	}
}
