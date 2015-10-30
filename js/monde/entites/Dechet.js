(function(){

if(!window.creatura)
	window.creatura={};

/**
 * Représente un déchet (un excrément en fait) dans le monde (étend creatura.Entite)
 */
creatura.Dechet = function(monde, quantite, x, y){
	creatura.Entite.call(this, monde, x, y, quantite/1000.0, quantite/1000.0);
	this.quantite = quantite;
	
	/**
	 * Renvoie le type de l'entité (TODO : Enum ?)
	 */
	this.getType = function(){
		return "dechet";
	};

	/**
	 * Renvoie la quantité de déchets pouvant être contenus dans cette litiere
	 */
	this.getTaille = function(){
		return this.quantite;
	};

	/**
	 * Met à jour l'entité
	 * @param frame : la frame de mise à jour
	 */
	this.metAJour = function(frame){}; //Inerte.
};

})();
