# Proyecto basico Spring Boot
java version: 17 /
Spring Boot: 3.2.1 /
MySql: 8 /

GitHub = https://github.com/PedroLuisPereira/api_clientes_spring_boot.git

# seguridad
https://dev.to/pryhmez/implementing-spring-security-6-with-spring-boot-3-a-guide-to-oauth-and-jwt-with-nimbus-for-authentication-2lhf


# Deploy Spring Boot MySQL Application to Docker - Dockerfile

https://iesgn.github.io/curso_docker_2021/sesion4/


1) MySQL
- docker pull mysql
- docker network create springboot-mysql-net
- docker run --name mysqldb --network springboot-mysql-net -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=clientes -d mysql
- docker exec -it mysqldb bash
- mysql -u root -p
- password = root
- exit

2) Spring Boot
- Crear aplication-docker.properties
- Create Dockerfile to Build the docker image
- .\mvnw clean package -DskipTests
- docker build -t springboot-restful-webservices .
- docker run --network springboot-mysql-net --name springboot-mysql-container -p 8080:8080 springboot-restful-webservices


# Deploy Spring Boot MySQL Application to Docker - docker-compose.yml
- Crear docker-compose.yml
- docker-compose up
- docker-compose up -d  (Ejecutar en segundo plano)
- 