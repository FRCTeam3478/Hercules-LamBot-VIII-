package org.usfirst.frc.team3478.robot.subsystems;

import org.usfirst.frc.team3478.robot.Robot;
import org.usfirst.frc.team3478.robot.RobotMap;
import org.usfirst.frc.team3478.robot.commands.Colocador_Release;
import org.usfirst.frc.team3478.robot.commands.Colocador_rampa_up;
import org.usfirst.frc.team3478.robot.commands.Shooter_main;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Colocador extends Subsystem {
	
	static boolean flagx=false;
	static boolean flagx2=false;
	static boolean flagx3=false;
	public static double Timer_start = 0;
	public static double Timer_start2 = 0;
	public static double Timer_start3 = 0;
	
	Solenoid Engranes_puerta = RobotMap.Engranes_puerta;
	Solenoid Engranes_empujador = RobotMap.Engranes_empujador;
	Solenoid posPiston = RobotMap.PosShooter;
	
	Command Stop_colocador;
	
	public void initDefaultCommand() {

	}
	
	public boolean Check_timer(){
    	if(((System.currentTimeMillis() - Timer_start)) >= 400){
    		return(true);
    	}else{
    		return(false);
    	}
    }
	
	public boolean Check_timer2(){
    	if(((System.currentTimeMillis() - Timer_start2)) >= 3000){
    		return(true);
    	}else{
    		return(false);
    	}
    }
	
	public boolean Check_timer3(){
    	if(((System.currentTimeMillis() - Timer_start3)) >= 200){
    		return(true);
    	}else{
    		return(false);
    	}
    }
	
	 public void Reset_vars(){
		 flagx=false;
		 flagx2=false;
		 flagx3=false;
		 Timer_start=System.currentTimeMillis();
		 Timer_start2=System.currentTimeMillis();
		 Timer_start3=System.currentTimeMillis();
		 stop();
	    }
	 
	public void Reset_state_push(){
		flagx=false;
		flagx2=false;
	}
	
	public void PutGear(){
		if(flagx2==false){
			Timer_start=System.currentTimeMillis();
			Timer_start2=System.currentTimeMillis();
			flagx2=true;
		}
		//Este codigo activa el engrane de la puerta,se espera un rato y luego activa el piston que lo empuja.
		Engranes_puerta.set(true);
		if(flagx==false){
		if(Check_timer()){
			Engranes_empujador.set(true);
			flagx=true;
		}
		}
		if(Check_timer2()){ //para que se cierre despues de 3 segundos
			Stop_colocador = new Colocador_Release();
			Stop_colocador.start();
		}
		if( Robot.oi.Stick1.getRawButton(2)==true){
			Timer_start2=System.currentTimeMillis();
		}
	}

	public void stop(){  //para y posiciona los dos pistones en su modo Default
		Engranes_empujador.set(false);
		Engranes_puerta.set(false);
		flagx=false;
		flagx2=false;
	}
	
	//funcion para cambiar a la posicion de disparo de abajo
    public void pos0(){  //Posicion 1 que es la de abajo
		posPiston.set(false);
		SmartDashboard.putBoolean("rampa",false);
		flagx3=false;
	}
    
    ////funcion para cambiar a la posicion de disparo de arriba
	public void pos1(){
		if(flagx3==false){
			posPiston.set(true);
			SmartDashboard.putBoolean("rampa",true);
			Timer_start3=System.currentTimeMillis();
			flagx3=true;
		}
		if(Check_timer3()){ //para que se cierre despues de 3 segundos
			Stop_colocador = new Colocador_rampa_up();
			Stop_colocador.start();
		}
		if( Robot.oi.Stick1.getRawButton(5)==true){
			Timer_start3=System.currentTimeMillis();
		}
	}
	
	

}
