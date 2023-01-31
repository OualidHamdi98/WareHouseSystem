package it.swe.work;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.swe.controlsystem.MonitorSystem;
import it.swe.controlsystem.WorkState;

public abstract class AbstractWorker extends Thread implements Observer {

	protected String worker;
	private boolean working;
	protected boolean onTeam;
	protected MonitorSystem mr ;
	protected List<AbstractWorker> team;
	protected WorkState workerState;
	
	protected static final Logger loggerApplication = LoggerFactory.getLogger("logApplication");

	public WorkState getWorkerState() {
		return workerState;
	}

	public void setWorkerState(WorkState workerState) {
		this.workerState = workerState;
	}

	public boolean isWorking() {
		return working;
	}

	public void setWorkingState(boolean state) {
		this.working = state;
	}

	public String getWorkerName() {
		return worker;
	}

	public void setWorkerName(String iWorker) {
		this.worker = iWorker;
	}

	public void startWorking() {
	}

	public void addIWorker(AbstractWorker w) {
		throw new UnsupportedOperationException();
	}

	public void removeIWormer(AbstractWorker w) {
		throw new UnsupportedOperationException();
	}

	public AbstractWorker getWorkerTeam(int i) {
		throw new UnsupportedOperationException();
	}

	public List<AbstractWorker> getTeam() {
		throw new UnsupportedOperationException();
	}
	
	public void notifyMonitor() {
		throw new UnsupportedOperationException();
	}
	
}
