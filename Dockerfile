# FROM openjdk:17-alpine3.14 as build

# Etapa 1: Construccion con Gradle
# Usar una imagen OpenJDK con Gradle preinstalado para construir el proyecto
FROM gradle:8.4.0-jdk17-alpine AS build

LABEL maintainer="christianmisaico@gmai.com"

# Copiar solo los archivos necesarios para la descarga de dependencias
# Esto aprovecha la cache de Docker si los archivos de dependencia no cambian
COPY --chown=gradle:gradle build.gradle settings.gradle gradlew /app/
COPY --chown=gradle:gradle gradle /app/gradle

WORKDIR /app

# Descargar las dependencias
RUN gradle --no-daemon dependencies

# Copiar el resto del codigo fuente
COPY --chown=gradle:gradle src /app/src

# Construir la aplicacion sin ejecutar pruebas para acelerar el proceso
RUN gradle --no-daemon build -x test

# Etapa 2: Creacion de la imagen de ejecucion con JRE
# Usar una imagen JRE mas ligera para la ejecucion
FROM openjdk:17.0.1-jdk-slim

WORKDIR /app

# Copiar el ejecutable de la etapa de build
COPY --from=build /app/build/libs/*.jar app.jar

# Definir un usuario no root para mejorar la seguridad
RUN useradd -m cmisaico
USER cmisaico

# Exponer el puerto en el que se ejecutara la aplicacion
EXPOSE 8080

# Comando para ejecutar la aplicacion
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
