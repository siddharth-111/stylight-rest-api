# FROM amazoncorretto:17
# VOLUME /tmp
# ARG JAR_FILE
# COPY ${JAR_FILE} nullbeansdockerdemo.jar
# SET DOCKER_HOST=tcp://localhost:3000& mvn clean install
# ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/nullbeansdockerdemo.jar"]

FROM amazoncorretto:17
EXPOSE 8080
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]