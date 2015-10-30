var canvas = document.getElementById("canvas");
canvas.width = window.innerWidth;
canvas.height = window.innerHeight;
var contexte = canvas.getContext("2d");

//UI Basique
var informations = document.getElementById("informations");
var variables = document.getElementById("variables");

//Construction du monde
var monde = new creatura.Monde(new creatura.Environnement(0, 0, "#000000"));
var vueMonde = new rendu2d.RenduMonde(monde, contexte);

//Ajout des zones
monde.ajouteZone(new creatura.Zone(monde, 0, 0, 50, 100, new creatura.Environnement(28, 1, "#A6FFAD")));
monde.ajouteZone(new creatura.Zone(monde, 50, 0, 50, 50, new creatura.Environnement(37, 1, "#FCFFA6")));
monde.ajouteZone(new creatura.Zone(monde, 50, 50, 50, 50, new creatura.Environnement(12, 1, "#A6FFEF")));

//Ajout d'une litière
monde.ajouteEntite(new creatura.Litiere(monde, type, 75, 25));

//Ajout d'une créature
var creature = new creatura.Creature(monde, 50, 50);
monde.ajouteEntite(creature);

//Ajout de la nourriture
for(var i=0; i<100; i++)
{
	var type = creatura.Nourriture.types[Math.floor(Math.random()*creatura.Nourriture.types.length)];
	monde.ajouteEntite(new creatura.Nourriture(monde, type, Math.floor(100*Math.random()), Math.floor(100*Math.random())));
}

//Stockage des valeurs précédentes des variables vitales
var variablesPrecedentes = {};

//Boucle de rendu
var numeroFrame = 0;
var boucleRendu = function(date){
	//Mise à jour du monde
	monde.metAJour(numeroFrame++);

	//On met a jour l'UI
	informations.innerHTML = "<ul>";
	if(creature.getActionActuelle()!==null)
		informations.innerHTML += "<li>Action : " + creature.getActionActuelle().nom + "</li>";
	else
		informations.innerHTML += "<li>Action : Aucune</li>";
	informations.innerHTML += "<hr/>";
	for(var nomBesoin in creature.besoins)
	{
		var besoin = creature.besoins[nomBesoin];
		informations.innerHTML += "<li>" + besoin.nom + " : " + Math.round(besoin.valeur*100) + "</li>";
	}
	informations.innerHTML += "</ul>";
	variables.innerHTML = "<ul>";
	variables.innerHTML += "<li>Variables</li>";
	variables.innerHTML += "<hr/>";
	for(var nomVariable in creature.variablesVitales)
	{
		var variableVitale = creature.variablesVitales[nomVariable];
		var delta = Math.round((variableVitale.get() - variablesPrecedentes[nomVariable])*100)/100;
		variablesPrecedentes[nomVariable] = variableVitale.get();
		variables.innerHTML +=
			"<li>"
			+ variableVitale.getNom()
			+ " : " + Math.round(variableVitale.get()) +"/"+ Math.round(variableVitale.getIdeal())
			+ ((delta>=0) ? " +" : " ") + delta
			+ "</li>";
		
	}
	variables.innerHTML += "</ul>";
	
	
	//Affichage du canvas
	canvas.width = canvas.width;
	//Scale + translation
	if(canvas.width<canvas.height)
		contexte.scale(canvas.width/100, canvas.width/100);
	else
		contexte.scale(canvas.height/100, canvas.height/100);
	
	vueMonde.dessine();
	
	requestAnimationFrame(boucleRendu);
};
requestAnimationFrame(boucleRendu);