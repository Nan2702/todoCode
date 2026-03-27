FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Instalar Maven
RUN apk add --no-cache maven

# Copiar pom.xml y descargar dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar el código fuente
COPY src ./src

# Compilar el proyecto y generar el jar
RUN mvn clean package -DskipTests

# Copiar el jar generado
ARG JAR_FILE=target/pruebaTecSupermercado-0.0.1.jar
COPY ${JAR_FILE} app_pruebatecsuper.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app_pruebatecsuper.jar"]