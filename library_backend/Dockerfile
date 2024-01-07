FROM maven:3.8.4-openjdk-17 as build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:17-oracle

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar
EXPOSE 8082

ENTRYPOINT ["java","-jar","app.jar"]
