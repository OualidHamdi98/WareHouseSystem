package it.swe.controlsystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Container {

	private List<Box> boxs;

	public Container() {}

	public Container(int n) {
		this.boxs = Collections.synchronizedList(new ArrayList<Box>());
		for (int i = 0; i < n; i++) {
			Box b = new Box();
			b.setCode(i);
			boxs.add(b);
		}
	}

	public List<Box> getListContainer() {
		return boxs;
	}

	public synchronized Box remove() {
		return (!boxs.isEmpty()) ? boxs.remove(0) : null;
	}

}
