package org.usfirst.frc.team3478.robot.subsystems;

import org.usfirst.frc.team3478.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

/* // Para pruebas
 * import edu.wpi.first.wpilibj.interfaces.Gyro;
 */

public class Robot_Heading extends Subsystem{
	/* // Para pruebas
	 * private static Gyro gyro;
	 */
	
	public Robot_Heading(){
		/* // Para pruebas
		 * gyro=RobotMap.gyro;
		 * gyro.calibrate();
		 * gyro.reset();
		 */
		/***********Inicializar NavX*********/
		
		/************************************/
	}
	public double getRate(){
		/* // Para pruebas
		 * return gyro.getRate();
		 */
		
		/****Regresar la entrada del NavX****/
		
		/************************************/		
		return 0.0;
	}
	public double getRotation(){
		/* // Para pruebas
		 * return gyro.getAngle(); 
		 */
		/****Regresar la entrada del NavX****/
		////necesita regresar de 0 a 180 y -180 a 0
		/************************************/
		return 0.0;
	}
	@Override
	protected void initDefaultCommand() {
		// nada
	}
	
	
}