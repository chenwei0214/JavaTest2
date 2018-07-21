#!/bin/bash
mvn clean;
mvn install;
java -cp ./Exam1/target/Exam1.jar com.hand.Test;
java -cp ./Exam2/target/Exam2.jar com.hand.Server;
java -cp ./Exam2/target/Exam2.jar com.hand.Client;
java -cp ./Exam3/target/Exam3.jar com.hand.Test;