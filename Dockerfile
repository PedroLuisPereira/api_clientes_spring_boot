FROM eclipse-temurin:17

LABEL mentainer="druped01@gmail.com"

WORKDIR /app

COPY target/clientes-0.0.1-SNAPSHOT.jar /app/clientes-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "clientes-0.0.1-SNAPSHOT.jar"]