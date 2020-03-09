FROM java:8

WORKDIR /root

COPY auth-server.jar $WORKDIR
#COPY application.properties $WORKDIR

ENV TZ=America/Sao_Paulo

RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

EXPOSE 8082

CMD ["java", "-jar", "auth-server.jar"]