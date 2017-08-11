# De Creatura - sp�cifications

## R�sum�
De Creatura est un jeu mettant en sc�ne des ALF (Artifical Life Form). Le joueur devra s'en occuper, les �duquer et les modifier pour explorer et s'�tendre sur toute une carte.

Le jeu comporte donc des �l�ments d'�levage, de petting, de gestion, de crafting et de construction.

Les cr�atures et les autres �l�ments "organiques" du jeu sont d�finis � l'�chelle de leurs organes, eux m�me construit � partir de s�quences "ADN".

La partie se d�roule sur une carte pr�sentant des environnements vari�s pour lesquels le joueur devra pr�parer ses cr�atures en vue de les explorer, puis de les capturer.

## Le jeu vu du joueur

### Buts du jeu

* Elever et prendre soin de ses cr�atures ;
* Etablir une petite industrie ;
* Explorer et prendre le contr�le de tous les secteurs.

### Actions du joueur

* Interagir avec les cr�atures (B�ton/Carotte, guider/focus)�;
* Soigner les cr�atures (Nourrir, soins m�dicaux, confort, etc.) ;
* Construire des b�timents (de production � la factorio, en beaucoup plus l�ger, mais aussi des trucs pour les cr�atures (nids, etc.)) ;
* Alt�rer les cr�atures (Modification, h�ritage, etc.) ;
* Editer l'ADN des oeufs (des cr�atures, des plantes, etc.) ;
* D�couvrir de nouveaux g�nes ;
* �tendre sa zone d'influence (requiert d'amener une cr�ature � un endroit pr�cis en �vitant un certain nombre de pi�ges).

### Interactions avec une cr�ature

Cajoler :
* augmente le *moral* de la cr�ature ;
* si elle vient de faire une action, elle sera plus encleinte � la refaire ;
* carresses ?

Punir :
* r�duit le *moral* de la cr�ature ;
* si elle vient de faire une action, elle sera moins encleinte � la refaire ;
* poke ? spray ? �viter de faire une action pour la frapper...

Reprendre :
* reprend un objet que la cr�ature a rammass� dans ses pattes.

Utiliser un objet :
* action en fonction de l'objet ;
* si c'est de la nourriture, �a lui donnera dans les pattes et elle pourra le manger ;
* si c'est un m�dicamment, �a lui administrera ;
* si c'est un objet d'hygi�ne, �a la lavera (frotter la cr�ature avec ?) ;
* si c'est un outil d'examen m�dical, �a r�cup�rera les donn�es (et fera sans doute perdre un peu de moral � la cr�ature) ;
* etc.

Donner le focus :
* selectionne un objet pour la cr�ature ;
* la cr�ature ressentira l'objet comme diff�rent ;
* suivant son �ducation elle l'examinera en priorit� ou non.

## Cr�atures

### Variables principales
*Energie* :
* capacit� � rester �veill�e et � faire des efforts mentaux ;
* � 0, la cr�ature s'endort de force ;
* d�fini par le taux de *Noradr�naline*.

*Moral* :
* l'humeur et la motivation de la cr�ature ;
* � 0, la cr�ature devient apathique et n'effectue plus d'action #d�pression ;
* d�fini par le taux de *S�rotonine* ;
* augmente en fonction de diverses actions et de l'�tat de la cr�ature ;
* ne peut �tre supp�rieur � *l'Eveil* de la cr�ature.

*Eveil* :
* la curiosit� et le renouvellement de la cr�ature ;
* pose une limite maximum au *Moral* ;
* d�fini par le taux de *Dopamine* ;
* augmente quand la cr�ature fait des choses nouvelles et se r�duit avec l'inactivit� ou la r�p�tition. 

*Sati�t�* :
* d�termine la sensation de ne pas avoir faim ;
* d�fini par le taux de *Chol�cystokinine* ;
* d�riv� du taux de remplissage de l'estomac de la cr�ature.

*Sant�* :
* niveau de sant� de la cr�ature ;
* � 0, la cr�ature d�c�de ;
* r�duite par un *moral* ou un *�veil* bas ;
* r�duite par les maladies ;
* r�duite par les blessures. 

### Ressources des cr�atures

Les ressources des cr�atures sont bas�s sur les taux des substances pr�sents dans ses organes. En fonction des organes, certaines substances peuvent avoir un int�r�t variable.

NOTE /!\ : les substances et leurs effets d�crits ici sont tr�s fortement approxim�s et rel�ve pour une grande part de la fiction !  

*Eau* :
* source de vie ;
* consomm� par tous les organes ;
* r�cup�r� lors de la digestion des aliments ;
* une carence provoque la mort de la cr�ature.

