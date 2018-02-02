package org.lambot3478.autonomous_steps;

import org.lambot3478.autonomous_step.AutonomousStep_IntakeElevador;

import com.ctre.phoenix.motorcontrol.ControlMode;

//////para subir o bajar el intake con encoder y seguridad de switch/////////////////////

public class MoveIntakeEncoder extends AutonomousStep_IntakeElevador{
	private final int Encoder_CPR = 1024; //pulsos por vuelta del encoder
	private int Step_limit = (int)((Encoder_CPR*4)/360); //que avance maximo 1 grado por ciclo
	private int Actualposition = 0;
	private int Degrees;
	
	
	public MoveIntakeEncoder(double Degrees){  //entrada en grados
		// Inicializar los parametros
		if(Degrees<0) {Degrees=0;}  //seguridad
		this.Degrees = (int) (((Degrees*Step_limit)/1)/Step_limit);
	}
	
	@Override
	public void start() {
		//elevatorTalon.setSelectedSensorPosition(0, 0, 0); //resetea el encoder
		Actualposition = (int)(elevatorTalon.getSelectedSensorPosition(0)/Step_limit);
	}

	@Override
	public void run() {
		intakeHinge.set(ControlMode.Position, (Actualposition*Step_limit));  //mueve el motor a donde le digamos (ticks del encoder)
	}

	@Override
	public boolean isFinished() {
		if( Actualposition == Degrees || (!intakeUpSwitch.get()) || (!intakeDownSwitch.get()) ) {
			return true;
		}else {
			if(Actualposition<Degrees) {
				Actualposition = Actualposition + 1;
			}else if(Actualposition>Degrees) {
				Actualposition = Actualposition - 1;
			}
			return false;
		}
	}

}
