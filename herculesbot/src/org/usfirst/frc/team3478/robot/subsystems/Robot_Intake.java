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
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;


public class Robot_Intake extends Subsystem {
	private static final double TOLERANCE=0.15;  //tolerancia del joystick
	private static final double POWERFACTOR = 0.07; //limitador del maximo
	private static final int Encoder_CPR = 1024; //pulsos por vuelta del encoder
	private final int Y_AXIS = 1; 
	private final int RIGHT_TRIGGER = 2;
	private final int LEFT_TRIGGER = 3;
			
	//max change no debe permitir movimientos de mas de 1 grado al mecanismo
	private static int direction = -1;
	private static int position_abs = 0;
	private static int position_abs_last = 0;
	
	private TalonSRX intakeRight;
	private TalonSRX intakeLeft;
	private TalonSRX intakeHinge;
	private DigitalInput intakeUp;
	private DigitalInput intakeDown;
	private AnalogInput boxIn;
	
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
		intakeHinge = RobotMap.intakeHinge;
		intakeUp = RobotMap.intakeUp;
		intakeDown = RobotMap.intakeDown;
		boxIn = RobotMap.boxIn;		
	}
	/////////////////////////////////////////////////////////////////
	
	//////////movimiento principal del intake/////////////////////
	public void Main_Move_Intake() {
		
		Joystick stick2 = Robot.oi.Stick2; // crea objeto del joystick 2
		
		double yAxisValue = mapDoubleT(stick2.getRawAxis(Y_AXIS),TOLERANCE,1,0,1)*direction; //mapea el valor del eje y da direccion
		int powerfake = (int)((yAxisValue*100)*POWERFACTOR); //fijamos cuanto se va a mover por ciclo de roborio con el eje

		if(powerfake>0 && !intakeUp.get()){ //checamos el switch de arriba para no pasarnos
			powerfake=0;
		}
		if(powerfake<0 && !intakeDown.get()){//checamos el switch de abajo para no pasarnos
			powerfake=0;
		}
		
		position_abs = powerfake+position_abs; //sumamos lo que se va mover a la variable global de posicion que fija donde esta el motor
		
		/////////aqui va el pov para los automaticos//////////////////
		if(stick2.getPOV()==0){			
			position_abs= 0;
		}else if(stick2.getPOV()==180){
			position_abs= Encoder_CPR;
		}else if(stick2.getPOV()==270){
			position_abs= (int) Encoder_CPR/2;
		}
		/////////////////////////////////////////////////////////////		
		
		if((position_abs-position_abs_last) < 0 && !intakeDown.get()) { //chemaos el switch para que no se pase del limite
			position_abs=position_abs_last;
		}else if((position_abs-position_abs_last) > 0 && !intakeUp.get()) { //chemaos el switch para que no se pase del limite
			position_abs=position_abs_last;
		}
		
		intakeHinge.set(ControlMode.Position, position_abs);  //mueve el motor a donde le digamos (ticks del encoder)
		position_abs_last = position_abs; //guardamos el ultimo estado		
		
		manipulate(stick2.getRawAxis(RIGHT_TRIGGER), stick2.getRawAxis(LEFT_TRIGGER));
	}
	///////////////////////////////////////////////////////////////
	
	//// Funcion para comer o escupir cajas //////////////////////////////
	public void manipulate(double rightTriggerValue, double leftTriggerValue) {
		double rightOutputValue = 0.0;
		double leftOutputValue = 0.0;
		// Mapear el valor de los triggers para la potencia
		rightTriggerValue = mapDoubleT(rightTriggerValue,TOLERANCE,1,0,1);
		leftTriggerValue = mapDoubleT(leftTriggerValue,TOLERANCE,1,0,1);
		
		// left trigger value is subtracted from right trigger value to get difference
		double triggerDifferenceValue = rightTriggerValue - leftTriggerValue;
		
		// if trigger difference value is less than 0.0
		if (triggerDifferenceValue < 0.0) {			
			// set -1.0 as output value
			rightOutputValue = -1.0;
			leftOutputValue = -1.0;		
			
			// Si el sensor detecto la caja evita que siga comiendo
			if (boxIn.getVoltage() < 3) {
				rightOutputValue = 0.0;
				leftOutputValue = 0.0;
			}
		}
		// else, if it is greater than 0.0
		else if (triggerDifferenceValue > 0.0) {
			// set -1.0 as output value
			rightOutputValue = 1.0;
			leftOutputValue = 1.0;
		}
		
		// set right and left intake motors their respective output value
		intakeRight.set(ControlMode.PercentOutput, rightOutputValue);
		intakeLeft.set(ControlMode.PercentOutput, leftOutputValue);
	}
	//////////////////////////////////////////////////////////////////////	
	
	//////////////para parar motores del intake/////////////
	public void Stop_Intake() {
		intakeRight.set(ControlMode.PercentOutput, 0.0);
		intakeLeft.set(ControlMode.PercentOutput, 0.0);
		intakeHinge.set(ControlMode.PercentOutput, 0.0);
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
