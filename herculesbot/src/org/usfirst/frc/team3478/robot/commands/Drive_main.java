package org.usfirst.frc.team3478.robot.commands;

import org.usfirst.frc.team3478.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Drive_main extends Command{
	
	public Drive_main() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.drivetrain);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Robot.drivetrain.Main_drive();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;  //cambiar a true si queremos que solo se haga una vez, false es que lo repite(el while held cambia el false a true o cuando se interrumpe)
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.drivetrain.Stop_drive();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}

}
