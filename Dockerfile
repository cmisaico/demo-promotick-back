
FROM gradle:8.4.0-jdk17-alpine AS build

LABEL maintainer="christianmisaico@gmai.com"

COPY --chown=gradle:gradle build.gradle settings.gradle gradlew /app/
COPY --chown=gradle:gradle gradle /app/gradle

WORKDIR /app

RUN gradle --no-daemon dependencies

COPY --chown=gradle:gradle src /app/src

RUN gradle bootJar --no-daemon build -x test


FROM openjdk:17.0.1-jdk-slim

WORKDIR /app
COPY --from=build /app/build/libs/demo-promotick-back-0.0.1-SNAPSHOT.jar ./app.jar

RUN useradd -m cmisaico
USER cmisaico

EXPOSE 8080

CMD ["java", "-jar", "/app/app.jar"]
