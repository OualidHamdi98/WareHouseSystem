package it.swe.controlsystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.swe.work.AbstractWorker;

public interface AbstractStrategy {

	static final Logger loggerApplication = LoggerFactory.getLogger("logApplication");
	
	void resolve(AbstractWorker w);

}
