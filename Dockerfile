# Pasul 1: Imaginea de bază cu Java 21
FROM eclipse-temurin:21-jdk-alpine

# Pasul 2: Directorul de lucru în interiorul containerului
WORKDIR /app

# Pasul 3: Copiem fișierul .jar (generat de Maven) în container
COPY target/*.jar app.jar

# Pasul 4: Expunem portul pe care rulează Spring Boot
EXPOSE 8080

# Pasul 5: Comanda care pornește aplicația
ENTRYPOINT ["java", "-jar", "app.jar"]