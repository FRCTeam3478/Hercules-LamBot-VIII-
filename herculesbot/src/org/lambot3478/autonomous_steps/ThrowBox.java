package org.lambot3478.autonomous_steps;

import org.lambot3478.autonomous_step.AutonomousStep_IntakeElevador;

import edu.wpi.first.wpilibj.Timer;

//////para sacar la caja con cierto tiempo/////////////////////////////

public class ThrowBox extends AutonomousStep_IntakeElevador{
	private final double TIME=2;
	private final double POWER=1.0;
	private Timer timer;
	
	public ThrowBox(){
		//nada
	}
	
	@Override
	public void start() {
		timer=new Timer();
		timer.start();
	}
	
	@Override
	public void run() {
		setIntakeTalons(-POWER);
	}

	@Override
	public boolean isFinished() {
		if(timer==null){
			timer=new Timer();
			timer.start();
		}
		if(timer.get()<TIME)
			return false;
		return true;
	}
}
