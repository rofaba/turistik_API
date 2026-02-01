# Etapa 1: Construcción (Build)
# Usamos una imagen de Maven para compilar el código
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
# Copiamos el pom.xml y el código fuente
COPY pom.xml .
COPY src ./src
# Compilamos el proyecto saltándonos los tests para ir más rápido
RUN mvn clean package -DskipTests

# Etapa 2: Ejecución (Run)
# Usamos una imagen ligera de Java para correr el .jar resultante
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Copiamos solo el archivo .jar generado en la etapa anterior
COPY --from=build /app/target/*.jar app.jar
# Exponemos el puerto donde corre Spring
EXPOSE 8080
# Comando para arrancar la app
ENTRYPOINT ["java", "-jar", "app.jar"]