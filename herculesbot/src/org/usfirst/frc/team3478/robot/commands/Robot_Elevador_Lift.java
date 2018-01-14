package org.usfirst.frc.team3478.robot.commands;

import org.usfirst.frc.team3478.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Robot_Elevador_Lift extends Command{
	public Robot_Elevador_Lift() {
		requires(Robot.Robot_elevador);
	}
	
	// Called just before this Command runs the first time
		@Override
		protected void initialize() {
		}

		// Called repeatedly when this Command is scheduled to run
		@Override
		protected void execute() {
			Robot.Robot_elevador.lift();
		}

		// Make this return true when this Command no longer needs to run execute()
		@Override
		protected boolean isFinished() {
			return false;  
		}

		// Called once after isFinished returns true
		@Override
		protected void end() {
			Robot.Robot_elevador.stop();
		}

		// Called when another command which requires one or more of the same
		// subsystems is scheduled to run
		@Override
		protected void interrupted() {
			end();
		}
}
