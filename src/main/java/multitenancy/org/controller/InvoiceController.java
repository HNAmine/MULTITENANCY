package multitenancy.org.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.Yaml;

import com.google.common.collect.Lists;

import multitenancy.org.model.Invoice;
import multitenancy.org.repository.InvoiceRepository;

@RestController
@RequestMapping("/{tenantId}/invoice")
public class InvoiceController {

	@Autowired
	private InvoiceRepository invoiceRepository;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Invoice> invoices() {
		return Lists.newArrayList(invoiceRepository.findAll());
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, value = "/test")
	public void writeInPropFile() throws IOException {
		System.out.println("i will back !");
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("name", "Silenthand Olleander");
		data.put("race", "Human");
		data.put("traits", new String[] { "ONE_HAND", "ONE_EYE" });

		Yaml yaml = new Yaml();
		FileWriter writer = new FileWriter("application.yml");
		yaml.dump(data, writer);
	}

}
