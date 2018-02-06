package org.lambot3478.autonomous_steps;

import org.lambot3478.autonomous_step.AutonomousStep_IntakeElevador;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Timer;

/////para subir o bajar el intake con tiempo y seguridad del switch////////////////////////

public class MoveIntakeTime extends AutonomousStep_IntakeElevador{
	private Timer timer;
	private double power;
	private double time;
	
	public MoveIntakeTime(double power,double time){
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
		intakeHinge.set(ControlMode.PercentOutput, power);
	}

	@Override
	public boolean isFinished() {
		if(timer==null){
			timer=new Timer();
			timer.start();
		}
		if(timer.get()<time && (intakeUpSwitch.get()) && (intakeDownSwitch.get()))
			return false;
		return true;
	}
}