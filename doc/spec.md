# De Creatura - spécifications

## Résumé
De Creatura est un jeu mettant en scène des ALF (Artifical Life Form). Le joueur devra s'en occuper, les éduquer et les modifier pour explorer et s'étendre sur toute une carte.

Le jeu comporte donc des éléments d'élevage, de petting, de gestion, de crafting et de construction.

Les créatures et les autres éléments "organiques" du jeu sont définis à l'échelle de leurs organes, eux même construit à partir de séquences "ADN".

La partie se déroule sur une carte présentant des environnements variés pour lesquels le joueur devra préparer ses créatures en vue de les explorer, puis de les capturer.

## Le jeu vu du joueur

### Buts du jeu

* Elever et prendre soin de ses créatures ;
* Etablir une petite industrie ;
* Explorer et prendre le contrôle de tous les secteurs.

### Actions du joueur

* Interagir avec les créatures (Bâton/Carotte, guider/focus) ;
* Soigner les créatures (Nourrir, soins médicaux, confort, etc.) ;
* Construire des bâtiments (de production à la factorio, en beaucoup plus léger, mais aussi des trucs pour les créatures (nids, etc.)) ;
* Altérer les créatures (Modification, héritage, etc.) ;
* Editer l'ADN des oeufs (des créatures, des plantes, etc.) ;
* Découvrir de nouveaux gènes ;
* Étendre sa zone d'influence (requiert d'amener une créature à un endroit précis en évitant un certain nombre de pièges).

### Interactions avec une créature

Cajoler :
* augmente le *moral* de la créature ;
* si elle vient de faire une action, elle sera plus encleinte à la refaire ;
* carresses ?

Punir :
* réduit le *moral* de la créature ;
* si elle vient de faire une action, elle sera moins encleinte à la refaire ;
* poke ? spray ? éviter de faire une action pour la frapper...

Reprendre :
* reprend un objet que la créature a rammassé dans ses pattes.

Utiliser un objet :
* action en fonction de l'objet ;
* si c'est de la nourriture, ça lui donnera dans les pattes et elle pourra le manger ;
* si c'est un médicamment, ça lui administrera ;
* si c'est un objet d'hygiène, ça la lavera (frotter la créature avec ?) ;
* si c'est un outil d'examen médical, ça récupérera les données (et fera sans doute perdre un peu de moral à la créature) ;
* etc.

Donner le focus :
* selectionne un objet pour la créature ;
* la créature ressentira l'objet comme différent ;
* suivant son éducation elle l'examinera en priorité ou non.

## Créatures

### Variables principales
*Energie* :
* capacité à rester éveillée et à faire des efforts mentaux ;
* à 0, la créature s'endort de force ;
* défini par le taux de *Noradrénaline*.

*Moral* :
* l'humeur et la motivation de la créature ;
* à 0, la créature devient apathique et n'effectue plus d'action #dépression ;
* défini par le taux de *Sérotonine* ;
* augmente en fonction de diverses actions et de l'état de la créature ;
* ne peut être suppérieur à *l'Eveil* de la créature.

*Eveil* :
* la curiosité et le renouvellement de la créature ;
* pose une limite maximum au *Moral* ;
* défini par le taux de *Dopamine* ;
* augmente quand la créature fait des choses nouvelles et se réduit avec l'inactivité ou la répétition. 

*Satiété* :
* détermine la sensation de ne pas avoir faim ;
* défini par le taux de *Cholécystokinine* ;
* dérivé du taux de remplissage de l'estomac de la créature.

*Santé* :
* niveau de santé de la créature ;
* à 0, la créature décède ;
* réduite par un *moral* ou un *éveil* bas ;
* réduite par les maladies ;
* réduite par les blessures. 

### Ressources des créatures

Les ressources des créatures sont basés sur les taux des substances présents dans ses organes. En fonction des organes, certaines substances peuvent avoir un intérêt variable.

NOTE /!\ : les substances et leurs effets décrits ici sont très fortement approximés et relève pour une grande part de la fiction !  

*Eau* :
* source de vie ;
* consommé par tous les organes ;
* récupéré lors de la digestion des aliments ;
* une carence provoque la mort de la créature.

