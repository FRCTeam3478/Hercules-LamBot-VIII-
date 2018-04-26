package org.usfirst.frc.team3478.robot.subsystems;

import org.usfirst.frc.team3478.robot.Robot;
import org.usfirst.frc.team3478.robot.RobotMap;
import org.usfirst.frc.team3478.robot.commands.Colocador_Release;
import org.usfirst.frc.team3478.robot.commands.Intake_resetflag;
import org.usfirst.frc.team3478.robot.commands.Torreta_activateauto2;
import org.usfirst.frc.team3478.robot.commands.Torreta_disableauto2;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Autonomo  extends Subsystem {
	
	////chasis
	CANTalon driveL1 = RobotMap.DriveL1;
	CANTalon driveL2 = RobotMap.DriveL2;
	CANTalon driveR1 = RobotMap.DriveR1;
	CANTalon driveR2 = RobotMap.DriveR2;
	Encoder driveEL = RobotMap.DriveEL;
	Encoder driveER = RobotMap.DriveER;
	Solenoid ShifterSolenoid = RobotMap.Cambios;
	
	///intake
	CANTalon RollerIntake = RobotMap.RollerIntake;
	
	////engrane
	Solenoid Engranes_puerta = RobotMap.Engranes_puerta;
	Solenoid Engranes_empujador = RobotMap.Engranes_empujador;
	
	////torreta
	CANTalon giroTorreta = RobotMap.GiroTorreta;
	DigitalInput limit_Torreta = RobotMap.Limit_torreta;
	public static double stablevar = 0;
	public static double Position_giro=0;
	
	////shooter
	CANTalon shooter = RobotMap.Shooter;
	CANTalon banda = RobotMap.BandaTorreta;
	CANTalon hoppermotor = RobotMap.Hoppermotor;

	////para el pid
	public static double pre_input = 0;
	public static double integral_val = 0;
	public static double max_pid_output = 100;
	public static double min_pid_output = -100;
	public static double temp_margin = 50;
	
	//camara
	private NetworkTable camerapoints = NetworkTable.getTable("Pitable");
	double y_camera = 0;
	
	public static double Timer_start = 0;
	public static boolean Timer_emerg = false;
	public static double vel_ramp = 0;
	public static double ramp_val = 0.01;
	
	///////para el disparo que modifica velocidad
	public static double vel_shootwheel = 0;
	
	public void initDefaultCommand() {

	}
	
	public void Check_timer(){
    	if(((System.currentTimeMillis() - Timer_start)/1000) >= 15){
    		Timer_emerg = true;
    	}    	
    }
	
	public void Stop_Motors(){
	    if(vel_ramp > 0){
	    	while((vel_ramp > 0) && (Timer_emerg==false)){
	        	vel_ramp = vel_ramp - ramp_val ;
	        	if(vel_ramp < 0){vel_ramp = 0;}
	        	driveR1.set(-vel_ramp);
	        	driveR2.set(-vel_ramp);
	        	driveL1.set(vel_ramp);
	        	driveL2.set(vel_ramp);
	        	Check_timer();
	        }
	    }else{
	    	while((vel_ramp < 0) && (Timer_emerg==false)){
	        	vel_ramp = vel_ramp + ramp_val;
	        	if(vel_ramp > 0){vel_ramp = 0;}
	        	driveR1.set(-vel_ramp);
	        	driveR2.set(-vel_ramp);
	        	driveL1.set(vel_ramp);
	        	driveL2.set(vel_ramp);
	        	Check_timer();
	        }
	    }
	    }
	    
	    
	    public void Stop_Motors2(){
	    	  //vel_ramp = 0;
	          driveR1.set(0);
	          driveR2.set(0);
	          driveL1.set(0);
	          driveL2.set(0); 
	        }
	    
	    public double ramp_fun(double x, double set_point, double ramp){
	    	if(x < set_point){
	    		x = x + ramp;
	    	}else if(x > set_point){
	    		x = x - ramp;
	    	}else{
	    		x = set_point;
	    	}
	    	return(x);
	    }
	    
	    public double map(double x, double in_min, double in_max, double out_min, double out_max){
	    	return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
	    }
	    
	    public void Move_robot(double x1,double x2,double distance,double speed){
	    	//adelante x1 = -1 x2 = 1
	    	//atras x1 = 1 x2 = -1
	    	//giro derecha x1 = 1 x2 = 1
	    	//giro izquierda x1 = -1 x2 = -1
	    	driveEL.reset();
	    	driveER.reset();
	    	while((abs(driveEL.getDistance()) <= distance && abs(driveER.getDistance()) <= distance ) && (Timer_emerg==false) ){
	    		vel_ramp = ramp_fun(vel_ramp,speed,ramp_val);
	        	driveL1.set((vel_ramp*x2*0.96));  ///0.955 porque esta chueco el chassis
	        	driveL2.set((vel_ramp*x2*0.96));  ///0.955 porque esta chueco el chassis
	    		driveR1.set(((vel_ramp)*x1));
	        	driveR2.set(((vel_ramp)*x1));
	        	Check_timer();
	    	}
	    }
	    
	    public double abs(double x){
	    	if(x<0){
	    		x=x*-1;
	    	}
	    	return (x);
	    }
	
	public void Autostart() {		
		
		Timer_start = System.currentTimeMillis();
    	Timer_emerg = false;
    	
    	int mode = (int) Robot.chooser.getSelected();
    	boolean waiter=false;
    	
    	switch(mode){
    	
    		case 1:
    			///nada
    		break;
    		
    		case 2: //1 engrane centro
    			ShifterSolenoid.set(false); 
    	    	Move_robot(-1,1,75,0.7);
    	    	Stop_Motors2();
    	    	PutGear();
    	    	Timer.delay(1);
    	    	Move_robot(1,-1,20,0.7);
    	    	Stop_Motors2();
    	    	CloseGear();
    		break;
    		
    		case 3: //engrane izquierda(se cambio para moverse adelante)
    			ShifterSolenoid.set(false); 
    	    	Move_robot(1,-1,81,1);
    	    	Stop_Motors2();
    		break;
    		
    		case 4: //engrane derecha
    			ShifterSolenoid.set(false); 
    	    	Move_robot(-1,1,81,0.7);
    	    	Stop_Motors2();
    	    	Move_robot(1,1,20,0.7);
    	    	Stop_Motors2();
    	    	Move_robot(-1,1,38,0.7);
    	    	Stop_Motors2();
    	    	PutGear();
    	    	Timer.delay(1);
    	    	Move_robot(1,-1,20,0.7);
    	    	Stop_Motors2();
    	    	CloseGear();
    		break;
    		
    		case 5:  //autonomo 10 pelotas boiler izquierdo
    			ShifterSolenoid.set(false); 
    	    	Move_robot(1,-1,78,1);  //75
    	    	Stop_Motors2();
    	    	move_torreta(0.03);
    	    	start_shooter(761);  //761
    	    	Move_robot(1,1,28,0.7);  //28
    	    	Stop_Motors2();
    	    	Move_robot(1,-1,80,1);
    	    	Stop_Motors2();
    	    	Move_robot(-1,1,4,1);
    	    	Stop_Motors2();
    	    	/*
    	    	waiter=false;
    	    	while((Timer_emerg==false) && waiter==false){
    	    		Check_timer();
    	    		waiter = start_shooter(770);
    	    		//SmartDashboard.putString("Autotest1",String.valueOf(waiter));
    	    	}
    	    	*/
    	    	//Timer.delay(1);  //para que caigan las pelotas
    	    	shoot_infinite();
    	    	Reset_autotorreta();
    	    	waiter=false;
    	    	while((Timer_emerg==false) && waiter==false){
    	    		Check_timer();
    	    		//waiter = movetorretaauto();
    	    		movetorretaauto();
    	    		//SmartDashboard.putString("Autotest1",String.valueOf(waiter));
    	    	}
    	    	
    	    	off_shooter();

    		break;
    		
    		case 6:  //autonomo 10 pelotas boiler derecho
    			ShifterSolenoid.set(false); 
    	    	Move_robot(1,-1,75,1);  //73
    	    	Stop_Motors2();
    	    	move_torreta(0.62);
    	    	start_shooter(761);  //757
    	    	Move_robot(-1,-1,28,0.7);  //29
    	    	Stop_Motors2();
    	    	Move_robot(1,-1,80,1);
    	    	Stop_Motors2();
    	    	Move_robot(-1,1,4,1);
    	    	Stop_Motors2();
    	    	/*
    	    	waiter=false;
    	    	while((Timer_emerg==false) && waiter==false){
    	    		Check_timer();
    	    		waiter = start_shooter(770);
    	    		//SmartDashboard.putString("Autotest1",String.valueOf(waiter));
    	    	}
    	    	*/
    	    	//Timer.delay(1);  //para que caigan las pelotas
    	    	shoot_infinite();
    	    	Reset_autotorreta();
    	    	waiter=false;
    	    	while((Timer_emerg==false) && waiter==false){
    	    		Check_timer();
    	    		//waiter = movetorretaauto();
    	    		movetorretaauto();
    	    		//SmartDashboard.putString("Autotest1",String.valueOf(waiter));
    	    	}
    	    	
    	    	off_shooter();

    		break;
    		
    		case 7: //7 centro derecha
    			ShifterSolenoid.set(false); 
    	    	Move_robot(-1,1,75,0.7);
    	    	Stop_Motors2();
    	    	PutGear();
    	    	Timer.delay(1);
    	    	Move_robot(1,-1,20,0.7);
    	    	Stop_Motors2();
    	    	CloseGear();
    	    	
    	    	move_torreta(0.03);
    	    	start_shooter(950);
    	    	Reset_autotorreta();
    	    	waiter=false;
    	    	while((Timer_emerg==false) && waiter==false){
    	    		Check_timer();
    	    		waiter = movetorretaauto();
    	    		//SmartDashboard.putString("Autotest1",String.valueOf(waiter));
    	    	}
    	    	shoot_infinite();
    	    	while(Timer_emerg==false){
    	    		Check_timer();
    	    	}
    	    	off_shooter();
    		break;
    		
    		case 8: //8 centro izquierda
    			ShifterSolenoid.set(false); 
    	    	Move_robot(-1,1,75,0.7);
    	    	Stop_Motors2();
    	    	PutGear();
    	    	Timer.delay(1);
    	    	Move_robot(1,-1,20,0.7);
    	    	Stop_Motors2();
    	    	CloseGear();
    	    	
    	    	move_torreta(0.62);
    			start_shooter(950);
    	    	Reset_autotorreta();
    	    	waiter=false;
    	    	while((Timer_emerg==false) && waiter==false){
    	    		Check_timer();
    	    		waiter = movetorretaauto();
    	    		//SmartDashboard.putString("Autotest1",String.valueOf(waiter));
    	    	}
    	    	shoot_infinite();
    	    	while(Timer_emerg==false){
    	    		Check_timer();
    	    	}
    	    	off_shooter();
    		break;
    		
    		case 9: //9 shoot test
    			shoot_infinite();
    	    	Reset_autotorreta();
    	    	waiter=false;
    	    	vel_shootwheel = 757;
    	    	while((Timer_emerg==false) && waiter==false){
    	    		Check_timer();
    	    		shooter.set(vel_shootwheel);
    	    		//waiter = movetorretaauto();
    	    		movetorretaauto2();
    	    		SmartDashboard.putString("shootwheelspeed",String.valueOf(vel_shootwheel));
    	    	}
    	    	
    	    	off_shooter();
    		break;
    	
    	}
    	
    	Finish_all();
		
    }
	
	
	public void Finish_all(){
		off_shooter();
		CloseGear();
		Stop_Motors2();
	}
	
	
	/////funciones para poner engrane/////////////////
	public void PutGear(){
		Engranes_puerta.set(true);
		boolean states = false;
		double temptime=System.currentTimeMillis();
		while(states==false){
		if(Check_timer2(temptime)>400 && (Timer_emerg==false)){ //para que se cierre despues de 3 segundos
			Engranes_empujador.set(true);
			states = true;
		}
		Check_timer();
		}	
	}
	
	public void CloseGear(){
		Engranes_puerta.set(false);
		Engranes_empujador.set(false);
	}
	
	public double Check_timer2(double temptime){
    	return (System.currentTimeMillis() - temptime);
	}
	//////////////////////////////////////////////////
	
	
	/////////////funcion para apuntar torreta/////////////////////////////
	public void move_torreta(double pos){
		Position_giro=pos;
		giroTorreta.set(Position_giro);  //mueve el motor por vueltas
	}

	
	public boolean movetorretaauto(){  //comienza la torreta

			double giro =0;
			boolean xxzstate=false;
		
			boolean newdatax = false;
			//espera a que haya nueva data
			if(camerapoints.getNumber("dataready",1)==0){
				newdatax = true;
			}
			if(newdatax == true){
				y_camera = camerapoints.getNumber("imagew",0);
				double y_position = camerapoints.getNumber("xposition",0);  //leemos donde esta el centro
				////suponemos que vamos a movernos 180 grados(0.5 de vuelta)
				giro = PID_fun((y_camera/2),y_position,0.03,0.01,0.01); //leemos el pid (-100 a 100)
				//SmartDashboard.putString("Autotest2",String.valueOf(giro));
				SmartDashboard.putString("Autogiro",String.valueOf(giro));
				giro = (giro/100)*(-0.1); 
				if(giro==0){
					stablevar=stablevar+1;
					if(stablevar>2){
						xxzstate=true;
					}
				}
				camerapoints.putNumber("dataready",1);
			}
		
		///********checar aqui si los switches tienen  pull up y a donde gira
		if(limit_Torreta.get()==false && Position_giro<=0.1 && giro<0){
			Position_giro=0;
			giroTorreta.setPosition(0); 
			giro=0;
		}

		if(Position_giro<=0 && giro<0){
			giro=0;
		}
		
		if(Position_giro>=0.65 && giro>0){
			giro=0;
		}
		
		
		Position_giro=Position_giro+giro;
		giroTorreta.set(Position_giro);  //mueve el motor por vueltas
		
		return(xxzstate);
			
		}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	
	////////////////////funcion de torreta automatica que tambien modifica velocidad de la llanta///////
	public boolean movetorretaauto2(){  //comienza la torreta

		double giro =0;
		boolean xxzstate=false;
	
		boolean newdatax = false;
		//espera a que haya nueva data
		if(camerapoints.getNumber("dataready",1)==0){
			newdatax = true;
		}
		if(newdatax == true){
			y_camera = camerapoints.getNumber("imagew",0);
			double y_position = camerapoints.getNumber("xposition",0);  //leemos donde esta el centro
			vel_shootwheel = (camerapoints.getNumber("distancecam",vel_shootwheel)*2.8358)+490.6;///modifica la velocidad del tiro
			////suponemos que vamos a movernos 180 grados(0.5 de vuelta)
			giro = PID_fun((y_camera/2),y_position,0.03,0.01,0.01); //leemos el pid (-100 a 100)
			//SmartDashboard.putString("Autotest2",String.valueOf(giro));
			SmartDashboard.putString("Autogiro",String.valueOf(giro));
			giro = (giro/100)*(-0.1); 
			if(giro==0){
				stablevar=stablevar+1;
				if(stablevar>2){
					xxzstate=true;
				}
			}
			camerapoints.putNumber("dataready",1);
		}
	
	///********checar aqui si los switches tienen  pull up y a donde gira
	if(limit_Torreta.get()==false && Position_giro<=0.1 && giro<0){
		Position_giro=0;
		giroTorreta.setPosition(0); 
		giro=0;
	}

	if(Position_giro<=0 && giro<0){
		giro=0;
	}
	
	if(Position_giro>=0.65 && giro>0){
		giro=0;
	}
	
	
	Position_giro=Position_giro+giro;
	giroTorreta.set(Position_giro);  //mueve el motor por vueltas
	
	return(xxzstate);
		
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void Reset_autotorreta(){
		 pre_input=(y_camera/2);  //resetea
    	 integral_val=0;  //resetea
    	 stablevar = 0;
    	 camerapoints.putNumber("dataready",1);
    	 
    	 ////calibracion de la camara///////////
    	 //camerapoints.putNumber("greenhl",1);
    	 //camerapoints.putNumber("greenhh",1);
    	 //camerapoints.putNumber("greensl",1);
    	 //camerapoints.putNumber("greensh",1);
    	 //camerapoints.putNumber("greenvs",1);
    	 //camerapoints.putNumber("greenvh",1);
    	 camerapoints.putNumber("minarea",70);
    	 //camerapoints.putNumber("targetmedida",0);
    	 //camerapoints.putNumber("imgselect",0);
    	 camerapoints.putNumber("changevalues",1);//hace los cambios
    	 ////////////////////////////////////////
	 }
	
	
	public double PID_fun(double setpoint,double actual_point,double kp,double ki,double kd){
    	double output_val = 0;  //la salida
        double dt = 0.1;  //tiempo que tarda entre medidas
        double epsilon = 2;  //tolerancia
        
      //para tener un pid variable a cierto rango
    	double kp2 = kp*3;
    	double kd2 = kd*3;
        double range_tol = 5;
        
        //obtiene el error
        double error = setpoint - actual_point;
        if(abs(error) <= epsilon){
        	error = 0;
        	pre_input=(y_camera/2);
        	integral_val=0;  //resetea
        	return(0);
        }else{
        	///calcula la integral
        	//considerando rectangulos pequenos donde dt es lo ancho y el error lo largo
        	integral_val=integral_val + (error*dt);  //el area al graficar la variable contra tiempo(y se suma con lo que ya hay)
        	////para limitar la integral
        	if(integral_val> (max_pid_output-temp_margin)){
        		integral_val = (max_pid_output-temp_margin);}
        	if(integral_val< (min_pid_output+temp_margin)){
        		integral_val = (min_pid_output+temp_margin);}
        }
                 
        //calcula la derivada
        double derivative_val = (actual_point - pre_input)/dt;  //la funcion dx/dt donde dx es la diferencia entre el ultimo error y el nuevo

        if( abs(error)>range_tol){   //si esta muy lejos
        	//calculates the output
        	output_val = (kp2*error) + (ki*integral_val) - (kd2*derivative_val);
        }
        
        if( abs(error)<=range_tol){  //si esta muy cerca
        	//calculates the output
        	output_val = (kp*error) + (ki*integral_val) - (kd*derivative_val);
        }
       
        //Saturation filter, para asegurar que no pase los valores maximos ni minimos
        //sirve como una rampa tambien para evitar cambios bruscos
        if(output_val > max_pid_output){output_val=max_pid_output;}
        if(output_val < min_pid_output){output_val=min_pid_output;}

        //update error, guardando el nuevo error que sera el viejo
        pre_input =actual_point;

        return(output_val); ///regresa el rewsultado del pid
    }
	 //////////////////////////////////////////////////////////////////////
	
	
	////////////shooter//////////////////////////////////////////////////////
	 public boolean start_shooter(double Shoot_speed) {
	   	 ////el encoder esta directo al talon por lo que solo hay que modificar el set point
	   	double Tol_var = 0.1;
	   	   shooter.set(Shoot_speed); //fija la velocidad que queremos en pulsos por cada 100 milisegundos
	       double actual_speed = 0;
	       actual_speed = shooter.getSpeed(); 
	       if(Shoot_speed != 0){
	       if( (actual_speed <= (Shoot_speed + (Shoot_speed*Tol_var))) && (actual_speed >= (Shoot_speed - (Shoot_speed*Tol_var)))){
	    	   return(true);
	       }else{
	    	   return(false);
	       }
	       }else{
	    	   return(false);
	       }
	       }
	 
	 public void off_shooter() {
	   	 shooter.set(0); //fija la velocidad que queremos en pulsos por cada 100 milisegundos
	   	 banda.set(0);
		 RollerIntake.set(0);
		 hoppermotor.set(0);
	       }
	 
	 public void shoot_infinite(){
		 banda.set(1);
	     RollerIntake.set(1);
	     hoppermotor.set(-1);
	 }
	 
	 public void Resetall(){
		 
		 //nada
		 
	 }
	/////////////////////////////////////////////////////////////////////////
	
	
}


