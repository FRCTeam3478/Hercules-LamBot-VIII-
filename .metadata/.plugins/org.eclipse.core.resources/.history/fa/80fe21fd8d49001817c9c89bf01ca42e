package org.usfirst.frc.team3478.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	/***********aqui se va declarar todo lo del robot**************************/
	//motores
	public static CANTalon RollerIntake;
	public static CANTalon Hoppermotor;
	public static CANTalon Escalador;
	public static CANTalon Shooter;
	public static CANTalon BandaTorreta;
	public static CANTalon GiroTorreta;
	public static CANTalon DriveR1;
	public static CANTalon DriveR2;
	public static CANTalon DriveL1;
	public static CANTalon DriveL2;
	
	//solenoides
	public static Solenoid Engranes_puerta;
	public static Solenoid Engranes_empujador;
	public static Solenoid PosShooter;
	public static Solenoid Cambios;
	
	///compresor
	public static Compressor Compressor;
	
	//encoders
    public static Encoder DriveEL;
    public static Encoder DriveER;
    
    /////entradas digitales
    public static DigitalInput Limit_torreta;

    ////pdp
    public static PowerDistributionPanel pdp;
	
	///////********************************************************************/
	
public static void init() {
    	///////////aqui vamos a inicializar todos los objetos de todo lo que usemos
	
		//compressor
    	Compressor = new Compressor(0);	
    	
    	//PDP para leer corrientes
    	pdp = new PowerDistributionPanel();
    	
    	///drive
    	DriveR1= new CANTalon(5);
    	DriveR1.enableBrakeMode(true); //Pone motores en brake
    	DriveR1.set(0);  //empieza en 0
    	DriveR2= new CANTalon(6);
    	DriveR2.enableBrakeMode(true); //Pone motores en brake
    	DriveR2.set(0);  //empieza en 0
    	DriveL1= new CANTalon(1);
    	DriveL1.enableBrakeMode(true); //Pone motores en brake
    	DriveL1.set(0);  //empieza en 0
    	DriveL2= new CANTalon(2);
    	DriveL2.enableBrakeMode(true); //Pone motores en brake
    	DriveL2.set(0);  //empieza en 0
        DriveEL = new Encoder(0,1,true, Encoder.EncodingType.k4X);
        DriveEL.setMaxPeriod(.1);
        DriveEL.setMinRate(10);
        DriveEL.setDistancePerPulse(13.195/(128));  //encoders 128 por RPM llantas 4 inches diametro perimetro de 12.57
        DriveEL.setSamplesToAverage(10);
        DriveEL.setReverseDirection(true);
        DriveEL.reset();
        DriveER = new Encoder(2,3,false, Encoder.EncodingType.k4X);
        DriveER.setMaxPeriod(.1);
        DriveER.setMinRate(10);
        DriveER.setDistancePerPulse(13.195/(128));  //encoders 128 por RPM llantas 4 inches diametro perimetro de 12.57
        DriveER.setSamplesToAverage(10);
        DriveER.setReverseDirection(false);
        DriveER.reset();
        Cambios = new Solenoid(2);
        Cambios.set(false);
	
		//Roller (Intake)
		RollerIntake = new CANTalon(8);
		RollerIntake.enableBrakeMode(false); //Pone motores en brake
		RollerIntake.set(0);  //empieza en 0
		
		///para el motor del hopper
		Hoppermotor = new CANTalon(9);
		Hoppermotor.enableBrakeMode(false); //Pone motores en brake
		Hoppermotor.set(0);  //empieza en 0
		
		//Ponedor de engranes
		Engranes_puerta = new Solenoid(1);
		Engranes_puerta.set(false);
		Engranes_empujador = new Solenoid(0);
		Engranes_empujador.set(false);
		
		//Escalador
		Escalador = new CANTalon(7);
		Escalador.enableBrakeMode(true); //Pone motores en brake
		Escalador.set(0);  //empieza en 0
		
		//torreta
		Shooter=new CANTalon(3);
		Shooter.enableBrakeMode(false); //Pone motores en brake
    	Shooter.setFeedbackDevice(FeedbackDevice.QuadEncoder);
    	Shooter.configEncoderCodesPerRev(128*4);
    	Shooter.reverseSensor(true);
    	Shooter.configNominalOutputVoltage(+0.0f,-0.0f);
    	Shooter.configPeakOutputVoltage(+12.0f, -12.0f);
    	Shooter.setProfile(0);
    	Shooter.setF(0.25); //feed forward value
    	Shooter.setP(2);  //P of PID
    	Shooter.setI(0);  //I of PID
    	Shooter.setD(100);  //D of PID
    	Shooter.setCloseLoopRampRate(0);  //Limit ramp (maximo voltaje por segundo)
    	Shooter.changeControlMode(TalonControlMode.Speed);
    	Shooter.setPosition(0);  //resetea las revoluciones del encoder
    	Shooter.set(0);
		BandaTorreta=new CANTalon(4); 
		BandaTorreta.enableBrakeMode(false); //Pone motores en brake
		BandaTorreta.set(0);  //empieza en 0\
		GiroTorreta=new CANTalon(0); 
		GiroTorreta.enableBrakeMode(true); //Pone motores en brake
		GiroTorreta.enableLimitSwitch(false, false);
		GiroTorreta.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		GiroTorreta.configEncoderCodesPerRev(4096);
		GiroTorreta.reverseSensor(false);
		GiroTorreta.reset();  //resetea el encoder
		GiroTorreta.configNominalOutputVoltage(+0.0f,-0.0f);
		GiroTorreta.configPeakOutputVoltage(+12.0f, -12.0f);
		GiroTorreta.changeControlMode(TalonControlMode.Position); //Change control mode of talon, default is PercentVbus (-1.0 to 1.0)
		GiroTorreta.setPID(1, 0, 0.0); //Set the PID constants (p, i, d)
		GiroTorreta.enableControl(); //Enable PID control on the talon
		GiroTorreta.setPosition(0);  //pone en la posicion 0
		GiroTorreta.set(0);  //pone en 0 la velocidad
		Limit_torreta = new DigitalInput(4);
		PosShooter=new Solenoid(3);
		PosShooter.set(false);
}

}
