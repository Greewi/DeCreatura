(function(){

if(!window.creatura)
	window.creatura={};

/**
 * Représente une variable vitale d'une créature
 */
creatura.VariableVitale = function(nom, valeurInitiale, valeurIdeale, valeurMin, valeurMax){
	this.nom = nom;
	this.valeur = valeurInitiale;
	this.valeurIdeale = valeurIdeale;
	this.valeurMin = valeurMin;
	this.valeurMax = valeurMax;
	
	/**
	 * Renvoie le nom de cette variable vitale (Pour affichage)
	 */
	this.getNom = function(){return this.nom;};
	
	/**
	 * Renvoie la valeur actuelle de cette variable vitale
	 */
	this.get = function(){return this.valeur;};

	/**
	 * Renvoie la valeur ideale de cette variable vitale
	 */
	this.getIdeal = function(){return this.valeurIdeale;};

	/**
	 * Fixe la valeur actuelle de cette variable vitale
	 */
	this.set = function(valeur){
		if(this.valeurMax!==undefined && valeur>this.valeurMax)
			this.valeur = this.valeurMax;
		else if(this.valeurMin!==undefined && valeur<this.valeurMin)
			this.valeur = this.valeurMin;
		else
			this.valeur = valeur;
	};
	
	/**
	 * Incremente la valeur actuelle de cette variable vitale d'un certain montant.
	 * @param increment la qunatité à ajouter
	 * @return la quantité effectivement ajoutée
	 */
	this.ajoute = function(increment){
		var valeurPrecedente = this.get();
		this.set(valeurPrecedente + increment);
		return this.get() - valeurPrecedente;
	};
	
	/**
	 * Décrémente la valeur actuelle de cette variable vitale d'un certain montant.
	 * @param decrement la qunatité à retirer
	 * @return la quantité effectivement retirée
	 */
	this.retire = function(decrement){
		return -this.ajoute(-decrement);
	};
};

/**
 * Représente l'ensemble des variables vitales d'une creature
 */
creatura.VariablesVitalesCreature = function(){
	//Nutriments
	this.eauDigestion = new creatura.VariableVitale("Eau (Digestion)", 0, 0, 0);
	this.eau = new creatura.VariableVitale("Eau", 2000, 2000, 0);
	this.sucresDigestion = new creatura.VariableVitale("Sucres (Digestion)", 0, 0, 0);
	this.sucres = new creatura.VariableVitale("Sucres", 800, 800, 0);
	this.proteinesDigestion = new creatura.VariableVitale("Proteines (Digestion)", 0, 0, 0);
	this.proteines = new creatura.VariableVitale("Proteines", 300, 300, 0);
	this.grasDigestion = new creatura.VariableVitale("Gras (Digestion)", 0, 0, 0);
	this.gras = new creatura.VariableVitale("Gras", 700, 700, 0);
	this.dechets = new creatura.VariableVitale("Dechets", 0, 0, 0);
	//Variables physiques
	this.temperature = new creatura.VariableVitale("Temperature", 35, 35);
	//Meta Variables
	this.fatigue = new creatura.VariableVitale("Fatigue", 0, 20000, 0, 20000);
	this.sante = new creatura.VariableVitale("Sante", 1000, 1000, 0, 1000);
};

})();