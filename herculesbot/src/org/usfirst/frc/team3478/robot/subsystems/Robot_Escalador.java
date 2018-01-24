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

public class Robot_Escalador extends Subsystem {
	
	private static final double TOLERANCE=0.15;  //tolerancia del joystick
	private static int direction = 1;
	private TalonSRX[] talons;  //arreglo para guadar los talon del chasis
	private DigitalInput[] signals; 

	public void initDefaultCommand() {
		//nada
	}
	
	////para poner todo en la posicion inicial/////
	public void InitDefaultState() {
		Stop_Escalador();
	}
	//////////////////////////////////////////////
	
	//////////constructor de la clase/////////////////////
	public Robot_Escalador(){
		talons=new TalonSRX[]{RobotMap.EscaladorMotLeft,RobotMap.EscaladorMotRight};
		signals=new DigitalInput[] {RobotMap.SwitchAbajo,RobotMap.SwitchArriba};
	}
	//////////////////////////////////////////////////////
	
	//////////main ciclo del escalador//////////////////
	public void Main_Escalador() {
		
		Joystick joystick=Robot.oi.Stick1;
		
		//lee cada eje de los joystick y les quita el error y mapea
		double translationY= mapDoubleT(joystick.getRawAxis(5),TOLERANCE,1,0,1);  //arriba -1 abajo 1
		if(translationY<0 && signals[1].get()==false) {
			translationY=0;
		}else if(translationY>0 && signals[0].get()==false) {
			translationY=0;
		}
		for(TalonSRX talon:talons) {
			talon.set(ControlMode.PercentOutput, (translationY*direction));
		}
		
	}
	///////////////////////////////////////////////////
	
	//////////////para parar el motor del escalador/////////////
	public void Stop_Escalador() {
		for(TalonSRX talon:talons) {
			talon.set(ControlMode.PercentOutput, 0.0);
		}
	}
	////////////////////////////////////////////////////////
	
	
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
	
}
