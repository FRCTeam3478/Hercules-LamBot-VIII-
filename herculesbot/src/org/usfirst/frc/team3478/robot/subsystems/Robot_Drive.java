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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot_Drive extends Subsystem {

	private static final double TOLERANCE=0.15;  //tolerancia del joystick
	private TalonSRX[] talons;  //arreglo para guadar los talon del chasis
	
	//////////constructor de la clase/////////////////////
	public Robot_Drive(){
		talons=new TalonSRX[]{RobotMap.frontLeft,RobotMap.frontRight,
				RobotMap.backLeft,RobotMap.backRight};
	}
	//////////////////////////////////////////////////////
	
	////para poner todo en la posicion inicial/////
	public void InitDefaultState() {
		Stop_drive();
	}
	//////////////////////////////////////////////
	
	
	////////funcion principal del drive/////////////
	public void Main_drive() {
		//lee el control 1
		Joystick joystick=Robot.oi.Stick1;
		
		//lee cada eje de los joystick y les quita el error y mapea
		double translationX=mapDoubleT(joystick.getRawAxis(0),TOLERANCE,1,0,1), 
			   translationY=mapDoubleT(joystick.getRawAxis(1),TOLERANCE,1,0,1),
			   rotationAxis=mapDoubleT(joystick.getRawAxis(4),TOLERANCE,1,0,1);
		
		//obtiene la magnbitud del vector del joystick
		double magnitude=Math.sqrt((translationX*translationX)+(translationY*translationY));
		
		// Calcula el angulo del vector
		double angle=-Math.atan2(translationX, translationY)+(Math.PI/4);
		
		// Show the translation angle on the dashboard
		SmartDashboard.putNumber("Translation Angle", angle*180/Math.PI);
		
		talons[0].set(ControlMode.PercentOutput,magnitude*
				Math.sin(angle)-rotationAxis);
		talons[1].set(ControlMode.PercentOutput,magnitude*
				Math.cos(angle)+rotationAxis);
		talons[2].set(ControlMode.PercentOutput,magnitude*
				Math.cos(angle)-rotationAxis);
		talons[3].set(ControlMode.PercentOutput,magnitude*
				Math.sin(angle)+rotationAxis);
	}

	///para mapear un numero de un rango a otro rango
	private double map(double x, double in_min, double in_max, double out_min, double out_max)
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
	
	///////////////////////////////////////////////
	
	//////funcion para detener el drive///////////
	public void Stop_drive() {
		for(TalonSRX talon:talons) {
			talon.set(ControlMode.PercentOutput, 0.0);
		}
	}
	//////////////////////////////////////////////\
	
	
	
	public void initDefaultCommand() {
		//nada
	}
	
}
