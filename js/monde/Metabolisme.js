(function(){

if(!window.creatura)
	window.creatura={};

/**
 * Représente le métabolisme d'une créature, stockant
 * son état et se modifiant au cours du temps.
 */
creatura.Metabolisme = function(variablesVitales){
	//Cycles des nutriments
	var digestionSucre = 1.0; //Quantité digérée par frame
	var digestionProteine = 0.3; //Quantité digérée par frame
	var digestionGras = 0.7; //Quantité digérée par frame
	var digestionEau = 2.0; //Quantité digérée par frame
	var dechetsDigestionSucre = 0.2; //Proportion de la quantitée digérée par frame convertie en déchets
	var dechetsDigestionProteine = 0.5; //Proportion de la quantitée digérée par frame convertie en déchets
	var dechetsDigestionGras = 0.3; //Proportion de la quantitée digérée par frame convertie en déchets
	var dechetsDigestionEau = 0.4; //Proportion de la quantitée digérée par frame convertie en déchets
	var peremptionSucre = 0.1; //Quantité perimée par frame
	var peremptionProteine = 0.01; //Quantité perimée par frame
	var peremptionGras = 0.0; //Quantité perimée par frame
	var peremptionEau = 0.1; //Quantité perimée par frame
	var conversionGrasSucres = 0.03; //Quantité de gras convertissable en sucre par frame
	
	//Génération de la fatigue
	var fatigueDormir = 0.0; //Fatigué générée pendant le sommeil
	var fatigueInactif = 0.1; //Fatigue générée pendant l'inactivité
	var fatigueAction = 1.0; //Fatigue générée pendant les autres actions
	var fatigueDigestion = 4.0; //Fatigue générée par la digestion
	
	//Gestion de la chaleur
	var raffraichissementTemperature = 0.02; //Quantité de chaleur supprimée en une frame maximum
	var rechauffementTemperature = 0.04; //Quantité de chaleur supprimée en une frame maximum
	var eauRaffrichissement = 20; //Quantité d'eau par degrés
	var sucreRechauffement = 10; //Quantité de sucres par degrés
	var deltaChaleur = 7.0; //Chaleur à ajouter pour obtenir la chaleur effective de l'environnement
	var transfertChaleur = 0.003; //Quantité de chaleur transféré par degré de différence
	
	/**
	 * Met à jour le métabolisme de la créature.
	 */
	this.metAJour = function(){
		this.genereFatigue();
		this.digereNutriments();
		this.reguleTemperature();
		this.convertiGrasEnSucres();
		this.perimeNutriments();
		this.calculeSante();
	};
	
	/**
	 * Procède à l'assimilation des aliments
	 */
	this.digereNutriments = function(){
		//Sucre
		var sucreDigere = variablesVitales.sucresDigestion.retire(digestionSucre);
		var sucreDechet = sucreDigere * dechetsDigestionSucre;
		variablesVitales.sucres.ajoute(sucreDigere - sucreDechet);
		variablesVitales.dechets.ajoute(sucreDechet);
		//Proteines
		var proteineDigere = variablesVitales.proteinesDigestion.retire(digestionProteine);
		var proteineDechet = proteineDigere * dechetsDigestionProteine;
		variablesVitales.proteines.ajoute(proteineDigere - proteineDechet);
		variablesVitales.dechets.ajoute(proteineDechet);
		//Gras
		var grasDigere = variablesVitales.grasDigestion.retire(digestionGras);
		var grasDechet = grasDigere * dechetsDigestionGras;
		variablesVitales.gras.ajoute(grasDigere - grasDechet);
		variablesVitales.dechets.ajoute(grasDechet);
		//Eau
		var eauDigeree = variablesVitales.eauDigestion.retire(digestionEau);
		var eauDechet = eauDigeree * dechetsDigestionEau;
		variablesVitales.eau.ajoute(eauDigeree - eauDechet);
		variablesVitales.dechets.ajoute(eauDechet);
	};
	
	/**
	 * Procède à la transformation des nutriments en déchets (pour simuler le consommation par le corps de façon passive)
	 */
	this.perimeNutriments = function(){
		//Sucre
		var sucrePerime = variablesVitales.sucres.retire(peremptionSucre);
		variablesVitales.dechets.ajoute(sucrePerime);
		//Proteines
		var proteinesPerime = variablesVitales.proteines.retire(peremptionProteine);
		variablesVitales.dechets.ajoute(proteinesPerime);
		//Gras
		var grasPerime = variablesVitales.gras.retire(peremptionGras);
		variablesVitales.dechets.ajoute(grasPerime);
		//Eau
		var eauPerime = variablesVitales.eau.retire(peremptionEau);
		variablesVitales.dechets.ajoute(eauPerime);
	};
	
	/**
	 * Procède àa la conversion du gras en sucres (Le gras est ici considéré comme de l'énergie potentielle qui doit être convertie avant de pouvoir être utilisée)
	 */
	this.convertiGrasEnSucres = function(){
		var deltaSucres = variablesVitales.sucres.getIdeal()*2/3 - variablesVitales.sucres.get();
		if(deltaSucres > 0.00001)
		{
			if(deltaSucres > conversionGrasSucres)
				deltaSucres = conversionGrasSucres;
			var grasConverti = variablesVitales.gras.retire(deltaSucres);
			variablesVitales.sucres.ajoute(grasConverti);
		}
	};
	
	/**
	 * Procède à la régulation de la température
	 */
	this.reguleTemperature = function(){
		//Tranfert de chaleur avec l'extérieur
		var temperatureExterne = creature.getEnvironnement().getTemperature() + deltaChaleur;
		var temperatureInterne = variablesVitales.temperature.get();
		if(temperatureExterne>temperatureInterne)
			variablesVitales.temperature.ajoute((temperatureExterne-temperatureInterne)*transfertChaleur);
		else
			variablesVitales.temperature.retire((temperatureInterne-temperatureExterne)*transfertChaleur);
		
		//Régulation de la température interne	
		var deltaTemperature = variablesVitales.temperature.get() - variablesVitales.temperature.getIdeal();
		//Refroidir
		if(deltaTemperature > 0.00001)
		{
			if(deltaTemperature>raffraichissementTemperature)
				deltaTemperature = raffraichissementTemperature;
			var eauRequise = deltaTemperature * eauRaffrichissement;
			var eauConvertie = variablesVitales.eau.retire(eauRequise);
			deltaTemperature *= eauConvertie/eauRequise;
			variablesVitales.temperature.retire(deltaTemperature);
		}
		//Rechauffer
		else if(deltaTemperature < -0.00001)
		{
			deltaTemperature = -deltaTemperature;
			if(deltaTemperature>rechauffementTemperature)
				deltaTemperature = rechauffementTemperature;
			var sucreRequis = deltaTemperature*sucreRechauffement;
			var sucreConverti = variablesVitales.sucres.retire(sucreRequis);
			variablesVitales.dechets.ajoute(sucreConverti);
			deltaTemperature *= sucreConverti/sucreRequis;
			variablesVitales.temperature.ajoute(deltaTemperature);
		}
	};
	
	/**
	 * Procède à la génération de la fatigue
	 */
	this.genereFatigue = function(){
		var actionActuelle = creature.getActionActuelle();
		var fatigueConsommee = 0;
		//Prise en compte de l'état d'activité
		if(actionActuelle === null)
			fatigueConsommee += fatigueInactif;
		else if(actionActuelle.getType() == "dormir")
			fatigueConsommee += fatigueDormir;
		else
			fatigueConsommee += fatigueAction;
		//Prise en compte de la digestion
		if(variablesVitales.sucresDigestion.get()>0 || variablesVitales.proteinesDigestion.get()>0 || variablesVitales.grasDigestion.get()>0 || variablesVitales.eauDigestion.get()>0)
			fatigueConsommee += fatigueDigestion;
		variablesVitales.fatigue.retire(fatigueConsommee);
	};
	
	/**
	 * Procède au calcul de la santé
	 */
	this.calculeSante = function(){
		var deltaSante = 0.5;
		if(variablesVitales.eau.get() === 0)
			deltaSante -= 1.0;
		if(variablesVitales.sucres.get() === 0)
			deltaSante -= 1.0;
		if(variablesVitales.proteines.get() === 0)
			deltaSante -= 1.0;
		if(variablesVitales.gras.get() === 0)
			deltaSante -= 1.0;
		if(variablesVitales.dechets.get() > 3000)
			deltaSante -= 1.0;
		if(variablesVitales.temperature.get() < 33)
			deltaSante -= 1.0;
		if(variablesVitales.temperature.get() > 37)
			deltaSante -= 1.0;
		
		if(deltaSante >= 0.0)
			variablesVitales.sante.ajoute(deltaSante);
		else
			variablesVitales.sante.retire(-deltaSante);
	};
};

})();