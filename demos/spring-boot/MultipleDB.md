# Multiple Base de datos

## Paquetes
```
com.example.demo_multi_db
|- domain
|- application
|- infrastructure
|  |- persistence
|  |  |- sqlserver
|  |  |  |- entity
|  |  |  |- repository
|  |  |  `- adapter
|  |  `- postgres
|  |     |- entity
|  |     |- repository
|  |     `- adapter
|  `- web
|- resources
`- shared
```

## Archivo "pom.xml"
```

		<!-- JASYPT Encriptacion para Usuario/Clave en propertie de DB -->
		<dependency>
			<groupId>com.github.ulisesbocchio</groupId>
			<artifactId>jasypt-spring-boot-starter</artifactId>
			<version>3.0.5</version>
		</dependency>

		<!-- DATA JPA -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>

		<!-- PostgreSQL Driver -->
		<dependency>
			<groupId>org.postgresql</groupId>
			<artifactId>postgresql</artifactId>
			<scope>runtime</scope>
		</dependency>

		<!-- SQL Server Driver (mssql-jdbc) -->
		<dependency>
			<groupId>com.microsoft.sqlserver</groupId>
			<artifactId>mssql-jdbc</artifactId>
			<scope>runtime</scope>
		</dependency>
```




