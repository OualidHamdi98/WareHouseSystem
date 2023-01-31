package it.swe.main;

import it.swe.controlsystem.MonitorSystem;
import it.swe.work.AbstractWorker;
import it.swe.work.Team;
import it.swe.work.Worker;

public class Demo {

	public static void main(String[] args) throws InterruptedException {
		
		MonitorSystem mr = MonitorSystem.getMonitor();
		
		AbstractWorker worker1 = new Worker("Mario Rossi", MonitorSystem.getLoadState());
		Worker worker2 = new Worker("Giulio Golia",  MonitorSystem.getLoadState());
		Worker worker3 = new Worker("Maria Bencini",  MonitorSystem.getPickState());
		Worker worker4 = new Worker("Alberto Ramires", MonitorSystem.getPickState());
		Worker worker5 = new Worker("Federica Giani", MonitorSystem.getPickState());
		Worker worker6 = new Worker("Mirco Fabbri", MonitorSystem.getPickState());
		Worker worker7 = new Worker("Salvatore Baglieri", MonitorSystem.getPickState());
		
		
		Team team1 = new Team("Team BLU", mr,  MonitorSystem.getLoadState());
		Team team2 = new Team("Team ROSSO", mr, MonitorSystem.getLoadState());
		Team team3 = new Team("Team ORO", mr, MonitorSystem.getPickState());
		Team team4 = new Team("Team Verde", mr, MonitorSystem.getPickState());
		
		team1.addIWorker(worker1);
		team2.addIWorker(worker2);
		team2.addIWorker(worker3);
		team2.addIWorker(worker4);
		team3.addIWorker(worker5);
		team3.addIWorker(worker5);
		team3.addIWorker(worker1);
		team2.addIWorker(worker6);
		team4.addIWorker(worker7);
		team4.addIWorker(team3);

		
		team1.startWorking();
		team2.startWorking();
		team3.startWorking();
		team4.startWorking();
		
		mr.notifyBreakTime(team1);
		mr.notifyNewState(team3, MonitorSystem.getLoadState());
		
		
		//TEST SEMPLICE CON PER UN SOLO WORKER e 2 PACCHI

//		AbstractWorker worker1 = new Worker("Mario Rossi", MonitorSystem.getLoadState());
		
//		worker1.startWorking();
		mr.notifyBreakTime(worker1);
		

	}
}
