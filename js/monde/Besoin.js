(function(){

if(!window.creatura)
	window.creatura={};

/**
 * Représente un besoin d'une créature dans le monde
 * Un besoin est associé à un nombre compris en 0 et 1 (inclus),
 * 0 signifiant que le besoin est rempli et 1 qu'il se manifeste au maximum
 */
creatura.Besoin = function(nom, valeurIntiale, creature){
	this.nom = nom;
	this.creature = creature;
	this.valeur = valeurIntiale;
	
	/**
	 * Renvoie la valeur de ce besoin
	 */
	this.get = function(){
		return this.valeur;
	};
	
	/**
	 * Fixe la valeur de ce besoin
	 */
	this.set = function(valeur){
		if(valeur>1)
			this.valeur=1;
		else if(valeur<0)
			this.valeur=0;
		else
			this.valeur=valeur;
	};
	
};

/**
 * Représente l'ensemble des besoins d'une creature
 */
creatura.BesoinsCreature = function(creature){
	//Besoins primaires
	this.soif = new creatura.Besoin("Soif", 0, creature);
	this.sucres = new creatura.Besoin("Sucre", 0, creature);
	this.proteines = new creatura.Besoin("Proteine", 0, creature);
	this.gras = new creatura.Besoin("Gras", 0, creature);
	this.popo = new creatura.Besoin("Popo", 0, creature);
	//Besoins environementaux
	this.chaud = new creatura.Besoin("Chaud", 0, creature);
	this.froid = new creatura.Besoin("Froid", 0, creature);
	//Besoins 
	this.ennui = new creatura.Besoin("Ennui", 0, creature);
	this.fatigue = new creatura.Besoin("Fatigue", 0, creature);
	this.souffle = new creatura.Besoin("Souffle", 0, creature);
	this.solitude = new creatura.Besoin("Solitude", 0, creature);
	//Etats d'esprits
	this.tristesse = new creatura.Besoin("Tristesse", 0, creature);
	this.colere = new creatura.Besoin("Colere", 0, creature);
	this.peur = new creatura.Besoin("Peur", 0, creature);	
};

})();