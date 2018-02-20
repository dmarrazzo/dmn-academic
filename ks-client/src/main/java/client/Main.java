package client;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNDecisionResult;
import org.kie.dmn.api.core.DMNResult;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.DMNServicesClient;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Person;

public class Main {
	final static Logger log = LoggerFactory.getLogger(Main.class);

	private static final String URL = "http://localhost:8081/kie-server/services/rest/server";
	private static final String user = "dmAdmin";
	private static final String password = "password";
	private static final String CONTAINER = "DMNTest_1.0.0";

	private DMNServicesClient dmnClient;
	

	public Main() {
		setup();
	}

	public static void main(String[] args) {
		Main main = new Main();

		long start = System.currentTimeMillis();
		main.executeDMN();
		long end = System.currentTimeMillis();
		System.out.println("elapsed time: " + (end - start));
	}

	private void executeDMN() {
		DMNContext dmnContext = dmnClient.newContext();   
//		Map<String, Object> person = new HashMap<>();
//		
//		person.put("name", "Donato");
//		person.put("age", 17);
//		person.put("country", "italy");
		Person person = new Person("pino", 15, "italy");
		
		dmnContext.set("Person", person);
		ServiceResponse<DMNResult> serverResp = dmnClient.evaluateAll(CONTAINER, 
				"http://www.trisotech.com/definitions/_90a17b17-c884-4fa9-ba59-7a47899d89b2", "driving-eligibility-2",
		        dmnContext);

		DMNResult dmnResult = serverResp.getResult();
		DMNDecisionResult result = dmnResult.getDecisionResultByName("Driving Licence Eligibility");
		System.out.println(result.getResult());
	}

	private void setup() {
		KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(URL, user, password);
		// Marshalling
		Set<Class<?>> extraClasses = new HashSet<Class<?>>();
		extraClasses.add(Person.class);
		config.addExtraClasses(extraClasses);
		config.setMarshallingFormat(MarshallingFormat.JSON);
		Map<String, String> headers = null;
		config.setHeaders(headers);

		KieServicesClient client = KieServicesFactory.newKieServicesClient(config);
		dmnClient = client.getServicesClient( DMNServicesClient.class );
	}

}
