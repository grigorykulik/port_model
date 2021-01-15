import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class ScheduleGenerator {
    //Schedule entries that represent vessels
    private ArrayList<Vessel> scheduleEntries=new ArrayList<>();

    public ScheduleGenerator() {

        //Vessel names from text file
        ArrayList<String> vesselNames=new ArrayList<>();

        //Get values from the CargoType enum
        List<CargoType> cargoTypes=Collections.unmodifiableList(Arrays.asList(CargoType.values()));
        Random rand=new Random();

        try {
            //Read all the names from the file and fill the array of names
            Scanner sc=new Scanner(new BufferedReader(new FileReader("input.txt")));

            while (sc.hasNextLine()) {
                vesselNames.add(sc.nextLine());
            }


            for (int i=0; i<10; i++) {
                Vessel se=new Vessel();
                //Select a random name for the vessel
                se.setVesselName(vesselNames.get(rand.nextInt(vesselNames.size())));

                //Select a random cargo type for the vessel
                se.setCargoType(cargoTypes.get(rand.nextInt(3)));

                //Set the date of the expected arrival for the vessel in the range from rigth now to right now + 30
                se.setExpectedArrival(RandomDateGenerator.generateRandomDate());

                //Set a random cargo weight for the bulk carrier and calculate the planned unloading time
                if (se.getCargoType()==CargoType.BULK) {
                    int randomBulkWeigth=ThreadLocalRandom.current().nextInt(10000, 350_001);
                    se.setCargoWeight(randomBulkWeigth);

                    int plannedUnloadingTime=(int)((se.getCargoWeight()/4200/24)+1);
                    se.setPlannedUnloadingTime(plannedUnloadingTime);
                }

                //Set a random cargo weight for the liquid cargo carrier and calculate the planned unloading time
                if (se.getCargoType()==CargoType.LIQUID) {
                    int randomLiquidWeigth=ThreadLocalRandom.current().nextInt(10000, 550_001);
                    se.setCargoWeight(randomLiquidWeigth);

                    int plannedUnlTime=(int)((se.getCargoWeight()/10200/24)+1);
                    se.setPlannedUnloadingTime(plannedUnlTime);
                }

                //Set a random cargo weight for the container carrier and calculate the planned unloading time
                if (se.getCargoType()==CargoType.CONTAINER) {
                    int randomContainerWeigth=ThreadLocalRandom.current().nextInt(5616, 453_601);
                    se.setCargoWeight(randomContainerWeigth);

                    int plannedUnlTime=(int)((se.getCargoWeight()/2040/24)+1);
                    se.setPlannedUnloadingTime(plannedUnlTime);
                }

                //Set a random actual arrival date as expected arrival +/-7 days
                se.setActualArrival(RandomDateGenerator.generateRandomActualArrival(se.getExpectedArrival()));

                //Initialize the number of days it took to unload the vessel
                se.setDaysToUnload(0);

                //Initialize the number of minutes the vessel spent in the queue
                se.setMinsInQueue(0);

                //Set the flag that shows the vessel is not not being unloaded right now
                se.setIsUnloading(false);
                scheduleEntries.add(se);
            }

        }

        catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

    }

    public ArrayList<Vessel> getSchedule() {
        return this.scheduleEntries;
    }
}
