# Demo GrapHQL + Spring boot + JPA + H2 

## Configuraciones

### Adicionar al archivo "pom.xml"
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

### Paquetes
```
-entities
-graphql
-implement
-mappers
-object
-repository
-servives
```

### Archivo "application.properties"
```
server.port=8080

# GraphQL
spring.graphql.graphiql.enabled=true
spring.graphql.graphiql.path=/graphiql

# H2
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JPA
spring.jpa.defer-datasource-initialization=true
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
```

### Archivo "data.sql"
```
INSERT INTO users(id, name, surname, email) VALUES (1, 'John', 'Doe', 'john.doe@example.com');
INSERT INTO users(id, name, surname, email) VALUES (2, 'Jane', 'Smith', 'jane.smith@example.com');
INSERT INTO users(id, name, surname, email) VALUES (3, 'Alice', 'Johnson', 'alice.johnson@example.com');

INSERT INTO vendedores(id, name) VALUES (1, 'Juan');
INSERT INTO vendedores(id, name) VALUES (2, 'Maria');
INSERT INTO vendedores(id, name) VALUES (3, 'Carlos');
```

### Arcivo "schema.graphqls" dentro de la carpeta resources/graphql
```
type User {
    id: ID
    name: String
    surname: String
    email: String
}

type Vendedor {
    id: ID
    name: String
}

input UserInput {
    name: String!
    surname: String!
    email: String!
}

input UserUpdateInput {
    id: ID!
    name: String
    surname: String
    email: String
}

input VendedorInput {
    name: String!
}

input VendedorUpdateInput {
    id: ID!
    name: String
}

type Query {
    users: [User]
    user(id: ID!): User
    vendedores: [Vendedor]
    vendedor(id: ID!): Vendedor
}

type Mutation {
    createUser(input: UserInput!): User
    updateUser(input: UserUpdateInput!): User
    deleteUser(id: ID!): Boolean

    createVendedor(input: VendedorInput!): Vendedor
    updateVendedor(input: VendedorUpdateInput!): Vendedor
    deleteVendedor(id: ID!): Boolean
}
```

## programas JAVA

### Archivo "UserEntity.java"
```
import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "Users")
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class UserEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String surname;
  private String email;
}
```


