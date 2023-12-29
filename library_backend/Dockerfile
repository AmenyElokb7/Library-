# Stage 1: Build with Maven
FROM maven:3.8.4-openjdk-17 as build

# Set the working directory in the Docker image
WORKDIR /app

# Copy the pom.xml and source code
COPY pom.xml .
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Stage 2: Create the final Docker image with just the built JAR file
FROM openjdk:17-oracle

WORKDIR /app

# Copy the JAR file from the build stage to the final image
COPY --from=build /app/target/*.jar app.jar

# Command to run the application
ENTRYPOINT ["java","-jar","app.jar"]
