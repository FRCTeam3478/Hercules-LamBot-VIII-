package org.usfirst.frc.team3478.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;

/* // Para pruebas
 * import edu.wpi.first.wpilibj.interfaces.Gyro;
 */

public class Robot_Heading extends Subsystem{

	private static AHRS ahrs;
	
	//////////////cosntructor de la clase inicializamos el imu/////////////////
	public Robot_Heading(){
		/* // Para pruebas
		 * gyro=RobotMap.gyro;
		 * gyro.calibrate();
		 * gyro.reset();
		 */
		/***********Inicializar NavX*********/
		try{
			ahrs=new AHRS(SPI.Port.kMXP);
			ahrs.reset();
		}
		catch(Exception e){
			
		}
		/************************************/
	}
	////////////////////////////////////////////////////////////////////////
	
	///////////para resetear el imu si necesario//////////////////////
	public void resetRotation(){
		ahrs.reset();
	}
	///////////////////////////////////////////////////////////////////
	
	/////////////para leer el rate del imu//////////////////////////////
	public double getRate(){
		/* // Para pruebas
		 * return gyro.getRate();
		 */
		
		/****Regresar la entrada del NavX****/
		return ahrs.getRate();
		/************************************/		
	}
	///////////////////////////////////////////////////////////////////
	
	//////////////////para leer el angulo 0 a 360 //////////////////////
	public double getRawRotation(){
		/****Regresar la entrada del NavX****/
		return ahrs.getAngle();
		/************************************/
	}
	//////////////////////////////////////////////////////////////////
	
	///////////////para leer el angulo ya convertido de -180 a 180 ////////////
	public double getRotation(){
		/****Regresar la entrada del NavX****/
		////mapear rotacion de -180 a 180
		return mapRound(ahrs.getAngle());
		/************************************/
	}
	/////////////////////////////////////////////////////////////////////////
	
	@Override
	protected void initDefaultCommand() {
		// nada
	}
	
	//////////////para convertir el angulo de -180 a 180/////////////////////////
	private double mapRound(double value){
		if(value>180) {
			value=value-360;
		}
		return((int)(value));
	}
	////////////////////////////////////////////////////////////////////////////////
	
}
