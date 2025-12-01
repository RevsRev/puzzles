mvn clean package -Dcheckstyle.skip -DskipTests

java -jar app/target/app-1.0-SNAPSHOT-jar-with-dependencies.jar --engine aoc --problem $1