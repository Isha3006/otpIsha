FROM openjdk:11

ADD target/otp-api.jar otp-api.jar

ENTRYPOINT ["java", "-jar", "otp-api.jar"]

EXPOSE 8080