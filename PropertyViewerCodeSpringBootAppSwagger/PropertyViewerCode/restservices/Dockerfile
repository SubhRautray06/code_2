FROM openjdk:11-jre-slim
EXPOSE 8080
ADD target/building.jar building.jar

ENTRYPOINT [ "java","-jar","/building.jar"]