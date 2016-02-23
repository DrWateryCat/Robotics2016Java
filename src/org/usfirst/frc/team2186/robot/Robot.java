
package org.usfirst.frc.team2186.robot;

import org.usfirst.frc.team2186.robot.RobotMap.Controller;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	Joystick driver = new Joystick(1);
	Compressor c;
	MotionPath autonomous;
	
	Intake i = Intake.getInstance();
	
	DigitalOutput ledRing;
	
    public void robotInit() {
    	c = new Compressor();
    	c.start();
    	
    	autonomous = new MotionPath(SmartDashboard.getString("AutoCode", "stop"));
    	
    	SmartDashboard.putNumber("DriveType", 1);
    	SmartDashboard.putBoolean("Rev", false);
    	ledRing = new DigitalOutput(5);
    }
    
    public void autonomousInit() {
    	//Intake.getInstance().start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	autonomous.interpret();
    }
    
    public void teleopInit() {
    	d.shift(0);
    }

    /**
     * This function is called periodically during operator control
     */
    int shift = 0;
    public void teleopPeriodic() {
    	ledRing.set(true);
    	
    	//Drive controls
    	d.teleop(j);
    	if(driver.getRawButton(3))
    		SmartDashboard.putNumber("DriveType", 1);
    	else
    		SmartDashboard.putNumber("DriveType", 0);
    	
    	//Gear shift controls
    	if(j.getRawButton(RobotMap.Controller.TRIANGLE))
    		d.shift(1);
    	else if(j.getRawButton(RobotMap.Controller.X_BUTTON))
    		d.shift(0);
    	
    	if(driver.getRawButton(Controller.SQUARE))     //change button later
    		SmartDashboard.putBoolean("Rev", true);
    	else
    		SmartDashboard.putBoolean("Rev", false);
    	
    	//Intake controls
    	if(j.getRawButton(Controller.CIRCLE))
    		i.moveIntake();
    	else if (j.getRawButton(Controller.SQUARE))
    		i.reverseIntake();
    	else
    		i.stopIntake();
    	
    	if(j.getRawButton(Controller.RIGHT_TOP_SHOULDER))
    		i.setRollers(1);
    	else if(j.getRawButton(Controller.RIGHT_BOTTOM_SHOULDER))
    		i.setRollers(-1);
    	else
    		i.setRollers(0);
    }
    
    public void disabledInit() {
    	i.setRollers(0);
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
