#!/bin/bash

echo " Compilation du projet..."
javac -d bin -cp "lib/*:src" src/**/*.java

if [ $? -ne 0 ]; then
  echo " Erreur de compilation"
  exit 1
fi

echo " Génération de la documentation Javadoc..."
javadoc -d doc -cp "lib/*:src" -sourcepath src -subpackages app BD Affichage Exceptions main test

echo " Lancement de l'application..."
java -cp "bin:lib/*" main.Executable
