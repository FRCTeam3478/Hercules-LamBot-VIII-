package org.lambot3478.autonomous_steps;

import org.lambot3478.autonomous_step.AutonomousStep_Drive;

//////clase para girar a la izquierda o a la derecha cierto angulos checado con giroscopio/////////////////

public class RotateDegrees extends AutonomousStep_Drive{
	private double rotation;
	private double adjustangle = 0;
	/////para el pid/////////////
	private double integral_val=0;
	private double pre_input = 0;
	private final double MAX_INTEGRAL_VAL = 0.3;
	private final double MIN_INTEGRAL_VAL = -0.3;
	private final double MAX_PID_VAL = 0.7;
	private final double MIN_PID_VAL = -0.7;
	/////////////////////////////
	
	
	public RotateDegrees(double rotation){
		super();
		this.rotation=rotation;
	}
	
	@Override
	public void start() {
		adjustangle = PID_fun(rotation,heading.getRotation(),0.025,0,0);
	}

	@Override
	public void run() {
		// Potencia de alineacion proporcional
		vectorMove(0,0,adjustangle);
	}
	@Override
	public boolean isFinished() {
		adjustangle = PID_fun(rotation,heading.getRotation(),0.025,0,0);
		if(adjustangle != 0){
			return false;
		}
		return true;
	}
	
	
	////////////////funcion de pid basica/////////////////////////////////////////////
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
	 //////////////////////////////////////////////////////////////////////

}