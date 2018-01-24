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

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;


public class Robot_Intake extends Subsystem {
	private final int X_AXIS = 0;
	private final int RIGHT_TRIGGER = 2;
	private final int LEFT_TRIGGER = 3;
	
	private final double UNITS_PER_ROTATION = 4096;
	
	private TalonSRX intakeRight;
	private TalonSRX intakeLeft;
	private TalonSRX intakeHinge;
	private Joystick stick1;
	private Encoder intakeHingeEncoder;
	
	private double count;
	private double angle;
	private double xAxisValue;
	private double rightTriggerValue;
	private double leftTriggerValue;
	private double triggerDifferenceValue;
	private double hingeOutputValue;
	private double rightOutputValue;
	private double leftOutputValue;
	
	
	public Robot_Intake() {
		intakeRight = RobotMap.intakeRight;
		intakeLeft = RobotMap.intakeRight;
		stick1 = Robot.oi.Stick1;
	}
	
	public void manipulate() {
		// read stick x-axis value
		xAxisValue = stick1.getRawAxis(X_AXIS);
		// read right trigger value
		rightTriggerValue = stick1.getRawAxis(RIGHT_TRIGGER);
		// read left trigger value
		leftTriggerValue = stick1.getRawAxis(LEFT_TRIGGER);
		
		// left trigger value is subtracted from right trigger value to get difference
		triggerDifferenceValue = rightTriggerValue - leftTriggerValue;
		
		// if trigger difference value is less than 0.0
		if (triggerDifferenceValue < 0.0) {
			// set -1.0 as output value
			rightOutputValue = -1.0;
			leftOutputValue = -1.0;
		}
		// else, if it is greater than 0.0
		else if (triggerDifferenceValue > 0.0) {
			// map value to target range of 0.6 to 1.0 and set as output value
			rightOutputValue = map(triggerDifferenceValue, 0.0, 1.0, 0.6, 1.0);
			leftOutputValue = map(triggerDifferenceValue, 0.0, 1.0, 0.6, 1.0);
		}
		// else, if it is equal to 0.0
		else {
			// set 0.0 as output value
			rightOutputValue = 0.0;
			leftOutputValue = 0.0;
		}
		
		// set right and left intake motors their respective output value
		intakeRight.set(ControlMode.PercentOutput, rightOutputValue);
		intakeLeft.set(ControlMode.PercentOutput, leftOutputValue);
	}
	
	public void tilt() {
		// get encoder current count
		count = intakeHingeEncoder.get();
		
		// convert count to degrees
		angle = count / UNITS_PER_ROTATION * 360;
		
		// if angle is equal to 45 degrees
		if (angle == 45) {
			// only read negative values
			if (xAxisValue < 0.0) {
				// set value as output value
				hingeOutputValue = xAxisValue;
			}
			// else, set 0.0 as output value
			else {
				hingeOutputValue = 0.0;
			}
		}
		// else, if angle is equal to 0 degrees
		else if (angle == 0) {
			// only read positive values
			if (xAxisValue > 0.0) {
				// set value as output value
				hingeOutputValue = xAxisValue;
			}
			// else, set 0.0 as output value
			else {
				hingeOutputValue = 0.0;
			}
		}
		// else, if angle is in between 0 to 45 degrees range
		else {
			// read value and set as output value
			hingeOutputValue = xAxisValue;
		}
		
		// map output value to a target range of half the length of initial range
		hingeOutputValue = map(hingeOutputValue, 0.0, 1.0, 0.0, 0.5);
		
		// set hinge motor its respective value
		intakeHinge.set(ControlMode.PercentOutput, hingeOutputValue);
	}
	
	
	public void initDefaultCommand() {
		//nada
	}
	
	////para poner todo en la posicion inicial/////
	public void InitDefaultState() {
		
	}
	
	double map(double x, double in_min, double in_max, double out_min, double out_max)
	{
	  return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
	}
	
	//////////////////////////////////////////////
}
