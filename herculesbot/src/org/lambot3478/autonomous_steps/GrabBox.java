package org.lambot3478.autonomous_steps;

import org.lambot3478.autonomous_step.AutonomousStep_IntakeElevador;

///////clase para comerse la caja hasta que detecte el sensor/////////

public class GrabBox extends AutonomousStep_IntakeElevador{
	private final double POWER=0.7;
	public GrabBox(){
	
	}
	@Override
	public void start() {
	
	}
	@Override
	public void run() {
		setIntakeTalons(-POWER);
	}

	@Override
	public boolean isFinished() {
		if (!boxin.get()) {
			return false;
		}
		return true;
	}
}
