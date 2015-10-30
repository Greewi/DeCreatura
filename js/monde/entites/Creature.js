(function(){

if(!window.creatura)
	window.creatura={};

/**
 * Représente une créatuire dans le monde (étend creatura.Entite)
 */
creatura.Creature = function(monde, x, y){
	creatura.Entite.call(this, monde, x, y, 5, 5);
	
	this.besoins = new creatura.BesoinsCreature(this);
	this.variablesVitales = new creatura.VariablesVitalesCreature(this);
	this.metabolisme = new creatura.Metabolisme(this.variablesVitales);
	this.environnement = monde.getEnvironnement(x, y);
	this.action = null;
	
	/**
	 * Renvoie le type de l'entité (TODO : Enum ?)
	 */
	this.getType = function(){
		return "creature";
	};
	
	/**
	 * Renvoie l'un des besoins de la créature
	 */
	this.getBesoin = function(nom){
		return this.besoins[nom];
	};
	
	/**
	 * Renvoie l'une des variables vitales de la créature
	 */
	this.getVariableVitale = function(nom){
		return this.variablesVitales[nom];
	};
	
	/**
	 * Renvoie les données environnementales de la créature
	 */
	this.getEnvironnement = function(){
		return this.environnement;
	};
	
	/**
	 * Défini l'action actuelle que doit entreprendre cette créature
	 */
	this.setActionActuelle = function(action){
		this.action = action;
	};
	
	/**
	 * Renvoie l'action actuellement entreprise par la créature
	 */
	this.getActionActuelle = function(){
		return this.action;
	};
	
	/**
	 * Met à jour l'entité
	 * @param frame : la frame de mise à jour
	 */
	this.metAJour = function(frame){
		//IA et exécution des actions
		if(this.action !== null)
		{
			if(!this.action.metAJour(frame))
				this.action = null;
		}
		else if(frame%10 === 0)
			this.action = this._determineAction(frame);
		
		//Mise à jour de l'environnement
		this.environnement = monde.getEnvironnement(this.x, this.y);
		
		//Mise à jour du metabolisme
		this.metabolisme.metAJour();
		
		//Mise à jour des besoins
		this.metAJourBesoins();
	};
	
	this.metAJourBesoins = function(){
		//Nutriments
		var eauMin = this.variablesVitales.eau.getIdeal()/4;
		var eauActuel = this.variablesVitales.eau.get() + this.variablesVitales.eauDigestion.get();
		this.besoins.soif.set(1-(eauActuel - eauMin)/(3.0*eauMin));
		
		var sucresMin = this.variablesVitales.sucres.getIdeal()/4;
		var sucresActuel = this.variablesVitales.sucres.get() + this.variablesVitales.sucresDigestion.get();
		this.besoins.sucres.set(1-(sucresActuel - sucresMin)/(3.0*sucresMin));
		
		var proteinesMin = this.variablesVitales.proteines.getIdeal()/4;
		var proteinesActuel = this.variablesVitales.proteines.get() + this.variablesVitales.proteinesDigestion.get();
		this.besoins.proteines.set(1-(proteinesActuel - proteinesMin)/(3.0*proteinesMin));
		
		var grasMin = this.variablesVitales.gras.getIdeal()/4;
		var grasActuel = this.variablesVitales.gras.get() + this.variablesVitales.grasDigestion.get();
		this.besoins.gras.set(1-(grasActuel - grasMin)/(3.0*grasMin));
		
		//Dechets
		var dechetsMax = 3000;
		this.besoins.popo.set(this.variablesVitales.dechets.get()/dechetsMax);
		
		//Chaud/Froid
		var temperatureActuelle = this.variablesVitales.temperature.get();
		var temperatureIdeale = this.variablesVitales.temperature.getIdeal();
		this.besoins.chaud.set(0);
		this.besoins.froid.set(0);
		if(temperatureActuelle>temperatureIdeale)
			this.besoins.froid.set((temperatureActuelle-temperatureIdeale)/2);
		else if(temperatureActuelle<temperatureIdeale)
			this.besoins.chaud.set((temperatureIdeale-temperatureActuelle)/2);
			
		//Sommeil
		this.besoins.fatigue.set(1 - this.variablesVitales.fatigue.get()/this.variablesVitales.fatigue.getIdeal());
	};
	
	/**
	 * Détermine la prochaine action à entreprendre
	 */
	this._determineAction = function(frame){
		//Focus aléatoire
		var entites = monde.getListeEntites();
		var focus = entites[Math.floor(Math.random()*entites.length)];
		
		if(focus.getType() == "nourriture" && this.besoins.sucres.get() > 0.5)
			return new creatura.ActionSeDeplacer(this, focus, frame, creatura.ActionManger);
		if(focus.getType() == "nourriture" && this.besoins.proteines.get() > 0.5)
			return new creatura.ActionSeDeplacer(this, focus, frame, creatura.ActionManger);
		if(focus.getType() == "nourriture" && this.besoins.gras.get() > 0.5)
			return new creatura.ActionSeDeplacer(this, focus, frame, creatura.ActionManger);
		if(focus.getType() == "nourriture" && this.besoins.soif.get() > 0.5)
			return new creatura.ActionSeDeplacer(this, focus, frame, creatura.ActionManger);
		if(this.besoins.popo.get() > 0.7)
			return new creatura.ActionSeDeplacer(this, focus, frame, creatura.ActionPopo);
		if(this.besoins.fatigue.get() > 0.9)
			return new creatura.ActionSeDeplacer(this, focus, frame, creatura.ActionDormir);
		return null;
	};
};
	
})();