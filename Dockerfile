# 베이스 이미지 + 이미지 별칭
FROM adoptopenjdk:11-jdk-hotspot AS builder

# gradlew 복사
COPY gradlew .

# gradle 복사
COPY gradle gradle

# build.gradle 복사
COPY build.gradle .

# settings.gradle 복사
COPY settings.gradle .

# 웹 어플리케이션 소스 복사
COPY src src

# gradlew 실행권한 부여
RUN chmod +x ./gradlew

RUN sed -i 's/\r$//' ./gradlew

# gradlew를 사용하여 실행 가능한 jar 파일 생성
RUN ["./gradlew", "bootJar"]

# 베이스 이미지
FROM adoptopenjdk:11-jdk-hotspot

# builder 이미지에서 build/libs/*.jar 파일을 java.jar로 복사
COPY --from=builder build/libs/*.jar java.jar

# 컨테이너 Port 노출
EXPOSE 8080

# jar 파일 실행
ENTRYPOINT ["java", "-jar","/java.jar"]

######################################################
# FROM adoptopenjdk/openjdk11
# WORKDIR usr/src/app
# # wait-for 스크립트 복사
# COPY wait-for.sh ./

# RUN sed -i 's/\r$//' ./wait-for.sh 
# # nc(netcat) 커맨드 설치
# RUN apt update && apt install netcat -y
# ARG JAR_FILE=build/libs/GoGetter-0.0.1-SNAPSHOT.jar
# COPY ${JAR_FILE} app.jar
# ENTRYPOINT ["java","-jar","/usr/src/app/app.jar"]

