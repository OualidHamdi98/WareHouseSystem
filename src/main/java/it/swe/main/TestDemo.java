package it.swe.main;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.swe.controlsystem.Box;
import it.swe.controlsystem.MonitorSystem;
import it.swe.work.Team;
import it.swe.work.Worker;

public class TestDemo {
	
	@BeforeEach
	void setUp(){
		MonitorSystem.reset();
	}

	@Test
	void throwsUnsupportedOperation() {
		System.out.println("Inizio Test throwsUnsupportedOperation");
		Team teamTest = new Team("testTeam", MonitorSystem.getMonitor(), MonitorSystem.getLoadState());
		Worker worker1 = new Worker("TestName1", MonitorSystem.getLoadState());
		Worker worker2 = new Worker("TestName2", MonitorSystem.getLoadState());
		assertThrows(UnsupportedOperationException.class, () -> worker1.addIWorker(worker2));
		assertThrows(UnsupportedOperationException.class, () -> worker1.getTeam());
		assertThrows(UnsupportedOperationException.class, () -> worker1.getWorkerTeam(0));
		assertThrows(UnsupportedOperationException.class, () -> worker1.removeIWormer(worker2));
		assertThrows(UnsupportedOperationException.class, () -> teamTest.notifyMonitor());
		System.out.println("Fine Test throwsUnsupportedOperation");
	}

	@Test
	void testSingletonMonitor() {
		System.out.println("Inizio Test testSingletonMonitor");
		MonitorSystem mr1 = MonitorSystem.getMonitor();
		MonitorSystem mr2 = MonitorSystem.getMonitor();
		assertEquals(mr1, mr2);
		System.out.println("Fine Test testSingletonMonitor");
	}

	@Test
	void testLoaderStrategyRepair() {
		System.out.println("Inizio Test testLoaderStrategyRepair");
		Worker worker1 = new Worker("TestName1", MonitorSystem.getLoadState());
		worker1.setWorkingState(false);
		worker1.notifyMonitor();
		assertTrue(worker1.isWorking());
		assertEquals(worker1.getWorkerState(), MonitorSystem.getPickState());
		System.out.println("Fine Test testLoaderStrategyRepair");
	}

	// Test picker che continua a lavorare in zona picking perchè l'area di
	// stoccaggio è ancora piena
	@Test
	void testPickerStrategyRepair() {
		System.out.println("Inizio Test testPickerStrategyRepair");
		Worker worker1 = new Worker("TestName1", MonitorSystem.getPickState());
		Box b = new Box();
		List<Box> test = new ArrayList<Box>();
		test.add(b);
		MonitorSystem.getMap().put("testKey", test);
		MonitorSystem.getContainer().getListContainer().clear();
		worker1.setWorkingState(false);
		worker1.notifyMonitor();
		assertTrue(worker1.isWorking());
		assertEquals(worker1.getWorkerState(), MonitorSystem.getPickState());
		System.out.println("Fine Test testPickerStrategyRepair");
	}

	// Test picker che, a causa del Container non ancora vuoto si sposta in zona
	// Loading.
	@Test
	void testPickerStrategyRepairToLoad() {
		System.out.println("Inizio Test testPickerStrategyRepairToLoad");
		MonitorSystem.reset();
		Worker worker1 = new Worker("TestName1", MonitorSystem.getPickState());
		worker1.setWorkingState(false);
		worker1.notifyMonitor();
		assertTrue(worker1.isWorking());
		assertEquals(worker1.getWorkerState(), MonitorSystem.getLoadState());
		System.out.println("Fine Test testPickerStrategyRepairToLoad");
	}

	@Test
	void testInterruzioneLavoratore() {
		System.out.println("Inizio Test testInterruzioneLavoratore");
		Worker worker1 = new Worker("TestName1", MonitorSystem.getPickState());
		MonitorSystem.getContainer().getListContainer().clear();
		MonitorSystem.getMap().clear();
		worker1.setWorkingState(false);
		worker1.notifyMonitor();
		assertFalse(worker1.isWorking());
		System.out.println("Fine Test testInterruzioneLavoratore");
	}

	@Test
	void addWorkerToTeam() {
		System.out.println("Inizio Test addWorkerToTeam");
		Team teamTest = new Team("testTeam", MonitorSystem.getMonitor(), MonitorSystem.getLoadState());
		Worker worker1 = new Worker("TestName1", MonitorSystem.getLoadState());
		teamTest.addIWorker(worker1);
		assertEquals(worker1, teamTest.getWorkerTeam(0));
		System.out.println("Fine Test addWorkerToTeam");
	}

	@Test
	void addWorkerToTeamDifferentState() {
		System.out.println("Inizio Test addWorkerToTeamDifferentState");
		Team teamTest = new Team("testTeam", MonitorSystem.getMonitor(), MonitorSystem.getLoadState());
		Worker worker1 = new Worker("TestName1", MonitorSystem.getPickState());
		teamTest.addIWorker(worker1);
		assertThrows(IndexOutOfBoundsException.class, () -> teamTest.getWorkerTeam(0));
		System.out.println("Fine Test addWorkerToTeamDifferentState");
	}

	@Test
	void addTeamToTeam() {
		System.out.println("Inizio Test addTeamToTeam");
		Team teamTest1 = new Team("testTeam1", MonitorSystem.getMonitor(), MonitorSystem.getLoadState());
		Team teamTest2 = new Team("testTeam2", MonitorSystem.getMonitor(), MonitorSystem.getLoadState());
		Worker worker1 = new Worker("TestName1", MonitorSystem.getLoadState());
		Worker worker2 = new Worker("TestName2", MonitorSystem.getLoadState());
		Worker worker3 = new Worker("TestName3", MonitorSystem.getLoadState());
		teamTest1.addIWorker(worker1);
		teamTest1.addIWorker(worker2);
		teamTest2.addIWorker(worker3);
		teamTest2.addIWorker(teamTest1);
		assertEquals(teamTest2.getTeam().size(), 3);
		System.out.println("Fine Test addTeamToTeam");
	}

	@Test
	void monitorNotifyNewState() {
		System.out.println("Inizio Test monitorNotifyNewState");
		Worker worker1 = new Worker("TestName1", MonitorSystem.getLoadState());
		MonitorSystem.getMonitor().notifyNewState(worker1, MonitorSystem.getPickState());
		assertEquals(worker1.getWorkerState(), MonitorSystem.getPickState());
		System.out.println("Fine Test monitorNotifyNewState");

	}

}