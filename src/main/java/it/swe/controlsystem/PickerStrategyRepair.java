package it.swe.controlsystem;

import it.swe.work.AbstractWorker;
import it.swe.work.Worker;

public class PickerStrategyRepair implements AbstractStrategy {

	@Override
	public void resolve(AbstractWorker w) {
		if (w instanceof Worker) {
			if (!MonitorSystem.getContainer().getListContainer().isEmpty()) {
				w.setWorkerState(MonitorSystem.getLoadState());
				w.setWorkingState(true);
				loggerApplication.info("Il lavoratore " + w.getWorkerName() + " si sposta in zona picking");
			} else if (MonitorSystem.checkStockingArea(MonitorSystem.getMap())) {
				w.setWorkingState(false);
				loggerApplication.info("Il lavoratore " + w.getWorkerName() + " ha terminato la sua attivita'");
				w.interrupt();
			} else {
				w.setWorkingState(true);
				loggerApplication.info("Il lavoratore " + w.getWorkerName() + " continua a lavorare in zona picking");
			}
		}
	}

}
