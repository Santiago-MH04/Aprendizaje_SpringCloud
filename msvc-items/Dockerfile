    #It's used the Alpine version since it's the lighter and more optimised one
FROM amazoncorretto:23-alpine-jdk
LABEL authors="Santiago"
    #To create a directory where the jar will be hosted into the container (optional. Copied into root path by default)
WORKDIR /app
    #To expose the port in the container (the same as the application.properties file). It's both default and dev profiles. It's 8007 for prod profile
EXPOSE 8005
    #To add the jar into the container, and to name the container
ADD ./target/msvc-items-0.0.1-SNAPSHOT.jar msvc-items.jar
    #To run from an image, and its instructions
ENTRYPOINT ["java", "-jar", "msvc-items.jar"]