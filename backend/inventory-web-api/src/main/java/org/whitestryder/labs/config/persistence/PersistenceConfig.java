package org.whitestryder.labs.config.persistence;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
		basePackages = {"org.whitestryder.labs.app.support"}
)
@EntityScan(basePackages ={ "org.whitestryder.labs.core"})
public class PersistenceConfig {

}
