package it.swe.controlsystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface WorkState {

	static final Logger loggerApplication = LoggerFactory.getLogger("logApplication");
	boolean work() throws Exception;
}
