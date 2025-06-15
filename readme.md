# SAE 

## Participant :

- Raphaël BROUSSEAU branche : devRaphe
- Corentin LACOUME branche : devCorentin
- Robin FAUCHEUX branche : devRobin
- Joris VACHEY branche : devJojo

## Compilation  sur linux: 

- Compiler le projet : ```javac -d -cp "lib/*:src" src/main/*.java src/test/*.java```
- Lancer les tests : ```java -cp "bin:lib/*" org.junit.runner.JUnitCore test.AllTests```
- Lancer : ```java -cp "bin:lib/*" main.Executable```

## Compilation sur windows 

- Compiler le projet : ```javac -d bin -cp "lib/*;src" src/main/*.java src/test/*.java```
- Lancer les tests : ```java -cp "bin;lib/*" org.junit.runner.JUnitCore test.AllTests`

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