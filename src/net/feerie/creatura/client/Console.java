package net.feerie.creatura.client;

/**
 * Effectue des sorties dans la console du navigateur
 * 
 * @author greewi
 */
public class Console
{
	public static native void log(Object message)/*-{
		console.log(message);
	}-*/;
}
