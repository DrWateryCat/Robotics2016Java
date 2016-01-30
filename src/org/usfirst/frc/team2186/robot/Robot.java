
package org.usfirst.frc.team2186.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	Drive d = Drive.getInstance();
	Joystick j = new Joystick(0);
	Compressor c;
	MotionPath autonomous;
    public void robotInit() {
    	c = new Compressor();
    	c.start();
    	
    	autonomous = new MotionPath("motion.txt");
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	autonomous.interpret();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        d.setLeft(j.getAxis(Joystick.AxisType.kY));
        if(j.getRawButton(1) && !j.getRawButton(3)) {
        	d.shift(1);
        } else if (j.getRawButton(3) && !j.getRawButton(1)) {
        	d.shift(0);
        }
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
