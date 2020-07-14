FROM maven:3.6.3-openjdk-14-slim AS build
WORKDIR /build

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src src

RUN mvn package --batch-mode

FROM adoptopenjdk/openjdk14:jre-14.0.1_7-alpine AS release

COPY --from=build /build/target/*.jar /app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]