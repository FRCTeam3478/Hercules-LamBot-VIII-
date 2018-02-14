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

public class Robot_Elevador extends Subsystem {
	
	private static final double TOLERANCE=0.15;  //tolerancia del joystick
	private static final int Encoder_CPR = 1024; //pulsos por vuelta del encoder
	
	private static int direction = 1;
	private final int Step_limit = (int)((Encoder_CPR*4)/36); //que avance maximo 5mm por ciclo, polea 36 dientes 5mm por diente
	private static int Position_abs = 0;
	private static int Position_Now = 0;
	private static int Position_Last = 0;
		
	private TalonSRX elevadormotor; //arreglo de talons del escalador
	private DigitalInput intakeDown; //limit switch superior del escalador
	
	public void initDefaultCommand() {
		//nada
	}
	
	////para poner todo en la posicion inicial/////
	public void InitDefaultState() {
		Stop_Elevador();
		Position_abs=elevadormotor.getSelectedSensorPosition(0);  //inicia la posicion donde se quedo en el autonomo
		Position_Now = Position_abs;
		Position_Last = Position_abs;
	}
	////////////////////////////////////////////////
	
	////////////constructor de la clase///////////////////////////////
	public Robot_Elevador(){
		elevadormotor=RobotMap.ElevadorMot;
		intakeDown = RobotMap.intakeDown;
	}
	/////////////////////////////////////////////////////////////////
	
	
	//////////movimiento principal del elevador/////////////////////
	public void Main_Move_Elevador() {
		
		if(elevadormotor.getSensorCollection().isRevLimitSwitchClosed()) { //si toca el switch de abajo resetea el encoder para quitar error
			elevadormotor.setSelectedSensorPosition(0, 0, 0); //resetea el sensor
		}
		
		Joystick joystick=Robot.oi.Stick2; //crea el objeto del joystick
		double power=mapDoubleT(joystick.getRawAxis(5),TOLERANCE,1,0,1)*direction; //mapea el valor del eje y da direccion
		int powerfake = (int)(power*Step_limit); //fijamos cuanto se va a mover por ciclo de roborio con el eje

		//////calcula la posicion (setpoint) //////////////
		Position_abs = ((int)((powerfake+Position_abs)/Step_limit))*Step_limit; //sacamos el nuevo setpoint y lo hacemos divisible a steplimit
		
		///////////sumamos a la rampa de pasos o restamos/////////////////
		if(Position_Now < Position_abs) {
			Position_Now = Position_Now + Step_limit;
		}else if(Position_Now > Position_abs) {
			Position_Now = Position_Now - Step_limit;
		}
		////////////////////////////////////////////////////////////////
		
		/////////para que no se mueva y no se pase la possicion mas de lo que debe///////////////
		if((Position_Now-Position_Last)>0 && elevadormotor.getSensorCollection().isFwdLimitSwitchClosed() ) {
			Position_abs=elevadormotor.getSelectedSensorPosition(0);  //no se mueve
			Position_Now = Position_abs;
		}else if((Position_Now-Position_Last)<0 && elevadormotor.getSensorCollection().isRevLimitSwitchClosed()) {
			Position_abs=elevadormotor.getSelectedSensorPosition(0);  //no se mueve
			Position_Now = Position_abs;
		}
		//////////////////////////////////////////////////////////////
		
		///****los switches del talon estan activados por proteccion y paran solo el motor****////
		
		////////no puede mover si el intake esta arriba  asi que agregamos eso/////////////////////
		if(intakeDown.get()){
			Position_abs=elevadormotor.getSelectedSensorPosition(0);  //no se mueve
			Position_Now = Position_abs;
			Position_Last = Position_abs;
		}
		//////////////////////////////////////////////////////////////////////////////////////////
		
		elevadormotor.set(ControlMode.Position, Position_Now);  //mueve el motor a donde le digamos (ticks del encoder)
		Position_Last = Position_Now;
	}
	///////////////////////////////////////////////////////////////
	
	
	//////////movimiento principal del elevador/////////////////////
	public void Elevador_Up() {
		Position_abs=(int)(3*Encoder_CPR*4);
	}
	///////////////////////////////////////////////////////////////
	
	//////////movimiento principal del elevador/////////////////////
	public void Elevador_Upper() {
		Position_abs=(int)(5*Encoder_CPR*4);
	}
	///////////////////////////////////////////////////////////////
	
	//////////movimiento principal del elevador/////////////////////
	public void Elevador_Down() {
		Position_abs=0;
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
