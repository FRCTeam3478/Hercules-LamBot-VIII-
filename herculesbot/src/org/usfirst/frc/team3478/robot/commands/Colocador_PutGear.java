package org.usfirst.frc.team3478.robot.commands;

import org.usfirst.frc.team3478.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Colocador_PutGear extends Command{
	
	public Colocador_PutGear() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.colocador);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.colocador.Reset_state_push();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Robot.colocador.PutGear();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;  //cambiar a true si queremos que solo se haga una vez, false es que lo repite(el while held cambia el false a true o cuando se interrumpe)
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.colocador.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}

}
