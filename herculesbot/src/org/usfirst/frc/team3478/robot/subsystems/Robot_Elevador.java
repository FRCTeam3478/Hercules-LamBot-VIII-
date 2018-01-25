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
	private static final double POWERFACTOR = 1; //limitador del maximo
	private static final int Encoder_CPR = 1024; //pulsos por vuelta del encoder
	//max change no debe permitir movimientos de mas de 10 mm al mecanismo
	// la polea 36 dientes 5 mm de pitch
	private static final int MAX_CHANGE = (int)(10*((Encoder_CPR*4)/(36*5))); //el maximo cambio es de media vuelta(para que no se pase los switches con una isntruccion)
	private static int direction = 1;
	private static int Position_abs = 0;
	private static int Position_abs_last = 0;
		
	
	private TalonSRX elevadormotor; //arreglo de talons del escalador
	private DigitalInput ElevadorTopSwitch; //limit switch superior del escalador
	private DigitalInput ElevadorBottomSwitch; //limit switch inferior del escalador

	public void initDefaultCommand() {
		//nada
	}
	
	////para poner todo en la posicion inicial/////
	public void InitDefaultState() {
		Stop_Elevador();
	}
	////////////////////////////////////////////////
	
	////////////constructor de la clase///////////////////////////////
	public Robot_Elevador(){
		elevadormotor=RobotMap.ElevadorMot;
		ElevadorTopSwitch=RobotMap.EleSwitchArriba;
		ElevadorBottomSwitch=RobotMap.EleSwitchAbajo;
	}
	/////////////////////////////////////////////////////////////////
	
	
	//////////movimiento principal del elevador/////////////////////
	public void Main_Move_Elevador() {
		Joystick joystick=Robot.oi.Stick2; //crea el objeto del joystick
		double power=mapDoubleT(joystick.getRawAxis(5),TOLERANCE,1,0,1)*direction; //mapea el valor del eje y da direccion
		int powerfake = (int)((power*100)*POWERFACTOR); //fijamos cuanto se va a mover por ciclo de roborio con el eje
		if(powerfake>0 && !ElevadorTopSwitch.get()){ //checamos el switch de arriba para no pasarnos
			powerfake=0;
		}
		if(powerfake<0 && !ElevadorBottomSwitch.get()){//checamos el switch de abajo para no pasarnos
			powerfake=0;
		}
		Position_abs = powerfake+Position_abs; //sumamos lo que se va mover a la variable global de posicion que fija donde esta el motor
		if((Position_abs-Position_abs_last) < 0 && !ElevadorBottomSwitch.get()) { //chemaos el switch para que no se pase del limite
			Position_abs=Position_abs_last;
		}else if((Position_abs-Position_abs_last) > 0 && !ElevadorTopSwitch.get()) { //chemaos el switch para que no se pase del limite
			Position_abs=Position_abs_last;
		}
		if(Math.abs(Position_abs-Position_abs_last)>MAX_CHANGE) {  //para limitar el cambio maximo
			if((Position_abs-Position_abs_last)<0) {
				Position_abs = Position_abs_last - MAX_CHANGE;
			}else {
				Position_abs = Position_abs_last + MAX_CHANGE;
			}
		}
		elevadormotor.set(ControlMode.Position, Position_abs);  //mueve el motor a donde le digamos (ticks del encoder)
		Position_abs_last = Position_abs; //guardamos el ultimo estado
	}
	///////////////////////////////////////////////////////////////
	
	
	//////////movimiento principal del elevador/////////////////////
	public void Elevador_Up() {
		Position_abs=(int)(3*Encoder_CPR);
	}
	///////////////////////////////////////////////////////////////
	
	//////////movimiento principal del elevador/////////////////////
	public void Elevador_Upper() {
		Position_abs=(int)(5*Encoder_CPR);
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
