package it.swe.work;

import it.swe.controlsystem.WorkState;

public interface Observer {

    void update(WorkState workerState);

}
