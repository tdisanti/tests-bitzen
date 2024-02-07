########## STAGE 1: Build ##########
FROM openjdk:17-slim AS build
LABEL maintainer="Thiago Di Santi <tdisanti@gmail.com>"

# Install Unzip + Curl
RUN \
    apt-get update && \
    apt-get -y install unzip && \
    apt-get -y install curl

# Download and install Maven
RUN \
    cd /usr/local && \
    curl -L https://downloads.apache.org/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.zip -o maven-3.9.6-bin.zip && \
    unzip maven-3.9.6-bin.zip && \
    rm maven-3.9.6-bin.zip

# Exporta variáveis de ambiente
ENV MAVEN_HOME=/usr/local/apache-maven-3.9.6
ENV PATH=$PATH:$MAVEN_HOME/bin
RUN export PATH=$PATH:/opt/maven/apache-maven-3.9.6/bin


COPY pom.xml /app/
COPY src /app/src/

WORKDIR /app

RUN mvn clean package



########## STAGE 2: Package ##########
FROM openjdk:17 AS package

WORKDIR /app

# Copia o arquivo JAR do projeto compilado do estágio de construção para o estágio de pacote
COPY --from=build /app/target/desafio-bitzen-0.0.1-SNAPSHOT.jar /app/desafio-bitzen-0.0.1-SNAPSHOT.jar



########## STAGE 3: Runtime ##########
FROM openjdk:17 AS runtime

WORKDIR /app

# Copia o arquivo JAR do estágio de pacote para o estágio de tempo de execução
COPY --from=package /app/desafio-bitzen-0.0.1-SNAPSHOT.jar .

# Open ports
EXPOSE 8080

# Comando para executar o aplicativo Spring Boot quando o container for iniciado
CMD ["java", "-jar", "desafio-bitzen-0.0.1-SNAPSHOT.jar"]
