package org.usfirst.frc.team3478.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/* // Para pruebas
 * import edu.wpi.first.wpilibj.interfaces.Gyro;
 */

public class Robot_Heading extends Subsystem{

	private static AHRS ahrs;
	private static double frontsave;
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
			frontsave=0;
		}
		catch(Exception e){
			
		}
		/************************************/
	}
	////////////////////////////////////////////////////////////////////////
	

	//////////para actualizar la posicion relativa del robot////////////////
	public void resetRotation(){
		frontsave=ahrs.getAngle();
	}
    ///////////////////////////////////////////////////////////////////
	
	////////////para resetear el sensor///////////
	public void Resetdevice() {
		ahrs.reset();
		frontsave=0;
		Timer.delay(0.2);
	}
	///////////////////////////////////////////
	
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
	
	//////////para convertir de 180 a -180 con dobles////////////////////////////
	private double mapRound(double value){
		value = value - frontsave;
		int rs=(int)(value/360);
		double valx = value-(rs*360);
		if(valx>180){
			valx = valx-360;
		}
		if(valx<-180){
			valx = valx+360;
		}
		return valx;
	}
	/////////////////////////////////////////////////////////////////////////////
	
}
