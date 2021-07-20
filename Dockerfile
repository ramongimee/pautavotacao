FROM openjdk:14-jdk-alpine as build
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw dependency:go-offline
COPY src src

RUN ./mvnw package -DskipTests

FROM openjdk:14-jdk-alpine
VOLUME /tmp
COPY --from=build /workspace/app/target/pautavotacao-api.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-server","-XX:+UseStringDeduplication","-Xms128M","-Xmx256M","-DskipTests","-jar","/app.jar"]

# COMMAND TO BUILD CONTAINER
# docker build -t pautavotacao-api:V1.0 .

# COMMAND TO RUN CONTAINER
# PAUTA=$(docker run -d -p 4321 pautavotacao nc -lk 4321 --memory="512m" --cpus="1.00");

# See container details
# docker inspect $PAUTA