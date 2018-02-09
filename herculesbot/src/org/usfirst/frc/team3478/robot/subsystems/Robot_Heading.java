package org.usfirst.frc.team3478.robot.subsystems;

import org.usfirst.frc.team3478.robot.RobotMap;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;

/* // Para pruebas
 * import edu.wpi.first.wpilibj.interfaces.Gyro;
 */

public class Robot_Heading extends Subsystem{
	/* // Para pruebas
	 * private static Gyro gyro;
	 */
	
	private AHRS ahrs;
	private double front;
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
	public void resetRotation(){
		front=ahrs.getAngle();
	}
	public double getRate(){
		/* // Para pruebas
		 * return gyro.getRate();
		 */
		
		/****Regresar la entrada del NavX****/
		return ahrs.getRate();
		/************************************/		
	}
	public double getRawRotation(){
		/****Regresar la entrada del NavX****/
		return ahrs.getAngle();
		/************************************/
	}
	
	public double getRotation(){
		/****Regresar la entrada del NavX****/
		////mapear rotacion de -180 a 180
		return mapRound(ahrs.getAngle(),-180,180);
		/************************************/
	}
	@Override
	protected void initDefaultCommand() {
		// nada
	}
	
	private double mapRound(double value,double min,double max){
		double res=value-min;
		int rs=(int)(res/(max-min));
		
		if(res<0){
			res+=(rs+1)*(max-min);
		}
		if(res>(max-min)){
			res-=(max-min)*(rs);
		}
		return min+res;
	}
}
