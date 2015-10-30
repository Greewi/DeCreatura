(function(){

if(!window.creatura)
	window.creatura={};

/**
 * Représente une entité dans le monde
 */
creatura.Entite = function(monde, x, y, l, h){
	this.monde = monde;
	this.ID = monde.genereIDEntite();
	this.position = {x:x, y:y};
	this.taille = {l:l, h:h};
	this.estDetruit = false;
	
	/**
	 * Accesseur sur les propriétés de position x, y et de taille l, h.
	 */
	Object.defineProperty(this, "x", {
		get : function(){return this.position.x;},
		set : function(x){this.position.x=x;}
	});
	Object.defineProperty(this, "y", {
		get : function(){return this.position.y;},
		set : function(y){this.position.y=y;}
	});
	Object.defineProperty(this, "l", {
		get : function(){return this.taille.l;},
	});
	Object.defineProperty(this, "h", {
		get : function(){return this.taille.h;},
	});

	/**
	 * Renvoie l'ID de l'entité
	 */
	this.getID = function(){
		return this.ID;
	};
	
	/**
	 * Renvoie le type de l'entité (TODO : Enum ?)
	 */
	this.getType = function(){
		return "rien";
	};
	
	/**
	 * Détruit l'entité.
	 */
	this.detruit = function(){
		this.estDetruit = true;
	};
	
	/**
	 * Détermine si l'entité existe encore
	 */
	this.existe = function(){
		return !this.estDetruit;
	};
	
	/**
	 * Met à jour l'entité
	 * @param frame : la frame de mise à jour
	 */
	this.metAJour = function(frame){};	
};

})();