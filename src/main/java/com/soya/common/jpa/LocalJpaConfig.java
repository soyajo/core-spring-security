package com.soya.common.jpa;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.dialect.MariaDBDialect;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;

import javax.sql.DataSource;
import java.util.Properties;

import static java.util.concurrent.TimeUnit.SECONDS;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "**.repository.local.**",
        transactionManagerRef = LocalJpaConfig.TransactionManager,
        entityManagerFactoryRef = LocalJpaConfig.EntityManager
)
public class LocalJpaConfig {

    public static final String dbname = "local";


    @Value("${spring.profiles.active}")
    public String active;

    public String url = "jdbc:mariadb://localhost:3306/local??allowPublicKeyRetrieval=true&useSSL=false";

    public String user="root";

    public String password="1715";

    public String poolname="local-jpa";

    public Integer poolsize=10;

    public final static String EntityManager = dbname + "EntityManagerFactory";
    public final static String TransactionManager = dbname + "JpaTransactionManager";

    @Primary
    @Bean(name = EntityManager)
    public LocalContainerEntityManagerFactoryBean localEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(localJpaDataSource());
        em.setPackagesToScan("com.soya.core_spring_security");
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(localAdditionalProperties());
        em.setPersistenceUnitName("fourfreeSmsEntityManager");
        return em;
    }

    @Primary
    @Bean
    public DataSource localJpaDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDataSourceClassName(null);
        hikariConfig.setDriverClassName("org.mariadb.jdbc.Driver");
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(user);
        hikariConfig.setPassword(password);
        hikariConfig.setPoolName(poolname);
        hikariConfig.setMaximumPoolSize(poolsize);
        hikariConfig.setConnectionTimeout(SECONDS.toMillis(100));
        hikariConfig.setValidationTimeout(SECONDS.toMillis(20));
        hikariConfig.setIdleTimeout(SECONDS.toMillis(10));
        hikariConfig.setMaxLifetime(SECONDS.toMillis(1));

        HikariDataSource dataSource = new HikariDataSource(hikariConfig);

        return new LazyConnectionDataSourceProxy(dataSource);
    }

    @Primary
    @Bean(name = TransactionManager)
    public PlatformTransactionManager localJpaTransactionManager(@Qualifier(EntityManager) EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Primary
    @Bean
    public static Properties localAdditionalProperties() {
        Properties properties = new Properties();
        // 기존 속성 설정
        properties.setProperty("hibernate.dialect", MariaDBDialect.class.getName());
//        properties.setProperty("hibernate.hbm2ddl.auto", "create");
        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");
//        properties.setProperty("hibernate.use_sql_comments", "true");

        return properties;
    }
}
