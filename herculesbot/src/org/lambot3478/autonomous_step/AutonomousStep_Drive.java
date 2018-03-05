package org.lambot3478.autonomous_step;

import org.usfirst.frc.team3478.robot.Robot;
import org.usfirst.frc.team3478.robot.RobotMap;
import org.usfirst.frc.team3478.robot.subsystems.Robot_Heading;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Encoder;

///////////clase base de los pasos del drive, cada metodo especifico del drive va sobrescribir las funciones de esta clase/////////////

public abstract class AutonomousStep_Drive extends AutonomousStep{
	protected TalonSRX[] talons;
	protected Encoder[] encoders;
	protected Robot_Heading heading;
	
	////////////constructor de la clase////////////////////
	public AutonomousStep_Drive(){
		///nada
	}
	//////////////////////////////////////////////////////
	
	/////////////funcion para ligar los objetos a los del robot map//////////
	public void setup(){
		heading=Robot.Robot_heading;
		talons=new TalonSRX[]{
				RobotMap.frontLeft,RobotMap.frontRight,
				RobotMap.backLeft,RobotMap.backRight};
		encoders=new Encoder[]{
				RobotMap.DriveEL,RobotMap.DriveER};
	}
	/////////////////////////////////////////////////////////////////////////
	
	// Metodo para inicializar el paso
	@Override
	public abstract void start();

	// Funcion iterativa del paso
	@Override
	public abstract void run();
	
	// Funcion que indica al autonomo si se termino el paso
	@Override
	public abstract boolean isFinished();
	
	//////////funciones genericas para los pasos del subsitema/////////////////////
	
	protected void resetEncoders(){
		for(Encoder encoder:encoders){
			encoder.reset();
		}
	}
	
	protected void vectorMove(double translationAngle,double power,double rotationPower){
		power=power*-1;
		//considera Y arriba - abajo +  y X derecha + izquierda - en los ejes
		double translationX = 0;
		double translationY = 0;
		if(translationAngle==0) {
			translationX = 0;
			translationY = -1;
		}else if(translationAngle==45) {
			translationX = 0.5;
			translationY = -0.5;
		}else if(translationAngle==90) {
			translationX = 1;
			translationY = 0;
		}else if(translationAngle==135) {
			translationX = 0.5;
			translationY = 0.5;
		}else if(translationAngle==180) {
			translationX = 0;
			translationY = 1;
		}else if(translationAngle==225) { 
			translationX = -0.5;
			translationY = 0.5;
		}else if(translationAngle==270) {
			translationX = -1;
			translationY = 0;
		}else if(translationAngle==315) {   
			translationX = -0.5;
			translationY = -0.5;
		}
		double angle=-Math.atan2(translationX, translationY)+(Math.PI/4);
		talons[0].set(ControlMode.PercentOutput,power*
				Math.sin(angle)-rotationPower);
		talons[1].set(ControlMode.PercentOutput,power*
				Math.cos(angle)+rotationPower);
		talons[2].set(ControlMode.PercentOutput,power*
				Math.cos(angle)-rotationPower);
		talons[3].set(ControlMode.PercentOutput,power*
				Math.sin(angle)+rotationPower);
	}
	
	
	/////////////////////////////////////////////////////////////////////////
	
	
	
}
