FROM maven:3.9.6-eclipse-temurin-11 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package

FROM tomcat:9.0-jdk11

COPY --from=build /app/target/ServletProject.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

CMD sed -i "s/8080/${PORT}/g" /usr/local/tomcat/conf/server.xml && catalina.sh run