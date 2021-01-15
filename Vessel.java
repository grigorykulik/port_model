import java.util.Date;

public class Vessel {
    //Field names are self-explaining
    private String vesselName;
    private Date expectedArrival;
    private Date actualArrival;
    private int daysToUnload;
    private CargoType cargoType;
    private double cargoWeight;
    private int plannedUnloadingTime;
    private double penalty;
    private Date unloadingStartDay;
    private Date unloadingFinishDay;
    private int minsInQueue;
    private boolean isUnloading;
    private int daysInQueue;


    public Date getUnloadingStartDay() {
        return unloadingStartDay;
    }

    public void setUnloadingStartDay(Date unloadingStartDay) {
        this.unloadingStartDay = unloadingStartDay;
    }

    public Date getUnloadingFinishDay() {
        return unloadingFinishDay;
    }

    public void setUnloadingFinishDay(Date unloadingFinishDay) {
        this.unloadingFinishDay = unloadingFinishDay;
    }

    public String getVesselName() {
        return vesselName;
    }

    public void setVesselName(String vesselName) {
        this.vesselName = vesselName;
    }

    public Date getExpectedArrival() {
        return expectedArrival;
    }

    public void setExpectedArrival(Date expectedArrival) {
        this.expectedArrival = expectedArrival;
    }

    public CargoType getCargoType() {
        return cargoType;
    }

    public void setCargoType(CargoType cargoType) {
        this.cargoType = cargoType;
    }

    public double getCargoWeight() {
        return cargoWeight;
    }

    public void setCargoWeight(double cargoWeight) {
        this.cargoWeight = cargoWeight;
    }

    public int getDuration() {
        return plannedUnloadingTime;
    }

    public void setPlannedUnloadingTime(int plannedUnloadingTime) {
        this.plannedUnloadingTime = plannedUnloadingTime;
    }

    public Date getActualArrival() {
        return actualArrival;
    }

    public void setActualArrival(Date actualArrival) { this.actualArrival = actualArrival; }

    public double getPenalty() {
        return penalty;
    }

    public void setPenalty(double penalty) { this.penalty = penalty; }

    public void setDaysToUnload(int n) {
        this.daysToUnload=n;
    }

    public int getDaysToUnload() {
        return this.daysToUnload;
    }

    public void incrementDaysToUnload(){
        this.daysToUnload++;
    }

    public void setMinsInQueue(int i) {
        this.minsInQueue =i;
    }

    public int getMinsInQueue() {
        return this.minsInQueue;
    }

    public void incrementMinsInQueue() {
        this.minsInQueue++;
    }

    public void setIsUnloading(boolean b) {
        this.isUnloading=b;
    }

    public boolean isUnloading() {
        return this.isUnloading;
    }

    public void setDaysInQueue() {
        if (this.minsInQueue%1440==0) {
            this.daysInQueue=this.minsInQueue/1440;
        }

        else this.daysInQueue=this.minsInQueue/1440+1;
    }

    public int getDaysInQueue() {
        return this.daysInQueue;
    }
}
