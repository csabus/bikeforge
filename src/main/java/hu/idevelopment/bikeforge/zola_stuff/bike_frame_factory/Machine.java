package hu.idevelopment.bikeforge.zola_stuff.bike_frame_factory;

public class Machine {
    private final MachineType machineType;

    public Machine(MachineType machineType) {
        this.machineType = machineType;
    }

    public MachineType getMachineType() {
        return machineType;
    }
}
