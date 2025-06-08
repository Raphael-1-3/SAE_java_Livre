# SAE 

## Participant :

- Raphaël BROUSSEAU nom de branch dev raphe 

## Compilation  sur linux: 

- Compiler le projet : ```javac -d -cp "lib/*:src" src/main/*.java src/test/*.java```
- Lancer les tests : ```java -cp "bin:lib/*" org.junit.runner.JUnitCore test.AllTests```
- Lancer le demineur : ```java -cp "bin:lib/*" main.Executable```

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