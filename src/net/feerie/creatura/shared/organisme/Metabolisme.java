package net.feerie.creatura.shared.organisme;

import static net.feerie.creatura.shared.organisme.TypeVariableVitale.DECHETS;
import static net.feerie.creatura.shared.organisme.TypeVariableVitale.EAU;
import static net.feerie.creatura.shared.organisme.TypeVariableVitale.EAU_DIGESTION;
import static net.feerie.creatura.shared.organisme.TypeVariableVitale.FATIGUE;
import static net.feerie.creatura.shared.organisme.TypeVariableVitale.GRAS;
import static net.feerie.creatura.shared.organisme.TypeVariableVitale.GRAS_DIGESTION;
import static net.feerie.creatura.shared.organisme.TypeVariableVitale.PROTEINES;
import static net.feerie.creatura.shared.organisme.TypeVariableVitale.PROTEINES_DIGESTION;
import static net.feerie.creatura.shared.organisme.TypeVariableVitale.SANTE;
import static net.feerie.creatura.shared.organisme.TypeVariableVitale.SUCRES;
import static net.feerie.creatura.shared.organisme.TypeVariableVitale.SUCRES_DIGESTION;
import static net.feerie.creatura.shared.organisme.TypeVariableVitale.TEMPERATURE;

import java.util.EnumMap;

import net.feerie.creatura.shared.actions.Action;
import net.feerie.creatura.shared.actions.TypeAction;
import net.feerie.creatura.shared.entites.Creature;

/**
 * Repr�sente le m�tabolisme d'une cr�ature
 * 
 * @author greewi
 */
public class Metabolisme
{
	//Cycles des nutriments
	private final double digestionSucre = 1.0; //Quantit� dig�r�e par frame
	private final double digestionProteine = 0.3; //Quantit� dig�r�e par frame
	private final double digestionGras = 0.7; //Quantit� dig�r�e par frame
	private final double digestionEau = 2.0; //Quantit� dig�r�e par frame
	private final double dechetsDigestionSucre = 0.2; //Proportion de la quantit�e dig�r�e par frame convertie en d�chets
	private final double dechetsDigestionProteine = 0.5; //Proportion de la quantit�e dig�r�e par frame convertie en d�chets
	private final double dechetsDigestionGras = 0.3; //Proportion de la quantit�e dig�r�e par frame convertie en d�chets
	private final double dechetsDigestionEau = 0.4; //Proportion de la quantit�e dig�r�e par frame convertie en d�chets
	private final double peremptionSucre = 0.1; //Quantit� perim�e par frame
	private final double peremptionProteine = 0.01; //Quantit� perim�e par frame
	private final double peremptionGras = 0.0; //Quantit� perim�e par frame
	private final double peremptionEau = 0.1; //Quantit� perim�e par frame
	private final double conversionGrasSucres = 0.03; //Quantit� de gras convertissable en sucre par frame
	
	//G�n�ration de la fatigue
	private final double fatigueDormir = 0.0; //Fatigu� g�n�r�e pendant le sommeil
	private final double fatigueInactif = 0.1; //Fatigue g�n�r�e pendant l'inactivit�
	private final double fatigueAction = 1.0; //Fatigue g�n�r�e pendant les autres actions
	private final double fatigueDigestion = 4.0; //Fatigue g�n�r�e par la digestion
	
	//Gestion de la chaleur
	private final double raffraichissementTemperature = 0.02; //Quantit� de chaleur supprim�e en une frame maximum
	private final double rechauffementTemperature = 0.04; //Quantit� de chaleur supprim�e en une frame maximum
	private final double eauRaffrichissement = 20; //Quantit� d'eau par degr�s
	private final double sucreRechauffement = 10; //Quantit� de sucres par degr�s
	private final double deltaChaleur = 7.0; //Chaleur � ajouter pour obtenir la chaleur effective de l'environnement
	private final double transfertChaleur = 0.003; //Quantit� de chaleur transf�r� par degr� de diff�rence
	
