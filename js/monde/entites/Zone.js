(function(){

if(!window.creatura)
	window.creatura={};

/**
 * Représente une zone (un endroit, une salle) dans le monde (étend creatura.Entite)
 * @param x, y : les coordonnées du coin Nord-Ouest de la zone
 * @param l, h : la largeur et la hauteur de la zone
 * @param environnement : l'environnement de la zone (voir creatura.Environnement)
 */
creatura.Zone = function(monde, x, y, l, h, environnement){
	creatura.Entite.call(this, monde, x+l/2, y+h/2, l, h);
	this.environnement = environnement;
	this.geometrie = {
		x : x,
		y : y,
		l : l,
		h : h
	};
	
	/**
	 * Renvoie le type de l'entité (TODO : Enum ?)
	 */
	this.getType = function(){
		return "zone";
	};

	/**
	 * Renvoie la couleur de la zone
	 */
	this.getCouleur = function(){
		return this.environnement.getCouleur();
	};
	
	/**
	 * Renvoie l'environnement de la zone
	 */
	this.getEnvironnement = function(){
		return this.environnement;
	};
	
	/**
	 * Renvoie la géométrie de la zone
	 */
	this.getGeometrie = function(){
		return this.geometrie;
	};
	
	/**
	 * Détermine si la zone contient le point passé en paramètre
	 */
	this.contient = function(x, y){
		return x>=this.geometrie.x && x<=this.geometrie.x+this.geometrie.l && y>=this.geometrie.y && y<=this.geometrie.y+this.geometrie.h;
	};
	
	/**
	 * Met à jour l'entité
	 * @param frame : la frame de mise à jour
	 */
	this.metAJour = function(frame){}; //Inerte. Pourrait toutefois changer d'environnement (par exemple si on prenait la météo réelle ;) )
};

})();
