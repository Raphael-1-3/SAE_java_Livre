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

- Compiler le projet : ```javac -d -cp "lib/*:src" src/main/*.java src/test/*.java```
- Lancer les tests : ```java -cp "bin:lib/*" org.junit.runner.JUnitCore test.AllTests```
- Lancer : ```java -cp "bin:lib/*" main.Executable```

## Compilation sur windows 

- Compiler le projet : ```javac -d bin -cp "lib/" src/*.java src/app/*.java src/Affichage/*.java src/BD/*.java src/Exceptions/*.java src/test/*.java```
- Lancer les tests : ```java -cp "bin;lib/*" Executable`

## Modification apporte a la bd
- ajout d'un attribut motDePasse (par defaut 1234)
- ajout de la table user
- ajout de l entite faible vendeur et admin
- passage de la table client en entite faible
- ajout des dependance nessecaire 
- ajout des information dans jeucomplet 

javac -d bin -cp "lib/*;src" src/Affichage/*.java src/test/*.java src/app/*.java src/BD/*.java src/Exceptions/*.java src/*.java
java -cp "bin;lib/*" Executable

## Creation de la base de Donnee 
- executer creationLivreExpress.sql pour creer les tables
- executer jeucomplet.sql pour avoir obtenir le jeu de donnees