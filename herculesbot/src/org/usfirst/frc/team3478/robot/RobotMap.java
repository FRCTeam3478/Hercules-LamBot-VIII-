/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3478.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class RobotMap {
	
	/***************Numero de los talons*********/
	private static final int FRONT_LEFT_CHASSIS_PORT = 0;
	private static final int FRONT_RIGHT_CHASSIS_PORT = 1;
	private static final int BACK_LEFT_CHASSIS_PORT = 2;
	private static final int BACK_RIGHT_CHASSIS_PORT = 3;
	
	private static final int INTAKE_LEFT_PORT = 9;
	private static final int INTAKE_RIGHT_PORT = 7;
	
	private static final int ESCALADOR_LEFT_PORT = 5;
	private static final int ESCALADOR_RIGHT_PORT = 6;
	
	private static final int ELEVADOR_PORT = 4;
	private static final int ELEVADOR_FOLLOW_PORT = 8;
	/******************************************/
	
	/**********numeros de las senales digitales********/
	private static final int ENCODER_CHASSIS_LEFT_A = 0;
	private static final int ENCODER_CHASSIS_LEFT_B = 1;
	private static final int ENCODER_CHASSIS_RIGHT_A = 2;
	private static final int ENCODER_CHASSIS_RIGHT_B = 3;
	
	private static final int ESCALADOR_UP = 4;
	private static final int ESCALADOR_DOWN = 5;
	
	private static final int BOX_IN = 8;
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
	public static DigitalInput boxIn;
	/***************************************/
		
	/********escalador******************/
	public static TalonSRX EscaladorMotLeft;
	public static TalonSRX EscaladorMotRight; 
	public static DigitalInput SwitchAbajo;
	public static DigitalInput SwitchArriba;
	/***********************************/
	
	/***********elevador****************/
	public static TalonSRX ElevadorMot;
	private static TalonSRX ElevadorMot2;
	/**********************************/
	
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
        DriveEL.setDistancePerPulse((((1/1.75)*(6*3.1416))/(128))*1.1);  //llantas 6 in relacion encoder flecha final de 1.75
        DriveEL.setSamplesToAverage(10);
        DriveEL.setReverseDirection(false);
        DriveEL.reset();
        DriveER = new Encoder(ENCODER_CHASSIS_RIGHT_A,ENCODER_CHASSIS_RIGHT_B,false, Encoder.EncodingType.k4X);
        DriveER.setMaxPeriod(.1);
        DriveER.setMinRate(10);
        DriveER.setDistancePerPulse((((1/1.75)*(6*3.1416))/(128))*1.1);  //llantas 6 in relacion encoder flecha final de 1.75
        DriveER.setSamplesToAverage(10);
        DriveER.setReverseDirection(true);
        DriveER.reset();
		/*********************************************/
		
		/*********intake***************************/
        //***encoder 0 cuando esta cerrado*********/
		intakeLeft = new TalonSRX(INTAKE_LEFT_PORT);
		intakeRight = new TalonSRX(INTAKE_RIGHT_PORT);
		intakeLeft.setInverted(true);
		intakeRight.setInverted(false);
		intakeLeft.setNeutralMode(NeutralMode.Brake);
		intakeRight.setNeutralMode(NeutralMode.Brake);
		intakeLeft.set(ControlMode.PercentOutput,0.0);
		intakeRight.set(ControlMode.PercentOutput,0.0);
        boxIn = new DigitalInput(BOX_IN); 
		/*********************************************/
		
		/*********escalador********************/
		EscaladorMotLeft = new TalonSRX(ESCALADOR_LEFT_PORT);
		EscaladorMotRight = new TalonSRX(ESCALADOR_RIGHT_PORT);
		EscaladorMotLeft.setInverted(true);
		EscaladorMotRight.setInverted(false);
		EscaladorMotLeft.setNeutralMode(NeutralMode.Brake);
		EscaladorMotRight.setNeutralMode(NeutralMode.Brake);
		EscaladorMotLeft.set(ControlMode.PercentOutput,0);
		EscaladorMotRight.set(ControlMode.PercentOutput,0);
		EscaladorMotLeft.configOpenloopRamp(0.2, 0);
		EscaladorMotRight.configOpenloopRamp(0.2, 0);
		
		SwitchAbajo = new DigitalInput(ESCALADOR_DOWN);  //tienen pull up en el roborio
		SwitchArriba = new DigitalInput(ESCALADOR_UP);   //tienen pull up en el roborio
		/**************************************/
		
		/***********elevador****************/
		 //***encoder 0 abajo*********/
		ElevadorMot = new TalonSRX(ELEVADOR_PORT);
		ElevadorMot.setNeutralMode(NeutralMode.Brake);
		/* set the enable state for limit switches */
		ElevadorMot.overrideLimitSwitchesEnable(true);
		/* first choose the sensor */
		ElevadorMot.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		ElevadorMot.setSensorPhase(true); //invierte al sensor(encoder)
		ElevadorMot.setInverted(false);  //invierte al motor
		ElevadorMot.configAllowableClosedloopError(0, 0, 0);
		/* set the peak and nominal outputs */
		ElevadorMot.configNominalOutputForward(0, 0);
		ElevadorMot.configNominalOutputReverse(0, 0);
		ElevadorMot.configPeakOutputForward(1.0, 0);
		ElevadorMot.configPeakOutputReverse(-0.7, 0);
		/* set closed loop gains in slot0*/
		ElevadorMot.selectProfileSlot(0, 0);
		ElevadorMot.config_kF(0, 0.3, 0);
		ElevadorMot.config_kP(0, 2, 0);
		ElevadorMot.config_kI(0, 0, 0);
		ElevadorMot.config_kD(0, 200, 0);
		/*configura la rampa*/
		ElevadorMot.configClosedloopRamp(0.1, 0);
		ElevadorMot.configOpenloopRamp(0.1, 0);
		/* zero the sensor */
		ElevadorMot.setSelectedSensorPosition(0, 0, 0); //resetea el sensor
		/* Set to position 0 */
		//ElevadorMot.set(ControlMode.Position, 0);  //encoder ticks (versa) 1024cpr
		ElevadorMot.set(ControlMode.PercentOutput,0);
		
		
		ElevadorMot2=new TalonSRX(ELEVADOR_FOLLOW_PORT);
		ElevadorMot2.setInverted(true);
		ElevadorMot2.setNeutralMode(NeutralMode.Brake);
		ElevadorMot2.set(ControlMode.Follower, ELEVADOR_PORT);
		/**********************************/
		
	}
}
