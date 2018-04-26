
package org.usfirst.frc.team3478.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3478.robot.Robot;


public class  Reset_variables extends Command {
	
	public Reset_variables() {
   ///aqui tenemos que declarar los subsystemas que vamso a usar
	requires(Robot.drivetrain);
	requires(Robot.colocador);
	requires(Robot.escalador);
	requires(Robot.Roller);
	requires(Robot.shooter);
	requires(Robot.torreta);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drivetrain.Reset_vars();
    	Robot.colocador.Reset_vars();
    	Robot.escalador.Reset_vars();
    	Robot.shooter.Reset_vars();
    	Robot.Roller.Reset_vars();
    	Robot.torreta.Reset_vars();
    }

    // Make this return true when this Command no longer needs to run execute()
    //while held o comando en programa continuo poner false (se rompe el ciclo cuando otro comadno llama al mismo systema
    // o cuando liberamos el boton en el caso del whileheld, button que liberas poner true
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	//necesitamos que active otra ves el comando dl drive
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();  //llama el end
    }
}
