/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/


/*
 * Autonomo v0.2
 */

package org.usfirst.frc.team3478.robot.subsystems;


import org.lambot3478.autonomous_step.AutonomousStep_Drive;
import org.lambot3478.autonomous_step.AutonomousStep_IntakeElevador;
import org.lambot3478.autonomous_steps.StepFactory_Drive;
import org.lambot3478.autonomous_steps.StepFactory_IntakeElevador;
import org.usfirst.frc.team3478.robot.Robot;
import org.usfirst.frc.team3478.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;


public class Robot_Autonomo extends Subsystem {
	private final int AUTONOMOUS_NOTHING=1;
	private final int AUTONOMOUS_CROSS_LINE=2;
	private final int AUTONOMOUS_CENTER=3;
	private final int AUTONOMOUS_LEFT=4;
	private final int AUTONOMOUS_RIGHT=5;
	
	// Tiempo para interrupcion de seguridad
	private final double SAFETY_TIMER=15;
	
	private int direction;
	
	// Arreglo de pasos del tren motriz 
	private AutonomousStep_Drive[] driveSteps={};
		/* // Ejemplo de secuencia de tren motriz
		 * {
		 * StepFactory_Drive.getNewVectorMoveTime(90,1.0,0.8),
		 * StepFactory_Drive.getNewVectorMoveTime(-90,1.0,0.8),
		 * StepFactory_Drive.getNewRotateDegrees(-180)};*/
	
	
	
	//Arreglo de pasos del intake y elevador
	private AutonomousStep_IntakeElevador[] intakeSteps={};
		/*// Ejemplo de secuencia de elevador
		 * {StepFactory_IntakeElevador.getNewLowerElevator(),
		 * StepFactory_IntakeElevador.getNewWait(1),
		 * StepFactory_IntakeElevador.getNewRaiseElevatorTime(0.7,2),
		 * StepFactory_IntakeElevador.getNewThrowBox(),
		 * StepFactory_IntakeElevador.getNewLowerElevator(),
		 * StepFactory_IntakeElevador.getNewGrabBox(),
		 * StepFactory_IntakeElevador.getNewRaiseElevatorTime(0.7,2),
		 * StepFactory_IntakeElevador.getNewThrowBox()};
		 */
	
	
	
	//arreglo para guadar los talons del chasis
	private TalonSRX[] talonsDrive;
	//arreglo para guadar los talons del elevador
	private TalonSRX[] talonsIntakeElevador;
	
	
	public Robot_Autonomo(){
		talonsDrive=new TalonSRX[]{RobotMap.frontLeft,RobotMap.frontRight,
				RobotMap.backLeft,RobotMap.backRight};
		talonsIntakeElevador=new TalonSRX[]{RobotMap.intakeHinge,
				RobotMap.intakeLeft,RobotMap.intakeRight,
				RobotMap.ElevadorMot};
	}
	public void initDefaultCommand() {
		//nada
	}
	private void driveStop(){
		for(TalonSRX talon:talonsDrive) {
			talon.set(ControlMode.PercentOutput, 0.0);
		}
	}
	private void intakeElevadorStop(){
		for(TalonSRX talon:talonsIntakeElevador) {
			talon.set(ControlMode.PercentOutput, 0.0);
		}
	}
	
