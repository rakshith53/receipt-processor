FROM maven:3.8.5-openjdk-17-slim

WORKDIR /app

COPY pom.xml /app/
COPY src /app/src

RUN mvn -f /app/pom.xml clean install -DskipTests

COPY target/receipt-processor-0.0.1-SNAPSHOT.jar /app/receipt-processor.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/receipt-processor.jar"]