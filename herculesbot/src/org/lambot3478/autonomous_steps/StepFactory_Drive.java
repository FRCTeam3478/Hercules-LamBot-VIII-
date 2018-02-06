package org.lambot3478.autonomous_steps;

import org.lambot3478.autonomous_step.AutonomousStep_Drive;

import edu.wpi.first.wpilibj.Timer;

///////////Esta clase esta disenada para regresar todos los ojbetos de las clases que extiende a la clase de los pasos por systema//////////////////

public class StepFactory_Drive {
	public static AutonomousStep_Drive getNewVectorMoveTime(double translationAngle,double power,double time){
		return new VectorMoveTime(translationAngle,power,time);
	}
	public static AutonomousStep_Drive getNewRotateTime(double rotationPower,double time){
		return new RotateTime(rotationPower,time);
	}
	public static AutonomousStep_Drive getNewRotateDegrees(double rotation){
		return new RotateDegrees(rotation);
	}
	public static AutonomousStep_Drive getNewVectorMoveEncoders(double translationAngle,double power,double distance){
		return new VectorMoveEncoders(translationAngle,power,distance);
	}
	public static AutonomousStep_Drive getNewWait(double time){
		AutonomousStep_Drive wait=new AutonomousStep_Drive(){
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
