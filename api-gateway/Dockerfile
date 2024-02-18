#base image
FROM openjdk:17

#copying jar file in target folder into the container
COPY target/*.jar app.jar

#whenever the container is running up or starting up it will run the command java -jar and it will point to app.jar file
ENTRYPOINT ["java","-jar","/app.jar"]

#docker build -t apigateway-dockerfile .