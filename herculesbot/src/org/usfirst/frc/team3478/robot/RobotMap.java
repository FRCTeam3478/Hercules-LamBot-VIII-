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
	private static final int FRONT_LEFT_DEVICE_NUMBER = 0;
	private static final int FRONT_RIGHT_DEVICE_NUMBER = 1;
	private static final int BACK_LEFT_DEVICE_NUMBER = 2;
	private static final int BACK_RIGHT_DEVICE_NUMBER = 3;
	private static final int ELEVATOR_DEVICE_NUMBER = 4;
	private static final int LOWER_INTAKE_LEFT_DEVICE_NUMBER = 5;
	private static final int LOWER_INTAKE_RIGHT_DEVICE_NUMBER = 6;
	private static final int UPPER_INTAKE_LEFT_DEVICE_NUMBER = 7;
	private static final int UPPER_INTAKE_RIGHT_DEVICE_NUMBER = 8;
	
	/************* 1 start ************************/
	
	public static TalonSRX frontLeft;
	public static TalonSRX frontRight;
	public static TalonSRX backLeft;
	public static TalonSRX backRight;
	
	public static Compressor Compressor;
	public static PowerDistributionPanel pdp;
	
	
	 /************* 1 end ************************/
		
		
	/************* 2 start (David) ************************/
	public static TalonSRX elevator;
	public static TalonSRX lowerIntakeLeft;
	public static TalonSRX lowerIntakeRight;
	public static TalonSRX upperIntakeLeft;
	public static TalonSRX upperIntakeRight;
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
		
		frontLeft = new TalonSRX(FRONT_LEFT_DEVICE_NUMBER);
		frontRight = new TalonSRX(FRONT_RIGHT_DEVICE_NUMBER);
		backLeft = new TalonSRX(BACK_LEFT_DEVICE_NUMBER);
		backRight = new TalonSRX(BACK_RIGHT_DEVICE_NUMBER);
		frontLeft.setInverted(true);
		backLeft.setInverted(true);
		frontLeft.setNeutralMode(NeutralMode.Brake);
		frontRight.setNeutralMode(NeutralMode.Brake);
		backLeft.setNeutralMode(NeutralMode.Brake);
		backRight.setNeutralMode(NeutralMode.Brake);
		frontLeft.set(ControlMode.PercentOutput,0);
		frontRight.set(ControlMode.PercentOutput,0);
		backLeft.set(ControlMode.PercentOutput,0);
		backRight.set(ControlMode.PercentOutput,0);
		
		/************* 1 end ************************/
		elevator = new TalonSRX(ELEVATOR_DEVICE_NUMBER);
		lowerIntakeLeft = new TalonSRX(LOWER_INTAKE_LEFT_DEVICE_NUMBER);
		lowerIntakeRight = new TalonSRX(LOWER_INTAKE_RIGHT_DEVICE_NUMBER);
		upperIntakeLeft = new TalonSRX(UPPER_INTAKE_LEFT_DEVICE_NUMBER);
		upperIntakeRight = new TalonSRX(UPPER_INTAKE_RIGHT_DEVICE_NUMBER);	
		
		elevator.setNeutralMode(NeutralMode.Brake);
		lowerIntakeLeft.setNeutralMode(NeutralMode.Brake);
		lowerIntakeRight.setNeutralMode(NeutralMode.Brake);
		upperIntakeLeft.setNeutralMode(NeutralMode.Brake);
		upperIntakeRight.setNeutralMode(NeutralMode.Brake);
		
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
