package dmn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNDecisionResult;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNResult;
import org.kie.dmn.api.core.DMNRuntime;

public class DmnRunner {
	private static final int NUMBER_OF_RUNS = 10;
	private static final int TEST_CONTROL_NUMBER = 1;
	private static final int NUMBER_OF_ELEMENTS_OF_INPUTLIST = 500;

	public static void main(String[] args) {
		KieServices kieServices = KieServices.Factory.get();

		KieContainer kieContainer = kieServices.getKieClasspathContainer();
		DMNRuntime dmnRuntime = kieContainer.newKieSession().getKieRuntime(DMNRuntime.class);

		Map<String, String> testModelsData = new HashMap<>();
//		testModelsData.put("testCase_academic_15", "http://www.trisotech.com/definitions/_453b392c-b474-415e-b3b5-84255b711990");
//		testModelsData.put("testCase_academic_20", "http://www.trisotech.com/definitions/_33429f3e-bbd5-4d34-9baf-5fb6e3d93bd9");
//		testModelsData.put("testCase_academic_25", "http://www.trisotech.com/definitions/_111a7145-f140-4eee-ab0b-a3f34b5a846c");
//		testModelsData.put("testCase_academic_30", "http://www.trisotech.com/definitions/_9ede3af6-f1aa-498e-bda4-b5880dfa67f5");
//		testModelsData.put("testCase_academic_35", "http://www.trisotech.com/definitions/_ff2c5219-6f70-401f-ba0b-2d32c0b7d95b");
//		testModelsData.put("testCase_academic_40", "http://www.trisotech.com/definitions/_26904dd6-17bd-41c3-a771-377fba245a7f");
//		testModelsData.put("testCase_academic_45", "http://www.trisotech.com/definitions/_b4bd2624-06ab-46cd-be07-6b41f6e7370a");
		testModelsData.put("testCase_academic_50", "http://www.trisotech.com/definitions/_822984a5-95bd-411b-88a4-f3ebfecc5f3c");
		
		// Sorting by converting HashMap to TreeMap 
		Map<String, String> treeMapTestModelData = new TreeMap<String, String>(testModelsData);
		Iterator<?> it = treeMapTestModelData.entrySet().iterator();
		
		sysoutTestParameters();

		while (it.hasNext()) {
			Map.Entry tmd = (Map.Entry) it.next();
			System.out.println("Testing: " + (String) tmd.getKey());
			DMNResult dmnResult = testAcademicExample(dmnRuntime, (String) tmd.getValue(), (String) tmd.getKey());
			DMNDecisionResult dr = dmnResult.getDecisionResultByName("Testcontrol");
			// System.out.println("Decision '" + dr.getDecisionName() + "' : " +
			// dr.getResult());
			System.out.println("======================");
			System.out.println(" ");
		}

	}

	public static DMNResult testAcademicExample(DMNRuntime dmnRuntime, String namespace, String name) {
		DMNModel dmnModel = dmnRuntime.getModel(namespace, name);

		Map<?, ?> person = createPerson("Bob", 20, "Boston", Arrays.asList("Jan", "Thomas", "Frank", "Ulrich"), "father2323", "mother23232");
		List<Map<?, ?>> personList = createPersonList();

		DMNResult dmnResult = null;

		long timeSum = 0;
		for (int i = 0; i < NUMBER_OF_RUNS; i++) {
			dmnResult = null;
			long starttime = System.nanoTime();
			DMNContext dmnContext = dmnRuntime.newContext();
			dmnContext.set("InputPersonList", personList);
			dmnContext.set("InputPerson", person);
			dmnContext.set("Testnumber", TEST_CONTROL_NUMBER);
			dmnResult = dmnRuntime.evaluateAll(dmnModel, dmnContext);
			long endtime = System.nanoTime();

			Long elapsedtime = endtime - starttime;
			timeSum += elapsedtime;
			endtime = 0;
			starttime = 0;
			elapsedtime = null;
			dmnContext = null;
		}
		Long averageTime = timeSum / NUMBER_OF_RUNS;
		System.out.println("Average time of: " + (averageTime.doubleValue() / 1000000000) + " sec with " + NUMBER_OF_RUNS + " runs.");

		return dmnResult;
	}

	private static Map<String, Object> createPerson(String name, int age, String city, List<String> silbling, String father, String mother) {
		Map<String, Object> person = new HashMap<>();
		person.put("Name", name);
		person.put("Age", age);
		person.put("City", city);
		Map<String, Object> parents = new HashMap<>();
		parents.put("Father", father);
		parents.put("Mother", mother);
		person.put("Parents", parents);
		person.put("Silbling", silbling);
		return person;
	}

	private static List<Map<?, ?>> createPersonList() {
		List<Map<?, ?>> personList = new ArrayList<>();
		personList.add(createPerson("Alice", 28, "Boston", Arrays.asList("Jan", "Alex", "Ulrich"), "father1", "mother2"));
		personList.add(createPerson("Charlie", 28, "New York", Arrays.asList("Ulrich", "Hans", "Ute"), "father1", "mother2"));
		personList.add(createPerson("Donna", 32, "Boston", Arrays.asList("Karla", "Jan", "Jana", "Hans"), "father1", "mother2"));
		personList.add(createPerson("Eleonore", 30, "Boston", Arrays.asList("Karla", "Jan", "Ulrich", "Hans"), "father1", "mother2"));
		personList.add(createPerson("Fernand", 31, "New York", Arrays.asList("Jan", "Ulrich", "Jana"), "father1", "mother2"));
		personList.add(createPerson("Grace", 25, "Boston", Arrays.asList("Jan", "Ulrich", "Jana"), "father1", "mother2"));
		personList.add(createPerson("Hector", 29, "Boston", Arrays.asList("Ute", "Rainer", "Jana"), "father1", "mother2"));
		personList.add(createPerson("Iris", 29, "Boston", Arrays.asList("Ute", "Rainer", "Jana", "Jan", "Karla"), "father1", "mother2"));

		for (int i = 0; i < NUMBER_OF_ELEMENTS_OF_INPUTLIST; i++) {
			personList.add(createPerson("Iris", 29 + i, "Boston", Arrays.asList("Ute", "Rainer", "Jana", "Jan", "Karla", "Alex", "ulrich", "Hans"), "father1", "mother2"));
		}
		return personList;
	}

	private static void sysoutTestParameters() {
		System.out.println("Test with following parameters: ");
		System.out.println("NUMBER_OF_RUNS: " + NUMBER_OF_RUNS);
		System.out.println("TESTNUMBER: " + TEST_CONTROL_NUMBER);
		System.out.println("NUMBER_OF_ELEMENTS_OF_INPUTLIST: " + NUMBER_OF_ELEMENTS_OF_INPUTLIST);
		System.out.println("-----------------");
	}

}
