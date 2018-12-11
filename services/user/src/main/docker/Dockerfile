FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD @service.jar@.jar app.jar
RUN sh -c 'touch /app.jar'
ENV LANG C.UTF-8
ENV JAVA_OPTS="" PARAMS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar $PARAMS" ]