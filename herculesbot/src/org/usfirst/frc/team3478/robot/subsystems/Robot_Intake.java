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

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot_Intake extends Subsystem {
	
	private static int direction = 1;
	private static final double BOX_VELOCITY=1;
	private static final double STICK_LIMIT=0.8;
	private static final double TOLERANCE=0.15;  //tolerancia del joystick

	private TalonSRX intakeRight;
	private TalonSRX intakeLeft;
	private DigitalInput boxIn;
	
	public void initDefaultCommand() {
		//nada
	}
	
	////para poner todo en la posicion inicial/////
	public void InitDefaultState() {
		Stop_Intake();
	}
	//////////////////////////////////////////////
	
	////////////constructor de la clase///////////////////////////////
	public Robot_Intake() {
		intakeRight = RobotMap.intakeRight;
		intakeLeft = RobotMap.intakeLeft;
		boxIn = RobotMap.boxIn;		      
	}
	/////////////////////////////////////////////////////////////////
	
	//////////movimiento principal del intake/////////////////////
	public void Main_Move_Intake() {
		Joystick stick2 = Robot.oi.Stick2; // crea objeto del joystick 2
	
		double powerrotate = mapDoubleT(stick2.getRawAxis(0),TOLERANCE,1,0,1)*direction,
		powerfinal = -1 * mapDoubleT(stick2.getRawAxis(1),TOLERANCE,1,0,1)*direction, //mapea el valor del eje y da direccion
		rightOutputValue=0,leftOutputValue=0;
		
		if(powerfinal > STICK_LIMIT) {
			rightOutputValue=-BOX_VELOCITY;
			leftOutputValue=-BOX_VELOCITY;
		}else if(powerfinal < -STICK_LIMIT) {
			if(boxIn.get()) {
			rightOutputValue=BOX_VELOCITY;
			leftOutputValue=BOX_VELOCITY;
			}
		}else if(powerrotate > STICK_LIMIT) {
			if(boxIn.get()) {
			rightOutputValue=-BOX_VELOCITY*0.5;
			leftOutputValue=BOX_VELOCITY*0.5;
			}
		}else if(powerrotate < -STICK_LIMIT) {
			if(boxIn.get()) {
			rightOutputValue=BOX_VELOCITY*0.5;
			leftOutputValue=-BOX_VELOCITY*0.5;
			}
		}
		intakeRight.set(ControlMode.PercentOutput, rightOutputValue);
		intakeLeft.set(ControlMode.PercentOutput, leftOutputValue);
		SmartDashboard.putBoolean("Sensor Caja", !boxIn.get());
	}
	///////////////////////////////////////////////////////////////
	
	
	//////////////para parar motores del intake/////////////
	public void Stop_Intake() {
		intakeRight.set(ControlMode.PercentOutput, 0.0);
		intakeLeft.set(ControlMode.PercentOutput, 0.0);
	}
	////////////////////////////////////////////////////////
	
	/// Para mapear un numero de un rango a otro rango //////////////////////
	double map(double x, double in_min, double in_max, double out_min, double out_max)
	{
	  return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
	}
	
	private double mapDoubleT(double x, double in_min, double in_max, double out_min, double out_max)
	{
		if(Math.abs(x)<TOLERANCE) {
			return 0;
		}
		if(x<0){
			return map(x,-in_min,-in_max,-out_min,-out_max);
		}
		return map(x,in_min,in_max,out_min,out_max);
	}
	///////////////////////////////////////////////////////////////////////////////////
}
