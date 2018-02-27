package org.lambot3478.autonomous_steps;

import org.lambot3478.autonomous_step.AutonomousStep_IntakeElevador;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Timer;

/////para subir o bajar el elevador con tiempo y seguridad del switch////////////////////////

public class MoveElevatorToSwitch extends AutonomousStep_IntakeElevador{
	private double power;
	private double select;
	private Timer timer;
	
	public MoveElevatorToSwitch(double select){
		this.select=select;
	}
	
	@Override
	public void start() {
		timer=new Timer();
		timer.start();
		if(select==1) {  //abajo
			power=-1;
		}else if(select==-1) { //arriba
			power=1;
		}else {
			power=0;
		}
	}

	@Override
	public void run() {
		elevatorTalon.set(ControlMode.PercentOutput, power);
	}

	@Override
	public boolean isFinished() { //abajo
		if(select==1) {
			if(elevatorTalon.getSensorCollection().isRevLimitSwitchClosed() || timer.get()>5) {
				elevatorTalon.setSelectedSensorPosition(0, 0, 0);
				return true;
			}
			return false;
		}else if(select==-1) {  //arriba
			if(elevatorTalon.getSensorCollection().isFwdLimitSwitchClosed() || timer.get()>5) {
				return true;
			}
			return false;
		}
		return true;
	}
}