# ---------- Build Stage ----------
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn -q dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests


# ---------- Runtime Stage ----------
# 💥 THIS IMAGE HAS CHROMIUM, DEPS AND FONTS BUILT IN
FROM mcr.microsoft.com/playwright/java:v1.44.0-jammy

WORKDIR /app

# Copy built JAR
COPY --from=build /app/target/job-apply-worker-service-1.0.jar app.jar

# Tell Playwright not to install again
ENV PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD=1

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
