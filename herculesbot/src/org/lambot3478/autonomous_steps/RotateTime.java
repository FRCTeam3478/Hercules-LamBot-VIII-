package org.lambot3478.autonomous_steps;

import org.lambot3478.autonomous_step.AutonomousStep_Drive;
import edu.wpi.first.wpilibj.Timer;

//////clase para girar a la izquierda o a la derecha cierto tiempo/////////////////

public class RotateTime extends AutonomousStep_Drive{
	private Timer timer;
	private double power;
	private double time;
	
	public RotateTime(double rotationPower,double time){
		this.power=rotationPower;
		this.time=time;
	}
	@Override
	public void start() {
		timer=new Timer();
		timer.start();
	}

	@Override
	public void run() {
		vectorMove(0,0,power);
	}
	@Override
	public boolean isFinished() {
		if(timer==null){
			timer=new Timer();
			timer.start();
		}
		if(timer.get()<time)
			return false;
		Timer.delay(0.2);
		heading.resetRotation();
		return true;
	}

}