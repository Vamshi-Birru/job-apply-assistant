# ---------- Build stage ----------
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml first for dependency caching
COPY pom.xml .
RUN mvn -B -q dependency:go-offline

# Copy source
COPY src ./src
COPY config.yml .

# Build the application
RUN mvn clean package -DskipTests

# ---------- Runtime stage ----------
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy only the built jar
COPY --from=build /app/target/job-apply-assistant-1.0.jar app.jar
COPY config.yml config.yml

EXPOSE 9090 9091

ENTRYPOINT ["java", "-Xms256m", "-Xmx512m", "-jar", "app.jar", "server", "config.yml"]

