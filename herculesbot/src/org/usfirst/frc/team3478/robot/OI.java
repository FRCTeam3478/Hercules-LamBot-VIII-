package org.usfirst.frc.team3478.robot;


import org.usfirst.frc.team3478.robot.commands.Drive_chassis_polarity;
import org.usfirst.frc.team3478.robot.commands.Drive_shifterdown;
import org.usfirst.frc.team3478.robot.commands.Intake_in;
import org.usfirst.frc.team3478.robot.commands.Intake_out;
import org.usfirst.frc.team3478.robot.commands.Shooter_move_position0;
import org.usfirst.frc.team3478.robot.commands.Shooter_move_position1;
import org.usfirst.frc.team3478.robot.commands.Shooter_setspeed_move;
import org.usfirst.frc.team3478.robot.commands.Shooter_setspeed_none;
import org.usfirst.frc.team3478.robot.commands.Torreta_activateauto;
import org.usfirst.frc.team3478.robot.commands.Colocador_PutGear;
import org.usfirst.frc.team3478.robot.commands.Colocador_rampa_down;
import org.usfirst.frc.team3478.robot.commands.Colocador_rampa_up;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;


public class OI {
	//En esta clase vamos a poner todo lo de los joystick y como lo ligamos a los comandos
	
		 public Joystick Stick1; //declara un joystick
		 public Joystick Stick2; //declara un joystick
		 public JoystickButton XboxA1; //declara un boton de joystick
		 public JoystickButton XboxB1; //declara un boton de joystick
		 public JoystickButton XboxX1; //declara un boton de joystick
		 public JoystickButton XboxY1; //declara un boton de joystick
		 public JoystickButton XboxRB1; //declara un boton de joystick
		 public JoystickButton XboxLB1; //declara un boton de joystick
		 public JoystickButton XboxA2; //declara un boton de joystick
		 public JoystickButton XboxB2; //declara un boton de joystick
		 public JoystickButton XboxLB2; //declara un boton de joystick
		 public JoystickButton XboxRB2; //declara un boton de joystick
		 public JoystickButton XboxX2; //declara un boton de joystick
		 
		 public OI() {
			///Aqui en el constructor vamos a ligar los botones a los comandos
			 	
			 	Stick1 = new Joystick(0);  //une el josytick al objeto
			 	Stick2 = new Joystick(1);  //une el josytick al objeto
			 	
			 	////////////info del joystick 1/////////////////////////////////////////////////
			 	XboxA1 = new JoystickButton(Stick1, 1);  //Le decimos que boton del joystick es
			 	//XboxA.whileHeld(new But_ball_in());  //mientras este presionado hace el comando
			 	//XboxX.whenReleased(new Shifter_change_down()); //solo hace el comando cuando soltemos
			 	//XboxA1.whenPressed(command);  //solo hace el comando cuando presionamos
			 	XboxA1.whenReleased(new Intake_in());  //mientras este presionado hace el comando
			 	XboxB1 = new JoystickButton(Stick1, 2);
			 	//XboxB1.whileHeld(new Intake_out()); //solo hace el comando cuando soltemos
			 	XboxX1 = new JoystickButton(Stick1, 3);
			 	XboxX1.whenReleased(new Drive_shifterdown()); //solo hace el comando cuando soltemos
			 	XboxY1 = new JoystickButton(Stick1, 4);
			 	XboxY1.whenReleased(new Drive_chassis_polarity()); //solo hace el comando cuando soltemos
			 	//XboxRB1 = new JoystickButton(Stick1, 6);
			 	XboxB1.whenPressed(new Colocador_PutGear()); //solo hace el comando cuando soltemos
			 	XboxLB1 = new JoystickButton(Stick1, 5);
				XboxLB1.whenPressed(new Colocador_rampa_down()); //solo hace el comando cuando soltemos
				//XboxRB1 = new JoystickButton(Stick1, 6);
				//XboxRB1.whenPressed(new Colocador_rampa_up()); //solo hace el comando cuando soltemos
			 	////////////////////////////////////////////////////////////////////////////////
			 	
			/////info del joystick 2////////////////////////////////////////////////////////
			XboxA2 = new JoystickButton(Stick2, 1);
			XboxA2.whenReleased(new Shooter_setspeed_move()); //solo hace el comando cuando soltemos
			XboxB2 = new JoystickButton(Stick2, 2);
			XboxB2.whenReleased(new Shooter_setspeed_none()); //solo hace el comando cuando soltemos
			XboxLB2 = new JoystickButton(Stick2, 5);
			XboxLB2.whenReleased(new Shooter_move_position0()); //solo hace el comando cuando soltemos
			XboxRB2 = new JoystickButton(Stick2, 6);
			XboxRB2.whenReleased(new Shooter_move_position1()); //solo hace el comando cuando soltemos
			XboxX2 = new JoystickButton(Stick2, 3);
		 	XboxX2.whenReleased(new Torreta_activateauto()); //solo hace el comando cuando soltemos
			/////////////////////////////////////////////////////////////////////////////////
			 		 	
			 	

		    }
}
