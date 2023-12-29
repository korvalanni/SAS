#
# Build stage
#
FROM gradle:7.4.2-jdk17-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

RUN chmod +x gradlew

RUN ./gradlew build --no-daemon

#
# Package stage
#
FROM eclipse-temurin:17-jre-alpine
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/demo.jar

EXPOSE 8080
CMD ["java", "-jar", "/app/demo.jar"]