package it.swe.controlsystem;

import it.swe.work.AbstractWorker;
import it.swe.work.Worker;

public class LoaderStratetegyRepair implements AbstractStrategy {

	@Override
	public void resolve(AbstractWorker w) {
		if (w instanceof Worker)
			loggerApplication.info(
					"Il lavoratore " + w.getWorkerName() + " non avendo più pacchi da scaricare, si sposta in zona picking");
		w.setWorkerState(MonitorSystem.getPickState());
		w.setWorkingState(true);

	}

}
