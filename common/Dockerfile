FROM openjdk:8
VOLUME /tmp

ADD target/common-0.0.1-SNAPSHOT.jar /opt/app.jar

WORKDIR /opt/

ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ENV SPRING_PROFILES_ACTIVE online
ENV JAVA_OPTS=""

EXPOSE 8080

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /opt/app.jar" ]

