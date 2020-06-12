package model;

import java.util.List;

public class ExcursionsInPortContainer {
    int portId;
    String portName;
    List<Excursion> excursions;

    public ExcursionsInPortContainer(int portId, String portName, List<Excursion> excursions) {
        this.portId = portId;
        this.portName = portName;
        this.excursions = excursions;
    }

    public int getPortId() {
        return portId;
    }

    public String getPortName() {
        return portName;
    }

    public List<Excursion> getExcursions() {
        return excursions;
    }

    @Override
    public String toString() {
        return "ExcursionsInPortContainer{" +
                "portId=" + portId +
                ", portName='" + portName + '\'' +
                ", excursions=" + excursions +
                '}';
    }
}
