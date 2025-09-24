FROM maven:4.0.0-rc-4-eclipse-temurin-21 AS build

WORKDIR /build

COPY src src
COPY pom.xml pom.xml

RUN mvn clean package -DskipTests


FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /build/target/*.jar example-edu.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "example-edu.jar"]