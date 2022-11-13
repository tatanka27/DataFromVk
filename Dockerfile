FROM gradle:jdk18-alpine as builder
WORKDIR /app
COPY . .
RUN  ./gradlew bootJar

FROM openjdk:18-slim
COPY --from=builder /app/build/libs/DataFromVK-0.0.1-SNAPSHOT.jar /app/

ENV SPRING_PROFILES_ACTIVE default
ENV SPRING_DATASOURCE_URL jdbc:postgresql://localhost:5432/data_from_vk
ENV SPRING_DATASOURCE_USER user
ENV SPRING_DATASOURCE_PASSWORD user
ENV JAVA_OPTS "$JAVA_OPTS -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}\
    -Dspring.datasource.url=${SPRING_DATASOURCE_URL}\
    -Dspring.datasource.user=${SPRING_DATASOURCE_USER}\
    -Dspring.datasource.password=${SPRING_DATASOURCE_PASSWORD}"

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/DataFromVK-0.0.1-SNAPSHOT.jar"]