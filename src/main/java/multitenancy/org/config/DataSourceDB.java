package multitenancy.org.config;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import multitenancy.org.config.MultitenancyConfigurationProperties.Tenant;

@Service
public class DataSourceDB {

	private Tenant defaultTenant;

	private List<Tenant> tenants = new ArrayList<Tenant>();

	@PostConstruct
	public void init() {

		Tenant t1 = new Tenant();
		t1.setName("tenant_1");
		t1.setUsername("root");
		t1.setDriverClassName("com.mysql.jdbc.Driver");
		t1.setPassword("");
		t1.setUrl("jdbc:mysql://localhost:3306/tenant_1?serverTimezone=UTC");
		t1.setDefault(true);

		Tenant t2 = new Tenant();
		t2.setName("tenant_2");
		t2.setUsername("root");
		t2.setDriverClassName("com.mysql.jdbc.Driver");
		t2.setPassword("");
		t2.setUrl("jdbc:mysql://localhost:3306/tenant_2?serverTimezone=UTC");
		t2.setDefault(false);

		tenants.add(t1);
		tenants.add(t2);

		List<Tenant> tcs = tenants.stream().filter(tc -> tc.isDefault())
				.collect(Collectors.toCollection(ArrayList::new));
		if (tcs.size() > 1) {
			throw new IllegalStateException(
					"Only can be configured as default one data source. Review your configuration");
		}
		this.defaultTenant = tcs.get(0);
	}

	public void addTenant(Tenant tenant) {
		this.tenants.add(tenant);
	}

	public List<Tenant> getTenants() {
		return tenants;
	}

	public void setTenants(List<Tenant> tenants) {
		this.tenants = tenants;
	}

	public Tenant getDefaultTenant() {
		return defaultTenant;
	}
}
