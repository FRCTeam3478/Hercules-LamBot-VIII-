package org.lambot3478.autonomous_steps;

import org.lambot3478.autonomous_step.AutonomousStep_IntakeElevador;

import edu.wpi.first.wpilibj.Timer;

///////clase para comerse la caja hasta que detecte el sensor/////////

public class GrabBox extends AutonomousStep_IntakeElevador{
	private final double POWER=1;
	private Timer timer;
	
	public GrabBox(){
	
	}
	
	@Override
	public void start() {
		timer=new Timer();
		timer.start();
	}
	
	@Override
	public void run() {
		setIntakeTalons(POWER);
	}

	@Override
	public boolean isFinished() {
		if(timer==null){
			timer=new Timer();
			timer.start();
		}
		if(timer.get()<5 && boxin.get())
			return false;
		return true;
		
	}
}
