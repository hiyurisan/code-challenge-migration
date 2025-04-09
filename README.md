Para roda a build docker `docker build -t dummy .`

Para executar a build `docker run -p 8080:8080 dummy:latest`

Para rodar a aplicação spring boot `mvn spring-boot:run`

Para rodar a os teste da aplicação spring boot `mvn clean test`

Endpoint
- http://localhost:8080/api/products
- http://localhost:8080/api/products/1
- http://localhost:8080/health
