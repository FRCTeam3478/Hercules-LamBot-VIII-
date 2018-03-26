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
import org.lambot3478.autonomous_steps.GrabBox;
import org.lambot3478.autonomous_steps.MoveElevatorEncoder;
import org.lambot3478.autonomous_steps.MoveElevatorTime;
import org.lambot3478.autonomous_steps.MoveElevatorToSwitch;
import org.lambot3478.autonomous_steps.RotateDegrees;
import org.lambot3478.autonomous_steps.RotateTime;
import org.lambot3478.autonomous_steps.StepFactory_Drive;
import org.lambot3478.autonomous_steps.StepFactory_IntakeElevador;
import org.lambot3478.autonomous_steps.ThrowBox;
import org.lambot3478.autonomous_steps.VectorMoveEncoders;
import org.lambot3478.autonomous_steps.VectorMoveTime;
import org.usfirst.frc.team3478.robot.Robot;
import org.usfirst.frc.team3478.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot_Autonomo extends Subsystem {
	private final int AUTONOMOUS_NOTHING=1;
	private final int AUTONOMOUS_CENTER_2BOX =5;
	private final int AUTONOMOUS_CENTER=3;
	private final int AUTONOMOUS_LEFT=2;
	private final int AUTONOMOUS_RIGHT=4;
	
	// Tiempo para interrupcion de seguridad
	private final double SAFETY_TIMER=15;
	
	private Robot_Heading heading;
	
	// Arreglo de pasos del tren motriz 
	private AutonomousStep_Drive[] driveSteps={};
		/* // Ejemplo de secuencia de tren motriz
		 * new AutonomousStep_Drive[]{
		 * StepFactory_Drive.getNewVectorMoveTime(90,1.0,0.8),
		 * StepFactory_Drive.getNewVectorMoveTime(-90,1.0,0.8),
		 * StepFactory_Drive.getNewRotateDegrees(-180)};*/
	
	
	
	//Arreglo de pasos del intake y elevador
	private AutonomousStep_IntakeElevador[] intakeSteps={};
		/*// Ejemplo de secuencia de elevador
		 * new AutonomousStep_IntakeElevador[]{
		 * StepFactory_IntakeElevador.getNewLowerElevator(),
		 * StepFactory_IntakeElevador.getNewWait(1),
		 * StepFactory_IntakeElevador.getNewRaiseElevatorTime(0.7,2),
		 * StepFactory_IntakeElevador.getNewThrowBox(),
		 * StepFactory_IntakeElevador.getNewLowerElevator(),
		 * StepFactory_IntakeElevador.getNewGrabBox(),
		 * StepFactory_IntakeElevador.getNewRaiseElevatorTime(0.7,2),
		 * StepFactory_IntakeElevador.getNewThrowBox()};
		 */
	
	//////////para hacer la sincronizacion con el tread y el main loop y viceversa///////////
	 public static int stepnumintake = 0;
	 public static int stepnumdrive = 0;
	 public static synchronized void incrementCountIntake() {
		 stepnumintake++;
     }
	 public static synchronized void incrementCountDrive() {
		 stepnumdrive++;
     }
	/////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	//arreglo para guadar los talons del chasis
	private TalonSRX[] talonsDrive;
	//arreglo para guadar los talons del elevador
	private TalonSRX[] talonsIntakeElevador;
	
	/////////constructor de la clase///////////////////////////////
	public Robot_Autonomo(){
		talonsDrive=new TalonSRX[]{RobotMap.frontLeft,RobotMap.frontRight,
				RobotMap.backLeft,RobotMap.backRight};
		talonsIntakeElevador=new TalonSRX[]{RobotMap.intakeLeft,RobotMap.intakeRight,
				RobotMap.ElevadorMot};
	}
	//////////////////////////////////////////////////////////////
	
	/////////////funcion para detener el chasis/////////////////////
	private void driveStop(){
		for(TalonSRX talon:talonsDrive) {
			talon.set(ControlMode.PercentOutput, 0.0);
		}
	}
	//////////////////////////////////////////////////////////////
	
	/////////////funcion para detener todo lo del intake////////////
	private void intakeElevadorStop(){
		for(TalonSRX talon:talonsIntakeElevador) {
			talon.set(ControlMode.PercentOutput, 0.0);
		}
	}
	//////////////////////////////////////////////////////////////
	
	////funcion principal del autonomo////////////
	public void RunAuto() {
		
		//Crea el Timer de seguridad
		Timer safetyTimer=new Timer();
		safetyTimer.start();
		
		/*******************Seleccion de autonomo********************************/
		// Obtener el autonomo seleccionado en la dashboard
		int selected=(int) Robot.autonomousChooser.getSelected();
		SmartDashboard.putNumber("selected", selected);
				
		// Obtener los datos de juego (3 caracteres)
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		////////resetea el giroscopio antes de cualquier cosa/////////////
		heading=Robot.Robot_heading;
		heading.Resetdevice();
		talonsIntakeElevador[2].setSelectedSensorPosition(0, 0, 0); //resetea el sensor
		/////////////////////////////////////////////////////////////////
		
		while(gameData.length() < 2 && safetyTimer.get()<SAFETY_TIMER) {
			//espere a que llegue la data
			gameData = DriverStation.getInstance().getGameSpecificMessage();
			Timer.delay(0.01);
		}
		SmartDashboard.putNumber("AutonomoSelected", selected);
		if(selected==AUTONOMOUS_CENTER_2BOX){
			
		}
		else if(selected==AUTONOMOUS_LEFT){
			if(gameData.charAt(0)=='R' && gameData.charAt(1)=='R'){ //scale y switch de tu lado

				intakeSteps=new AutonomousStep_IntakeElevador[]{
						StepFactory_IntakeElevador.move2Switch(1),
						StepFactory_IntakeElevador.getMoveElevatorEncoder(30000),
						StepFactory_IntakeElevador.move2Switch(-1),
						StepFactory_IntakeElevador.syncWait(7),
						StepFactory_IntakeElevador.getNewWait(0.5),
						StepFactory_IntakeElevador.getNewThrowBox(),
						StepFactory_IntakeElevador.syncWait(9),
						StepFactory_IntakeElevador.move2Switch(1),
						
				};
				
				driveSteps = new AutonomousStep_Drive[]{
						StepFactory_Drive.syncWait(1),
						StepFactory_Drive.getNewWait(0.5),
						StepFactory_Drive.getNewVectorMoveEncoders(0,1,280), //Avanza
						StepFactory_Drive.getNewVectorMoveEncoders(90,1,10), //Avanza
						StepFactory_Drive.syncWait(3),
						StepFactory_Drive.getNewRotateDegrees(90), // Gira 90
						StepFactory_Drive.getNewVectorMoveEncoders(0,1,5),
						StepFactory_Drive.syncWait(6),
						StepFactory_Drive.getNewRotateDegrees(90), // Gira 90
						
						};
				
				
			} else if(gameData.charAt(0)=='R' && gameData.charAt(1)=='L'){ //switch de tu lado
				
				intakeSteps=new AutonomousStep_IntakeElevador[]{
						StepFactory_IntakeElevador.move2Switch(1),
						StepFactory_IntakeElevador.getMoveElevatorEncoder(30000),
						StepFactory_IntakeElevador.syncWait(5),
						StepFactory_IntakeElevador.getNewWait(0.5),
						StepFactory_IntakeElevador.getNewThrowBox()
				};
				
				driveSteps = new AutonomousStep_Drive[]{
						StepFactory_Drive.syncWait(1),
						StepFactory_Drive.getNewWait(0.5),
						StepFactory_Drive.getNewVectorMoveEncoders(0,1,120), //Avanza
						StepFactory_Drive.getNewRotateDegrees(90), // Gira 90
						StepFactory_Drive.getNewVectorMoveEncoders(0,1,10)
						};
				
			}else if(gameData.charAt(0)=='L' && gameData.charAt(1)=='R'){ //scale de tu lado
				
				intakeSteps=new AutonomousStep_IntakeElevador[]{
						StepFactory_IntakeElevador.move2Switch(1),
						StepFactory_IntakeElevador.getMoveElevatorEncoder(30000),
						StepFactory_IntakeElevador.move2Switch(-1),
						StepFactory_IntakeElevador.syncWait(7),
						StepFactory_IntakeElevador.getNewWait(0.5),
						StepFactory_IntakeElevador.getNewThrowBox(),
						StepFactory_IntakeElevador.syncWait(9),
						StepFactory_IntakeElevador.move2Switch(1),
						
				};
				
				driveSteps = new AutonomousStep_Drive[]{
						StepFactory_Drive.syncWait(1),
						StepFactory_Drive.getNewWait(0.5),
						StepFactory_Drive.getNewVectorMoveEncoders(0,1,280), //Avanza
						StepFactory_Drive.getNewVectorMoveEncoders(90,1,10), //Avanza
						StepFactory_Drive.syncWait(3),
						StepFactory_Drive.getNewRotateDegrees(90), // Gira 90
						StepFactory_Drive.getNewVectorMoveEncoders(0,1,5),
						StepFactory_Drive.syncWait(6),
						StepFactory_Drive.getNewRotateDegrees(90), // Gira 90
						
						};
				
			}else{  //nada
				intakeSteps=new AutonomousStep_IntakeElevador[]{
						StepFactory_IntakeElevador.move2Switch(1),
						StepFactory_IntakeElevador.getMoveElevatorEncoder(30000)
				};
				
				driveSteps = new AutonomousStep_Drive[]{
						StepFactory_Drive.syncWait(1),
						StepFactory_Drive.getNewWait(0.5),
						StepFactory_Drive.getNewVectorMoveEncoders(0,1,120), //Avanza
				};
			}
		}
		else if(selected==AUTONOMOUS_CENTER){
			if(gameData.charAt(0)=='L'){
				intakeSteps=new AutonomousStep_IntakeElevador[]{
						StepFactory_IntakeElevador.move2Switch(1),
						StepFactory_IntakeElevador.getMoveElevatorEncoder(30000),
						StepFactory_IntakeElevador.syncWait(4),
						StepFactory_IntakeElevador.getNewThrowBox(),
						StepFactory_IntakeElevador.syncWait(6),
						StepFactory_IntakeElevador.move2Switch(1),
						StepFactory_IntakeElevador.syncWait(8),
						StepFactory_IntakeElevador.getNewGrabBox(),
						StepFactory_IntakeElevador.getMoveElevatorEncoder(30000),
						StepFactory_IntakeElevador.syncWait(12),
						StepFactory_IntakeElevador.getNewWait(0.5),
						StepFactory_IntakeElevador.getNewThrowBox()
				};
				
				driveSteps = new AutonomousStep_Drive[]{
						StepFactory_Drive.syncWait(1),
						StepFactory_Drive.getNewWait(0.5),
						StepFactory_Drive.getNewVectorMoveEncoders(270,1,25),
						StepFactory_Drive.getNewVectorMoveEncoders(315,1,32),
						StepFactory_Drive.syncWait(4),
						StepFactory_Drive.getNewVectorMoveEncoders(0,-1,50),
						StepFactory_Drive.getNewVectorMoveEncoders(90,1,45),
						StepFactory_Drive.syncWait(6),
						StepFactory_Drive.getNewVectorMoveEncoders(0,1,30), //30  40
						StepFactory_Drive.syncWait(8),
						StepFactory_Drive.getNewVectorMoveEncoders(90,-1,70), //85
						StepFactory_Drive.getNewVectorMoveEncoders(0,1,27) //27  35
						};
			}
			else{
				intakeSteps=new AutonomousStep_IntakeElevador[]{
						StepFactory_IntakeElevador.move2Switch(1),
						StepFactory_IntakeElevador.getMoveElevatorEncoder(30000),
						StepFactory_IntakeElevador.syncWait(4),
						StepFactory_IntakeElevador.getNewWait(0.5),
						StepFactory_IntakeElevador.getNewThrowBox(),
						
						StepFactory_IntakeElevador.syncWait(6),
						StepFactory_IntakeElevador.move2Switch(1),
						StepFactory_IntakeElevador.syncWait(8),
						StepFactory_IntakeElevador.getNewGrabBox(),
						StepFactory_IntakeElevador.getMoveElevatorEncoder(30000),
						StepFactory_IntakeElevador.syncWait(12),
						StepFactory_IntakeElevador.getNewWait(0.5),
						StepFactory_IntakeElevador.getNewThrowBox()
				};
				
				driveSteps = new AutonomousStep_Drive[]{
						StepFactory_Drive.syncWait(1),
						StepFactory_Drive.getNewWait(0.5),
						StepFactory_Drive.getNewVectorMoveEncoders(45,1,38),  //50
						StepFactory_Drive.getNewVectorMoveEncoders(90,1,5),
						StepFactory_Drive.syncWait(5),
						
						StepFactory_Drive.getNewVectorMoveEncoders(0,-1,50),
						StepFactory_Drive.getNewVectorMoveEncoders(90,-1,48),  //48  65
						StepFactory_Drive.syncWait(7),
						StepFactory_Drive.getNewVectorMoveEncoders(0,1,42), //25  //55
						StepFactory_Drive.syncWait(9),
						StepFactory_Drive.getNewVectorMoveEncoders(90,1,70), //70
						StepFactory_Drive.getNewVectorMoveEncoders(0,1,30) //27  40
						};
			}
		}
		else if(selected==AUTONOMOUS_RIGHT){
			if(gameData.charAt(0)=='R' && gameData.charAt(1)=='R'){ //scale y switch de tu lado

				intakeSteps=new AutonomousStep_IntakeElevador[]{
						StepFactory_IntakeElevador.move2Switch(1),
						StepFactory_IntakeElevador.getMoveElevatorEncoder(30000),
						StepFactory_IntakeElevador.move2Switch(-1),
						StepFactory_IntakeElevador.syncWait(7),
						StepFactory_IntakeElevador.getNewWait(0.5),
						StepFactory_IntakeElevador.getNewThrowBox(),
						StepFactory_IntakeElevador.syncWait(9),
						StepFactory_IntakeElevador.move2Switch(1),
						
				};
				
				driveSteps = new AutonomousStep_Drive[]{
						StepFactory_Drive.syncWait(1),
						StepFactory_Drive.getNewWait(0.5),
						StepFactory_Drive.getNewVectorMoveEncoders(0,1,280), //Avanza
						StepFactory_Drive.getNewVectorMoveEncoders(90,1,10), //Avanza
						StepFactory_Drive.syncWait(3),
						StepFactory_Drive.getNewRotateDegrees(-90), // Gira 90
						StepFactory_Drive.getNewVectorMoveEncoders(0,1,5),
						StepFactory_Drive.syncWait(6),
						StepFactory_Drive.getNewRotateDegrees(-90), // Gira 90
						
						};
				
				
			} else if(gameData.charAt(0)=='R' && gameData.charAt(1)=='L'){ //switch de tu lado
				
				intakeSteps=new AutonomousStep_IntakeElevador[]{
						StepFactory_IntakeElevador.move2Switch(1),
						StepFactory_IntakeElevador.getMoveElevatorEncoder(30000),
						StepFactory_IntakeElevador.syncWait(5),
						StepFactory_IntakeElevador.getNewWait(0.5),
						StepFactory_IntakeElevador.getNewThrowBox()
				};
				
				driveSteps = new AutonomousStep_Drive[]{
						StepFactory_Drive.syncWait(1),
						StepFactory_Drive.getNewWait(0.5),
						StepFactory_Drive.getNewVectorMoveEncoders(0,1,120), //Avanza
						StepFactory_Drive.getNewRotateDegrees(-90), // Gira 90
						StepFactory_Drive.getNewVectorMoveEncoders(0,1,10)
						};
				
			}else if(gameData.charAt(0)=='L' && gameData.charAt(1)=='R'){ //scale de tu lado
				
				intakeSteps=new AutonomousStep_IntakeElevador[]{
						StepFactory_IntakeElevador.move2Switch(1),
						StepFactory_IntakeElevador.getMoveElevatorEncoder(30000),
						StepFactory_IntakeElevador.move2Switch(-1),
						StepFactory_IntakeElevador.syncWait(7),
						StepFactory_IntakeElevador.getNewWait(0.5),
						StepFactory_IntakeElevador.getNewThrowBox(),
						StepFactory_IntakeElevador.syncWait(9),
						StepFactory_IntakeElevador.move2Switch(1),
						
				};
				
				driveSteps = new AutonomousStep_Drive[]{
						StepFactory_Drive.syncWait(1),
						StepFactory_Drive.getNewWait(0.5),
						StepFactory_Drive.getNewVectorMoveEncoders(0,1,280), //Avanza
						StepFactory_Drive.getNewVectorMoveEncoders(90,1,10), //Avanza
						StepFactory_Drive.syncWait(3),
						StepFactory_Drive.getNewRotateDegrees(-90), // Gira 90
						StepFactory_Drive.getNewVectorMoveEncoders(0,1,5),
						StepFactory_Drive.syncWait(6),
						StepFactory_Drive.getNewRotateDegrees(-90), // Gira 90
						
						};
				
			}else{  //nada
				intakeSteps=new AutonomousStep_IntakeElevador[]{
						StepFactory_IntakeElevador.move2Switch(1),
						StepFactory_IntakeElevador.getMoveElevatorEncoder(30000)
				};
				
				driveSteps = new AutonomousStep_Drive[]{
						StepFactory_Drive.syncWait(1),
						StepFactory_Drive.getNewWait(0.5),
						StepFactory_Drive.getNewVectorMoveEncoders(0,1,120), //Avanza
				};
			}
		
		}else if(selected==AUTONOMOUS_NOTHING){
			///no hace nada
		}
		/************************************************************************/
		
		/*******************Ejecucion de pasos***********************************/
		
		//////Crea un tread para correr las secuencias del intake en paralelo al chassis
		Thread intakeElevadorThread=new Thread(new Runnable(){
			@Override
			public void run(){
				for(AutonomousStep_IntakeElevador step:intakeSteps){
					////para poder sincronizar los ciclos si necesario
					int flagwait = step.changewait();
					while(flagwait>stepnumdrive && safetyTimer.get()<SAFETY_TIMER) {
						Timer.delay(0.01);
					}
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
					incrementCountIntake();
					if(safetyTimer.get()>=SAFETY_TIMER){
						break;
					}
				}
			}
		});
		intakeElevadorThread.start(); //inicia el tread del intake
		
		////corre las secuencias del chasis//////////////////
		for(AutonomousStep_Drive step:driveSteps){
			////para poder sincronizar los ciclos si necesario
			int flagwait = step.changewait();
			while(flagwait>stepnumintake && safetyTimer.get()<SAFETY_TIMER) {
				Timer.delay(0.01);
			}
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
			incrementCountDrive();
			if(safetyTimer.get()>=SAFETY_TIMER){
				break;
			}
		}
		//Espera a que termine las secuencias del intake en el tread
		while(intakeElevadorThread.isAlive() && safetyTimer.get()<SAFETY_TIMER){}
		// Detener los motores del tren motriz
		safetyTimer.stop();
		EndAuto();
		/************************************************************************/
	}
	
	/////////////////////////////////////////////
	
	/////////funcion que hace al final del autonomo////////
	public void EndAuto() {
		driveStop();
		intakeElevadorStop();
	}
	///////////////////////////////////////////////////
	
	
	public void initDefaultCommand() {
		//nada
	}
	
}