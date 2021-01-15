import java.util.ArrayList;

public class ReportGenerator {
    public static void printSchedule(ArrayList<Vessel> se) {
        System.out.println("---------------------------------------------------------GENERATED SCHEDULE----" +
                "--------------------------------------------------------------------");
        System.out.printf("%-30s%-30s%-30s%-30s%s",
                "EXPECTED ARRIVAL", "VESSEL NAME",
                "CARGO TYPE", "CARGO WEIGHT", "PLANNED UNLOADING TIME, D");
        System.out.println();
        System.out.println("--------------------------------------------------------------------------------------------" +
                "-------------------------------------------------------");

        for (int i = 0; i < se.size(); i++) {
            System.out.printf("%-30s%-30s%-30s%-30s%s", se.get(i).getExpectedArrival(),
                    se.get(i).getVesselName(), se.get(i).getCargoType(),
                    se.get(i).getCargoWeight(), se.get(i).getDuration());
            System.out.println();
        }

        System.out.println();
    }

        public static void printVessels(ArrayList<Vessel> v) {


            System.out.println("---------------------------------------------------------ACTUAL ARRIVALS-------------" +
                    "---------------------------------------------------------------------------------------------");
            System.out.printf("%-30s%-30s%-30s%-30s%-30s%s",
                    "EXPECTED ARRIVAL", "ACTUAL ARRIVAL", "VESSEL NAME",
                    "CARGO TYPE", "CARGO WEIGHT", "PLANNED UNLOADING TIME, D");
            System.out.println();
            System.out.println("----------------------------------------------------------------" +
                    "---------------------------------------------------------------------------" +
                    "---------------------------------------");
            for (int i=0; i< v.size(); i++) {
                System.out.printf("%-30s%-30s%-30s%-30s%-30s%s",
                        v.get(i).getExpectedArrival(), v.get(i).getActualArrival(),
                        v.get(i).getVesselName(), v.get(i).getCargoType(),
                        v.get(i).getCargoWeight(), v.get(i).getDuration());
                System.out.println();
            }
        }

        public static void printUnloadedVessels(ArrayList<Vessel> v)
        {
            System.out.println();
            System.out.println("---------------------------------------------------------UNLOADING-------------" +
                    "------------------------------------------------------------------------------------------" +
                    "------------------------------------------------------------------------------------------" +
                    "------------------------------------------------------------------------------------------" +
                    "------------------------------------------------------------------------------------------");
            System.out.printf("%-40s%-40s%-40s%-40s%-40s%-40s%-40s%-40s%-40s%s",
                    "EXPECTED ARRIVAL", "ACTUAL ARRIVAL", "VESSEL NAME",
                    "CARGO TYPE", "PLANNED UNLOADING TIME, D",
                    "ACT. DAYS TO UNLOAD", "UNLOADING STARTED ON",
                    "UNLOADING FINISHED ON", "DAYS IN QUEUE", "PENALTY");
            System.out.println();
            System.out.println("----------------------------------------------------------------" +
                    "---------------------------------------------------------------------------" +
                    "---------------------------------------------------------------------------" +
                    "---------------------------------------------------------------------------" +
                    "---------------------------------------------------------------------------" +
                    "---------------------------------------------------------------------------");
            for (int i=0; i< v.size(); i++) {
                System.out.printf("%-40s%-40s%-40s%-40s%-40s%-40s%-40s%-40s%-40s%s",
                        v.get(i).getExpectedArrival(), v.get(i).getActualArrival(),
                        v.get(i).getVesselName(), v.get(i).getCargoType(),
                        v.get(i).getDuration(), v.get(i).getDaysToUnload(),
                        v.get(i).getUnloadingStartDay(), v.get(i).getUnloadingFinishDay(),
                        v.get(i).getDaysInQueue(), v.get(i).getPenalty());
                System.out.println();
            }
        }

        public static void printFinalStats(Port p) {
            System.out.println();
            System.out.println("--------------------------------------------------------FINAL STATS-------------" +
                    "---------------------------");
            System.out.println("TOTAL NUMBER OF UNLOADED VESSELS: " + p.getNumberOfUnloadedVessels());
            System.out.println("AVERAGE DAYS IN QUEUE: " + p.getAverageDaysInQueue());
            System.out.println("TOTAL PENALTY: " + p.getTotalPenalty());
            System.out.println("BULK HANDLING CRANES: " + p.getNumberOfBulkHandlingCranes());
            System.out.println("CONTAINER HANDLING CRANES: " + p.getNumberOfContainerHandlingCranes());
            System.out.println("LIQUID HANDLING CRANES: " + p.getNumberOfLiquidHandlingCranes());
            System.out.println("AVERAGE QUEUE LENGTH: " + p.getAverageQueueLength());
            System.out.println("AVERAGE UNLOADING DELAY: " + p.getAverageUnloadingDelay());
            System.out.println("MAXIMUM DELAY BEFORE UNLOADING: " + p.getMaxUnloadingDelay());
        }
}
