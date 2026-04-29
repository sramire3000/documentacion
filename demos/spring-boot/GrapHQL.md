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
-object.type
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

### Archivo "User.java" en la carpeta type
```
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

  private Long id;
  private String name;
  private String surname;
  private String email;

}
```

### Archivo "Vendedor.java"
```
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Vendedor {
  private Long id;
  private String name;
}
```

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

### Archivo "VendedorEntity.java"
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

@Entity(name = "Vendedor")
@Table(name = "vendedores")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class VendedorEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
}
```

### Archivo "UserRepository.java"ç
```
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo_graphql.app.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
```

### Archivo "VendedorRepository.java"
```
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo_graphql.app.entities.VendedorEntity;

public interface VendedorRepository extends JpaRepository<VendedorEntity, Long> {

}
```

### Archivo "UserService.java"
```
import java.util.List;

import com.example.demo_graphql.app.object.type.User;

public interface UserService {

  List<User> findAll();

  User findById(Long id);

  User save(User user);

  User update(User user);

  void deleteById(Long id);
}
```

### Archivo "VendedorService.java"
```
import java.util.List;

import com.example.demo_graphql.app.object.type.Vendedor;

public interface VendedorService {
  List<Vendedor> findAll();

  Vendedor findById(Long id);

  Vendedor save(Vendedor vendedor);

  Vendedor update(Vendedor vendedor);

  void deleteById(Long id);
}
```

### Archivo "UserMapper.java"
```
import com.example.demo_graphql.app.entities.UserEntity;
import com.example.demo_graphql.app.object.type.User;

public class UserMapper {

  public static UserEntity userToEntity(User user) {
    return new UserEntity(user.getId(), user.getName(), user.getSurname(), user.getEmail());
  }

  public static User entityToUser(UserEntity userEntity) {
    return new User(userEntity.getId(), userEntity.getName(), userEntity.getSurname(), userEntity.getEmail());
  }
}
```

### Archivo "VendedorMapper.java"
```
import com.example.demo_graphql.app.entities.VendedorEntity;
import com.example.demo_graphql.app.object.type.Vendedor;

public class VendedorMapper {

  public static VendedorEntity vendedorToEntity(Vendedor vendedor) {
    return new VendedorEntity(vendedor.getId(), vendedor.getName());
  }

  public static Vendedor entityToVendedor(VendedorEntity vendedorEntity) {
    return new Vendedor(vendedorEntity.getId(), vendedorEntity.getName());
  }

}
```

### Archivo "UserServiceImpl.java"
```
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo_graphql.app.mappers.UserMapper;
import com.example.demo_graphql.app.object.type.User;
import com.example.demo_graphql.app.repository.UserRepository;
import com.example.demo_graphql.app.services.UserService;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public List<User> findAll() {
    return userRepository.findAll().stream()
        .map(UserMapper::entityToUser)
        .collect(Collectors.toList());
  }

  @Override
  public User findById(Long id) {
    return userRepository.findById(id)
        .map(UserMapper::entityToUser)
        .orElse(null);
  }

  @Override
  public User save(User user) {
    return UserMapper.entityToUser(userRepository.save(UserMapper.userToEntity(user)));
  }

  @Override
  public User update(User user) {
    return userRepository.findById(user.getId()).map(existing -> {
      if (user.getName() != null)
        existing.setName(user.getName());
      if (user.getSurname() != null)
        existing.setSurname(user.getSurname());
      if (user.getEmail() != null)
        existing.setEmail(user.getEmail());
      return UserMapper.entityToUser(userRepository.save(existing));
    }).orElse(null);
  }

  @Override
  public void deleteById(Long id) {
    userRepository.deleteById(id);
  }

}
```

### Archivo "VendedorServiceImpl.java"
```
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo_graphql.app.mappers.VendedorMapper;
import com.example.demo_graphql.app.object.type.Vendedor;
import com.example.demo_graphql.app.repository.VendedorRepository;
import com.example.demo_graphql.app.services.VendedorService;

