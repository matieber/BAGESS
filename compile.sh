#!/bin/bash

rm Bagess.jar
cd src
javac *.java
jar cfm Bagess.jar Manifest.txt *.class
rm *.class
mv ./Bagess.jar ..
