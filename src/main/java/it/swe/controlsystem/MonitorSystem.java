package it.swe.controlsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.swe.work.AbstractWorker;
import it.swe.work.Observer;
import it.swe.work.Team;
import it.swe.work.Worker;

public class MonitorSystem {

	private static final Logger loggerApplication = LoggerFactory.getLogger("logApplication");

	private static MonitorSystem instance;
	public static List<Observer> workers = new ArrayList<>();

	private static Container container ;
	private static Map<String, List<Box>> map = new ConcurrentHashMap<>();

	private static WorkState loadState = new LoaderState();
	private static WorkState pickState = new PickerState();
	private static WorkState breakState = new BreakState();
	private static int number = 100000;

	private static LoaderStratetegyRepair loadRepair = new LoaderStratetegyRepair();
	private static PickerStrategyRepair pickRepair = new PickerStrategyRepair();
	
	public MonitorSystem(int n) {
		MonitorSystem.container = new Container(n);
	}

	public static WorkState getLoadState() {
		return loadState;
	}

	public static synchronized Container getContainer() {
		return container;
	}

	public static Map<String, List<Box>> getMap() {
		return map;
	}

	public static WorkState getPickState() {
		return pickState;
	}

	public static MonitorSystem getMonitor() {
		if (instance == null) {
			instance = new MonitorSystem(number);
		}
		return instance;
	}
	
	public static void reset() {
	    instance = new MonitorSystem(number);
	}

	public static void attach(Observer w) {
		workers.add(w);
	}

	public static void detach(Observer worker) {
		workers.remove(worker);
	}

	public void notifyNewState(Observer w, WorkState ws) {
		w.update(ws);

	}

	public void notifyBreakTime(AbstractWorker w) throws InterruptedException {
		WorkState tmpState = w.getWorkerState();
		w.update(breakState);
		Thread.sleep(1000);
		w.update(tmpState);
	}

	private void resolveStrategy(AbstractWorker worker) {
		if (worker.getWorkerState() == loadState)
			loadRepair.resolve(worker);
		else
			pickRepair.resolve(worker);
	}

	public void startWorking(AbstractWorker w) {
		if (w instanceof Worker && !w.isWorking()) {
			w.setWorkingState(true);
			loggerApplication.info(w.getWorkerName() + "sta iniziando a lavorare");
			w.start();
		}
		if (w instanceof Team) {
			for (int i = 0; i < w.getTeam().size(); i++)
				this.startWorking(w.getWorkerTeam(i));
		}

	}

	public void updateWorker(Worker worker) {
		loggerApplication.info("Il monitor e' stato notificato riguardo l'inattivita' di " + worker.getWorkerName());
		this.resolveStrategy(worker);

	}

	public static synchronized boolean checkStockingArea(Map<String, List<Box>> map) {
		for (Entry<String, List<Box>> entry : map.entrySet()) {
			if (!entry.getValue().isEmpty()) {
				return false;
			}
		}
		return true;
	}

}
