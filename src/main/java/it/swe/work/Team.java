package it.swe.work;

import java.util.ArrayList;
import java.util.List;

import it.swe.controlsystem.MonitorSystem;
import it.swe.controlsystem.WorkState;

public class Team extends AbstractWorker {

	public Team(String teamName, MonitorSystem mr, WorkState workerState) {
		this.worker = teamName;
		this.team = new ArrayList<>();
		this.workerState = workerState;
		this.mr = mr;
	}

	@Override
	public void addIWorker(AbstractWorker w) {
		if (w.getWorkerState() == this.workerState) {
			if (w instanceof Worker && !w.onTeam) {
				w.onTeam = true;
				loggerApplication.info("Il lavoratore " + w.getWorkerName() + " e' stato aggiunto a questo team : "
						+ this.getWorkerName());
				this.team.add(w);
				MonitorSystem.attach(w);
			}
			if (w instanceof Team) {
				loggerApplication.info(
						"Il team " + this.getWorkerName() + " vuole aggiungere i membiri del team : " + w.getWorkerName());
				while(w.getTeam().size()>0) {
					w.getWorkerTeam(0).onTeam = false;
					MonitorSystem.detach(w);
					this.addIWorker(w.getWorkerTeam(0));
					w.getTeam().remove(0);
				}
			}

		} else {
			loggerApplication.info("Il lavoratore " + w.getWorkerName() + " ha un compito diverso dal team : "
					+ this.getWorkerName() + " per aggiungerlo cambiare attivià al lavoratore o al team !");
		}
	}

	@Override
	public void removeIWormer(AbstractWorker w) {
		team.remove(w);
		MonitorSystem.detach(w);
		w.onTeam = false;
	}

	@Override
	public List<AbstractWorker> getTeam() {
		return team;
	}

	@Override
	public AbstractWorker getWorkerTeam(int i) throws UnsupportedOperationException {
		return team.get(i);
	}

	@Override
	public void startWorking() {
		loggerApplication.info("il team " + this.getWorkerName() + " inizia a lavorare ..");
		for (int i = 0; i < this.getTeam().size(); i++) {
			if (this.getWorkerTeam(i) instanceof Worker && this.getWorkerTeam(i).interrupted())
				this.getWorkerTeam(i).startWorking();
			else
				this.getWorkerTeam(i).startWorking();
		}
	}

	@Override
	public void run() {
		for (AbstractWorker w : team)
			w.run();
	}

	@Override
	public void update(WorkState workerState) {
		for (int i = 0; i < team.size(); i++) {
			if (this.getWorkerTeam(i) instanceof Worker)
				this.getWorkerTeam(i).setWorkerState(workerState);
			else
				this.getWorkerTeam(i).update(workerState);
		}
	}

}
