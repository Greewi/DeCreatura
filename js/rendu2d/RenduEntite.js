(function(){

if(!window.rendu2d)
	window.rendu2d={};

/**
 * Représente une entité dans le monde
 */
rendu2d.RenduEntite = function(entite, contexte){
	this.entite = entite;
	this.contexte = contexte;
	
	if(entite.getType() == "creature")
		this.fillStyle="#FF7300";
	else if(entite.getType() == "nourriture")
		this.fillStyle=entite.getCouleur();
	else if(entite.getType() == "zone")
		this.fillStyle=entite.getCouleur();
	else if(entite.getType() == "dechet")
		this.fillStyle="#161610";
	else
		this.fillStyle="#737373";

	/**
	 * Détruit la vue de l'entité
	 */
	this.detruit = function(){
		//Rien à faire pour le moment
	};
		
	/**
	 * Dessine l'entite
	 */
	this.dessine = function(date){
		this.contexte.fillStyle=this.fillStyle;
		this.contexte.fillRect(
			this.entite.x - this.entite.l/2,
			this.entite.y - this.entite.h/2,
			this.entite.l,
			this.entite.h
		);
	};
};

})();