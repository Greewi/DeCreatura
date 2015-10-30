(function(){

if(!window.creatura)
	window.creatura={};

/**
 * Représente les données envrionnementales d'une zone ou celles affectant une créature
 */
creatura.Environnement = function(temperature, luminosite, couleur){
	this.temperature = temperature;
	this.luminosite = luminosite;
	this.couleur = couleur;
	
	/**
	 * Renvoie la couleur de l'environnement
	 */
	this.getCouleur = function(){
		return this.couleur;
	};
	
	/**
	 * Renvoie la température de l'environnement
	 */
	this.getTemperature = function(){
		return this.temperature;
	};
	
	/**
	 * Renvoie la luminosité de l'envrionnement 
	 */	
	this.getLuminosite = function(){
		return this.luminosite;
	};
};
	
})();