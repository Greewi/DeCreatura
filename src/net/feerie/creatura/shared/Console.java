package net.feerie.creatura.shared;

import com.google.gwt.core.client.GWT;

/**
 * Effectue des sorties dans la console du navigateur
 * 
 * @author greewi
 */
public class Console
{
	//private static Logger logger = java.util.logging.Logger.getLogger("Console");
	
	/**
	 * Écrit un message dans la console du navigateur
	 * 
	 * @param message le message à écrire
	 */
	public static void log(String message)
	{
		GWT.log(message);
		//logger.log(Level.SEVERE, message);
	}
	
	public static void log(double message)
	{
		log("" + message);
	}
	
	public static void log(Object object)
	{
		log("" + object);
	}
}
