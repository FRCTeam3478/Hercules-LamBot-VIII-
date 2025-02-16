package org.lambot3478.autonomous_step;

///////////estructura basica del autonomo, esta clase es abstracta para ser sobreescrita por los pasos ya mas especificos/////
////////en ninguno de los pasos podemos utilizar for o whiles para evitar que se trabe el robot/////////////////////////////

public abstract class AutonomousStep {
	// Metodo para inicializar el paso
	public abstract void start();
	//metodo para sincronizar
	public abstract int changewait();
	// Funcion iterativa del paso
	public abstract void run();
	// Funcion que indica al autonomo si se termino el paso
	public abstract boolean isFinished();
}