*Glucides* :
* carburant de tous les organes ;
* exc�s dans le sang converti en *Lipides* ;
* si le taux sanguin devient bas, les *Lipides* commenceront � �tre converti en *Glucides* ;
* r�cup�r� lors de la digestion des aliments ;
* de fa�on triviale, si la quantit� de *Glucide* d'une cr�ature atteint 0, elle est morte.

*Lipides* :
* r�serve de carburant et isolant ;
* stock� dans les graisses ;
* si le taux de *Glucides* devient faible, les *Lipides* seront convertis en *Glucides* ;
* inversement si le taux de *Glucides* devient �lev� ; 
* r�cup�r� lors de la digestion des aliments.
 
*Proteines* :
* compos�s dont les organes se servent pour produire d'autres substances ;
* peuvent �tre converti en *Glucides* en tout dernier recours (mais produit des Toxines) ;
* r�cup�r� lors de la digestion des aliments.

*Toxines* : 
* d�chets produit par le fonctionnement des organes ;
* trait�s par le syst�me r�nal ;
* �vacu�s lors de l'action Faire ses besoins ;
* rend la cr�ature malade si le taux devient �lev�. 

*Chol�cystokinine* :
* d�finit la *Sati�t�* de la cr�ature ;
* produit par l'estomac et stock� dans le r�seau sanguin ;
* taux directement d�pendant du taux de remplissage de l'estomac de la cr�ature.

*Noradr�naline* :
* d�fini *l'Energie* de la cr�ature ;
* produit et stoqu� dans le cerveau de la cr�ature ;
* produit durant le sommeil ;
* consomm� � chaque cycle du cerveau.

*S�rotonine* :
* d�fini le *Moral* de la cr�ature ;
* produit et stoqu� dans le cerveau de la cr�ature ;
* augmente en fonction de diverses actions et de l'�tat de la cr�ature ;
* un taux sup�rieur � celui de la *Dopamine* n'a pas d'effet. 

*Dopamine* :
* d�fini *l'Eveil* de la cr�ature ;
* produit et stoqu� dans le cerveau de la cr�ature ;
* augmente quand la cr�ature fait des choses nouvelles et se r�duit avec l'inactivit� ou la r�p�tition. 

### Actions des cr�atures

D�placement :
* la cr�ature se d�place vers une autre entit�

Actionner :
* la cr�ature actionne une entit� ;
* si c'est un bouton, elle appuie dessus ;
* si c'est une autre cr�ature, elle papote avec et gagne de la *sociabilit�*;
* autre ? il serait int�ressant qu'elle puisse aussi faire des conneries.

Dormir :
* la cr�ature dort l� o� elle se trouve ;
* suivant les conditions elle peut ne pas arriver � trouver le sommeil ;
* durant le sommeil, elle regagne son *�nergie* ;
* une fois qu'elle a regagn� toute son *�nergie*, elle se r�veillera automatiquement.

Faire ses besoins :
* la cr�ature va se d�lester des d�chets qu'elle a dans le ventre sur une entit� ;
* le joueur pr�f�rera qu'elle fasse �a dans les latrines.

Rammasser :
* la cr�ature ramasse un objet

Poser :
* la cr�ature pose ce qu'elle a en main

Manger :
* la cr�ature mange ce qu'elle a en main

### Organes
* Syst�me digestif�: re�oit les aliments mang�s par la cr�ature et les dig�re. Les nutriments dig�r�s sont transmis au syst�me sanguin et les d�chets sont stock�s en attente d'�tre �vacu�s�;
* Syst�me sanguin�: stocke les nutriments pr�ts � �tre utilis�s et les d�chets de fonctionnement des autres organes�;
* Syst�me r�nal�: extrait les d�chets du syst�me sanguin et les stocke en attendant de les �vacuer�;
* Syst�me sensoriel�: compile les informations de l'environnement et certaines informations internes pour les transformer en signaux utilisables par le cerveau�;
* Cerveau�: traite les signaux du syst�me sensoriel et donne des ordres au syst�me moteur�;
* Syst�me moteur�: fait agir la cr�ature (d�placement et autres actions)�;
* Graisses : stoque les *Lipides* et am�liore l'isolation thermique.
* Pelage�: am�liore l'isolation thermique.

### ADN

TODO

## Interfaces

Vue du monde :
* le joueur peut s�lectionner une cr�ature et lui donner un focus, interagir avec ou utiliser un objet dessus ;
* le joueur peut s�lectionner un batiment et activer ses fonctions ;
* le joueur peut lancer la construction d'un batiment ;
* en dehors de sa zone contr�l�e, le joueur ne peut que s�lectionner une de ses cr�atures, lui donner un focus et interagir avec.

Vue carte :
* le joueur peut observer la carte d'un seul tenant ;
* il peut ouvrir la vue monde en un seul clic sur un point de cette carte ;
* il peut afficher divers calques pour afficher les informations environnementales ;
* il peut s�lectionner une cr�ature ou un batiment et avoir un apper�u de ses carat�ristiques.






