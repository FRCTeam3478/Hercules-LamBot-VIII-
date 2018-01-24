/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3478.robot.subsystems;

import org.usfirst.frc.team3478.robot.RobotMap;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;


public class Robot_Topes extends Subsystem {
	
	private Servo servo1;

	public void initDefaultCommand() {
		//nada
	}
	
	////para poner todo en la posicion inicial/////
	public void InitDefaultState() {
		Close_tope();
	}
	//////////////////////////////////////////////
	
	//////////constructor de la clase/////////////////////
	public Robot_Topes(){
		servo1 = RobotMap.Servo1;
	}
	//////////////////////////////////////////////////////
	
	/////////funcion para liberar los topes///////////////
	public void Release_tope() {
		servo1.setAngle(180);
	}
	//////////////////////////////////////////////////////
	
	/////////funcion para cerrar el servo///////////////
	public void Close_tope() {
		servo1.setAngle(0);
	}
	//////////////////////////////////////////////////////
	
	
}
