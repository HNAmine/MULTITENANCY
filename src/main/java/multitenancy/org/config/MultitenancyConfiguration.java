package multitenancy.org.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import multitenancy.org.DataSourceBasedMultiTenantConnectionProviderImpl;

@Configuration
@EnableConfigurationProperties(MultitenancyConfigurationProperties.class)
public class MultitenancyConfiguration {

	@Autowired
	private MultitenancyConfigurationProperties multitenancyProperties;

	@Autowired
	private DataSourceDB dataSourceDB;

	@Bean(name = "multitenantProvider")
	public DataSourceBasedMultiTenantConnectionProviderImpl dataSourceBasedMultiTenantConnectionProvider() {

		HashMap<String, DataSource> dataSources = new HashMap<String, DataSource>();

		System.out.println("----------------------");
		this.dataSourceDB.getTenants().forEach(t -> {
			System.out.println(t.getName());
		});
		
		// load conf
		multitenancyProperties.getTenants().stream().forEach(
				tc -> dataSources.put(tc.getName(), DataSourceBuilder.create().driverClassName(tc.getDriverClassName())
						.username(tc.getUsername()).password(tc.getPassword()).url(tc.getUrl()).build()));

		// dataSourceDB.getTenants().stream().forEach(tc ->
		// dataSources.put(tc.getName(),
		// DataSourceBuilder.create()
		// .driverClassName(tc.getDriverClassName())
		// .username(tc.getUsername())
		// .password(tc.getPassword())
		// .url(tc.getUrl()).build()));

		return new DataSourceBasedMultiTenantConnectionProviderImpl(multitenancyProperties.getDefaultTenant().getName(),
				dataSources);
	}

	@Bean
	@DependsOn("multitenantProvider")
	public DataSource defaultDataSource() {
		return dataSourceBasedMultiTenantConnectionProvider().getDefaultDataSource();
	}

}
