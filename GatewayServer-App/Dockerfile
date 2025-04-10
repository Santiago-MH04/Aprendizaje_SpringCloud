    #It's used the Alpine version since it's the lighter and more optimised one
FROM amazoncorretto:23-alpine-jdk
LABEL authors="Santiago"
    #To create a directory where the jar will be hosted into the container (optional. Copied into root path by default)
WORKDIR /app
    #To expose the port in the container (the same as the application.properties file). Since users has a random port, it doesn't go in here
EXPOSE 8090
    #To add the jar into the container, and to name the container
ADD ./target/GatewayServer-App-0.0.1-SNAPSHOT.jar gateway-server.jar
    #To run from an image, and its instructions
ENTRYPOINT ["java", "-jar", "gateway-server.jar"]