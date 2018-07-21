#!/bin/bash
mvn clean;
mvn install;

cd Exam1;
java -cp target/Exam1.jar com.hand.Test;

cd ../
cd Exam3;
java -cp target/Exam3.jar com.hand.Test sh601006;

cd ../
cd Exam2;
java -cp target/Exam2.jar com.hand.Server;
java -cp target/Exam2.jar com.hand.Client;




