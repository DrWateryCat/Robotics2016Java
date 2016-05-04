
package org.usfirst.frc.team2186.robot;

import edu.wpi.first.wpilibj.Compressor;
//import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException;

import java.io.IOException;

import org.usfirst.frc.team2186.robot.Autonomous.*;

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
	
	MacroPlay play;
	MacroRecord record;
	//DigitalOutput ledRing;
	
	//USBCamera cam0;
	//CameraServer cs;
	
    public void robotInit() {
    	c = new Compressor();
    	c.start();
    	
    	SmartDashboard.putNumber("Auto Number", 0);
    	SmartDashboard.putBoolean("Rev", false);
    	//ledRing = new DigitalOutput(5);
    	
    	//cam0 = new USBCamera("cam0");
    	//cs = CameraServer.getInstance();
    	//cs.setQuality(50);
    	//cs.startAutomaticCapture(cam0);
    	SmartDashboard.putBoolean("isInAuto", false);
    	
    	Dashboard.getInstance().getConfig().putString("Robot", "Craig");
    }
    
    public void autonomousInit() {
    	/*
    	System.out.println(SmartDashboard.getString("AutoCode", "stop;"));
    	
    	if((driver.getRawAxis(2)) > 0.7) {
			autonomous = new MotionPath("forward 1 sec; stop;");
		} else {
			autonomous = new MotionPath("stop;");
		}
    	stateMachine = new StateMachine();
    	*/
    	
    	try {
    		play = new MacroPlay("/home/lvuser/auto" + SmartDashboard.getInt("Auto Number", 0) + ".csv");
    	} catch(Exception e) {
    		e.printStackTrace();
    		System.out.println("You should probably record an autonomous first");
    	}
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	/*
    	if(stateMachine.getState() == StateMachine.STOPPED)
    		autonomous.interpret();
    	else
    		stateMachine.update();
    	*/
    	if(play != null) {
    		play.play(Drive.getInstance());
    	}
    	
    	SmartDashboard.putBoolean("isInAuto", true);
    }
    
    public void teleopInit() {
    //	d.shift(0);
    	SmartDashboard.putBoolean("isInAuto", false);
    	if(play != null) {
    		play.end(d);
    	}
    	
    	try {
			record = new MacroRecord("/home/lvuser/auto" + SmartDashboard.getInt("Auto Number", 0) + ".csv");
		} catch (TableKeyNotDefinedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**
     * This function is called periodically during operator controlakshay gets bitches*.
     * *(Bitches means nothing)
     */
    int shift = 0;
    boolean recording = false;
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
    	
    	if(j.getRawButton(6) || driver.getRawButton(6)) {
    		recording = !recording;
    	}
    	
    	if(recording) {
    		try {
    			if(record != null) {
    				record.record(d);
    			}
    		} catch(Exception e) {
    			e.printStackTrace();
    		}
    	} else {
    		try {
				record.end();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    
    public void disabledInit() {
    	i.setRollers(0);
    	
    	if(recording) {
    		try {
				record.end();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
