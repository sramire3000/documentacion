


## JDK's

### Amazon Corretto Documentation 
````
https://docs.aws.amazon.com/corretto/
````

### Amazon Download
````
https://aws.amazon.com/corretto/
````

## Datos del microservicio API

### Spring Iniz
```
groupId      = sv.jh.springcloud.msvc.products
packageId    = jh-msvc-products
packageName  = sv.jh.springcloud.msvc.products.app
type         = jar
Version Java = 21
```

### Dependencias
```
Spring Web
Spring boot Devtools
JPA
PostgreSQL Driver
Cloud Bootstrap spring cloud
Lombok
Actuator
Eureka Discovery Client
```

### Packages
````
controllers
entities
repositories
services
````

## Datos del microservicio Client

### Spring Iniz
```
groupId      = sv.jh.springcloud.msvc.items
packageId    = jh-msvc-items
packageName  = sv.jh.springcloud.msvc.items.app
type         = jar
Version Java = 21
```

### Dependencias
```
Spring Web
spring boot Devtools
Cloud Bootstrap spring cloud
Spring Reactive Web
OpenFeign
Lombok
Actuator
Eureka Discovery Client
Resilience4J
Cloud Bootstrap
Config Client
```

### Packages
````
models
controllers
services
clients
````
