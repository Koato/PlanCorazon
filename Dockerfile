# Utiliza una imagen base con Java preinstalado
FROM openjdk:8-jre-slim

# Crear un argumento
ARG JAR_FILE=target/plancorazon-0.0.1-SNAPSHOT.jar

# Copia el archivo JAR a la imagen
COPY ${JAR_FILE} app.jar

# Expone el puerto que tu aplicación utilizará
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
