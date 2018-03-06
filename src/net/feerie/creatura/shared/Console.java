package net.feerie.creatura.shared;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Effectue des sorties dans la console du navigateur
 * 
 * @author greewi
 */
public class Console
{
	private static Logger logger = java.util.logging.Logger.getLogger("Console");
	
	/**
	 * Écrit un message dans la console du navigateur
	 * 
	 * @param message le message à écrire
	 */
	public static void log(String message)
	{
		logger.log(Level.SEVERE, message);
	}
}
