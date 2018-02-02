package org.lambot3478.autonomous_steps;

import org.lambot3478.autonomous_step.AutonomousStep_IntakeElevador;
import edu.wpi.first.wpilibj.Timer;

public class RaiseElevatorTime extends AutonomousStep_IntakeElevador{
	private Timer timer;
	private double power;
	private double time;
	public RaiseElevatorTime(double power,double time){
		this.power=power;
		this.time=time;
	}
	@Override
	public void start() {
		timer=new Timer();
		timer.start();
	}

	@Override
	public void run() {
		setElevatorTalons(Math.abs(power));
	}

	@Override
	public boolean isFinished() {
		if(timer==null){
			timer=new Timer();
			timer.start();
		}
		if(timer.get()<time)
			return false;
		return true;
	}
}