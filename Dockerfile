FROM maven:3.8.4-openjdk-11-slim AS build
WORKDIR /app
COPY . .
# Set Java version to 11 explicitly
ENV JAVA_HOME /usr/local/openjdk-11
RUN mvn clean package -DskipTests

FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Add a wait script to ensure database is ready before app starts
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.9.0/wait /wait
RUN chmod +x /wait

EXPOSE 8080
CMD ["/bin/sh", "-c", "/wait && java -jar app.jar"]