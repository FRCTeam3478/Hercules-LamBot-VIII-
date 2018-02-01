package org.lambot3478.autonomous_step;

public abstract class AutonomousStep {
	// Metodo para inicializar el paso
	public abstract void start();
	// Funcion iterativa del paso
	public abstract void run();
	// Funcion que indica al autonomo si se termino el paso
	public abstract boolean isFinished();
}
