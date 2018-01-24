
package org.usfirst.frc.team3478.robot;

import org.usfirst.frc.team3478.robot.commands.Robot_Drive_ChangePolarity;
import org.usfirst.frc.team3478.robot.commands.Robot_Topes_Open;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
	
	
	/////objetos que vamos a utilizar/////////////////
	public Joystick Stick1; //declara un joystick
	public Joystick Stick2; //declara un joystick
	public JoystickButton XboxY1; //declara un boton de joystick
	public JoystickButton XboxA1; //declara un boton de joystick
	/////////////////////////////////////////////////
	

	 ///////constructor de la clase
	 public OI() {
		 
		 ////////////los dos jostick a utilizar(controles de xbox)////////////////
		 Stick1 = new Joystick(0);  //une el josytick al objeto
		 Stick2 = new Joystick(1);  //une el josytick al objeto
		 ////////////////////////////////////////////////////////////////////////
		
		 /////////////intake/////////////////////////(driver2)
		 
		 ///////////////////////////////////////////
		 
		 /////////////elevador///////////////////////(driver2)
		 
		 ///////////////////////////////////////////
		 
		 ////////////chasis/////////////////////////(driver1)
		///usa el stick izquierdo y los triggers analogos para el manejo del drive
		 XboxA1 = new JoystickButton(Stick1, 1);
		 //XboxA1.whileHeld(new But_ball_in());  //mientras este presionado hace el comando
		 //XboxA1.whenReleased(new Shifter_change_down()); //solo hace el comando cuando soltemos
		 //XboxA1.whenPressed(command);  //solo hace el comando cuando presionamos
		 XboxA1.whenReleased(new Robot_Drive_ChangePolarity());  //mientras este presionado hace el comando
		 ///////////////////////////////////////////
		 
		 ////////////escalador///////////////////////(driver1)
		 ///usa el stick derecho para subir y bajar
		 ///////////////////////////////////////////
		 
		 ///////////////topes////////////////////////(driver1)
		 XboxY1 = new JoystickButton(Stick1, 4);  //Le decimos que boton del joystick es
		 XboxY1.whenReleased(new Robot_Topes_Open());  //mientras este presionado hace el comando
		 ////////////////////////////////////////////

		 
	    }
	
	 
}
