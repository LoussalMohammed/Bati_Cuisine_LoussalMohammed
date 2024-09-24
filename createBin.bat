@echo off

if exist "C:/Users/youcode/Documents/Briefs/Bati_Cuisine_LoussalMohammed/bin" (
    rmdir /S /Q "C:/Users/youcode/Documents/Briefs/Bati_Cuisine_LoussalMohammed/bin"
)

cd "C:/Users/youcode/Documents/Briefs/Bati_Cuisine_LoussalMohammed/"

mkdir bin

cd src/

javac -d ../bin org/app/Commands/CommandsRunner.java