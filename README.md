# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

- [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
- [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.3/maven-plugin)
- [Create an OCI image](https://docs.spring.io/spring-boot/3.4.3/maven-plugin/build-image.html)
- [Spring Boot DevTools](https://docs.spring.io/spring-boot/3.4.3/reference/using/devtools.html)
- [Docker Compose Support](https://docs.spring.io/spring-boot/3.4.3/reference/features/dev-services.html#features.dev-services.docker-compose)
- [Spring Web](https://docs.spring.io/spring-boot/3.4.3/reference/web/servlet.html)
- [Spring for GraphQL](https://docs.spring.io/spring-boot/3.4.3/reference/web/spring-graphql.html)
- [Spring Security](https://docs.spring.io/spring-boot/3.4.3/reference/web/spring-security.html)
- [OAuth2 Client](https://docs.spring.io/spring-boot/3.4.3/reference/web/spring-security.html#web.security.oauth2.client)
- [OAuth2 Authorization Server](https://docs.spring.io/spring-boot/3.4.3/reference/web/spring-security.html#web.security.oauth2.authorization-server)
- [OAuth2 Resource Server](https://docs.spring.io/spring-boot/3.4.3/reference/web/spring-security.html#web.security.oauth2.server)
- [Spring Data JPA](https://docs.spring.io/spring-boot/3.4.3/reference/data/sql.html#data.sql.jpa-and-spring-data)
- [Spring Data Reactive Redis](https://docs.spring.io/spring-boot/3.4.3/reference/data/nosql.html#data.nosql.redis)
- [WebSocket](https://docs.spring.io/spring-boot/3.4.3/reference/messaging/websockets.html)
- [Validation](https://docs.spring.io/spring-boot/3.4.3/reference/io/validation.html)

### Guides

The following guides illustrate how to use some features concretely:

- [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
- [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
- [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
- [Building a GraphQL service](https://spring.io/guides/gs/graphql-server/)
- [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
- [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
- [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)
- [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
- [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
- [Messaging with Redis](https://spring.io/guides/gs/messaging-redis/)
- [Using WebSocket to build an interactive web application](https://spring.io/guides/gs/messaging-stomp-websocket/)
- [Validation](https://spring.io/guides/gs/validating-form-input/)

### Docker Compose support

This project contains a Docker Compose file named `compose.yaml`.
In this file, the following services have been defined:

- mysql: [`mysql:latest`](https://hub.docker.com/_/mysql)
- redis: [`redis:latest`](https://hub.docker.com/_/redis)

Please review the tags of the used images and set them to the same as you're running in production.

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.
