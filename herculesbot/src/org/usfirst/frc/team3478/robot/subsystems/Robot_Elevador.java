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

public class Robot_Elevador extends Subsystem {
	public static int RIGHT_STICK = 1;
	
	TalonSRX elevator;
	
	double rightStickValue;
	
	public Robot_Elevador() {
		elevator = RobotMap.elevator;
	}
	
	public void initDefaultCommand() {
		//nada
	}
	
	////para poner todo en la posicion inicial/////
	public void InitDefaultState() {
		
	}
	//////////////////////////////////////////////
	
	public void lift() {
		Joystick joystick = Robot.oi.Stick2;
		rightStickValue = joystick.getRawAxis(RIGHT_STICK);
		
		elevator.set(ControlMode.PercentOutput, rightStickValue * 0.5);
	}
	
	public void stop(){
		Joystick joystick = Robot.oi.Stick2;
		elevator.set(ControlMode.PercentOutput, 0.0);
	}
}
