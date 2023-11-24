FROM openjdk:8
VOLUME /tmp
ADD ./target/keu-0.0.1-SNAPSHOT.jar keu-0.0.1-SNAPSHOT.jar
EXPOSE 443
ENTRYPOINT ["java","-jar","keu-0.0.1-SNAPSHOT.jar"]