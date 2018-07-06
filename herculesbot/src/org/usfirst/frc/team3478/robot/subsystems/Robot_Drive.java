/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3478.robot.subsystems;

import org.usfirst.frc.team3478.robot.Robot;
import org.usfirst.frc.team3478.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot_Drive extends Subsystem {

	private static final double TOLERANCE=0.15;  //tolerancia del joystick
	private static final double TOLERANCE2=0.3;  //tolerancia del joystick
	private static int direction = -1;
	private static TalonSRX[] talons;  //arreglo para guadar los talon del chasis
	private static Robot_Heading robotHeading;
	private static double Select_drive=1;
	protected Encoder[] encoders;
	
	
	/////para el pid/////////////
	private static double integral_val=0;
	private static double pre_input = 0;
	private static final double MAX_INTEGRAL_VAL = 0.3;
	private static final double MIN_INTEGRAL_VAL = -0.3;
	private static final double MAX_PID_VAL = 0.5;
	private static final double MIN_PID_VAL = -0.5;
	private static int rotatingrobot = 0;
	private static double rotatingrobotramp = 0;
	/////////////////////////////
	
	//////////constructor de la clase/////////////////////
	public Robot_Drive(){
		talons=new TalonSRX[]{RobotMap.frontLeft,RobotMap.frontRight,
			   RobotMap.backLeft,RobotMap.backRight};
		robotHeading=Robot.Robot_heading;
		encoders=new Encoder[]{
				RobotMap.DriveEL,RobotMap.DriveER};
	}
	//////////////////////////////////////////////////////
	
	////para poner todo en la posicion inicial/////
	public void InitDefaultState() {
		Select_drive = 1;
		rotatingrobot = 0;
		rotatingrobotramp = 0;
		robotHeading.resetRotation();
		Stop_drive();
	}
	//////////////////////////////////////////////
	
	
	////////funcion principal del drive//////////////////////////////////////
	public void Main_drive() {
		if(Select_drive ==1) {
			Front_drive();
			SmartDashboard.putString("drive mode", "field oriented");
		}else {
			Tank_drive();
			SmartDashboard.putString("drive mode", "tanque");
		}
		SmartDashboard.putNumber("encoderL", encoders[0].getRaw());
		SmartDashboard.putNumber("enocderR", encoders[1].getRaw());
	}
	//////////////////////////////////////////////////////////////////
	
	
	////////funcion del drive estilo tanque//////////////////////////////////////
	public void Tank_drive() {
		//lee el control 1
		Joystick joystick=Robot.oi.Stick1;
		SmartDashboard.putBoolean("chassis invertido",(direction==1));
		//lee cada eje de los joystick y les quita el error y mapea
		double translationX=mapDoubleT(joystick.getRawAxis(0),TOLERANCE,1,0,1)*direction, 
			   translationY=mapDoubleT(joystick.getRawAxis(1),TOLERANCE,1,0,1)*direction,
			   rotationAxis=mapDoubleT(joystick.getRawAxis(4),TOLERANCE2,1,0,0.7)*-1;
		
		//obtiene la magnbitud del vector del joystick
		double magnitude=Math.sqrt((translationX*translationX)+(translationY*translationY));
		
		// Calcula el angulo del vector
		double angle=-Math.atan2(translationX, translationY)+(Math.PI/4);
		
		talons[0].set(ControlMode.PercentOutput,magnitude*
				Math.sin(angle)-rotationAxis);
		talons[1].set(ControlMode.PercentOutput,magnitude*
				Math.cos(angle)+rotationAxis);
		talons[2].set(ControlMode.PercentOutput,magnitude*
				Math.cos(angle)-rotationAxis);
		talons[3].set(ControlMode.PercentOutput,magnitude*
				Math.sin(angle)+rotationAxis);
	}
	//////////////////////////////////////////////////////////////////
	
	///////////////funcion del drive con field oriented///////////////////////////////
	public void Front_drive() {
		//lee el control 1
		Joystick joystick=Robot.oi.Stick1;
		
		double stempx=0.060;  //paso de la rampa
		
		//lee cada eje de los joystick y les quita el error y mapea
		double translationX=mapDoubleT(joystick.getRawAxis(0),TOLERANCE,1,0,1)*-1, 
			   translationY=mapDoubleT(joystick.getRawAxis(1),TOLERANCE,1,0,1)*-1;
		
	    double rotationAxis= (mapDoubleT(joystick.getRawAxis(4),TOLERANCE,1,0,0.7)*-1);
		
		/////////rampa para dar los giros///////////////////
		if(rotatingrobotramp<rotationAxis) {
			rotatingrobotramp=rotatingrobotramp+stempx;
		}else if(rotatingrobotramp>rotationAxis) {
			rotatingrobotramp=rotatingrobotramp-stempx;
		}
		if(rotatingrobotramp<(stempx) && rotatingrobotramp>0) {
			rotatingrobotramp=0;
		}
		if(rotatingrobotramp>(-stempx) && rotatingrobotramp<0) {
			rotatingrobotramp=0;
		}
		////////////////////////////////////////////////////

		//obtiene la magnbitud del vector del joystick
		double magnitude=Math.sqrt((translationX*translationX)+(translationY*translationY));
		
		
		// Calcular el angulo del vector relativo al frente(para hacer el filed oriented drive)////
		double angle=-Math.atan2(translationX, translationY)+(Math.PI/4);
		angle-=(robotHeading.getRawRotation())*Math.PI/180.0;
		SmartDashboard.putNumber("angulo chasis", robotHeading.getRawRotation());
		SmartDashboard.putNumber("angulo chasis convert", robotHeading.getRotation());
		//////////////////////////////////////////////////////////////////////////////////////////
		
		///////////para evitar que el robot se gire cuando no debe///////////////////////////////
		double rotationGain=PID_fun(0,robotHeading.getRotation(),0.025,0,0)*-1;
		if(Math.abs((rotationAxis))>0.0){
			rotatingrobot=1;
			rotationGain=0;
			SmartDashboard.putNumber("resetting",0);
		}else if(rotatingrobot==1 && rotatingrobotramp==0) {
			rotatingrobot=0;
			robotHeading.resetRotation();
			rotationGain=0;
			SmartDashboard.putNumber("resetting",1);
		}
		////////////////////////////////////////////////////////////////////////////////////////////
		
		talons[0].set(ControlMode.PercentOutput,magnitude*
				Math.sin(angle)-(rotatingrobotramp)-rotationGain);
		talons[1].set(ControlMode.PercentOutput,magnitude*
				Math.cos(angle)+(rotatingrobotramp)+rotationGain);
		talons[2].set(ControlMode.PercentOutput,magnitude*
				Math.cos(angle)-(rotatingrobotramp)-rotationGain);
		talons[3].set(ControlMode.PercentOutput,magnitude*
				Math.sin(angle)+(rotatingrobotramp)+rotationGain);
	}
	////////////////////////////////////////////////////////////////////////////////////////
	
	////////////////funcion de pid basica/////////////////////////////////////////////
	/////pid se trabaj de 0 a 180 y -180 a 0 ///////////////////////////////////////
	public double PID_fun(double setpoint,double actual_point,double kp,double ki,double kd){
    	double output_val = 0;  //la salida
        double dt = 0.1;  //tiempo que tarda entre medidas
        double epsilon = 2;  //tolerancia
        
      //para tener un pid variable a cierto rango
    	double kp2 = kp*3;
    	double kd2 = kd*3;
        double range_tol = 10;
        
        //obtiene el error
        double error = setpoint - actual_point;
        if(Math.abs(error) <= epsilon){
        	error = 0;
        	integral_val=0;  //resetea
        	pre_input=0; //resetea
        	return(0);
        }else{
        	///calcula la integral
        	//considerando rectangulos pequenos donde dt es lo ancho y el error lo largo
        	integral_val=integral_val + (error*dt);  //el area al graficar la variable contra tiempo(y se suma con lo que ya hay)
        	////para limitar la integral
        	if(integral_val> (MAX_INTEGRAL_VAL)){
        		integral_val = (MAX_INTEGRAL_VAL);}
        	if(integral_val< (MIN_INTEGRAL_VAL)){
        		integral_val = (MIN_INTEGRAL_VAL);}
        }
                 
        //calcula la derivada
        double derivative_val = (actual_point - pre_input)/dt;  //la funcion dx/dt donde dx es la diferencia entre el ultimo error y el nuevo

        if( Math.abs(error)>range_tol){   //si esta muy lejos
        	//calculates the output
        	output_val = (kp2*error) + (ki*integral_val) - (kd2*derivative_val);
        }else{  //si esta muy cerca
        	//calculates the output
        	output_val = (kp*error) + (ki*integral_val) - (kd*derivative_val);
        }
       
        //Saturation filter, para asegurar que no pase los valores maximos ni minimos
        //sirve como una rampa tambien para evitar cambios bruscos
        if(output_val > MAX_PID_VAL){output_val=MAX_PID_VAL;}
        if(output_val < MIN_PID_VAL){output_val=MIN_PID_VAL;}

        //update error, guardando el nuevo error que sera el viejo
        pre_input =actual_point;

        return(output_val); ///regresa el rewsultado del pid
    }
	
	/////////para cambiar la polaridad del chassis/////////////////////////////////
	public void Change_polarity() {
		direction = direction*-1;
	}
	//////////////////////////////////////////////////////////////////////////////
	
	/////////para cambiar la polaridad del chassis/////////////////////////////////
	public void Change_drive() {
		Select_drive = Select_drive*-1;
	}
	//////////////////////////////////////////////////////////////////////////////
	
	//////funcion para detener el drive///////////
	public void Stop_drive() {
		for(TalonSRX talon:talons) {
			talon.set(ControlMode.PercentOutput, 0.0);
		}
	}
	///////////////////////////////////////////////

	///para mapear un numero de un rango a otro rango
	private double map(double x, double in_min, double in_max, double out_min, double out_max)
	{
		return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
	}
	
	private double mapDoubleT(double x, double in_min, double in_max, double out_min, double out_max)
	{
		if(Math.abs(x)<TOLERANCE) {
			return 0;
		}
		if(x<0){
			return map(x,-in_min,-in_max,-out_min,-out_max);
		}
		return map(x,in_min,in_max,out_min,out_max);
	}
	
	///////////////////////////////////////////////
	

	public void initDefaultCommand() {
		//nada
	}
	
}
