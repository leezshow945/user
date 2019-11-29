FROM harbor.xm6f.com/library/java:8-jdk-alpine
COPY ./user-service/target/user-service.jar /user-service.jar
ENV TZ Asia/Shanghai
ENV JAVA_OPTS=""
EXPOSE 8080 30001
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /user-service.jar
