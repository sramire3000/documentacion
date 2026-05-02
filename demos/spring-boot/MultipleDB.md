# Multiple Base de datos

## Paquetes
```
com.example.demo_multi_db
|- domain
|- application
|- bootstrap
|  |- DemoMultiDbApplication.java
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
		<!-- Begin validation -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-validation</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-validation-test</artifactId>
			<scope>test</scope>
		</dependency>
		<!-- End validation -->

		<!-- CONFIGURATION PROCESSOR -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-configuration-processor</artifactId>
			<optional>true</optional>
		</dependency>

		<!-- JASYPT Encriptacion para Usuario/Clave en propertie de DB -->
		<dependency>
			<groupId>com.github.ulisesbocchio</groupId>
			<artifactId>jasypt-spring-boot-starter</artifactId>
			<version>3.0.5</version>
		</dependency>


		<!-- SQL Server Driver (mssql-jdbc) -->
		<dependency>
			<groupId>com.microsoft.sqlserver</groupId>
			<artifactId>mssql-jdbc</artifactId>
			<scope>runtime</scope>
		</dependency>

		<!-- PostgreSQL Driver -->
		<dependency>
			<groupId>org.postgresql</groupId>
			<artifactId>postgresql</artifactId>
			<scope>runtime</scope>
		</dependency>

		<!-- MySQL Driver -->
		<dependency>
			<groupId>com.mysql</groupId>
			<artifactId>mysql-connector-j</artifactId>
			<scope>runtime</scope>
		</dependency>

		<!-- Sybase Driver (jTDS) -->
		<dependency>
			<groupId>net.sourceforge.jtds</groupId>
			<artifactId>jtds</artifactId>
			<version>1.3.1</version>
			<scope>runtime</scope>
		</dependency>

		<!-- Community dialects for Sybase -->
		<dependency>
			<groupId>org.hibernate.orm</groupId>
			<artifactId>hibernate-community-dialects</artifactId>
		</dependency>

		<!-- DATA JPA -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
```

## Archuvo "application.properties"
```
```
