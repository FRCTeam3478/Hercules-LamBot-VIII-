/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3478.robot.subsystems;

import org.usfirst.frc.team3478.robot.RobotMap;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Robot_Control extends Subsystem {
	
	
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
