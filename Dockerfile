# Utiliza una imagen base con Java preinstalado
FROM openjdk:8-jdk-alpine

# Expone el puerto que tu aplicación utilizará
EXPOSE 8080

# Copia el archivo JAR a la imagen
COPY ./target/plancorazon-0.0.1-SNAPSHOT.jar app.jar

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app.jar"]
