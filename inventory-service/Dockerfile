FROM mcr.microsoft.com/openjdk/jdk:21-ubuntu

# WORKDIR /app

# COPY . .

# Install Maven (this adds Maven to the container)
RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean

RUN apt-get update && apt-get install -y git && apt-get clean

# Set the working directory
WORKDIR /app

# Clone the repository containing the parent POM or project code
RUN git clone https://github.com/dobrediana99/SA_project.git SA_project

WORKDIR /app/SA_project/publisher-service

RUN mvn clean install

EXPOSE 8082

ENTRYPOINT ["mvn", "exec:java", "-Dexec.mainClass=com.example.publisher.Application"]
