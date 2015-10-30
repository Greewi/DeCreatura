(function(){

if(!window.nnet)
	window.nnet={};

var $ = numeric;
	
/**
 * Représente un réseau de neuronnes.
 */
nnet.CoucheNeuronnes = function(matrice){
	this.matrice = matrice;
	
	/**
	 * Calcule l'activation de la couche pour une entrée donnée.
	 * @param entree : le tableau des entrees (ne pas inclure le biais !)
	 */
	this.calculeActivation = function(entree){
		var resultat  = $.dot(this.matrice, [1.0].concat(entree));
		for(var i=0; i<resultat.length; i++)
			resultat[i] = nnet.sigmoid(resultat[i]);
		return resultat;
	};
	
	/**
	 * Construit une chaine claire pour afficher la matrice de cette couche
	 */
	this.toString = function(){
		return $.prettyPrint(this.matrice);
	};
};

/**
 * Construit une couche de neuronnes dont les fonctions d'activation ont été initialisées aléatoirement
 */
nnet.CoucheNeuronnes.creeAleatoire = function(nombreEntree, nombreUnites){
	var matrice = $.add(-1, $.mul(2, $.random([nombreUnites, nombreEntree])));;
	return new nnet.CoucheNeuronnes(matrice);
};

})();