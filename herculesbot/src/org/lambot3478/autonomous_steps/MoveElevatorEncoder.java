package org.lambot3478.autonomous_steps;

import org.lambot3478.autonomous_step.AutonomousStep_IntakeElevador;

import com.ctre.phoenix.motorcontrol.ControlMode;

//////para subir o bajar el elevador con encoder y seguridad de switch/////////////////////

public class MoveElevatorEncoder extends AutonomousStep_IntakeElevador{
	private final int Encoder_CPR = 1024; //pulsos por vuelta del encoder
	private int Step_limit = (int)((Encoder_CPR*4)/36); //que avance maximo 5mm por ciclo, polea 36 dientes 5mm por diente
	private int Actualposition = 0;
	private int Position;
	private boolean breakstate = false;
	
	
	public MoveElevatorEncoder(double Position){  //entrada en mm
		// Inicializar los parametros
		if(Position<0) {Position=0;}  //seguridad
		this.Position = (int) (((Position*Step_limit)/5)/Step_limit);
	}
	
	@Override
	public void start() {
		//elevatorTalon.setSelectedSensorPosition(0, 0, 0); //resetea el encoder
		Actualposition = (int)(elevatorTalon.getSelectedSensorPosition(0)/Step_limit);
		////no se puede mover si esta arriba el intake//////////
		if(intakeHinge.getSelectedSensorPosition(0) < 40 ) {
			breakstate=true;  //rompe no puede subir asi
		}
		///////////////////////////////////////////////////////
	}

	@Override
	public void run() {
		elevatorTalon.set(ControlMode.Position, (Actualposition*Step_limit));  //mueve el motor a donde le digamos (ticks del encoder)
	}

	@Override
	public boolean isFinished() {
		if( Actualposition == Position || (!eleUpSwitch.get()) || (!eleDownSwitch.get()) || breakstate ) {
			return true;
		}else {
			if(Actualposition<Position) {
				Actualposition = Actualposition + 1;
			}else if(Actualposition>Position) {
				Actualposition = Actualposition - 1;
			}
			return false;
		}
	}

}
