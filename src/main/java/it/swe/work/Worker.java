package it.swe.work;


import it.swe.controlsystem.MonitorSystem;
import it.swe.controlsystem.WorkState;

public class Worker extends AbstractWorker {

	public Worker(String workerName, WorkState workerState) {
		this.worker = workerName;
		this.workerState = workerState;
		this.onTeam = false;
	}

	@Override
	public void run() {
		while (!this.isInterrupted()) {
			if (this.isWorking()) {
				try {
					sleep(1000);
					this.setWorkingState(this.getWorkerState().work());
					loggerApplication.info(this.getWorkerName() + " ha completato la sua attivita'");
				} catch (Exception e) {
					e.printStackTrace();
					loggerApplication.error(e.getMessage());
					this.setWorkingState(false);
				}
			} else
				notifyMonitor();
		}

	}

	@Override
	public void update(WorkState w) {
		this.setWorkerState(w);
		loggerApplication.info(this.getWorkerName() + " ha cambiato attivita'!");
	}

	@Override
	public void startWorking() throws IllegalThreadStateException {
		this.setWorkingState(true);
		loggerApplication.info(this.getWorkerName() + " inizia a lavorare");
		this.start();

	}

	@Override
	public void notifyMonitor() {
		MonitorSystem.getMonitor().updateWorker(this);

	}

}
