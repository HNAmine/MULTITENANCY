package multitenancy.org.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import multitenancy.org.DataSourceBasedMultiTenantConnectionProviderImpl;
import multitenancy.org.config.MultitenancyConfigurationProperties.Tenant;

@RestController
@RequestMapping("/tenants")
public class TenantController {

	@Autowired
	DataSourceBasedMultiTenantConnectionProviderImpl dataSourceBasedMultiTenantConnectionProvider;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String invoices() {
		return dataSourceBasedMultiTenantConnectionProvider.getDefaultTenant();
	}

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, value = "")
	public String addTenantConnection(@RequestBody Tenant tc) {
		this.dataSourceBasedMultiTenantConnectionProvider.addDataSource(tc.getName(),
				DataSourceBuilder.create().driverClassName(tc.getDriverClassName()).username(tc.getUsername())
						.password(tc.getPassword()).url(tc.getUrl()).build());
		return "data source was created !";
	}

	@RequestMapping(method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE, value = "/{idTenant}")
	public String deleteTenantConnection(@PathVariable String idTenant) {
		this.dataSourceBasedMultiTenantConnectionProvider.deleteDataSource(idTenant);
		return "data source was deleted !";
	}

}
