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
	public static int LEFT_TRIGGER = 0;
	public static int RIGHT_TRIGGER = 1;
	
	Joystick joystick;
	TalonSRX lowerIntakeLeft;
	TalonSRX lowerIntakeRight; 
	TalonSRX upperIntakeLeft;
	TalonSRX upperIntakeRight;
	
	double leftTriggerValue;
	double rightTriggerValue;
	
	public Robot_Intake() {
		joystick = Robot.oi.Stick2;
		lowerIntakeLeft = RobotMap.lowerIntakeLeft;
		lowerIntakeRight = RobotMap.lowerIntakeRight;
		upperIntakeLeft = RobotMap.upperIntakeLeft;
		upperIntakeRight = RobotMap.lowerIntakeRight;
		
	}
	public void initDefaultCommand() {
		//nada
	}
	
	////para poner todo en la posicion inicial/////
	public void InitDefaultState() {
		
	}
	//////////////////////////////////////////////
	
	public void pickUp() {
		leftTriggerValue = joystick.getRawAxis(LEFT_TRIGGER);
		
		lowerIntakeLeft.set(ControlMode.PercentOutput,leftTriggerValue);
		lowerIntakeRight.set(ControlMode.PercentOutput,-leftTriggerValue);
		upperIntakeLeft.set(ControlMode.PercentOutput,leftTriggerValue);
		upperIntakeRight.set(ControlMode.PercentOutput,-leftTriggerValue);
	}
	
	public void drop() {
		rightTriggerValue = joystick.getRawAxis(RIGHT_TRIGGER);
		
		upperIntakeLeft.set(ControlMode.PercentOutput,rightTriggerValue);
		upperIntakeRight.set(ControlMode.PercentOutput,-rightTriggerValue);
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
