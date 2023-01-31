package it.swe.controlsystem;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Box {

	private int code;
	private String shippingDestination;
	private Boolean sent;

	private static final Logger loggerApplication = LoggerFactory.getLogger("logApplication");

	public Box() {
		this.setShippingVan(this.getDestination());
		this.sent = false;
	}

	private static enum ShippingDestination {

		Firenze,
		Provincie,
		Prato,
		Siena,
		Pisa,
		Montecatini

	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public String getShippingVan() {
		return shippingDestination;
	}

	private void setShippingVan(String shippingVan) {
		this.shippingDestination = shippingVan;
	}

	private String getDestination() {
		int pick = new Random().nextInt(ShippingDestination.values().length);
		return ShippingDestination.values()[pick].toString();
	}

	public boolean send() {
		if (!sent) {
			loggerApplication.info("Il pacco è stato correttamente spedito");
			return this.sent = true;
		}

		loggerApplication.error("Il pacco è stato già spedito");
		return false;

	}

}
