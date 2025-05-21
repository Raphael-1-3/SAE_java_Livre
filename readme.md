# SAE 

## Participant :

- Raphaël BROUSSEAU nom de branch dev raphe 

## Compilation  sur linux: 

- Compiler le projet : ```javac -d -cp "lib/*:src" src/main/*.java src/test/*.java```
- Lancer les tests : ```java -cp "bin:lib/*" org.junit.runner.JUnitCore test.AllTests```
- Lancer le demineur : ```java -cp "bin:lib/*" main.Executable```

## Compilation sur windows 

- Compiler le projet : ```javac -d -cp "lib/*;src" src/main/*.java src/test/*.java```
- Lancer les tests : ```java -cp "bin;lib/*" org.junit.runner.JUnitCore test.AllTests`