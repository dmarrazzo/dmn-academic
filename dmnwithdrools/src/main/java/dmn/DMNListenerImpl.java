package dmn;

import org.kie.dmn.api.core.event.AfterEvaluateBKMEvent;
import org.kie.dmn.api.core.event.AfterEvaluateDecisionEvent;
import org.kie.dmn.api.core.event.AfterEvaluateDecisionTableEvent;
import org.kie.dmn.api.core.event.BeforeEvaluateBKMEvent;
import org.kie.dmn.api.core.event.BeforeEvaluateDecisionEvent;
import org.kie.dmn.api.core.event.BeforeEvaluateDecisionTableEvent;
import org.kie.dmn.api.core.event.DMNRuntimeEventListener;

public class DMNListenerImpl implements DMNRuntimeEventListener {

	@Override
	public void afterEvaluateBKM(AfterEvaluateBKMEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterEvaluateDecision(AfterEvaluateDecisionEvent afterEvaluateDecisionEvent) {
		System.out.println("afterEvaluateDecision: "+afterEvaluateDecisionEvent.getDecision().getName());
	}

	@Override
	public void afterEvaluateDecisionTable(AfterEvaluateDecisionTableEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeEvaluateBKM(BeforeEvaluateBKMEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeEvaluateDecision(BeforeEvaluateDecisionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeEvaluateDecisionTable(BeforeEvaluateDecisionTableEvent arg0) {
		// TODO Auto-generated method stub

	}

}
