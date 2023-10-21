package org.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;


@Configuration
@ComponentScan(basePackages = "org.example")
@EnableWebMvc
@EnableJpaRepositories(basePackages = "org.example")
@PropertySource("classpath:db.properties")
public class AppConfig {

    @Autowired
    private Environment env;

    private final ApplicationContext applicationContext;

    @Autowired
    public AppConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan( "org.example" );
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(hibernateProperties());
        return em;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName( Objects.requireNonNull( env.getProperty( "dataSourceClassName" ) ) );

        if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
            dataSource.setUrl(env.getProperty("dataSource.url"));
        } else {
            dataSource.setUrl("jdbc:postgresql://" + env.getProperty("dataSource.serverName") +
                    ":" + env.getProperty("dataSource.portNumber") +
                    "/" + env.getProperty("dataSource.databaseName"));
        }

        dataSource.setUsername(env.getProperty("dataSource.user"));
        dataSource.setPassword(env.getProperty("dataSource.password"));

        return dataSource;
    }


    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.hbm2ddl.auto", "update");
        String activeProfile = Arrays.asList(env.getActiveProfiles()).contains("test") ?
                "org.hibernate.dialect.H2Dialect" : "org.hibernate.dialect.PostgreSQLDialect";
        properties.put("hibernate.dialect", activeProfile);
        properties.put("hibernate.id.new_generator_mappings", "true");
        return properties;
    }
}
