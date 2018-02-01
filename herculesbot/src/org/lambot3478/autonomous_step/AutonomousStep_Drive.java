package org.lambot3478.autonomous_step;

import org.usfirst.frc.team3478.robot.Robot;
import org.usfirst.frc.team3478.robot.RobotMap;
import org.usfirst.frc.team3478.robot.subsystems.Robot_Heading;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Encoder;

public abstract class AutonomousStep_Drive extends AutonomousStep{
	protected TalonSRX[] talons;
	protected Encoder[] encoders;
	protected Robot_Heading heading;
	public AutonomousStep_Drive(){
		
	}
	public void setup(){
		heading=Robot.Robot_heading;
		talons=new TalonSRX[]{
				RobotMap.frontLeft,RobotMap.frontRight,
				RobotMap.backLeft,RobotMap.backRight};
		encoders=new Encoder[]{
				RobotMap.DriveEL,RobotMap.DriveER};
	}
	@Override
	public abstract void start();

	@Override
	public abstract void run();
	
	@Override
	public abstract boolean isFinished();
	protected void resetEncoders(){
		for(Encoder encoder:encoders){
			encoder.reset();
		}
	}
	protected void vectorMove(double translationAngle,double power,double rotationPower){
		double angle=(translationAngle+180)*Math.PI/180+(Math.PI/4);
		talons[0].set(ControlMode.PercentOutput,power*
				Math.sin(angle)-rotationPower);
		talons[1].set(ControlMode.PercentOutput,power*
				Math.cos(angle)+rotationPower);
		talons[2].set(ControlMode.PercentOutput,power*
				Math.cos(angle)-rotationPower);
		talons[3].set(ControlMode.PercentOutput,power*
				Math.sin(angle)+rotationPower);
	}
}
