FROM gradle:jdk21-alpine AS builder

WORKDIR /build

COPY build.gradle.kts .
COPY gradle ./gradle
COPY src ./src

RUN gradle clean bootJar --no-daemon


FROM bellsoft/liberica-openjre-debian:21 AS runner

WORKDIR /app

COPY --from=builder /build/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]