@echo off
set FX_LIB="D:\java\javafx-sdk-21.0.1\lib"
set JAR1="summarizator-1.0-SNAPSHOT.jar"
set JAR2="original-summarizator-1.0-SNAPSHOT.jar"

java --module-path %FX_LIB% --add-modules javafx.controls,javafx.fxml -jar %JAR1%
