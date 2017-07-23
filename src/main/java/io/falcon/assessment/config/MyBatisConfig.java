package io.falcon.assessment.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "io.falcon.assessment.repository", sqlSessionFactoryRef="falconSqlSessionFactory")
@EnableTransactionManagement
public class MyBatisConfig {
    @Bean(name = "falconDataSource", destroyMethod = "close")
    @Qualifier("falconDataSource")
    @ConfigurationProperties(prefix="datasource")
    public DataSource falconDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "falconTransactionManager")
    @Qualifier("falconTransactionManager")
    protected DataSourceTransactionManager falconTransactionManager(DataSource falconDataSource) {
        return new DataSourceTransactionManager(falconDataSource);
    }

    @Bean(name ="falconSqlSessionFactory")
    @Qualifier("falconSqlSessionFactory")
    public SqlSessionFactory falconSqlSessionFactory(DataSource falconDataSource) throws Exception{
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(falconDataSource);
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/mybatis/*.xml"));
        sqlSessionFactory.setTypeAliasesPackage("io.falcon.assessment.model");

        return sqlSessionFactory.getObject();
    }

    @Bean(name = "falconSqlSession", destroyMethod = "clearCache")
    @Qualifier("falconSqlSession")
    public SqlSessionTemplate falconSqlSession(SqlSessionFactory falconSqlSessionFactory) {
        return new SqlSessionTemplate(falconSqlSessionFactory);
    }
}
