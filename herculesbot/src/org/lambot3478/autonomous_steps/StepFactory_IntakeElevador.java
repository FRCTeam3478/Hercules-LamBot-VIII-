package org.lambot3478.autonomous_steps;

import org.lambot3478.autonomous_step.AutonomousStep_IntakeElevador;

import edu.wpi.first.wpilibj.Timer;

///////////Esta clase esta disenada para regresar todos los ojbetos de las clases que extiende a la clase de los pasos por systema//////////////////

public class StepFactory_IntakeElevador {
	public static AutonomousStep_IntakeElevador getMoveElevatorEncoder( double position){
		return new MoveElevatorEncoder( position );
	}
	public static AutonomousStep_IntakeElevador getMoveElevatorTime(double power,double time){
		return new MoveElevatorTime(power,time);
	}
	public static AutonomousStep_IntakeElevador getNewGrabBox(){
		return new GrabBox();
	}
	public static AutonomousStep_IntakeElevador getNewThrowBox(){
		return new ThrowBox();
	}
	public static AutonomousStep_IntakeElevador move2Switch(double select){
		return new MoveElevatorToSwitch(select);
	}
	public static AutonomousStep_IntakeElevador getNewWait(double time){
		AutonomousStep_IntakeElevador wait=new AutonomousStep_IntakeElevador(){
			double delay=time;
			Timer timer;
			@Override
			public void start() {
				timer=new Timer();
				timer.start();
			}

			@Override
			public void run() {
				
			}
			@Override
			public boolean isFinished() {
				if(timer==null){
					timer=new Timer();
					timer.start();
				}
				if(timer.get()<delay)
					return false;
				return true;
			}
			
		};
		return wait;
	}
}