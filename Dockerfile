FROM adoptopenjdk/openjdk11
WORKDIR usr/src/app
ARG JAR_FILE=build/libs/GoGetter-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/usr/src/app/app.jar"]
