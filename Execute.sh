#!/bin/bash

JAVAFX="lib/javafx-sdk-24.0.1/lib"

echo "🛠 Vérification de l'existence du dossier bin..."
if [ ! -d "bin" ]; then
    echo "🧮 Compilation du projet ..."
    
    # 🛠 Correction: search for .java (not ".java") and quote the file list
    find src -name "*.java" > sources.txt

    # 🛠 Correction: lib/* instead of lib/
    javac -d bin \
        --module-path "$JAVAFX" \
        --add-modules javafx.controls,javafx.fxml \
        -cp "lib/*:src" \
        @sources.txt

    # 📦 Copy resources (FXML, images, etc.) to bin

    mkdir -p bin/dbUser
    mkdir -p bin/fonts
    mkdir -p bin/img

    cp -r dbUser/* bin/dbUser/
    cp -r fonts/* bin/fonts/
    cp -r img/* bin/img/
    cp -r src/IHM/styles/*
else
    echo "✅ Le dossier bin existe déjà."
fi

# Optional Javadoc section (uncomment if needed)
# echo "🛠 Vérification de l'existence du dossier doc..."
# if [ ! -d "doc" ]; then
#     echo "📃 génération de la javadoc..."
#     javadoc -d doc \
#         src/**/*.java \
#         --module-path "$JAVAFX" \
#         --add-modules javafx.controls,javafx.fxml
# else
#     echo "✅ Le dossier doc existe déjà."
# fi

echo "🚀 Lancement de l'application..."
java \
    --module-path "$JAVAFX" \
    --add-modules javafx.controls,javafx.fxml \
    -cp "lib/*:bin" \
    IHM.vues.LivreExpress
