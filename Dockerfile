FROM java:8-jdk
VOLUME /tmp
ADD ./target/keu-0.0.1-SNAPSHOT.jar keu-0.0.1-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","keu-0.0.1-SNAPSHOT.jar"]