/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3478.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3478.robot.Robot;

/**
 * An example command.  You can replace me with your own command.
 */
public class Robot_Elevador_MainMove extends Command {
	public Robot_Elevador_MainMove() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.Robot_elevador);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Robot.Robot_elevador.Main_Move_Elevador();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;  //cambiar a true si queremos que solo se haga una vez, false es que lo repite(el while held cambia el false a true o cuando se interrumpe)
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.Robot_elevador.Stop_Elevador();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
