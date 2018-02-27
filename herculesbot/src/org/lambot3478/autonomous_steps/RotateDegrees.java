package org.lambot3478.autonomous_steps;

import org.lambot3478.autonomous_step.AutonomousStep_Drive;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//////clase para girar a la izquierda o a la derecha cierto angulos checado con giroscopio/////////////////

public class RotateDegrees extends AutonomousStep_Drive{
	private double rotation=0;
	private double adjustangle = 0;
	private double arrdata[]= {1,1,1,1,1};
	private int filternum = 0;
	/////para el pid/////////////
	private double integral_val=0;
	private double pre_input = 0;
	private final double MAX_INTEGRAL_VAL = 0.3;
	private final double MIN_INTEGRAL_VAL = -0.3;
	private final double MAX_PID_VAL = 0.5;
	private final double MIN_PID_VAL = -0.5;
	/////////////////////////////
	
	
	public RotateDegrees(double rotation){
		this.rotation=rotation;
	}
	
	@Override
	public void start() {
		heading.resetRotation();
		adjustangle=-10;
	}

	@Override
	public void run() {
		// Potencia de alineacion proporcional
		adjustangle = PID_fun(rotation,heading.getRotation(),0.025,0,0)*-1;
		vectorMove(0,0,adjustangle);
		SmartDashboard.putNumber("anguloauto", heading.getRotation());
		arrdata[filternum]=adjustangle;
		filternum=filternum+1;
		if(filternum>=arrdata.length) {
			filternum=0;
		}
		Timer.delay(0.01);
	}
	@Override
	public boolean isFinished() {
		if(arrdata[0]==0 && arrdata[1]==0 && arrdata[2]==0 && arrdata[3]==0 && arrdata[4]==0){
			return true;
		}
		return false;
	}
	
	
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
        int error = (int) ( setpoint - actual_point);
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
        
        if(output_val!=0) {
        	if(output_val<0.2 && output_val>0) {
        		output_val=0.2;
        	}else if(output_val>-0.2 && output_val<0) {
        		output_val=-0.2;
        	}
        }

        //update error, guardando el nuevo error que sera el viejo
        pre_input =actual_point;

        return(output_val); ///regresa el rewsultado del pid
    }
	 //////////////////////////////////////////////////////////////////////

}