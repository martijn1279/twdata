FROM openjdk:8u151-jdk-alpine

ENV TZ=Europe/Amsterdam

RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime \
    && echo $TZ > /etc/timezone \
    && apk add --update curl \
    && rm -rf /var/cache/apk/*

COPY twdata.jar app.jar

HEALTHCHECK --interval=5s --timeout=5s --retries=24 \
    CMD curl -k -o /dev/null --max-time 5 --silent --write-out '%{http_code}\n' https://localhost:8081/actuator/health | grep 200

EXPOSE 8080 8081

USER root

CMD java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /app.jar