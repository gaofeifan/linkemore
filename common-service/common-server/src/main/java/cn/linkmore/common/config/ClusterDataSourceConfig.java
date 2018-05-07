package cn.linkmore.common.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;
/**
 * Config - 从库配置[只读操作]
 * @author liwenlong
 * @version 2.0
 *
 */
@Configuration
@ConfigurationProperties(prefix = "cluster-db")
@MapperScan(basePackages = ClusterDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "clusterSqlSessionFactory")
public class ClusterDataSourceConfig {
	private String url;
    private String username;
    private String password;
    private String driverClassName;
    private int initialSize;
    private int minIdle;
    private int maxActive;
    private int maxWait;
    private int timeBetweenEvictionRunsMillis;
    private int minEvictableIdleTimeMillis;
    private String validationQuery;
    private boolean testWhileIdle;
    private boolean testOnBorrow;
    private boolean testOnReturn;
    private boolean poolPreparedStatements;
    private int maxPoolPreparedStatementPerConnectionSize;
    private String filters;
    private String connectionProperties; 

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    public int getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    public int getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    public String getValidationQuery() {
        return validationQuery;
    }

    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }

    public boolean isTestWhileIdle() {
        return testWhileIdle;
    }

    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public boolean isTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public boolean isPoolPreparedStatements() {
        return poolPreparedStatements;
    }

    public void setPoolPreparedStatements(boolean poolPreparedStatements) {
        this.poolPreparedStatements = poolPreparedStatements;
    }

    public int getMaxPoolPreparedStatementPerConnectionSize() {
        return maxPoolPreparedStatementPerConnectionSize;
    }

    public void setMaxPoolPreparedStatementPerConnectionSize(int maxPoolPreparedStatementPerConnectionSize) {
        this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
    }

    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }

    public String getConnectionProperties() {
        return connectionProperties;
    }

    public void setConnectionProperties(String connectionProperties) {
        this.connectionProperties = connectionProperties;
    }

	static final String PACKAGE = "cn.linkmore.common.dao.cluster";
	static final String MAPPER_LOCATION = "classpath:/cn/linkmore/common/mapper/cluster/*.xml";

	@Bean(name = "clusterDataSource")
	public DataSource clusterDataSource() {
		DruidDataSource datasource = new DruidDataSource();
		datasource.setUrl(this.getUrl());
		datasource.setUsername(this.getUsername());
		datasource.setPassword(this.getPassword());
		datasource.setDriverClassName(this.getDriverClassName());
		// configuration
		datasource.setInitialSize(this.getInitialSize());
		datasource.setMinIdle(this.getMinIdle());
		datasource.setMaxActive(this.getMaxActive());
		datasource.setMaxWait(this.getMaxWait());
		datasource.setTimeBetweenEvictionRunsMillis(this.getTimeBetweenEvictionRunsMillis());
		datasource.setMinEvictableIdleTimeMillis(this.getMinEvictableIdleTimeMillis());
		datasource.setValidationQuery(this.getValidationQuery());
		datasource.setTestWhileIdle(this.isTestWhileIdle());
		datasource.setTestOnBorrow(this.isTestOnBorrow());
		datasource.setTestOnReturn(this.isTestOnReturn());
		datasource.setPoolPreparedStatements(this.isPoolPreparedStatements());
		datasource.setMaxPoolPreparedStatementPerConnectionSize(
				this.getMaxPoolPreparedStatementPerConnectionSize());
		try {
			datasource.setFilters(this.getFilters());
		} catch (SQLException e) {
			System.err.println("druid configuration initialization filter: " + e);
		}
		datasource.setConnectionProperties(this.getConnectionProperties());
		return datasource;
	}

	@Bean(name = "clusterTransactionManager")
	public DataSourceTransactionManager clusterTransactionManager() {
		return new DataSourceTransactionManager(clusterDataSource());
	}

	@Bean(name = "clusterSqlSessionFactory")
	public SqlSessionFactory clusterSqlSessionFactory(@Qualifier("clusterDataSource") DataSource clusterDataSource)
			throws Exception {
		final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(clusterDataSource);
		sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
		return sessionFactory.getObject();
	}
}
