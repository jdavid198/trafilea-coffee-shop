# Utiliza una imagen base de Java 17
FROM openjdk:17-jdk-alpine

# Establece el directorio de trabajo en /app
WORKDIR /app

# Copia el archivo WAR de tu aplicación Spring Boot al contenedor
COPY ./target/coffee-shop-0.0.1-SNAPSHOT.war coffee-shop.war

# Expone el puerto en el que se ejecuta la aplicación Spring Boot (ajusta el puerto según sea necesario)
EXPOSE 8080

# Comando para ejecutar la aplicación Spring Boot a través de un servidor web (por ejemplo, Tomcat)
CMD ["java", "-jar", "coffee-shop.war"]