	private final Creature creature;
	private final EnumMap<TypeVariableVitale, VariableVitale> variablesVitales;
	
	public Metabolisme(Creature creature)
	{
		this.creature = creature;
		variablesVitales = new EnumMap<>(TypeVariableVitale.class);
		for (TypeVariableVitale typeVariable : TypeVariableVitale.values())
			variablesVitales.put(typeVariable, new VariableVitale(typeVariable));
	}
	
	/**
	 * @param type le type de la varianle vitale d�sir�e
	 * @return la variable vitale demand�e
	 */
	public VariableVitale get(TypeVariableVitale type)
	{
		return variablesVitales.get(type);
	}
	
	/**
	 * Met � jour le m�tabolisme de la cr�ature.
	 */
	public void metAJour()
	{
		genereFatigue();
		digereNutriments();
		reguleTemperature();
		convertiGrasEnSucres();
		perimeNutriments();
		calculeSante();
	}
	
	/**
	 * Proc�de � la g�n�ration de la fatigue
	 */
	public void genereFatigue()
	{
		Action actionActuelle = creature.getActionActuelle();
		double fatigueConsommee = 0;
		//Prise en compte de l'�tat d'activit�
		if (actionActuelle == null)
			fatigueConsommee += fatigueInactif;
		else if (actionActuelle.getType() == TypeAction.DORMIR)
			fatigueConsommee += fatigueDormir;
		else
			fatigueConsommee += fatigueAction;
		//Prise en compte de la digestion
		if (variablesVitales.get(SUCRES_DIGESTION).get() > 0 || variablesVitales.get(PROTEINES_DIGESTION).get() > 0 || variablesVitales.get(GRAS_DIGESTION).get() > 0 || variablesVitales.get(EAU_DIGESTION).get() > 0)
			fatigueConsommee += fatigueDigestion;
		variablesVitales.get(FATIGUE).retire(fatigueConsommee);
	}
	
	/**
	 * Proc�de � l'assimilation des aliments
	 */
	public void digereNutriments()
	{
		//Sucre
		double sucreDigere = variablesVitales.get(SUCRES_DIGESTION).retire(digestionSucre);
		double sucreDechet = sucreDigere * dechetsDigestionSucre;
		variablesVitales.get(SUCRES).ajoute(sucreDigere - sucreDechet);
		variablesVitales.get(DECHETS).ajoute(sucreDechet);
		//Proteines
		double proteineDigere = variablesVitales.get(PROTEINES_DIGESTION).retire(digestionProteine);
		double proteineDechet = proteineDigere * dechetsDigestionProteine;
		variablesVitales.get(PROTEINES).ajoute(proteineDigere - proteineDechet);
		variablesVitales.get(DECHETS).ajoute(proteineDechet);
		//Gras
		double grasDigere = variablesVitales.get(GRAS_DIGESTION).retire(digestionGras);
		double grasDechet = grasDigere * dechetsDigestionGras;
		variablesVitales.get(GRAS).ajoute(grasDigere - grasDechet);
		variablesVitales.get(DECHETS).ajoute(grasDechet);
		//Eau
		double eauDigeree = variablesVitales.get(EAU_DIGESTION).retire(digestionEau);
		double eauDechet = eauDigeree * dechetsDigestionEau;
		variablesVitales.get(EAU).ajoute(eauDigeree - eauDechet);
		variablesVitales.get(DECHETS).ajoute(eauDechet);
	}
	
