# De Creatura - spécifications 2

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

# Créatures

## Personnalisation et ADN
L'aspect, les performances et les capacités spéciales de la créature sont définies par son ADN.

ADN : 
* chaine de texte balisé par des séquences (codons)
* code directement les structures

Gènes :
* Chaque gène contient deux paramètres :
  * Parametre organe : le paramètre de l'organe qu'il modifie
  * Ex. "saturation peau", "teinte peau", "luminosité peau", "efficacité poumons", "efficacité branchies"
  * La valeur du paramètre (de 0 à 5)
  * Une valeur de 0 veut dire que le gène est défectueux
* Un gène peut être présent plusieurs fois
  * Pour certains, le plus fort est pris en compte
  * Pour les autres, on additionne leur valeur

## Organes et fonctions de la créature

La créature possède plusieurs organes dont la présence et les performances sont déterminés par son ADN.

Certains organes sont accompagnés de l'un des qualificatifs suivants :
* vital : si la créature n'en a pas, elle n'est pas viable
* passif : ils ne comptent pas dans la charge de la faim
* cosmétique : ils sont passif et n'ont pas de fonction propre

* Cerveau (vital) : détermine la capacité disponible pour l'IA
* Coeur (vital) : détermine l'endurance de la créature
* Poumons (vital) : détermine la capacité d'apnée de la créature
* Branchies : permet à la créature de respirer sous l'eau
* Estomac (vital) : détermine la rapidité de la décharge de la faim et de la soif
* Pattes : détermine la vitesse de la créature
* Pelage (passif) : détermine l'isolation thermique de la créature
* Couleur peau (cosmétique) : détermine la couleur de la peau (de blanc à noir)
* Couleur yeux (cosmétique) : détermine la couleur des yeux (albinos, bleu, vert, jaune, brun, noir)




## Variables vitales

L'état de santé de la créature est définie par deux choses :
* les moodles : des éléments affectant la créature (fatigue, maladie, faim, surprise, etc...), idéalement affichées sous la forme d'une icone.
* la vie : l'état de santé de la créature. Idéalement représenté par une jauge.

### La vie
La vie représente l'état de santé global de la créature. À 100, la créature est en pleine forme, à 0 elle est morte (RIP).

Naturellement la vie se remplie avec le temps, mais certains moodles peuvent bloquer cette régénération, voir la faire se réduire à leur apparition ou au fil du temps.

### Moodles

Ils peuvent se trouver sous deux états :
* actif : la créature est affecté et le moodle sera affiché (idéalement sous forme d'icone)
* inactif : la créature n'est pas affecté par le moodle qui ne sera donc pas affiché.

Les moodles possèdent un compteur en interne et peuvent être en charge ou décharge :
* en charge : un moodle inactif peut être en charge : son compteur est augmenté par quelque chose (le temps, un autre état, l'environnement, certaines actions) et lorsqu'il atteindra le seuil maximal, le moodle deviendra actif.
* en décharge : un moodle actif peut être en décharge. Son compte est décrémenté par quelque chose (là encore le temps, etc.) et lorsqu'il atteindra 0, le moodle deviendra inactif.

Chaque moodle possède ses propres mécaniques d'activation et de charge. Un seul moodle d'un même type peut être attaché à la même créature.

Il est à noter que tous les moodles ne sont pas nécessairement connus par la créature elle-même (notamment certaines maladies).

### Exemples de moodles

* Faim : se charge avec le temps, se désactive quand la créature mange. Bloque la vie.
* Soif : se charge avec le temps, se désactive quand la créature boit.  Bloque et réduit la vie.
* Fatigue : se charge avec les actions et le temps, se désactive quand la créature dort. Bloque la vie.
* Ennui : se charge avec le temps, se décharge lorsque la créature effectue des actions nouvelles.
* Froid : se charge avec le temps dans un environnement froid ou si la créature est mouillée. Se décharge avec le temps une fois dans un environnement tempéré ou chaud. Bloque et réduit la vie. Charge la faim.
* Chaud : se charge avec le temps dans un environnement chaud. Se décharge avec le temps une fois dans un environnement témpéré ou froid. Bloque et réduit la vie. Charge la soif.
* Mouillé : activé quand la créature est mouillée. Se décharge avec le temps si la créature est au sec.
