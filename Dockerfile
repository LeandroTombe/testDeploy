
FROM maven as build
COPY src src/
COPY pom.xml pom.xml
RUN mvn clean package -Dmaven.test.skip=true


# Usa la imagen base de OpenJDK 17
FROM openjdk:17-alpine


# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR de la aplicación (asegúrate de que el JAR generado se encuentra en la misma ubicación que este Dockerfile)
COPY --from=build target/C1739-0.0.1-SNAPSHOT.jar /app/app.jar

# Expone el puerto en el que la aplicación se ejecuta dentro del contenedor

# Comando para ejecutar la aplicación Spring Boot al iniciar el contenedor
CMD ["java", "-jar", "app.jar"]