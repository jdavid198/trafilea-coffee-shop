# Use Java 17
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY ./app/demo-coffee-shop-0.0.1-SNAPSHOT.war coffee-shop.war
EXPOSE 9091
CMD ["java", "-jar", "coffee-shop.war"]