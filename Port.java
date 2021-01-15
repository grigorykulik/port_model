import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class Port {
    public int nBulkCranes=5;
    public int nLiquidCranes=5;
    public int nContainerCranes=4;

    //Arrays of vessels with cargo
    private ArrayList<Vessel> bulkLoaded=new ArrayList<>();
    private ArrayList<Vessel> containerLoaded=new ArrayList<>();
    private ArrayList<Vessel> liquidLoaded=new ArrayList<>();

    //Arrays of cranes
    private ArrayList<Crane> bulkHandlingCranes=new ArrayList<>();
    private ArrayList<Crane> liquidHandlingCranes=new ArrayList<>();
    private ArrayList<Crane> containerHandlingCranes=new ArrayList<>();

    //Array of unloaded vessels
    public ArrayList<Vessel> unloadedVessels=new ArrayList<>();

    //Fields containing the final statistics
    private double averageDaysInQueue=0;
    private int numberOfUnloadedVessels=0;
    private double totalPenalty=0;
    private int numberOfBulkHandlingCranes=0;
    private int numberOfContainerHandlingCranes=0;
    private int numberOfLiquidHandlingCranes=0;
    private double averageQueueLength=0;
    private double averageUnloadingDelay=0;
    private int maxUnloadingDelay=0;

    GregorianCalendar gc=new GregorianCalendar();

    //Getters
    public double getAverageDaysInQueue() {
        return this.averageDaysInQueue;
    }
    public int getNumberOfUnloadedVessels() {
        return this.numberOfUnloadedVessels;
    }
    public double getTotalPenalty() {
        return this.totalPenalty;
    }
    public int getNumberOfBulkHandlingCranes() {
        return this.numberOfBulkHandlingCranes;
    }
    public int getNumberOfContainerHandlingCranes() {
        return this.numberOfContainerHandlingCranes;
    }
    public int getNumberOfLiquidHandlingCranes() {
        return this.numberOfLiquidHandlingCranes;
    }
    public double getAverageQueueLength() {
        return this.averageQueueLength;
    }
    public double getAverageUnloadingDelay() {
        return this.averageUnloadingDelay;
    }
    public int getMaxUnloadingDelay() {
        return this.maxUnloadingDelay;
    }


    public void createCranes() {
        for (int i=0; i<this.nBulkCranes; i++) {
            Crane c=new Crane(70);
            bulkHandlingCranes.add(c);
        }

        for (int i=0; i<this.nLiquidCranes; i++) {
            Crane d=new Crane(170);
            liquidHandlingCranes.add(d);
        }

        for (int i=0; i<this.nContainerCranes; i++) {
            Crane e=new Crane(34);
            containerHandlingCranes.add(e);
        }
    }

    public void distributeVessels(ArrayList<Vessel> vessels) {
        //Sort vessels by actual arrival date
        List<Vessel> v=vessels
                .stream()
                .sorted(Comparator.comparing(Vessel::getActualArrival))
                .collect(Collectors.toList());

        for (int i=0; i<vessels.size(); i++) {
            if (v.get(i).getCargoType()==CargoType.BULK) bulkLoaded.add(v.get(i));
            else if (v.get(i).getCargoType()==CargoType.CONTAINER) containerLoaded.add(v.get(i));
            else liquidLoaded.add(v.get(i));
        }
    }

    //
    public void unloadVessels(ArrayList<Vessel> vessels) {

        ArrayList<Vessel> testBulkLoaded=new ArrayList<>(bulkLoaded);
        ArrayList<Vessel> testContainerLoaded=new ArrayList<>(containerLoaded);
        ArrayList<Vessel> testLiquidLoaded=new ArrayList<>(liquidLoaded);

//        Collections.copy(testBulkLoaded, bulkLoaded);
//        Collections.copy(testLiquidLoaded, liquidLoaded);
//        Collections.copy(testContainerLoaded, containerLoaded);

        //Distribute vessels between three arrays


        //Main loop
        for (int i=0; i<30; i++) {
            for (int j=0; j<24; j++) {
                for (int k=0; k<60; k++) {
                    gc.add(Calendar.MINUTE, 1);
                    Date currentDate = gc.getTime();

                    unload(bulkLoaded, currentDate, bulkHandlingCranes);
                    unload(liquidLoaded, currentDate, liquidHandlingCranes);
                    unload(containerLoaded, currentDate, containerHandlingCranes);
                }
            }
        }
    }

    public void unload(ArrayList<Vessel> array, Date currentDate, ArrayList<Crane> cranes) {

        // If the array of vessels is not empty, iterate through it searching for vessels whose arrival date is less
        //than the current date from the main loop. If there are no such vessels do nothing.
        // If, however, there are such vessel, check that there are available cranes.
        // If there is no available cranes, increment the time the vessel spent waiting for being unloaded.
        // If there is at least one available crane of the corresponding type, pass the vessel to it.
        // Set the flag that shows the vessel is being unloaded at the moment.
        // Set the flag that shows the crane is busy.
        if (!array.isEmpty()) {
            for (int k=0; k<array.size(); k++) {
                if (currentDate.after(array.get(k).getActualArrival()) && !array.get(k).isUnloading()) {
                    if (cranesAvailable(cranes)) {
                        for (int l=0; l<cranes.size(); l++) {
                            if (!cranes.get(l).isBusy()) {
                                cranes.get(l).setV(array.get(k));
                                array.get(k).setIsUnloading(true);
                                cranes.get(l).setBusy(true);
                                break;
                            }
                        }
                    }
                    else array.get(k).incrementMinsInQueue();
                }
            }

            //Iterate through cranes
            for (int m=0; m<cranes.size(); m++) {

                Crane currentCrane=cranes.get(m);

                // If the crane is busy unloading a vessel and the vessel still has no unloading start date,
                // set this date to the current one from the main loop
                if (currentCrane.isBusy()) {
                    if (currentCrane.getV().getUnloadingStartDay()==null) {
                        currentCrane.getV().setUnloadingStartDay(currentDate);
                    }

                    // Start unloading the vessel by subtracting a particular number from its cargo weight
                    currentCrane.unload(cranes.get(m).getV());


                    // As soon as the vessel's been unloaded, set its unloading finish date.
                    // Unloading finish day is set as the actual finish date + 0 to 10 days.
                    // The crane stays busy until this final date.
                    if (currentCrane.getV().getCargoWeight()<0) {
                        if (currentCrane.getV().getUnloadingFinishDay()==null) {
                            Date finishDay=RandomDateGenerator.generateRandomDelay(currentDate);
                            currentCrane.getV().setUnloadingFinishDay(finishDay);

                            //Calculate actual unloading time
                            LocalDateTime date1= currentCrane.getV().getUnloadingStartDay().toInstant()
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDateTime();
                            LocalDateTime date2= currentCrane.getV().getUnloadingFinishDay().toInstant()
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDateTime();
                            long daysBetween=ChronoUnit.DAYS.between(date1, date2);
                            currentCrane.getV().setDaysToUnload((int)daysBetween+1);
                        }

                        currentCrane.getV().setDaysInQueue();

                        // Calculate the penalty as the sum of the number of days it took to unload the vessel and
                        // the number of days it waited for unloading
                        int penalty=(currentCrane.getV().getDaysToUnload()-cranes.get(m).getV().getDuration()+
                                currentCrane.getV().getDaysInQueue())*1000;
                        currentCrane.getV().setPenalty(penalty);

                        if (currentDate.after(currentCrane.getV().getUnloadingFinishDay())) {
                            unloadedVessels.add(currentCrane.getV());
                            array.remove(currentCrane.getV());
                            currentCrane.getV().setIsUnloading(false);
                            currentCrane.setV(null);
                            currentCrane.setBusy(false);
                        }
                    }
                }
            }
        }
    }

    public boolean cranesAvailable(ArrayList<Crane> cranes) {
        for (Crane c: cranes) {
            if (!c.isBusy()) {
                return true;
            }
        }

        return false;
    }

    public void getStatistics() {
        this.numberOfUnloadedVessels=this.unloadedVessels.size();
        this.averageDaysInQueue=this.calculateAverageDaysInQue();
        this.totalPenalty=this.calculateTotalPenalty();
        this.numberOfBulkHandlingCranes=bulkHandlingCranes.size();
        this.numberOfContainerHandlingCranes=containerHandlingCranes.size();
        this.numberOfLiquidHandlingCranes=liquidHandlingCranes.size();
        this.averageQueueLength=this.calculateAverageQueueLength();
        this.averageUnloadingDelay=this.calculateAverageUnloadingDelay();
        this.maxUnloadingDelay=this.calculateMaxUnloadingDelay();

    }

    // Calculate the average time in queue
    private double calculateAverageDaysInQue() {
        IntSummaryStatistics stats=unloadedVessels.stream()
                .mapToInt(v -> v.getDaysInQueue())
                .summaryStatistics();

        return stats.getAverage();
    }

    // Calculate the total penalty for all vessels
    private double calculateTotalPenalty() {
        double result=unloadedVessels.stream()
                .mapToDouble(v->v.getPenalty())
                .sum();

        return result;
    }

    // Calculate average queue length
    private double calculateAverageQueueLength() {
        int counter=0;

        for (Vessel v:unloadedVessels) {
            if (v.getDaysInQueue()!=0) {
                counter++;
            }
        }

        double result=(double)counter/unloadedVessels.size();

        return result;
    }


    // Calculate the average time span between actual arrival date and unloading finish date
    private double calculateAverageUnloadingDelay() {
        int totalDelayAllVessels=0;
        for (Vessel v:unloadedVessels) {
            long daysBetween=calculateDelays(v);
            totalDelayAllVessels=+((int)daysBetween+1);
        }

        double result=(double)totalDelayAllVessels/unloadedVessels.size();

        return result;
    }


    // Calculate the maximum time span between actual arrival date and unloading finish date
    private int calculateMaxUnloadingDelay() {
        ArrayList<Integer> al=new ArrayList<>();

        for (Vessel v:unloadedVessels) {
            long daysBetween=calculateDelays(v);
            al.add((int)daysBetween+1);
        }

        int result=al.stream()
                .mapToInt(v->v)
                .max()
                .orElseThrow(NoSuchElementException::new);

        return result;
    }


    // Auxilliary method to calculate time span in days between two dates
    private long calculateDelays(Vessel v) {
        LocalDateTime date1= v.getActualArrival().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        LocalDateTime date2= v.getUnloadingFinishDay().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        long daysBetween=ChronoUnit.DAYS.between(date1, date2);

        return daysBetween;
    }

    public boolean isEnough() {
        for (Vessel v:unloadedVessels) {
            if (v.getPenalty()>10000) {
                return false;
            }
        }

        return true;
    }

    public void increaseCranes() {
        for (Vessel v:unloadedVessels) {
            if (v.getPenalty()>10_000) {
                switch (v.getCargoType()) {
                    case CONTAINER: this.nContainerCranes++;
                    break;

                    case BULK: this.nBulkCranes++;
                    break;

                    case LIQUID: this.nLiquidCranes++;
                    break;
                }

                break;
            }


        }
    }

    public void reset () {
        for (int i=0; i<unloadedVessels.size(); i++) {
            bulkHandlingCranes.clear();
            liquidHandlingCranes.clear();
            containerHandlingCranes.clear();
            unloadedVessels.clear();
        }
    }
}
