package org.lambot3478.autonomous_steps;

import org.lambot3478.autonomous_step.AutonomousStep_IntakeElevador;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//////para subir o bajar el elevador con encoder y seguridad de switch/////////////////////

public class MoveElevatorEncoder extends AutonomousStep_IntakeElevador{
	private static final int TOP_LIMIT = 69000; //pulsos por vuelta del encoder
	private static final int LOW_LIMIT = -10000; //pulsos por vuelta del encoder
	private static final int TOLERANCE_ENCODER = 250; //pulsos por vuelta del encoder
	private int Actualposition = 0;
	private int Position;
	private Timer timer;
	
	
	public MoveElevatorEncoder(double Position){  //entrada en mm
		// Inicializar los parametros
		if(Position<LOW_LIMIT) {Position=LOW_LIMIT;}  //seguridad
		if(Position>TOP_LIMIT) {Position=TOP_LIMIT;}  //seguridad
		this.Position = (int)Position;
	}
	
	@Override
	public void start() {
		timer=new Timer();
		timer.reset();
		timer.start();
	}

	@Override
	public void run() {
		elevatorTalon.set(ControlMode.Position, Position);  //mueve el motor a donde le digamos (ticks del encoder)
	}

	@Override
	public boolean isFinished() {
		Actualposition = (int)(elevatorTalon.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("enocderEleAuto", Actualposition);
		if(Math.abs(Actualposition-Position)<=TOLERANCE_ENCODER || timer.get()>5 ) {
			elevatorTalon.set(ControlMode.PercentOutput, 0);
			return true;
		}else {
			return false;
		}
	}

}
