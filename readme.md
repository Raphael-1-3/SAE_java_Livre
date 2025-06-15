# SAE 

## Participant :

- Raphaël BROUSSEAU branche : devRaphe
- Corentin LACOUME branche : devCorentin
- Robin FAUCHEUX branche : devRobin
- Joris VACHEY branche : devJojo

## Lancement de l'application

### Installer la base de donnee

- Dans un premier placait vous dans le dossier BASEDONNE
- Ensuite executer le fichier creationLivreExpress avec ``source creationLivreExpress.sql``
- Puis le fichier de jeucomplet avec ``source jeucomplet.sql``

## Apres cela vous pouvez normalement lancer l 'application dans l executable avec run java

## Compilation  sur linux: 

javac -d bin -cp "lib/*:src" src/**/*.java
java -cp "bin:lib/*" main.Executable

## Compilation sur windows 

javac -d bin -cp "lib/*;src" src/**/*.java
java -cp "bin;lib/*" main.Executable

## Creation de la base de Donnee 
- executer creationLivreExpress.sql pour creer les tables
- executer jeucomplet.sql pour avoir obtenir le jeu de donnees## Modification apporte a la bd
- ajout d'un attribut motDePasse (par defaut 1234)
- ajout de la table user
- ajout de l entite faible vendeur et admin
- passage de la table client en entite faible
- ajout des dependance nessecaire 
- ajout des information dans jeucomplet