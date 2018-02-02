/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3478.robot;

import org.usfirst.frc.team3478.robot.commands.Robot_Autonomo_Main;
import org.usfirst.frc.team3478.robot.commands.Robot_Drive_MainDrive;
import org.usfirst.frc.team3478.robot.commands.Robot_Elevador_MainMove;
import org.usfirst.frc.team3478.robot.commands.Robot_Escalador_MainMove;
import org.usfirst.frc.team3478.robot.commands.Robot_General_InitPositions;
import org.usfirst.frc.team3478.robot.commands.Robot_Intake_MainMove;
import org.usfirst.frc.team3478.robot.subsystems.Robot_Autonomo;
import org.usfirst.frc.team3478.robot.subsystems.Robot_Control;
import org.usfirst.frc.team3478.robot.subsystems.Robot_Drive;
import org.usfirst.frc.team3478.robot.subsystems.Robot_Elevador;
import org.usfirst.frc.team3478.robot.subsystems.Robot_Escalador;
import org.usfirst.frc.team3478.robot.subsystems.Robot_Heading;
import org.usfirst.frc.team3478.robot.subsystems.Robot_Intake;
import org.usfirst.frc.team3478.robot.subsystems.Robot_Topes;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot extends TimedRobot {
	
	public static OI oi; //clase de los joysticks
	
	/*********comandos que necesite el robot para empezar********/
	Command autonomo_command;
	Command drivestart_command;
	Command escaladorstart_command;
	Command elevadorstart_command;
	Command intakestart_command;
	Command resetall_command; //todos los subsistemas deben tener una inicializacion en este comando
	/***********************************************************/
	
	/********subsistemas del robot*******************************/
	public static Robot_Intake Robot_intake;
	public static Robot_Elevador Robot_elevador;
	public static Robot_Escalador Robot_escalador;
	public static Robot_Topes Robot_topes;
	public static Robot_Drive Robot_drive;
	public static Robot_Autonomo Robot_autonomo;
	public static Robot_Control Robot_control;
	public static Robot_Heading Robot_heading;
	/*****************************************************************************************/

	/*******seleccion de autonomo************************************/
	public static SendableChooser autonomousChooser;
	/****************************************************************/
	@Override
	public void robotInit() {
		RobotMap.init(); //inicializa todos los elementos del robot
		
		/*******aqui vamos inicializar los subsystemas que se necesite*************************/
		Robot_intake = new Robot_Intake();
		Robot_elevador = new Robot_Elevador();
		Robot_escalador = new Robot_Escalador();
		Robot_topes = new Robot_Topes();
		Robot_drive = new Robot_Drive();
		Robot_autonomo = new Robot_Autonomo();
		Robot_control = new Robot_Control();
		Robot_heading = new Robot_Heading();
		/*************************************************************************************/
		
		oi = new OI();  ///lo del joystick
	
		/***********aqui vamos a inicializar la camara si se necesita***********************/
		//activa la camara
        //UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
        //camera.setResolution(640,480);
		/**********************************************************************************/
		
		/****************aqui vamos a inicializar los comandos*******************************/
		autonomo_command = new Robot_Autonomo_Main();
		drivestart_command = new Robot_Drive_MainDrive();
		escaladorstart_command = new Robot_Escalador_MainMove();
		intakestart_command = new Robot_Intake_MainMove();
		elevadorstart_command = new Robot_Elevador_MainMove();
		resetall_command = new Robot_General_InitPositions();
		/***********************************************************************************/
		/****************para seleccionar entre autonomos***********************************//////
		autonomousChooser = new SendableChooser();
		autonomousChooser.addDefault("Nada", 1);
		autonomousChooser.addObject("Cruzar", 2);
		autonomousChooser.addObject("Izquierda", 3);
		autonomousChooser.addObject("Centro", 4);
		autonomousChooser.addObject("Derecha", 5);
	    SmartDashboard.putData("Autonomous Selector", autonomousChooser);
		/************************************************************************************/
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		if (autonomo_command != null) autonomo_command.start();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		if (autonomo_command != null) autonomo_command.cancel();  //cancelamos el comando si esta activo
		if (resetall_command != null) resetall_command.start();  //inicializamos el comando si no existe
		if (drivestart_command != null) drivestart_command.start();  //inicializamos el comando si no existe
		if (escaladorstart_command != null) escaladorstart_command.start();  //inicializamos el comando si no existe
		if (elevadorstart_command != null) elevadorstart_command.start();  //inicializamos el comando si no existe
		if (intakestart_command != null) intakestart_command.start();  //inicializamos el comando si no existe
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
