public class Crane {
    private Vessel v;
    private int unloadingSpeed=0;
    private boolean busy=false;

    public Crane(int unloadingSpeed) {
        this.unloadingSpeed=unloadingSpeed;
    }

    public Vessel getV() {
        return v;
    }

    public void setV(Vessel v) {
        this.v = v;
    }

    public boolean isBusy() {
        return this.busy;
    }

    public void setBusy(boolean b) {
        this.busy=b;
    }

    public void unload(Vessel v) {
        setV(v);

        if (v.getCargoWeight() > 0) {
            v.setCargoWeight(v.getCargoWeight() - unloadingSpeed);
        }
    }
}