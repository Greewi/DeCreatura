(function(){

if(!window.rendu2d)
	window.rendu2d = {};
	
rendu2d.RenduMonde = function(monde, contexte){
	var that = this;
	this.monde = monde;
	this.contexte = contexte;
	this.renduEntites = {};
	
	//On attache les observateurs sur les événements du monde pour mettre à jour la vue
	this.monde.onEntiteAjoutee(function(entite){
		that.renduEntites[entite.getID()] = new rendu2d.RenduEntite(entite, contexte);
	});
	this.monde.onEntiteSupprimee(function(entite){
		that.renduEntites[entite.getID()].detruit();
		delete that.renduEntites[entite.getID()];
	});
	
	/**
	 * Détruit ce moteur de rendu
	 */
	this.detruit = function(){
		for(var i in this.renduEntites)
			this.renduEntites[i].detruit();
		this.renduEntites = {};
	};
	
	/**
	 * Dessine la frame
	 */
	this.dessine = function(date){
		var entites = this.monde.getListeEntites();
		for(var i in this.renduEntites)
		{
			var renduEntite = this.renduEntites[i];
			renduEntite.dessine(date);
		}
	};
};

})();