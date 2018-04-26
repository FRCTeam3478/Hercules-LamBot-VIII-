package org.usfirst.frc.team3478.robot.subsystems;

import org.usfirst.frc.team3478.robot.Robot;
import org.usfirst.frc.team3478.robot.RobotMap;
import org.usfirst.frc.team3478.robot.commands.Intake_resetflag;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Escalador extends Subsystem {
	
	TalonSRX elevador = RobotMap.Escalador;
	TalonSRX RollerIntake = RobotMap.RollerIntake;
	
	Command Intakecommand;
	
	public static double Vel_L_act =0;
	public static boolean flagx1=false;
	
	
	public void initDefaultCommand() {

	}
	
	
    public void Reset_vars(){
    	Vel_L_act =0;
    	flagx1=false;
    	stop();
    }

	
	public void start(){	//Activa el motor que va elevar el robot en la cuerda
		double Zizq=0;
    	double Zder=0;
    	double Z3;
    	double tolerancia = 0.2;
		//Zizq = Robot.oi.Stick1.getRawAxis(5);
    	Zder = Robot.oi.Stick2.getRawAxis(5);
    	Z3 = (Zder - Zizq);
    	
    	if((Z3<=tolerancia && Z3 > 0) || (Z3 >= -tolerancia && Z3 < 0)){
			Z3=0; //quita el error del jostick
		}else{
			if(Z3>0){
				///mappea los valores para que no inicie arriba de la tolerancia sino en 0
				Z3 = map(Z3,tolerancia,1,0,1);
			}
			if(Z3<0){
				///mappea los valores para que no inicie arriba de la tolerancia sino en 0
		    	Z3 = map(Z3,-tolerancia,-1,0,-1);	
			}
		}
    	
    	if(Z3>0){
    		Z3=0;
    	}else if(Z3<0){
    		RollerIntake.set(ControlMode.PercentOutput,0);
    		flagx1=true;
    	}else{
    		if(flagx1==true){
    		Intakecommand = new Intake_resetflag();
 		    Intakecommand.start();
 		    flagx1=false;
    		}
    	}
    	
		Vel_L_act = ramp_fun(Vel_L_act, -Z3 , 0.1);
		elevador.set(ControlMode.PercentOutput,Vel_L_act);
	}
	
	public void stop(){// si se interrumpe o se acaba el match el motor para
		elevador.set(ControlMode.PercentOutput,0);
	}
	
	
    public double ramp_fun(double x, double set_point, double ramp){
    	double x1 = make_divisible(x,ramp);
    	
    	if(x1 < set_point){
    		x1 = x + ramp;
    	}else if(x1 > set_point){
    		x1 = x - ramp;
    	}else{
    		x1 = set_point;
    	}
    	
    	return(x1);
    }
    
    public double make_divisible(double x,double div){  //operacion modulo para flotantes
    	double resi = 0;
    	int resi_int = 0;
    	double x1 = 0;
    	
    	if(x!=0){
    		resi = x/div;
    	    resi_int = (int)(resi);
    	    x1 = x - (div * resi_int);  //el residuo
    	    x = x-x1;
    	}
    	
    	return(x);
    }
    
    ///para mappear los valores del stick
    public double map(double x, double in_min, double in_max, double out_min, double out_max){
    	return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }
    
}
	
	