*Glucides* :
* carburant de tous les organes ;
* excès dans le sang converti en *Lipides* ;
* si le taux sanguin devient bas, les *Lipides* commenceront à être converti en *Glucides* ;
* récupéré lors de la digestion des aliments ;
* de façon triviale, si la quantité de *Glucide* d'une créature atteint 0, elle est morte.

*Lipides* :
* réserve de carburant et isolant ;
* stocké dans les graisses ;
* si le taux de *Glucides* devient faible, les *Lipides* seront convertis en *Glucides* ;
* inversement si le taux de *Glucides* devient élevé ; 
* récupéré lors de la digestion des aliments.
 
*Proteines* :
* composés dont les organes se servent pour produire d'autres substances ;
* peuvent être converti en *Glucides* en tout dernier recours (mais produit des Toxines) ;
* récupéré lors de la digestion des aliments.

*Toxines* : 
* déchets produit par le fonctionnement des organes ;
* traités par le système rénal ;
* évacués lors de l'action Faire ses besoins ;
* rend la créature malade si le taux devient élevé. 

*Cholécystokinine* :
* définit la *Satiété* de la créature ;
* produit par l'estomac et stocké dans le réseau sanguin ;
* taux directement dépendant du taux de remplissage de l'estomac de la créature.

*Noradrénaline* :
* défini *l'Energie* de la créature ;
* produit et stoqué dans le cerveau de la créature ;
* produit durant le sommeil ;
* consommé à chaque cycle du cerveau.

*Sérotonine* :
* défini le *Moral* de la créature ;
* produit et stoqué dans le cerveau de la créature ;
* augmente en fonction de diverses actions et de l'état de la créature ;
* un taux supérieur à celui de la *Dopamine* n'a pas d'effet. 

*Dopamine* :
* défini *l'Eveil* de la créature ;
* produit et stoqué dans le cerveau de la créature ;
* augmente quand la créature fait des choses nouvelles et se réduit avec l'inactivité ou la répétition. 

### Actions des créatures

Déplacement :
* la créature se déplace vers une autre entité

Actionner :
* la créature actionne une entité ;
* si c'est un bouton, elle appuie dessus ;
* si c'est une autre créature, elle papote avec et gagne de la *sociabilité*;
* autre ? il serait intéressant qu'elle puisse aussi faire des conneries.

Dormir :
* la créature dort là où elle se trouve ;
* suivant les conditions elle peut ne pas arriver à trouver le sommeil ;
* durant le sommeil, elle regagne son *énergie* ;
* une fois qu'elle a regagné toute son *énergie*, elle se réveillera automatiquement.

Faire ses besoins :
* la créature va se délester des déchets qu'elle a dans le ventre sur une entité ;
* le joueur préférera qu'elle fasse ça dans les latrines.

Rammasser :
* la créature ramasse un objet

Poser :
* la créature pose ce qu'elle a en main

Manger :
* la créature mange ce qu'elle a en main

### Organes
* Estomac : reçoit les aliments mangés par la créature et les digère. Les nutriments digérés sont transmis au système sanguin et les déchets sont stockés en attente d'être évacués ;
* Système sanguin : stocke les nutriments prêts à être utilisés et les déchets de fonctionnement des autres organes ;
* Reins : extrait les déchets du système sanguin et les stocke en attendant de les évacuer ;
* Système sensoriel : compile les informations de l'environnement et certaines informations internes pour les transformer en signaux utilisables par le cerveau ;
* Cerveau : traite les signaux du système sensoriel et donne des ordres au système moteur ;
* Système moteur : fait agir la créature (déplacement et autres actions) ;
* Tissu adipeux : stoque les *Lipides* et améliore l'isolation thermique.
* Peau : protège les autres organes de l'environnement.

### ADN

TODO

## Interfaces

Vue du monde :
* le joueur peut sélectionner une créature et lui donner un focus, interagir avec ou utiliser un objet dessus ;
* le joueur peut sélectionner un batiment et activer ses fonctions ;
* le joueur peut lancer la construction d'un batiment ;
* en dehors de sa zone contrôlée, le joueur ne peut que sélectionner une de ses créatures, lui donner un focus et interagir avec.

Vue carte :
* le joueur peut observer la carte d'un seul tenant ;
* il peut ouvrir la vue monde en un seul clic sur un point de cette carte ;
* il peut afficher divers calques pour afficher les informations environnementales ;
* il peut sélectionner une créature ou un batiment et avoir un apperçu de ses caratéristiques.






