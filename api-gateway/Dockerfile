#base image
FROM openjdk:17

#copying jar file in target folder into the container
COPY target/*.jar app.jar

#whenever the container is running up or starting up it will run the command java -jar and it will point to app.jar file
ENTRYPOINT ["java","-jar","/app.jar"]

#docker build -t apigateway-dockerfile .
#-t -> we're providing a tag for image which we'll build.
#. -> means that we want to give present folder for the image.
#docker images -> localde ki tüm imageleramı verir.
#apigateway-dockerfile 551MB. Bu iyi birşey değil bunu improve etmemiz lazım.