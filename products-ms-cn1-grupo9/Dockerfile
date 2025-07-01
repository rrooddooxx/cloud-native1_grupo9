FROM gradle:jdk21-alpine AS builder

WORKDIR /app

COPY build.gradle.kts settings.gradle.kts ./
COPY gradle/ gradle/
COPY gradlew ./

RUN chmod +x gradlew

COPY src/ src/

RUN ./gradlew clean build -x test

FROM bellsoft/liberica-openjre-debian:21

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar
COPY ./wallet/ /app/wallet/

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]