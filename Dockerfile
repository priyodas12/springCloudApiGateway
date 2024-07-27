FROM openjdk:22
MAINTAINER priyobrato_das
COPY target/springCloudApiGateway-0.0.1-SNAPSHOT.jar api-gateway-server-1.0.0.jar
EXPOSE 9999
ENTRYPOINT ["java","-jar","/api-gateway-server-1.0.0.jar"]



#docker build --tag=api-gateway-server:latest .

#docker run -p 9999:9999 api-gateway-server:latest