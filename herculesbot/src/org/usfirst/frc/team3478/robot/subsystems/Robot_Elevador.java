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
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot_Elevador extends Subsystem {
	
	private static final double TOLERANCE=0.15;  //tolerancia del joystick
	private static final int TOP_LIMIT = 69000; //pulsos por vuelta del encoder
	
	private static int direction = -1;
	private static int Position_abs = -1;
	private static Timer timerx;
		
	private TalonSRX elevadormotor; //arreglo de talons del escalador
	private DigitalInput intakeDown; //limit switch superior del escalador
	
	public void initDefaultCommand() {
		//nada
	}
	
	////para poner todo en la posicion inicial/////
	public void InitDefaultState() {
		Stop_Elevador();
		Position_abs=elevadormotor.getSelectedSensorPosition(0);  //inicia la posicion donde se quedo en el autonomo
		timerx.reset();
	}
	////////////////////////////////////////////////
	
	////////////constructor de la clase///////////////////////////////
	public Robot_Elevador(){
		elevadormotor=RobotMap.ElevadorMot;
		intakeDown = RobotMap.intakeDown;
		timerx=new Timer();
		Position_abs = -1;
	}
	/////////////////////////////////////////////////////////////////
	
	
	//////////movimiento principal del elevador/////////////////////
	public void Main_Move_Elevador() {
		if(elevadormotor.getSensorCollection().isRevLimitSwitchClosed()) { //si toca el switch de abajo resetea el encoder y limita abajo
			elevadormotor.setSelectedSensorPosition(0, 0, 0); //resetea el sensor
		}
		Joystick joystick=Robot.oi.Stick2; //crea el objeto del joystick
		double power=mapDoubleT(joystick.getRawAxis(5),TOLERANCE,1,0,1)*direction; //mapea el valor del eje y da direccion
		
		elevadormotor.set(ControlMode.PercentOutput, power);
		
		if( power < 0 && elevadormotor.getSensorCollection().isRevLimitSwitchClosed()) {
			power=0;
		}else if(power > 0 && elevadormotor.getSensorCollection().isFwdLimitSwitchClosed()) {
			power=0;
		}
		
		////////no puede mover si el intake esta arriba  asi que agregamos eso/////////////////////
		/*
		if(intakeDown.get()){
			power=0;
		}
		*/
		//////////////////////////////////////////////////////////////////////////////////////////
	
		//////////////////////////////////////////////////////////////////////////////////////////
		if(power==0 && Position_abs != -1) {
			if(Position_abs>TOP_LIMIT) {
				Position_abs=TOP_LIMIT;
			}
			if(Position_abs<0) { ///automaticos hasta el switch
				if(Position_abs==-10) {  //para abajo
					
					////////timer de seguridad////////////
					if(elevadormotor.getSelectedSensorPosition(0)<250 && timerx.get()==0) {
						timerx.start();
					}
					/////////////////////////////////////
					
					if(!elevadormotor.getSensorCollection().isRevLimitSwitchClosed() && timerx.get()<2) { //si toca el switch de abajo resetea el encoder y limita abajo
						elevadormotor.set(ControlMode.PercentOutput, -1);
					}else {
						timerx.stop();
						timerx.reset();
						Position_abs=-1;
						elevadormotor.set(ControlMode.PercentOutput, 0);
					}
				}else if(Position_abs==-20) { //para arriba
					
					////////timer de seguridad////////////
					if(elevadormotor.getSelectedSensorPosition(0)>68000 && timerx.get()==0) {
						timerx.start();
					}
					/////////////////////////////////////
					
					if(!elevadormotor.getSensorCollection().isFwdLimitSwitchClosed() && timerx.get()<2 ) { //si toca el switch de abajo resetea el encoder y limita abajo
						elevadormotor.set(ControlMode.PercentOutput, 1);
					}else {
						timerx.stop();
						timerx.reset();
						Position_abs=-1;
						elevadormotor.set(ControlMode.PercentOutput, 0);
					}
				}
			}else {
				if(Math.abs(elevadormotor.getSelectedSensorPosition(0)-Position_abs)<=250) {
					Position_abs=-1;
				}
				elevadormotor.set(ControlMode.Position, Position_abs); //mantiene el motor
			}
		}else {
			timerx.stop();
			timerx.reset();
			Position_abs=-1;
			elevadormotor.set(ControlMode.PercentOutput, power);  //mueve el motor a donde le digamos (ticks del encoder)
		}
		
		SmartDashboard.putNumber("positionencoder", elevadormotor.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("positionabs", Position_abs);
		SmartDashboard.putBoolean("toplimit", elevadormotor.getSensorCollection().isFwdLimitSwitchClosed());
		SmartDashboard.putBoolean("bottomlimit", elevadormotor.getSensorCollection().isRevLimitSwitchClosed());
	}
	///////////////////////////////////////////////////////////////
	
	
	//////////movimiento principal del elevador/////////////////////
	public void Elevador_Up() {
		Position_abs=30000;
	}
	///////////////////////////////////////////////////////////////
	
	//////////movimiento principal del elevador/////////////////////
	public void Elevador_Upper() {
		Position_abs=-20;
	}
	///////////////////////////////////////////////////////////////
	
	//////////movimiento principal del elevador/////////////////////
	public void Elevador_Down() {
		Position_abs=-10;
	}
	///////////////////////////////////////////////////////////////
	
	
	//////////////para parar el motor del escalador/////////////
	public void Stop_Elevador() {
		elevadormotor.set(ControlMode.PercentOutput, 0.0);
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
		//////////////////////////////////////////////
	
	
}
