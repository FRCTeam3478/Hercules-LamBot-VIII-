
package org.usfirst.frc.team3478.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team3478.robot.commands.Autostart;
import org.usfirst.frc.team3478.robot.commands.Drive_main;
import org.usfirst.frc.team3478.robot.commands.Escalador_start;
import org.usfirst.frc.team3478.robot.commands.Reset_variables;
import org.usfirst.frc.team3478.robot.commands.Shooter_main;
import org.usfirst.frc.team3478.robot.commands.Torreta_main;
import org.usfirst.frc.team3478.robot.subsystems.Autonomo;
import org.usfirst.frc.team3478.robot.subsystems.Colocador;
import org.usfirst.frc.team3478.robot.subsystems.Control;
import org.usfirst.frc.team3478.robot.subsystems.Drivetrain;
import org.usfirst.frc.team3478.robot.subsystems.Escalador;
import org.usfirst.frc.team3478.robot.subsystems.Intake;
import org.usfirst.frc.team3478.robot.subsystems.Shooter;
import org.usfirst.frc.team3478.robot.subsystems.Torreta;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	
	
	/***********aqui vamos a declarar los comandos con los que inicia el robot*************/
	Command autonomo_command;
	Command drivestart_command;
	Command escalador_command;
	Command torreta_command;
	Command shooter_command;
	Command resetall_command;
	/****************************************************************************************/
	
	public static OI oi;

	/********aqui vamos a crear los objetos de los subsystemas*******************************/
	public static Drivetrain drivetrain;
	public static Intake Roller;
	public static Escalador escalador;
	public static Colocador colocador;
	public static Control control;
	public static Torreta torreta;
	public static Autonomo autonomo;
	public static Shooter shooter;
	/*****************************************************************************************/
	
	///////////****para seleccionar entre autonomos********************************************/
	private int mode = 1; // initialize default mode
	public static SendableChooser chooser;
	///////**********************************************************************************///
	


	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
		RobotMap.init(); //inicializa todos los elementos del robot
		
		/*******aqui vamos inicializar los subsystemas**************************************/
		drivetrain = new Drivetrain();
		Roller = new Intake();
		escalador = new Escalador();
		colocador = new Colocador();
		control = new Control();
		torreta = new Torreta();
		autonomo = new Autonomo();
		shooter = new Shooter();
		/*************************************************************************************/
		
		
		oi = new OI();  ///lo del joystick
	
		/***********aqui vamos a inicializar la camara si se necesita***********************/
		//activa la camara
        //UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
        //camera.setResolution(640,480);
		/**********************************************************************************/
		
		/****************aqui vamos a inicializar los comandos*******************************/
		autonomo_command = new Autostart();
		drivestart_command = new Drive_main();
		escalador_command = new Escalador_start();
		resetall_command = new Reset_variables();
		torreta_command = new Torreta_main();
		shooter_command = new Shooter_main();
		Robot.control.turnCompressorOn();	//activa el ciclo automatico de can del compresor
		/***********************************************************************************/
		
		/////*******para seleccionar entre autonomos***********************************//////
		chooser = new SendableChooser();
	    chooser.addDefault("Nada", 1);
	    chooser.addObject("Engrane centro", 2);
	    chooser.addObject("Engrane izquierda", 3);
	    chooser.addObject("Engrane derecha", 4);
	    chooser.addObject("Hopper izquierda", 5);
	    chooser.addObject("Hopper derecho", 6);
	    chooser.addObject("centro derecha", 7);
	    chooser.addObject("centro izquierda", 8);
	    chooser.addObject("shoot crazy", 9);
	    SmartDashboard.putData("Autonomous Selector", chooser);
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
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		if (autonomo_command != null) autonomo_command.start();  //inicializamos el comando si no existe
	}

	/**
	 * This function is called periodically during autonomous
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
		if (escalador_command != null) escalador_command.start();  //inicializamos el comando si no existe
		if (shooter_command != null) shooter_command.start();  //inicializamos el comando si no existe
		if (torreta_command != null) torreta_command.start();  //inicializamos el comando si no existe

	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
