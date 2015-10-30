(function(){

if(!window.creatura)
	window.creatura={};

/**
 * Représente le monde. Il contiendra les entités.
 */
creatura.Monde = function(environnementParDefaut){
	this.entites = {};  //Les entités indexées par ID -> Entitée
	this.IDEntiteSuivant = 1; //L'ID de l'entité
	this.zones = [];		//Les zones (aussi incluses dans les entités)
	this.environnement = environnementParDefaut;
	//Observateurs
	this.observateurEntiteAjoutee = [];
	this.observateurEntiteSupprimee = [];
	
	/**
	 * Génère et renvoie l'ID d'une nouvelle entité
	 */
	this.genereIDEntite = function(){
		return this.IDEntiteSuivant++;
	};
	
	/**
	 * Ajoute une entité au monde
	 */
	this.ajouteEntite = function(entite){
		this.entites[entite.getID()] = entite;
		//On notifie les observateurs
		for(var i=0; i<this.observateurEntiteAjoutee.length; i++)
			this.observateurEntiteAjoutee[i](entite);
	};
	
	/**
	 * Supprime une entité du monde
	 */
	this.supprimeEntite = function(ID){
		if(this.entites.hasOwnProperty(ID))
		{
			var entite = this.entites[ID];
			if(entite.getType()=="zone") //On ne supprime pas les zones
				return;
			delete this.entites[ID];
			//On notifie les observateurs
			for(var i=0; i<this.observateurEntiteAjoutee.length; i++)
				this.observateurEntiteSupprimee[i](entite);
		}
	};
	
	/**
	 * Renvoie une entité du monde (ou null si elle n'existe pas)
	 */
	this.getEntite = function(ID){
		if(this.entites.hasOwnProperty(ID))
			return this.entites[ID];
		return null;
	};
	
	/**
	 * Renvoie la liste des entités du monde
	 */
	this.getListeEntites = function(){
		var liste = [];
		for(var ID in this.entites)
			liste.push(this.entites[ID]);
		return liste;
	};
	
	/**
	 * Ajoute une zone dans le monde
	 */
	this.ajouteZone = function(zone){
		this.ajouteEntite(zone);
		this.zones.push(zone);
	};
	
	/**
	 * Renvoie la zone correspondant aux coordonnées passées en paramètre
	 * Renvoie null si aucune zone ne couvre les coordonnées passées
	 */
	this.getZone = function(x, y){
		for(var i=0; i<this.zones.length; i++)
			if(this.zones[i].contient(x, y))
				return this.zones[i];
		return null;
	};
	
	/**
	 * Renvoie les donénes d'environnement au point (x,y)
	 */
	this.getEnvironnement = function(x, y){
		var zone = this.getZone(x, y);
		if(zone === null)
			return this.environnement;
		else
			return zone.getEnvironnement();
	};
	
	/**
	 * Met à jour le monde lors d'une nouvelle frame
	 */
	this.metAJour = function(frame){
		for(var ID in this.entites)
			this.entites[ID].metAJour(frame);
		//Nettoyage des entitées disparues
		for(var ID in this.entites)
			if(!this.entites[ID].existe())
				this.supprimeEntite(ID);
	};
	
	/**
	 * Ajoute un observateur sur l'événement d'ajout d'une entité
	 * @param observateur : une fonction de callback prenant l'entité ajoutée en paramètre
	 */
	this.onEntiteAjoutee = function(observateur){
		this.observateurEntiteAjoutee.push(observateur);
	};
	
	/**
	 * Ajoute un observateur sur l'événement de suppression d'une entité.
	 * @param observateur : une fonction de callback prenant l'entité suprimée en paramètre
	 */
	this.onEntiteSupprimee = function(observateur){
		this.observateurEntiteSupprimee.push(observateur);
	};
};
	
})();