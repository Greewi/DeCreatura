(function(){

if(!window.creatura)
	window.creatura={};

/**
 * Représente une litiere dans le monde (étend creatura.Entite)
 */
creatura.Litiere = function(monde, capacite, x, y){
	creatura.Entite.call(this, monde, x, y, 10, 10);
	this.capacite = capacite;
	this.contenu = 0;
	
	/**
	 * Renvoie le type de l'entité (TODO : Enum ?)
	 */
	this.getType = function(){
		return "litiere";
	};

	/**
	 * Renvoie la quantité de déchets pouvant être contenus dans cette litiere
	 */
	this.getCapacite = function(){
		return this.capacite;
	};
	
	/**
	 * Renvoie la quantité de déchets actuellement contenus dans cette litiere
	 */
	this.getContenu = function(){
		return this.contenu;
	};
	
	/**
	 * Ajoute des déchets dans la litière
	 */
	this.ajouteDechets = function(quantite){
		//Si la litière n'est pas pleine on stock le déchet
		if(this.contenu + quantite <= this.capacite)
			this.contenu += quantite;
		//Si la litiere est pleine on créé un déchet dans le monde
		else
		{
			var x = this.x + (Math.random()-0.5)*this.l;
			var y = this.y + (Math.random()-0.5)*this.h;			
			monde.ajouteEntite(new creatura.Dechet(monde, quantite, x, y));
		}
	};
	
	/**
	 * Met à jour l'entité
	 * @param frame : la frame de mise à jour
	 */
	this.metAJour = function(frame){}; //Inerte.
};

})();