/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3478.robot.subsystems;

import org.usfirst.frc.team3478.robot.Robot;
import org.usfirst.frc.team3478.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;


public class Robot_Intake extends Subsystem {
	public static final int LEFT_TRIGGER = 2;
	public static final int RIGHT_TRIGGER = 3;
	
	TalonSRX lowerIntakeLeft;
	TalonSRX lowerIntakeRight; 
	TalonSRX upperIntakeLeft;
	TalonSRX upperIntakeRight;
	
	double leftTriggerValue;
	double rightTriggerValue;
	double leftOutputValue;
	double rightOutputValue;
	
	public Robot_Intake() {
		lowerIntakeLeft = RobotMap.lowerIntakeLeft;
		lowerIntakeRight = RobotMap.lowerIntakeRight;
		upperIntakeLeft = RobotMap.upperIntakeLeft;
		upperIntakeRight = RobotMap.upperIntakeRight;
	}
	
	public void initDefaultCommand() {
		//nada
	}
	
	////para poner todo en la posicion inicial/////
	public void InitDefaultState() {
		
	}
	//////////////////////////////////////////////
	
	public void pickUp() {
		Joystick joystick = Robot.oi.Stick2;
		leftTriggerValue = joystick.getRawAxis(LEFT_TRIGGER);
		rightTriggerValue = joystick.getRawAxis(RIGHT_TRIGGER);
		
		
		
		if (rightTriggerValue - leftTriggerValue > 0.8) {
			leftOutputValue = 1.0;
			rightOutputValue = 1.0;
		}
		else if (rightTriggerValue - leftTriggerValue < -0.8) {
			leftOutputValue = -1.0;
			rightOutputValue = -1.0;
		}
		else {
			leftOutputValue = 0.0;
			rightOutputValue = 0.0;
		}
		
		lowerIntakeLeft.set(ControlMode.PercentOutput,-leftOutputValue);
		lowerIntakeRight.set(ControlMode.PercentOutput,-leftOutputValue);
		upperIntakeLeft.set(ControlMode.PercentOutput,-rightOutputValue);
		upperIntakeRight.set(ControlMode.PercentOutput,rightOutputValue);
	}
	
	public void stop() {
		lowerIntakeLeft.set(ControlMode.PercentOutput, 0.0);
		lowerIntakeRight.set(ControlMode.PercentOutput, 0.0);
		upperIntakeLeft.set(ControlMode.PercentOutput, 0.0);
		upperIntakeRight.set(ControlMode.PercentOutput, 0.0);
		upperIntakeLeft.set(ControlMode.PercentOutput, 0.0);
		upperIntakeRight.set(ControlMode.PercentOutput, 0.0);
	}
}
