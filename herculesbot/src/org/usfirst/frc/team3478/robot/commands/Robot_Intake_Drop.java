package org.usfirst.frc.team3478.robot.commands;

import org.usfirst.frc.team3478.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Robot_Intake_Drop extends Command {
	public Robot_Intake_Drop() {
		requires(Robot.Robot_intake);
	}
	
	// Called just before this Command runs the first time
		@Override
		protected void initialize() {
		}

		// Called repeatedly when this Command is scheduled to run
		@Override
		protected void execute() {
			Robot.Robot_intake.drop();
		}

		// Make this return true when this Command no longer needs to run execute()
		@Override
		protected boolean isFinished() {
			return false;  
		}

		// Called once after isFinished returns true
		@Override
		protected void end() {
			Robot.Robot_intake.stop();
		}

		// Called when another command which requires one or more of the same
		// subsystems is scheduled to run
		@Override
		protected void interrupted() {
			end();
		}
}
