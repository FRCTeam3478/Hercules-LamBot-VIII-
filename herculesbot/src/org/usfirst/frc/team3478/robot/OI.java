
package org.usfirst.frc.team3478.robot;

import org.usfirst.frc.team3478.robot.commands.Robot_Drive_ChangePolarity;
import org.usfirst.frc.team3478.robot.commands.Robot_Elevador_AutoDown;
import org.usfirst.frc.team3478.robot.commands.Robot_Elevador_AutoUp;
import org.usfirst.frc.team3478.robot.commands.Robot_Elevador_AutoUpper;
import org.usfirst.frc.team3478.robot.commands.Robot_Escalador_MainMove;
import org.usfirst.frc.team3478.robot.commands.Robot_Intake_RotateLeft;
import org.usfirst.frc.team3478.robot.commands.Robot_Intake_RotateRight;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
	
	
	/////objetos que vamos a utilizar/////////////////
	public Joystick Stick1; //declara un joystick
	public Joystick Stick2; //declara un joystick
	public Joystick Stick3; //declara un joystick
	public JoystickButton XboxY1; //declara un boton de joystick
	public JoystickButton XboxA1; //declara un boton de joystick
	public JoystickButton XboxRB1; //declara un boton de joystick
	public JoystickButton XboxLB1; //declara un boton de joystick
	
	public JoystickButton XboxA2; //declara un boton de joystick
	public JoystickButton XboxB2; //declara un boton de joystick
	public JoystickButton XboxY2; //declara un boton de joystick
	public JoystickButton XboxX2;
	/////////////////////////////////////////////////
	

	 ///////constructor de la clase
	 public OI() {
		 
		 ////////////los dos jostick a utilizar(controles de xbox)////////////////
		 Stick1 = new Joystick(0);  //une el josytick al objeto
		 Stick2 = new Joystick(1);  //une el josytick al objeto
		 Stick3 = new Joystick(2);  //une el josytick al objeto
		 ////////////////////////////////////////////////////////////////////////
		
		 /////////////intake/////////////////////////(driver 1 y 2)
		 /// Utiliza stick izquierdo para subir y bajar (driver 2)
		 /// Utiliza triggers para comer y escupir y botones para girar (driver 1) 
		 XboxLB1 = new JoystickButton(Stick1, 5);
		 XboxLB1.whileHeld(new Robot_Intake_RotateLeft());  //mientras este presionado hace el comando
		 XboxRB1 = new JoystickButton(Stick1, 6);
		 XboxRB1.whileHeld(new Robot_Intake_RotateRight());  //mientras este presionado hace el comando
		 ///////////////////////////////////////////
		 
		 /////////////elevador///////////////////////(driver2)
		 ////triggers para subir y bajar
		 XboxA2 = new JoystickButton(Stick2, 1);
		 XboxA2.whenReleased(new Robot_Elevador_AutoDown());  //mientras este presionado hace el comando
		 XboxB2 = new JoystickButton(Stick2, 2);
		 XboxB2.whenReleased(new Robot_Elevador_AutoUp());  //mientras este presionado hace el comando
		 XboxY2 = new JoystickButton(Stick2, 4);
		 XboxY2.whenReleased(new Robot_Elevador_AutoUpper());  //mientras este presionado hace el comando
		 ///////////////////////////////////////////
		 
		 ////////////chasis/////////////////////////(driver1)
		///usa el stick izquierdo y los triggers analogos para el manejo del drive
		 XboxA1 = new JoystickButton(Stick1, 1);
		 //XboxA1.whileHeld(new But_ball_in());  //mientras este presionado hace el comando
		 //XboxA1.whenReleased(new Shifter_change_down()); //solo hace el comando cuando soltemos
		 //XboxA1.whenPressed(command);  //solo hace el comando cuando presionamos
		 XboxA1.whenReleased(new Robot_Drive_ChangePolarity());  //mientras este presionado hace el comando
		 ///////////////////////////////////////////
		 
		 ////////////escalador///////////////////////(driver 2)
		 ///usa los triggers para subir y bajar y solo jala si esta presionado x
		 XboxX2 = new JoystickButton(Stick2, 3);
		 XboxX2.whileHeld(new Robot_Escalador_MainMove());  //mientras este presionado hace el comando
		 ///////////////////////////////////////////
		 
	    }
	
	 
}
