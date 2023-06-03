package api.core.config;



import org.hibernate.dialect.PostgreSQLDialect;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(basePackages = "api.db", entityManagerFactoryRef = "apiEntityManager", transactionManagerRef = "apiTransactionManager")
public class ApiDataSourceConfiguration {

    @Primary
    @Bean
    @ConfigurationProperties("app.datasource.api")
    DataSourceProperties apiSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean
    DataSource apiDataSource() {
        return apiSourceProperties().initializeDataSourceBuilder().build();
    }

    @Primary
    @Bean
    LocalContainerEntityManagerFactoryBean apiEntityManager(EntityManagerFactoryBuilder builder, @Qualifier("apiDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource).packages("api.db").persistenceUnit("api").properties(additionalJpaProperties()).build();

    }

    private Map<String, String> additionalJpaProperties() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("hibernate.dialect", PostgreSQLDialect.class.getCanonicalName());
        map.put("hibernate.show_sql", "true");
        map.put("hibernate.format_sql", "true");
        map.put("hibernate.hbm2ddl.auto", "validate"); //update
        return map;
    }

    @Primary
    @Bean
    PlatformTransactionManager apiTransactionManager(@Qualifier("apiEntityManager") LocalContainerEntityManagerFactoryBean entryManager) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entryManager.getObject());
        return transactionManager;
    }

}