	/**
	 * Proc�de � la r�gulation de la temp�rature
	 */
	public void reguleTemperature()
	{
		//Tranfert de chaleur avec l'ext�rieur
		double temperatureExterne = creature.getEnvironnementActuel().getTemperature() + deltaChaleur;
		double temperatureInterne = variablesVitales.get(TEMPERATURE).get();
		if (temperatureExterne > temperatureInterne)
			variablesVitales.get(TEMPERATURE).ajoute((temperatureExterne - temperatureInterne) * transfertChaleur);
		else
			variablesVitales.get(TEMPERATURE).retire((temperatureInterne - temperatureExterne) * transfertChaleur);
		
		//R�gulation de la temp�rature interne	
		double deltaTemperature = variablesVitales.get(TEMPERATURE).get() - variablesVitales.get(TEMPERATURE).getIdeal();
		//Refroidir
		if (deltaTemperature > 0.00001)
		{
			if (deltaTemperature > raffraichissementTemperature)
				deltaTemperature = raffraichissementTemperature;
			double eauRequise = deltaTemperature * eauRaffrichissement;
			double eauConvertie = variablesVitales.get(EAU).retire(eauRequise);
			deltaTemperature *= eauConvertie / eauRequise;
			variablesVitales.get(TEMPERATURE).retire(deltaTemperature);
		}
		//Rechauffer
		else if (deltaTemperature < -0.00001)
		{
			deltaTemperature = -deltaTemperature;
			if (deltaTemperature > rechauffementTemperature)
				deltaTemperature = rechauffementTemperature;
			double sucreRequis = deltaTemperature * sucreRechauffement;
			double sucreConverti = variablesVitales.get(SUCRES).retire(sucreRequis);
			variablesVitales.get(DECHETS).ajoute(sucreConverti);
			deltaTemperature *= sucreConverti / sucreRequis;
			variablesVitales.get(TEMPERATURE).ajoute(deltaTemperature);
		}
	}
	
	/**
	 * Proc�de �a la conversion du gras en sucres (Le gras est ici consid�r�
	 * comme de l'�nergie potentielle qui doit �tre convertie avant de pouvoir
	 * �tre utilis�e)
	 */
	public void convertiGrasEnSucres()
	{
		double deltaSucres = variablesVitales.get(SUCRES).getIdeal() * 2 / 3 - variablesVitales.get(SUCRES).get();
		if (deltaSucres > 0.00001)
		{
			if (deltaSucres > conversionGrasSucres)
				deltaSucres = conversionGrasSucres;
			double grasConverti = variablesVitales.get(GRAS).retire(deltaSucres);
			variablesVitales.get(SUCRES).ajoute(grasConverti);
		}
	}
	
	/**
	 * Proc�de � la transformation des nutriments en d�chets (pour simuler le
	 * consommation par le corps de fa�on passive)
	 */
	public void perimeNutriments()
	{
		//Sucre
		double sucrePerime = variablesVitales.get(SUCRES).retire(peremptionSucre);
		variablesVitales.get(DECHETS).ajoute(sucrePerime);
		//Proteines
		double proteinesPerime = variablesVitales.get(PROTEINES).retire(peremptionProteine);
		variablesVitales.get(DECHETS).ajoute(proteinesPerime);
		//Gras
		double grasPerime = variablesVitales.get(GRAS).retire(peremptionGras);
		variablesVitales.get(DECHETS).ajoute(grasPerime);
		//Eau
		double eauPerime = variablesVitales.get(EAU).retire(peremptionEau);
		variablesVitales.get(DECHETS).ajoute(eauPerime);
	}
	
	/**
	 * Proc�de au calcul de la sant�
	 */
	public void calculeSante()
	{
		double deltaSante = 0.5;
		if (variablesVitales.get(EAU).get() == 0)
			deltaSante -= 1.0;
		if (variablesVitales.get(SUCRES).get() == 0)
			deltaSante -= 1.0;
		if (variablesVitales.get(PROTEINES).get() == 0)
			deltaSante -= 1.0;
		if (variablesVitales.get(GRAS).get() == 0)
			deltaSante -= 1.0;
		if (variablesVitales.get(DECHETS).get() > 3000)
			deltaSante -= 1.0;
		if (variablesVitales.get(TEMPERATURE).get() < 33)
			deltaSante -= 1.0;
		if (variablesVitales.get(TEMPERATURE).get() > 37)
			deltaSante -= 1.0;
		
		variablesVitales.get(SANTE).ajoute(deltaSante);
	}
}
