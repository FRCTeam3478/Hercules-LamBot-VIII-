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
	private static int direction = -1;
	
	private TalonSRX[] climberMotors; //arreglo de talons del escalador
	private DigitalInput topLimitSwitch; //limit switch superior del escalador
	private DigitalInput bottomLimitSwitch; //limit switch inferior del escalador
	
	////////////constructor de la clase///////////////////////////////
	public Robot_Escalador(){
		climberMotors=new TalonSRX[]{RobotMap.EscaladorMotLeft,RobotMap.EscaladorMotRight};
		topLimitSwitch=RobotMap.SwitchArriba;
		bottomLimitSwitch=RobotMap.SwitchAbajo;
	}
	/////////////////////////////////////////////////////////////////
	
	////////////metodo para subir y bajar el escalador//////////////
	public void Main_Escalador(){
		Joystick joystick=Robot.oi.Stick2;
		//Mapear el valor del joystick para la potencia
		double Zizq=mapDoubleT(joystick.getRawAxis(2),TOLERANCE,1,0,1)*direction,
			   Zder=mapDoubleT(joystick.getRawAxis(3),TOLERANCE,1,0,1)*direction,
			   power=Zder - Zizq;
		//Interrumpir la funcion cuando el escalador
		//llegue al limite superior
		if(power<0 && !topLimitSwitch.get()){
			power=0;
		}
		//Interrumpir la funcion cuando el escalador
		//llegue al limite inferior
		if(power>0 && !bottomLimitSwitch.get()){
			power=0;
		}
		//Ajustar la potencia de los motores
		for(TalonSRX motor:climberMotors) {
			motor.set(ControlMode.PercentOutput, power);
		}
	}
	//////////////////////////////////////////////////////////////////////
	
	//////////////para parar el motor del escalador/////////////
	public void Stop_Escalador() {
		for(TalonSRX motor:climberMotors) {
			motor.set(ControlMode.PercentOutput, 0.0);
		}
	}
	////////////////////////////////////////////////////////
	
	
	////para poner todo en la posicion inicial/////
	public void InitDefaultState() {
		Stop_Escalador();
	}
	//////////////////////////////////////////////
	
	
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
	//////////////////////////////////////////////
	
	public void initDefaultCommand() {
		//nada
	}
	
}
