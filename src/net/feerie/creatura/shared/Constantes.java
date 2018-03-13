package net.feerie.creatura.shared;

public class Constantes
{
	/*
	 * Général
	 */
	// Gestion du temps
	public final static long PERIODE_TIC = 250l; // Durée d'un tic en millisecondes
	public final static long PERIODE_CYCLE_IA = 1; // Nombre de tic pour un cycle d'IA
	public final static long PERIODE_CYCLE_METABOLIQUE = 10; // Nombre de tic pour un cycle métabolique
	
	// Physique
	public final static int ACCELERATION_GRAVITE = 100; //Accélération de la gravité en pixel par tic carré
	public final static int VITESSE_MARCHE_CREATURE = 50; //Vitesse de déplacement de la créature en pixel par tics
	
	public final static int ACTION_BOIRE_DUREE = 10; //Durée de l'action Boire en tic 
	public final static int ACTION_MANGER_DUREE = 10; //Durée de l'action Boire en tic 
	public final static int ACTION_POPO_DUREE = 10; //Durée de l'action Boire en tic 
	public final static int ACTION_NUISIBLE_MANGER_DUREE = 20; //Durée de l'action manger en tic pour un nuisible
	
	/*
	 * Métabolismes
	 */
	public static final int TEMPERATURE_FROID = 12; //Température (en °C) en dessous de laquelle il fait froid 
	public static final int TEMPERATURE_CHAUD = 25; //Température (en °C) en dessus de laquelle il fait chaud
	public static final int QUANTITE_ELEMENT_POUR_NUISIBLE = 20; //Nombre d'élément commestible sur la carte avant l'apparition d'un nuisible.
	
	// Arbres
	public static final int ARBRE_CYCLE_NOUVEAU_FRUIT = 80; // Arbre : nombre de cycle métabolique pour générer un nouveau fruit
	public static final int ARBRE_CYCLE_MATURATION = 70; // Arbre : nombre de cycle métabolique pour qu'un fruit devienne mature
	public static final int ARBRE_CYCLE_TOMBE = 140; // Arbre : nombre de cycle métabolique pour qu'un fruit tombe de l'arbre
	
	/*
	 * Moodles (Tout est en unité par cycle métabolique)
	 */
	//Ennui
	public static final int ENNUI_CHARGEMENT_INACTIF = 10;
	public static final int ENNUI_DECHARGEMENT_ACTIF = 24;
	//Fatigue
	public static final int FATIGUE_CHARGEMENT_PASSIF = 1;
	public static final int FATIGUE_REDUCTION_SANTE = 1;
	public static final int FATIGUE_DECHARGEMENT_DODO = 5;
	//Soif
	public static final int SOIF_REDUCTION_SANTE = 1;
	public static final int SOIF_CHARGE_PASSIVE = 3;
	public static final int SOIF_CHARGE_PASSIVE_CHALEUR = 8;
	//Deshydratation
	public static final int DESHYDRATATION_CHARGE_SOIF = 3;
	public static final int DESHYDRATATION_REDUCTION_SANTE = 5;
	//Faim
	public static final int FAIM_REDUCTION_SANTE = 1;
	public static final int FAIM_CHARGE_PASSIVE = 2;
	public static final int FAIM_CHARGE_PASSIVE_FROID = 6;
	//Famine
	public static final int FAMINE_CHARGE_FAIM = 1;
	public static final int FAMINE_REDUCTION_SANTE = 5;
	//Mouillé
	public static final int MOUILLE_DECHARGEMENT_PASSIF = 1;
	//Chaud
	public static final int CHAUD_DECHARGEMENT_FROID = 20;
	public static final int CHAUD_DECHARGEMENT_FROID_MOUILLE = 25;
	public static final int CHAUD_DECHARGEMENT_TEMPERE = 15;
	public static final int CHAUD_DECHARGEMENT_TEMPERE_MOUILLE = 20;
	public static final int CHAUD_CHARGEMENT_CHAUD = 10;
	public static final int CHAUD_CHARGEMENT_CHAUD_MOUILLE = 5;
	//Froid
	public static final int FROID_DECHARGEMENT_CHAUD = 20;
	public static final int FROID_DECHARGEMENT_CHAUD_MOUILLE = 15;
	public static final int FROID_DECHARGEMENT_TEMPERE = 15;
	public static final int FROID_DECHARGEMENT_TEMPERE_MOUILLE = 10;
	public static final int FROID_CHARGEMENT_FROID = 10;
	public static final int FROID_CHARGEMENT_FROID_MOUILLE = 15;
}
