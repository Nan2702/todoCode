FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .

# Construimos el proyecto
RUN mvn clean package -DskipTests

#Etapa 2: Ejecución (Runtime) -Imagen ligera
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

#Copiamos el Jar desde la etapa anterior
COPY --from=build /app/target/pruebaTecSupermercado-0.0.1.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]