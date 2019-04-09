FROM maven:3.5.3-jdk-8-alpine AS builder
RUN mkdir -p /build
WORKDIR /build
COPY pom.xml /build
RUN mvn dependency:go-offline
COPY src/ /build/src/
RUN mvn package

FROM frolvlad/alpine-java:latest AS runtime
EXPOSE 8080
ENV APP_HOME /app
#Create base app folder
RUN mkdir $APP_HOME
#Create folder to save configuration files
RUN mkdir $APP_HOME/config
#Create folder with application logs
RUN mkdir $APP_HOME/log
 
VOLUME $APP_HOME/log
VOLUME $APP_HOME/config
 
WORKDIR $APP_HOME
COPY --from=builder /build/target/*.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]
CMD ["-start"]