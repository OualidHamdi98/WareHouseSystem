package it.swe.controlsystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BreakState implements WorkState {
	
	private static final Logger loggerApplication = LoggerFactory.getLogger("logApplication");

	@Override
	public boolean work() throws InterruptedException {
		try {
			loggerApplication.info("Break-time !");
			Thread.sleep(10000);
			loggerApplication.info("Back to work !");
			return true;
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new InterruptedException();
		}
	}

}
