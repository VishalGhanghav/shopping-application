# shopping-application
An interface which will allow Admin to create a product,Allow User to order a product based on it's presence in inventory.
All the microservices are maintained using Eureka Discovery which provides dynamic ip address allocation.
API Gateway which routes the requests to particular ports.
The microservices have been secured using Oauth-2 security using Keycloak.
Resilience 4J circuit breaker pattern is used to avoid deadlock,retry mechanism,fast-failure,etc
Used Apache Kafka to send Order Placed email Notifications,etc
Have dockerized the micro-services.

Technologies used:Spring Cloud,Eureka,Resilience 4J,Sleuth-Zipkin,Apache Kafka,etc
