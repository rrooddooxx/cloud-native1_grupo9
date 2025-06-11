FROM openjdk:21-jdk-slim as builder

WORKDIR /app

COPY build.gradle.kts settings.gradle.kts ./
COPY gradle/ gradle/
COPY gradlew ./

RUN chmod +x gradlew

COPY src/ src/

RUN ./gradlew clean build -x test

FROM openjdk:21-jre-slim

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar
COPY /Users/sebastiankravetz/bbdd/Wallet_CN1G9/ /app/wallet/

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]