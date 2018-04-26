package org.usfirst.frc.team3478.robot.commands;

import org.usfirst.frc.team3478.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Shooter_move_position0 extends Command{
	
	Command main_shooter;
	
	public Shooter_move_position0() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.shooter);
		main_shooter = new Shooter_main();
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Robot.shooter.pos0();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return true;  //cambiar a true si queremos que solo se haga una vez, false es que lo repite(el while held cambia el false a true o cuando se interrumpe)
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		main_shooter.start();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}

}
