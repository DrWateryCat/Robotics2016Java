
package org.usfirst.frc.team2186.robot;

import edu.wpi.first.wpilibj.Compressor;
//import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
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
	boolean toggle = true;
	Drive d = Drive.getInstance();
	Joystick j = new Joystick(0);
	Joystick driver = new Joystick(1);
	Compressor c;
	MotionPath autonomous;
	static StateMachine stateMachine;
	
	Intake i = Intake.getInstance();
	
	//DigitalOutput ledRing;
	
	//USBCamera cam0;
	//CameraServer cs;
	
    public void robotInit() {
    	c = new Compressor();
    	c.start();
    	
    	SmartDashboard.putNumber("DriveType", 0);
    	SmartDashboard.putBoolean("Rev", false);
    	//ledRing = new DigitalOutput(5);
    	
    	//cam0 = new USBCamera("cam0");
    	//cs = CameraServer.getInstance();
    	//cs.setQuality(50);
    	//cs.startAutomaticCapture(cam0);
    	SmartDashboard.putBoolean("isInAuto", false);
    }
    
    public void autonomousInit() {
    	System.out.println(SmartDashboard.getString("AutoCode", "stop;"));
    	
    	if((driver.getRawAxis(2)) > 0.7) {
			autonomous = new MotionPath("forward 1 sec; stop;");
		} else {
			autonomous = new MotionPath("stop;");
		}
    	stateMachine = new StateMachine();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	if(stateMachine.getState() == StateMachine.STOPPED)
    		autonomous.interpret();
    	else
    		stateMachine.update();
    	
    	SmartDashboard.putBoolean("isInAuto", true);
    }
    
    public void teleopInit() {
    //	d.shift(0);
    	SmartDashboard.putBoolean("isInAuto", false);
    }

    /**
     * This function is called periodically during operator control
     */
    int shift = 0;
    public void teleopPeriodic() {
    	//ledRing.set(true);
    	
    	//Drive controls
    	d.teleop(j, driver);
    	
    	//Gear shift controls
    	if(toggle == true){
    		 if(j.getRawButton(4) && d.m_left.m_value == Value.kForward){
    			 d.shift(0);
    			 toggle = false;
    		 } else if(j.getRawButton(4) && d.m_left.m_value == Value.kReverse){
    		 		d.shift(1);
    		 		toggle = false;
    		 }
    	} else if(!j.getRawButton(4) && toggle == false)
			 toggle = true;
    	
    	//Intake controls
    	if(j.getRawButton(3) || driver.getRawButton(3))
    		i.setRollers(1);
    	else if(j.getRawButton(1) || driver.getRawButton(1))
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
