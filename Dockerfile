FROM maven:3.9.6-eclipse-temurin-11 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package

FROM tomcat:9.0-jdk11

COPY --from=build /app/target/ServletProject.war /usr/local/tomcat/webapps/

EXPOSE 8080

CMD ["catalina.sh","run"]