#!/bin/bash
# Generate challenge directory structure
# Usage: create_challenge <dir> <artifactId>

BASE="/home/wanony/ashusevim/challenges"
APP_PROPS="spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.h2.console.enabled=true"

SPRING_APP='package com.springarena;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringArenaApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringArenaApplication.class, args);
    }
}'

create_challenge() {
    local dir="$1"
    local artifactId="$2"
    local fullPath="$BASE/$dir"

    # Create directories
    mkdir -p "$fullPath/src/main/java/com/springarena/model"
    mkdir -p "$fullPath/src/main/java/com/springarena/controller"
    mkdir -p "$fullPath/src/main/java/com/springarena/repository"
    mkdir -p "$fullPath/src/main/java/com/springarena/service"
    mkdir -p "$fullPath/src/main/java/com/springarena/dto"
    mkdir -p "$fullPath/src/main/resources"
    mkdir -p "$fullPath/src/test/java/com/springarena"

    # application.properties
    echo "$APP_PROPS" > "$fullPath/src/main/resources/application.properties"

    # SpringArenaApplication.java
    echo "$SPRING_APP" > "$fullPath/src/main/java/com/springarena/SpringArenaApplication.java"

    # pom.xml
    cat > "$fullPath/pom.xml" << POMEOF
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.4.1</version>
    <relativePath/>
  </parent>
  <groupId>com.springarena</groupId>
  <artifactId>$artifactId</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  <properties><java.version>21</java.version></properties>
  <dependencies>
    <dependency><groupId>org.springframework.boot</groupId><artifactId>spring-boot-starter-web</artifactId></dependency>
    <dependency><groupId>org.springframework.boot</groupId><artifactId>spring-boot-starter-data-jpa</artifactId></dependency>
    <dependency><groupId>org.springframework.boot</groupId><artifactId>spring-boot-starter-validation</artifactId></dependency>
    <dependency><groupId>com.h2database</groupId><artifactId>h2</artifactId><scope>runtime</scope></dependency>
    <dependency><groupId>org.springframework.boot</groupId><artifactId>spring-boot-starter-test</artifactId><scope>test</scope></dependency>
  </dependencies>
  <build><plugins><plugin><groupId>org.springframework.boot</groupId><artifactId>spring-boot-maven-plugin</artifactId></plugin></plugins></build>
</project>
POMEOF

    echo "✅ Created $dir"
}

# Create all 19 new challenges (01 already exists)
create_challenge "02-crud-contact" "02-crud-contact"
create_challenge "03-crud-note" "03-crud-note"
create_challenge "04-dto-employee" "04-dto-employee"
create_challenge "05-dto-student" "05-dto-student"
create_challenge "06-dto-vehicle" "06-dto-vehicle"
create_challenge "07-service-book" "07-service-book"
create_challenge "08-service-movie" "08-service-movie"
create_challenge "09-service-gadget" "09-service-gadget"
create_challenge "10-service-dto-recipe" "10-service-dto-recipe"
create_challenge "11-service-dto-task" "11-service-dto-task"
create_challenge "12-service-dto-event" "12-service-dto-event"
create_challenge "13-queries-blogpost" "13-queries-blogpost"
create_challenge "14-queries-job" "14-queries-job"
create_challenge "15-queries-product-v2" "15-queries-product-v2"
create_challenge "16-full-inventory" "16-full-inventory"
create_challenge "17-full-course" "17-full-course"
create_challenge "18-full-restaurant" "18-full-restaurant"
create_challenge "19-mock-clinic" "19-mock-clinic"
create_challenge "20-mock-warehouse" "20-mock-warehouse"

echo ""
echo "🎯 All 19 challenge directories created!"
