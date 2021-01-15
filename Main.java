import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {

        ScheduleGenerator sg=new ScheduleGenerator();
        ReportGenerator.printVessels(sg.getSchedule());

        Port port = new Port();
        port.createCranes();

        ArrayList<Vessel> test=new ArrayList<>(sg.getSchedule());
        port.distributeVessels(test);


        port.unloadVessels(sg.getSchedule());
        ReportGenerator.printUnloadedVessels(port.unloadedVessels);

        //Collect statistics
        port.getStatistics();

        //Print statistics
        ReportGenerator.printFinalStats(port);
    }
}
