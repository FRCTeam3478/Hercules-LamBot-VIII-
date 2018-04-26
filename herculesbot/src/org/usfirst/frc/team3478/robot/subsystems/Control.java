package org.usfirst.frc.team3478.robot.subsystems;

import org.usfirst.frc.team3478.robot.RobotMap;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Control extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	Compressor compressor = RobotMap.Compressor;


    public void initDefaultCommand() {

    }
    
    public void turnCompressorOn() {
    	compressor.setClosedLoopControl(true);
    }
    
    public void turnCompressorOff() {
    	compressor.setClosedLoopControl(false);
    }

    
    
}