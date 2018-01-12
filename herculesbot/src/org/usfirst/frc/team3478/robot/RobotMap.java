/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3478.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	
	/************* 1 start ************************/
	
	public static TalonSRX frontLeft;
	public static TalonSRX frontRight;
	public static TalonSRX backLeft;
	public static TalonSRX backRight;
	
	public static Compressor Compressor;
	public static PowerDistributionPanel pdp;
	
	
	 /************* 1 end ************************/
		
		
	/************* 2 start ************************/
	
	
	/************* 2 end ************************/
	
	
	/************* 3 start ************************/
	
	
	/************* 3 end ************************/
	
	
	/************* 4 start ************************/
	
	
	/************* 4 end ************************/
	
	
	/************* 5 start ************************/
	
	
	/************* 5 end ************************/
	
	
	/************* 6 start ************************/
	
	
	/************* 6 end ************************/
	
	
	
	public static void init() {
		
		/************* 1 start ************************/
		
		Compressor = new Compressor(0);	
		pdp = new PowerDistributionPanel();
		
		frontLeft = new TalonSRX(0);
		frontRight = new TalonSRX(1);
		backLeft = new TalonSRX(2);
		backRight = new TalonSRX(3);
		frontLeft.setNeutralMode(NeutralMode.Brake);
		frontLeft.setNeutralMode(NeutralMode.Brake);
		frontLeft.setNeutralMode(NeutralMode.Brake);
		frontLeft.setNeutralMode(NeutralMode.Brake);
		frontLeft.set(ControlMode.PercentOutput,0);
		frontRight.set(ControlMode.PercentOutput,0);
		backLeft.set(ControlMode.PercentOutput,0);
		backRight.set(ControlMode.PercentOutput,0);
		
		/************* 1 end ************************/
			
			
		/************* 2 start ************************/
		
		
		/************* 2 end ************************/
		
		
		/************* 3 start ************************/
		
		
		/************* 3 end ************************/
		
		
		/************* 4 start ************************/
		
		
		/************* 4 end ************************/
		
		
		/************* 5 start ************************/
		
		
		/************* 5 end ************************/
		
		
		/************* 6 start ************************/
		
		
		/************* 6 end ************************/
		
		
		
	}
	
	
}
