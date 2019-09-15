# Start with a base image containing Java runtime
FROM openjdk:11-jre-slim

# Add Maintainer Info
LABEL maintainer="yesilyurt.selim@metu.edu.tr"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8082 available to the world outside this container
EXPOSE 8082

# The application's jar file
ARG JAR_FILE=target/potential-playlist-1.0-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} potential-playlist.jar

# Run the jar file
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-jar","/potential-playlist.jar"]

#######
# sudo docker build -t potential-playlist .
# sudo docker run -e"SPRING_PROFILES_ACTIVE=dev" --network="host" -d --rm --name potential-playlist potential-playlist
#######