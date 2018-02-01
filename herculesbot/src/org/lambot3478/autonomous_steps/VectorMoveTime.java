package org.lambot3478.autonomous_steps;

import org.lambot3478.autonomous_step.AutonomousStep_Drive;

import edu.wpi.first.wpilibj.Timer;

public class VectorMoveTime extends AutonomousStep_Drive{
	private Timer timer;
	private double front;
	private double power;
	private double time;
	private double translationAngle;
	
	public VectorMoveTime(double translationAngle,double power,double time){
		// Inicializar los parametros
		this.translationAngle=translationAngle;
		this.power=power;
		this.time=time;
	}
	@Override
	public void start() {
		front=heading.getRotation();
		timer=new Timer();
		timer.start();
	}

	@Override
	public void run() {
		// Potencia de alineacion proporcional
		double rotationGain=(front-heading.getRotation())/22.5;
		vectorMove(translationAngle, power, rotationGain);
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