@Service
public class VendedorServiceImpl implements VendedorService {

  private final VendedorRepository vendedorRepository;

  public VendedorServiceImpl(VendedorRepository vendedorRepository) {
    this.vendedorRepository = vendedorRepository;
  }

  @Override
  public List<Vendedor> findAll() {
    return vendedorRepository.findAll().stream()
        .map(VendedorMapper::entityToVendedor)
        .collect(Collectors.toList());
  }

  @Override
  public Vendedor findById(Long id) {
    return vendedorRepository.findById(id)
        .map(VendedorMapper::entityToVendedor)
        .orElse(null);
  }

  @Override
  public Vendedor save(Vendedor vendedor) {
    return VendedorMapper.entityToVendedor(vendedorRepository.save(VendedorMapper.vendedorToEntity(vendedor)));
  }

  @Override
  public Vendedor update(Vendedor vendedor) {
    return vendedorRepository.findById(vendedor.getId()).map(existing -> {
      if (vendedor.getName() != null)
        existing.setName(vendedor.getName());
      return VendedorMapper.entityToVendedor(vendedorRepository.save(existing));
    }).orElse(null);
  }

  @Override
  public void deleteById(Long id) {
    vendedorRepository.deleteById(id);
  }

}
```

### Archivo "UserGraphQLController.java" en la carpeta "graphql"
```

import java.util.List;
import java.util.Map;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.example.demo_graphql.app.object.type.User;
import com.example.demo_graphql.app.services.UserService;

@Controller
public class UserGraphQLController {

  private final UserService userService;

  public UserGraphQLController(UserService userService) {
    this.userService = userService;
  }

  @QueryMapping
  public List<User> users() {
    return userService.findAll();
  }

  @QueryMapping
  public User user(@Argument Long id) {
    return userService.findById(id);
  }

  @MutationMapping
  public User createUser(@Argument Map<String, Object> input) {
    User user = new User(
        null,
        (String) input.get("name"),
        (String) input.get("surname"),
        (String) input.get("email"));
    return userService.save(user);
  }

  @MutationMapping
  public User updateUser(@Argument Map<String, Object> input) {
    Long id = Long.valueOf(input.get("id").toString());
    User user = new User(
        id,
        (String) input.get("name"),
        (String) input.get("surname"),
        (String) input.get("email"));
    return userService.update(user);
  }

  @MutationMapping
  public Boolean deleteUser(@Argument Long id) {
    userService.deleteById(id);
    return true;
  }

}
```

### Archivo "VendedorGraphQLController.java" en la carpeta "graphql"
```

import java.util.List;
import java.util.Map;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.example.demo_graphql.app.object.type.Vendedor;
import com.example.demo_graphql.app.services.VendedorService;

@Controller
public class VendedorGraphQLController {

  private final VendedorService vendedorService;

  public VendedorGraphQLController(VendedorService vendedorService) {
    this.vendedorService = vendedorService;
  }

  @QueryMapping
  public List<Vendedor> vendedores() {
    return vendedorService.findAll();
  }

  @QueryMapping
  public Vendedor vendedor(@Argument Long id) {
    return vendedorService.findById(id);
  }

  @MutationMapping
  public Vendedor createVendedor(@Argument Map<String, Object> input) {
    Vendedor vendedor = new Vendedor(
        null,
        (String) input.get("name"));
    return vendedorService.save(vendedor);
  }

  @MutationMapping
  public Vendedor updateVendedor(@Argument Map<String, Object> input) {
    Long id = Long.valueOf(input.get("id").toString());
    Vendedor vendedor = new Vendedor(
        id,
        (String) input.get("name"));
    return vendedorService.update(vendedor);
  }

  @MutationMapping
  public Boolean deleteVendedor(@Argument Long id) {
    vendedorService.deleteById(id);
    return true;
  }

}
```
