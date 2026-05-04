# Arquetipo


## Eliminar
```
C:\Users\HSR\.m2\repository\sv\com\jhtechnologies
```

## Archivo "settings.xml"
```
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                      https://maven.apache.org/xsd/settings-1.0.0.xsd">
</settings>
```

## Generar arquetipo
```
mvn archetype:create-from-project -DinteractiveMode=false
```

## Instalar windows
```
cd target\generated-sources\archetype
mvn install
```

## Instalar linux
```
cd target/generated-sources/archetype
mvn install
```


## Ejecutar
```
mvn archetype:generate -DarchetypeGroupId=sv.com.jhtechnologies -DarchetypeArtifactId=desarrollo-springboot-jdk21-archetype -DarchetypeVersion=0.0.1-SNAPSHOT
```

## Ejemplo
```
Define value for property 'groupId': sv.com.acme
Define value for property 'artifactId': servicio-login
Define value for property 'version' 1.0-SNAPSHOT: :
Define value for property 'package' sv.com.acme: : sv.com.acme.app
```
