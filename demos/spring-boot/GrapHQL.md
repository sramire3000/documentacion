# Demo GrapHQL + Spring boot + JPA + H2 


## Adicionar al archivo "pom.xml"
```
<!-- Begin Graphql -->
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-graphql</artifactId>
</dependency>
<!-- End Graphql -->

<!-- Begin H2 -->
<dependency>
	<groupId>com.h2database</groupId>
	<artifactId>h2</artifactId>
	<scope>runtime</scope>
</dependency>
<!-- End H2 -->

<!-- Begin JPA -->
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<!-- End JPA -->

<!-- Begin Lombok -->
<dependency>
	<groupId>org.projectlombok</groupId>
	<artifactId>lombok</artifactId>
	<optional>true</optional>
</dependency>
<!-- End Lombok -->

```