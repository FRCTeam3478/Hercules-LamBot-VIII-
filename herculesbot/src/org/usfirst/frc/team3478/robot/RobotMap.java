/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3478.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Servo;


public class RobotMap {
	
	/***************Numero de los talons*********/
	private static final int FRONT_LEFT_CHASSIS_PORT = 0;
	private static final int FRONT_RIGHT_CHASSIS_PORT = 1;
	private static final int BACK_LEFT_CHASSIS_PORT = 2;
	private static final int BACK_RIGHT_CHASSIS_PORT = 3;
	
	private static final int INTAKE_LEFT_PORT = 4;
	private static final int INTAKE_RIGHT_PORT = 5;
	private static final int INTAKE_HINGE_PORT = 6;
	
	private static final int ESCALADOR_LEFT_PORT = 7;
	private static final int ESCALADOR_RIGHT_PORT = 8;
	/******************************************/
	
	/**********numeros de las senales digitales********/
	private static final int ENCODER_CHASSIS_LEFT_A = 0;
	private static final int ENCODER_CHASSIS_LEFT_B = 1;
	private static final int ENCODER_CHASSIS_RIGHT_A = 2;
	private static final int ENCODER_CHASSIS_RIGHT_B = 3;
	
	private static final int ESCALADOR_UP = 4;
	private static final int ESCALADOR_DOWN = 5;
	/***********************************************/
	
	
	/*************chasis************************/
	public static TalonSRX frontLeft;
	public static TalonSRX frontRight;
	public static TalonSRX backLeft;
	public static TalonSRX backRight;
	public static Encoder DriveEL;
    public static Encoder DriveER;
	public static PowerDistributionPanel pdp;
	/*******************************************/
	
	/*********intake***************************/
	public static TalonSRX intakeLeft;
	public static TalonSRX intakeRight;
	public static TalonSRX intakeHinge;
	//public static Encoder intakeHingeEncoder;
	/***************************************/
		
	/*********topes********************/
	public static Servo Servo1;
	/**********************************/
	
	/********escalador******************/
	public static TalonSRX EscaladorMotLeft;
	public static TalonSRX EscaladorMotRight; 
	public static DigitalInput SwitchAbajo;
	public static DigitalInput SwitchArriba;
	/***********************************/
	
	
	
	public static void init() {
		
		/*************chasis************************/
		pdp = new PowerDistributionPanel();
		frontLeft = new TalonSRX(FRONT_LEFT_CHASSIS_PORT);
		frontRight = new TalonSRX(FRONT_RIGHT_CHASSIS_PORT);
		backLeft = new TalonSRX(BACK_LEFT_CHASSIS_PORT);
		backRight = new TalonSRX(BACK_RIGHT_CHASSIS_PORT);
		frontLeft.setInverted(true);
		backLeft.setInverted(true);
		frontRight.setInverted(false);
		backRight.setInverted(false);
		frontLeft.setNeutralMode(NeutralMode.Brake);
		frontRight.setNeutralMode(NeutralMode.Brake);
		backLeft.setNeutralMode(NeutralMode.Brake);
		backRight.setNeutralMode(NeutralMode.Brake);
		frontLeft.set(ControlMode.PercentOutput,0);
		frontRight.set(ControlMode.PercentOutput,0);
		backLeft.set(ControlMode.PercentOutput,0);
		backRight.set(ControlMode.PercentOutput,0);
		DriveEL = new Encoder(ENCODER_CHASSIS_LEFT_A,ENCODER_CHASSIS_LEFT_B,true, Encoder.EncodingType.k4X);
        DriveEL.setMaxPeriod(.1);
        DriveEL.setMinRate(10);
        DriveEL.setDistancePerPulse(((1/1.75)*(6*3.1416))/(128));  //llantas 6 in relacion encoder flecha final de 1.75
        DriveEL.setSamplesToAverage(7);
        DriveEL.reset();
        DriveER = new Encoder(ENCODER_CHASSIS_RIGHT_A,ENCODER_CHASSIS_RIGHT_B,false, Encoder.EncodingType.k4X);
        DriveER.setMaxPeriod(.1);
        DriveER.setMinRate(10);
        DriveER.setDistancePerPulse(((1/1.75)*(6*3.1416))/(128));  //llantas 6 in relacion encoder flecha final de 1.75
        DriveER.setSamplesToAverage(7);
        DriveER.reset();
		/*********************************************/
		
		/*********intake***************************/
		intakeLeft = new TalonSRX(INTAKE_LEFT_PORT);
		intakeRight = new TalonSRX(INTAKE_RIGHT_PORT);
		intakeHinge = new TalonSRX(INTAKE_HINGE_PORT);
		intakeLeft.setNeutralMode(NeutralMode.Brake);
		intakeRight.setNeutralMode(NeutralMode.Brake);
		intakeHinge.setNeutralMode(NeutralMode.Brake);
		intakeLeft.set(ControlMode.PercentOutput,0.0);
		intakeRight.set(ControlMode.PercentOutput,0.0);
		intakeHinge.set(ControlMode.PercentOutput,0.0);
		/*********************************************/

		/*********topes***********************/
		Servo1 = new Servo(1);
		/*************************************/
		
		/*********escalador********************/
		EscaladorMotLeft = new TalonSRX(ESCALADOR_LEFT_PORT);
		EscaladorMotRight = new TalonSRX(ESCALADOR_RIGHT_PORT);
		EscaladorMotLeft.setInverted(true);
		EscaladorMotRight.setInverted(false);
		EscaladorMotLeft.setNeutralMode(NeutralMode.Brake);
		EscaladorMotRight.setNeutralMode(NeutralMode.Brake);
		EscaladorMotLeft.set(ControlMode.PercentOutput,0);
		EscaladorMotRight.set(ControlMode.PercentOutput,0);
		SwitchAbajo = new DigitalInput(ESCALADOR_DOWN);  //tienen pull up en el roborio
		SwitchArriba = new DigitalInput(ESCALADOR_UP);   //tienen pull up en el roborio
		/**************************************/
		
		
		
	}
	
	
}
