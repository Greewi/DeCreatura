(function(){

if(!window.creatura)
	window.creatura={};

/**
 * Représente de la nourriture (étend creatura.Entite)
 */
creatura.Nourriture = function(monde, typeNourriture, x, y){
	creatura.Entite.call(this, monde, x, y, 2, 2);
	
	this.typeNourriture = typeNourriture; //Le type de la nourriture (Voir les constantes creatura.Nourriture.*)
	
	/**
	 * Renvoie le type de l'entité (TODO : Enum ?)
	 */
	this.getType = function(){
		return "nourriture";
	};
	
	/**
	 * Renvoie la couleur de cette nourriture
	 */
	this.getCouleur = function(){
		return this.typeNourriture.couleur;
	};
	
	/**
	 * Renvoie la quantité d'eay contenue dans cette nourriture
	 */
	this.getEau = function(){
		return this.typeNourriture.eau;
	};
	
	/**
	 * Renvoie la quantité de sucres contenus dans cette nourriture
	 */
	this.getSucres = function(){
		return this.typeNourriture.sucres;
	};
	
	/**
	 * Renvoie la quantité de proteines contenues dans cette nourriture
	 */
	this.getProteines = function(){
		return this.typeNourriture.proteines;
	};
	
	/**
	 * Renvoie la quantité de gras contenus dans cette nourriture
	 */
	this.getGras = function(){
		return this.typeNourriture.gras;
	};
	
	/**
	 * Met à jour l'entité
	 * @param frame : la frame de mise à jour
	 */
	this.metAJour = function(frame){}; //Inerte. On verra plus tard pour la nourriture qui se sauve :p
};

// Fruits (valeurs en grammes pour 1kg, source : http://www.1001fruits.net/ )
creatura.Nourriture.TOMATE = 		{nom : "Tomate",	couleur : "#EB4B4B",	sucres : 40, 	proteines : 10, 	gras : 3, 		eau : 930};
creatura.Nourriture.CERISE = 		{nom : "Cerise",	couleur : "#BA3442",	sucres : 160, 	proteines : 10, 	gras : 10, 		eau : 800};
creatura.Nourriture.PRUNE = 		{nom : "Prune",		couleur : "#832B8F",	sucres : 130, 	proteines : 10, 	gras : 6, 		eau : 850};
creatura.Nourriture.FRAMBOISE =		{nom : "Framboise",	couleur : "#ED268A",	sucres : 110, 	proteines : 10, 	gras : 110, 	eau : 860};
creatura.Nourriture.FRAISE =		{nom : "Fraise",	couleur : "#F0401D",	sucres : 3, 	proteines : 6,	 	gras : 3,	 	eau : 910};
creatura.Nourriture.NOIX = 			{nom : "Noix",		couleur : "#A3754D",	sucres : 130, 	proteines : 150, 	gras : 650, 	eau : 40};
creatura.Nourriture.CACAHUETE = 	{nom : "Cacahuete",	couleur : "#C9BB7F",	sucres : 90, 	proteines : 230, 	gras : 400, 	eau : 70};
creatura.Nourriture.NOISETTE = 		{nom : "Noisette",	couleur : "#D18834",	sucres : 160, 	proteines : 150, 	gras : 600, 	eau : 50};
creatura.Nourriture.MAIS = 			{nom : "Mais",		couleur : "#D7DE12",	sucres : 200, 	proteines : 30, 	gras : 10, 		eau : 150}; //Pas trouvé de valeur l'eau mise au pif

creatura.Nourriture.types = [
	creatura.Nourriture.TOMATE,
	creatura.Nourriture.CERISE,
	creatura.Nourriture.PRUNE,
	creatura.Nourriture.FRAMBOISE,
	creatura.Nourriture.FRAISE,
	creatura.Nourriture.NOIX,
	creatura.Nourriture.CACAHUETE,
	creatura.Nourriture.NOISETTE,
	creatura.Nourriture.MAIS
];

})();