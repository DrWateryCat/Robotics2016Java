
package org.usfirst.frc.team2186.robot;

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
    public void robotInit() {

    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	d.setLeft(1);
    	d.setRight(-1);
    	Timer.delay(5);
    	d.shift(1);
    	d.setLeft(0.5);
    	d.setRight(-0.5);
    	Timer.delay(5);
    	d.setLeft(0);
    	d.setRight(0);
    	d.shift(0);
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        d.setLeft(j.getAxis(Joystick.AxisType.kY));
        d.setRight(j.getRawAxis(3));
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
