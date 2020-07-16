package hu.idevelopment.bikeforge.machine;

public class MachineFactory {
    private MachineFactory() {
    }

    public static Machine createMachine(MachineType type, int id) {
        Machine machine;
        switch (type) {
            case CURVE:
                machine = new CurveMachine(id);
                break;
            case CUT:
                machine = new CutMachine(id);
                break;
            case PACK:
                machine = new PackMachine(id);
                break;
            case PAINT:
                machine = new PaintMachine(id);
                break;
            case TEST:
                machine = new TestMachine(id);
                break;
            case WELD:
                machine = new WeldMachine(id);
                break;
            default:
                machine = null;
        }
        return machine;
    }
}
