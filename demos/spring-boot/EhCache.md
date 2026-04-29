# EhCache con spring boot JDK 21

## Configuraciones

### Archivo "pom.xml"
```
<!-- Begin javax.validation -->
<dependency>
  <groupId>javax.validation</groupId>
  <artifactId>validation-api</artifactId>
  <version>2.0.1.Final</version>
</dependency>
<!-- End javax.validation -->

<!-- eh chace -->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-cache</artifactId>
</dependency>
<dependency>
  <groupId>javax.cache</groupId>
  <artifactId>cache-api</artifactId>
</dependency>
<dependency>
  <groupId>org.ehcache</groupId>
  <artifactId>ehcache</artifactId>
</dependency>
<!-- eh chace -->

<!-- JPA -->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- H2 Database -->
<dependency>
  <groupId>com.h2database</groupId>
  <artifactId>h2</artifactId>
  <scope>runtime</scope>
</dependency>
```

### Archivo "application.properties"
```
#JPA
spring.jpa.show-sql=true
#Habilita las consultas nativas
logging.level.org.hibernate.sql=debug

# Cache eviction schedule (milliseconds)
cache.color.evict.fixed-delay-ms=60000
cache.color.evict.initial-delay-ms=500
```

### Programas JAVA

### La clase "EhCacheConfig"
```
import java.util.Date;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableCaching
@EnableScheduling
public class EhCacheConfig {

  /*
   * n = Numero de minutos
   *
   * fixedDelay = n * 60 * 1000
   *
   */
  @CacheEvict(allEntries = true, value = "colorCache")
  @Scheduled(fixedDelayString = "${cache.color.evict.fixed-delay-ms:60000}", initialDelayString = "${cache.color.evict.initial-delay-ms:500}")
  public void reportCacheEvictColor() {
    System.out.println("Flush Cache Color " + new Date());
  }

  @Bean
  CacheManager cacheManager() {
    log.trace("Creating cache manager.");
    // return new ConcurrentMapCacheManager("userCache","colorCache");
    return new ConcurrentMapCacheManager("colorCache");
  }
}
```

### La Clase "ColorEntity"
```
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor // genera un constructor sin argumentos, uno vacío
@AllArgsConstructor // genera un constructor para todos los atributos de la clase.
@Data // Metodos Setters
@EqualsAndHashCode // genera los métodos equals y hashcode.
@ToString // genera el método toString.
@Entity(name = "ColorEntity") // Nombre de la Entidad
@Table(name = "colores")
public class ColorEntity {
  // Key
  @Id // Llave Primaria
  @GeneratedValue(strategy = GenerationType.IDENTITY) // Estrategia de generacion
  private Long color_id; // Key

  // Descripción del color
  @NotNull(message = "Este campo es requerido, Descripción del color.")
  @Size(min = 1, max = 75, message = "El Descripción del color debe tener de 1 a 75 caracteres.")
  @Column(name = "color_descripcion", length = 75, columnDefinition = "char(75)", nullable = false)
  private String color_descripcion;
}
```

### La interface "IColorEntityRepository"
```
import org.springframework.data.jpa.repository.JpaRepository;

public interface IColorEntityRepository extends JpaRepository<ColorEntity, Long> {

}
```

### La interface "IColorService"
```
import java.util.List;
public interface IColorService {
  List<ColorEntity> getAll();

  void create(String descripcion);
}
```

### La clase "ColorServiceImpl"
```

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.example.demo_ehCache.app.entities.ColorEntity;
import com.example.demo_ehCache.app.repository.IColorEntityRepository;
import com.example.demo_ehCache.app.services.IColorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ColorServiceImpl implements IColorService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ColorServiceImpl.class);

  @Autowired
  IColorEntityRepository repo;

  @Override
  @Cacheable(value = "colorCache")
  public List<ColorEntity> getAll() {
    LOGGER.info("Obteniendo todos los usuarios de la  BD");

    List<ColorEntity> list = repo.findAll();
    return list;
  }

  @Override
  @Caching(evict = {
      @CacheEvict(value = "colorCache", allEntries = true),
  })
  public void create(String descripcion) {
    ColorEntity color = new ColorEntity();
    color.setColor_id(null);
    color.setColor_descripcion(descripcion);

    repo.save(color);

  }

}
```

### La clase "ColorController"
```
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/color")
public class ColorController {
  @Autowired
  private IColorService colorService;

  // Buscar todos
  @GetMapping
  public List<ColorEntity> getAll() {

    List<ColorEntity> resultado = colorService.getAll();

    return resultado;

  }
}
```


