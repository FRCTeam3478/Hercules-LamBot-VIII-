package org.usfirst.frc.team3478.robot.subsystems;

import org.usfirst.frc.team3478.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake extends Subsystem {
	
	//talond que se va usar para el intake
	CANTalon RollerIntake = RobotMap.RollerIntake;
	Shooter objectshooter = new Shooter();
	
	static boolean flagx1=false;
	
	public void initDefaultCommand() {

	}
	
	 public void Reset_vars(){
		 flagx1=false;
		 stop();
	 }

	
	public void stop(){  //para el Intake
		if(objectshooter.state3==0){ //solo si no esta activo en el shooter
		RollerIntake.set(0);
		}
	}
	
	public void RollerIn(){
		if(objectshooter.state3==0){  //solo si no esta activo en el shooter
				//Activa el motor para que gire hacia adentro
				if(flagx1==false){
					RollerIntake.set(1);
					SmartDashboard.putBoolean("intake", true);
					flagx1=true;
				}else{
					RollerIntake.set(0);
					SmartDashboard.putBoolean("intake", false);
					flagx1=false;
				}
		}
	}
	
	public void reset_rollerflag(){
		SmartDashboard.putBoolean("intake", false);
		flagx1=false;
	}
	
	public void RollerOut(){
		//Activa el motor para que gira hacia afuera
		RollerIntake.set(-1);
		
	}
	

}
