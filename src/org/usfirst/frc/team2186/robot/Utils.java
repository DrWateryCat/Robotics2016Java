package org.usfirst.frc.team2186.robot;

public class Utils {
	public static double clamp(double in, double min, double max){
		return Math.max(min, Math.min(max,  in));
	}
	
	public static double deadzone(double in){
		double res = clamp(in, -1, 1);
		if(Math.abs(res) < 0.1) {
			return 0;
		}
		return res;
	}
}