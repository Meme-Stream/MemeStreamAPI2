FROM eclipse-temurin:25-jre-alpine

WORKDIR /api

COPY ./build/libs/memestream-api-2.0.0.jar .

EXPOSE 8080

CMD ["java", "-jar", "memestream-api-2.0.0.jar"]