	////funcion principal del autonomo////////////
	public void RunAuto() {
		/*******************Seleccion de autonomo********************************/
		// Obtener el autonomo seleccionado en la dashboard
		int selected=(int) Robot.autonomousChooser.getSelected();
		// Obtener los datos de juego
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		if(selected==AUTONOMOUS_CROSS_LINE){
			driveSteps=new AutonomousStep_Drive[]{
					StepFactory_Drive.getNewFwdDistance(1,14)};
		}
		else if(selected==AUTONOMOUS_LEFT){
			if(gameData.charAt(0)=='L'){
				// TODO
			}
			else{
				// TODO
			}
		}
		else if(selected==AUTONOMOUS_CENTER){
			if(gameData.charAt(0)=='L'){
				direction = -1;
			}
			else{
				direction = 1;
			}
			
			driveSteps = new AutonomousStep_Drive[]{
					// go forward 6.75 ft to the midpoint between switch and starting line
					StepFactory_Drive.getNewFwdDistance(0.8,6.75),	
					// turn sideways, depending on the switch assignment
					StepFactory_Drive.getNewRotateDegrees(45 * direction),
					// go forward 8 ft to be in front of the switch
					StepFactory_Drive.getNewFwdDistance(0.8,8),
					// turn sideways
					StepFactory_Drive.getNewRotateDegrees(-45 * direction),
					// go forward 4 ft to get closer to the switch
					StepFactory_Drive.getNewFwdDistance(0.8,4),
					//  
					//turn sideways to look towards the pyramid of power cubes
					StepFactory_Drive.getNewRotateDegrees(-45 * direction),
					// go forward 2 ft to get closer to the pyramid
					StepFactory_Drive.getNewFwdDistance(0.8,8),
					// turn around
					StepFactory_Drive.getNewRotateDegrees(180),
					// go forward 2 ft to be in front of the switch
					StepFactory_Drive.getNewFwdDistance(0.8,2),
					// turn sideways to look towards the switch
					StepFactory_Drive.getNewRotateDegrees(-45 * direction),				
					};
			intakeSteps=new AutonomousStep_IntakeElevador[]{
					// raise elevator while moving towards the switch
					StepFactory_IntakeElevador.getNewRaiseElevatorTime(0.7,0.5),
					// wait any remaining time to get to the switch
					StepFactory_IntakeElevador.getNewWait(4.0),
					// throw box
					StepFactory_IntakeElevador.getNewThrowBox(),
					// lower elevator while moving towards the pyramid of power cubes
					StepFactory_IntakeElevador.getNewLowerElevator(),
					// wait any remaining time to get to the pyramid
					StepFactory_IntakeElevador.getNewWait(1.0),
					// grab box from pyramid
					StepFactory_IntakeElevador.getNewGrabBox(),
					// raise elevator while moving towards the switch again
					StepFactory_IntakeElevador.getNewRaiseElevatorTime(0.7,0.5),
					// wait any remaining time to get to the switch again
					StepFactory_IntakeElevador.getNewWait(4.0),
					// throw box
					StepFactory_IntakeElevador.getNewThrowBox(),
			};
		}
		else if(selected==AUTONOMOUS_RIGHT){
			// TODO
		}
		/************************************************************************/
		
		/*******************Ejecucion de pasos***********************************/
		// Timer de seguridad
		Timer safetyTimer=new Timer();
		safetyTimer.start();
		Thread intakeElevadorThread=new Thread(new Runnable(){
			@Override
			public void run(){
				for(AutonomousStep_IntakeElevador step:intakeSteps){
					// Inicializar los actuadores y sensores
					// de los mecanismos
					step.setup();
					// Correr la inicializacion del paso
					step.start();
					// Interrumpir el paso si excede el tiempo
					// de seguridad o si alcanza su limite
					while(safetyTimer.get()<SAFETY_TIMER&&!step.isFinished()){
						step.run();
					}
					// Detener motores al terminar paso
					intakeElevadorStop();
					if(safetyTimer.get()>=SAFETY_TIMER){
						break;
					}
				}
				// Detener los motores de los mecanismos
				// manipuladores de cajas
				intakeElevadorStop();
			}
		});
		intakeElevadorThread.start();
		for(AutonomousStep_Drive step:driveSteps){
			// Inicializar los actuadores tren motriz
			// y sensores usados por el
			step.setup();
			// Correr la inicializacion del paso
			step.start();
			// Interrumpir el paso si excede el tiempo
			// de seguridad o si alcanza su limite
			while(safetyTimer.get()<SAFETY_TIMER&&!step.isFinished()){
				// Ejecutar paso hasta que
				// el limite se cumpla
				step.run();
			}
			// Detener motores al terminar paso
			driveStop();
			if(safetyTimer.get()>=SAFETY_TIMER){
				break;
			}
		}
		safetyTimer.stop();
		// Esperar a que los pasos del intake y elevador 
		// terminen de ejecutarse
		while(intakeElevadorThread.isAlive()){}
		// Detener los motores del tren motriz
		driveStop();
		/************************************************************************/
	}
	
	/////////////////////////////////////////////
	
	/////////funcion que hace al final del autonomo////////
	public void EndAuto() {
	}
	///////////////////////////////////////////////////
	
}