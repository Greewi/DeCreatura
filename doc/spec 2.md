# De Creatura - sp�cifications 2

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

# Cr�atures

## Personnalisation et ADN
L'aspect, les performances et les capacit�s sp�ciales de la cr�ature sont d�finies par son ADN.

ADN : 
* chaine de texte balis� par des s�quences (codons)
* code directement les structures

G�nes :
* Chaque g�ne contient deux param�tres :
  * Parametre organe : le param�tre de l'organe qu'il modifie
  * Ex. "saturation peau", "teinte peau", "luminosit� peau", "efficacit� poumons", "efficacit� branchies"
  * La valeur du param�tre (de 0 � 5)
  * Une valeur de 0 veut dire que le g�ne est d�fectueux
* Un g�ne peut �tre pr�sent plusieurs fois
  * Pour certains, le plus fort est pris en compte
  * Pour les autres, on additionne leur valeur

## Organes et fonctions de la cr�ature

La cr�ature poss�de plusieurs organes dont la pr�sence et les performances sont d�termin�s par son ADN.

Certains organes sont accompagn�s de l'un des qualificatifs suivants :
* vital : si la cr�ature n'en a pas, elle n'est pas viable
* passif : ils ne comptent pas dans la charge de la faim
* cosm�tique : ils sont passif et n'ont pas de fonction propre

* Cerveau (vital) : d�termine la capacit� disponible pour l'IA
* Coeur (vital) : d�termine l'endurance de la cr�ature
* Poumons (vital) : d�termine la capacit� d'apn�e de la cr�ature
* Branchies : permet � la cr�ature de respirer sous l'eau
* Estomac (vital) : d�termine la rapidit� de la d�charge de la faim et de la soif
* Pattes : d�termine la vitesse de la cr�ature
* Pelage (passif) : d�termine l'isolation thermique de la cr�ature
* Couleur peau (cosm�tique) : d�termine la couleur de la peau (de blanc � noir)
* Couleur yeux (cosm�tique) : d�termine la couleur des yeux (albinos, bleu, vert, jaune, brun, noir)




## Variables vitales

L'�tat de sant� de la cr�ature est d�finie par deux choses :
* les moodles : des �l�ments affectant la cr�ature (fatigue, maladie, faim, surprise, etc...), id�alement affich�es sous la forme d'une icone.
* la vie : l'�tat de sant� de la cr�ature. Id�alement repr�sent� par une jauge.

### La vie
La vie repr�sente l'�tat de sant� global de la cr�ature. � 100, la cr�ature est en pleine forme, � 0 elle est morte (RIP).

Naturellement la vie se remplie avec le temps, mais certains moodles peuvent bloquer cette r�g�n�ration, voir la faire se r�duire � leur apparition ou au fil du temps.

### Moodles

Ils peuvent se trouver sous deux �tats :
* actif : la cr�ature est affect� et le moodle sera affich� (id�alement sous forme d'icone)
* inactif : la cr�ature n'est pas affect� par le moodle qui ne sera donc pas affich�.

Les moodles poss�dent un compteur en interne et peuvent �tre en charge ou d�charge :
* en charge : un moodle inactif peut �tre en charge : son compteur est augment� par quelque chose (le temps, un autre �tat, l'environnement, certaines actions) et lorsqu'il atteindra le seuil maximal, le moodle deviendra actif.
* en d�charge : un moodle actif peut �tre en d�charge. Son compte est d�cr�ment� par quelque chose (l� encore le temps, etc.) et lorsqu'il atteindra 0, le moodle deviendra inactif.

Chaque moodle poss�de ses propres m�caniques d'activation et de charge. Un seul moodle d'un m�me type peut �tre attach� � la m�me cr�ature.

Il est � noter que tous les moodles ne sont pas n�cessairement connus par la cr�ature elle-m�me (notamment certaines maladies).

### Exemples de moodles

* Faim : se charge avec le temps, se d�sactive quand la cr�ature mange. Bloque la vie.
* Soif : se charge avec le temps, se d�sactive quand la cr�ature boit.  Bloque et r�duit la vie.
* Fatigue : se charge avec les actions et le temps, se d�sactive quand la cr�ature dort. Bloque la vie.
* Ennui : se charge avec le temps, se d�charge lorsque la cr�ature effectue des actions nouvelles.
* Froid : se charge avec le temps dans un environnement froid ou si la cr�ature est mouill�e. Se d�charge avec le temps une fois dans un environnement temp�r� ou chaud. Bloque et r�duit la vie. Charge la faim.
* Chaud : se charge avec le temps dans un environnement chaud. Se d�charge avec le temps une fois dans un environnement t�mp�r� ou froid. Bloque et r�duit la vie. Charge la soif.
* Mouill� : activ� quand la cr�ature est mouill�e. Se d�charge avec le temps si la cr�ature est au sec.
