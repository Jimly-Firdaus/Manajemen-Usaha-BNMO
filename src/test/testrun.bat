@echo off
cd ../main/java/com
javac --module-path "C:\Program Files\JavaFX\javafx-sdk-17.0.7\lib" --add-modules javafx.controls -d out Main.java gui/Router.java gui\interfaces\PageSwitcher.java gui\pages\*.java gui\components\*.java logic\feature\interfaces\IBill.java logic\constant\interfaces\IPayment.java logic\feature\*.java
java --module-path "C:\Program Files\JavaFX\javafx-sdk-17.0.7\lib" --add-modules javafx.controls -cp out com.Main
pause