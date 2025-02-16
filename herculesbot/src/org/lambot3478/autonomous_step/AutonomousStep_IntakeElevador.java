package org.lambot3478.autonomous_step;

import org.usfirst.frc.team3478.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;

///////////clase base de los pasos del intake, cada metodo especifico del intake va sobrescribir las funciones de esta clase/////////////

public abstract class AutonomousStep_IntakeElevador extends AutonomousStep{
	protected DigitalInput boxin;
	protected TalonSRX elevatorTalon;
	protected TalonSRX[] intakeTalons;
	
	////////////constructor de la clase////////////////////
	public AutonomousStep_IntakeElevador(){
		///nada
	}
	//////////////////////////////////////////////////////
	
	/////////////funcion para ligar los objetos a los del robot map//////////
	public void setup(){
		elevatorTalon=RobotMap.ElevadorMot;
		intakeTalons=new TalonSRX[]{RobotMap.intakeLeft,RobotMap.intakeRight};
		boxin=RobotMap.boxIn;
	}
	////////////////////////////////////////////////////////////////////////
	
	// Metodo para inicializar el paso
	@Override
	public abstract void start();
	
	// Funcion para sincronizar
	@Override
	public int changewait() {
		return(0);
	}

	// Funcion iterativa del paso
	@Override
	public abstract void run();
	
	// Funcion que indica al autonomo si se termino el paso
	@Override
	public abstract boolean isFinished();
	
	
	//////////funciones genericas para los pasos del subsitema/////////////////////
	
	protected void setIntakeTalons(double power){
		for(TalonSRX intakeTalon:intakeTalons){
			intakeTalon.set(ControlMode.PercentOutput, power);
		}
	}
	///////////////////////////////////////////////////////////////////////////////
	
}
