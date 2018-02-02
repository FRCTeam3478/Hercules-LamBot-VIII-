package org.lambot3478.autonomous_step;

import org.usfirst.frc.team3478.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;

///////////clase base de los pasos del intake, cada metodo especifico del intake va sobrescribir las funciones de esta clase/////////////

public abstract class AutonomousStep_IntakeElevador extends AutonomousStep{
	protected DigitalInput eleUpSwitch,eleDownSwitch,intakeUpSwitch,intakeDownSwitch;
	protected AnalogInput boxin;
	protected TalonSRX elevatorTalon;
	protected TalonSRX intakeHinge;
	protected TalonSRX[] intakeTalons;
	
	////////////constructor de la clase////////////////////
	public AutonomousStep_IntakeElevador(){
		///nada
	}
	//////////////////////////////////////////////////////
	
	/////////////funcion para ligar los objetos a los del robot map//////////
	public void setup(){
		elevatorTalon=RobotMap.ElevadorMot;
		intakeHinge = RobotMap.intakeHinge;
		intakeTalons=new TalonSRX[]{RobotMap.intakeLeft,RobotMap.intakeRight};
		eleUpSwitch=RobotMap.EleSwitchArriba;
		eleDownSwitch=RobotMap.EleSwitchAbajo;
		intakeUpSwitch=RobotMap.intakeUp;
		intakeDownSwitch=RobotMap.intakeDown;
		boxin=RobotMap.boxIn;
	}
	////////////////////////////////////////////////////////////////////////
	
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
	
	protected void setIntakeTalons(double power){
		for(TalonSRX intakeTalon:intakeTalons){
			intakeTalon.set(ControlMode.PercentOutput, power);
		}
	}
	///////////////////////////////////////////////////////////////////////////////
	
}
