(function(){

if(!window.nnet)
	window.nnet={};

/**
 * Représente un réseau de neuronnes.
 */
nnet.ReseauNeuronnes = function(couches){
	this.couches = couches;

	/**
	 * Calcule l'activation du réseau de neuronnes pour une entrée donnée. (Forward propagation)
	 * @param entree : le tableau des entrees (ne pas inclure le biais !)
	 */
	this.calculeActivation = function(entree){
		var activation = Array.from(entree);
		for(var i=0; i<this.couches.length; i++)
			activation = this.couches[i].calculeActivation(activation);
		return activation;
	};
	
	/**
	 * Construit une chaine claire pour afficher le reseau de neuronne
	 */
	this.toString = function(){
		var string = "NNet:";
		for(var i =0; i<this.couches.length; i++)
			string += "\n"+this.couches[i].toString();
		return string;
	};
};

})();