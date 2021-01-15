import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomDateGenerator {
    public static Date generateRandomDate() {
        GregorianCalendar gc=new GregorianCalendar();
        Random random=new Random();
        int randDay=random.nextInt(2);
        int randHour=random.nextInt(12);
        int randMinute=random.nextInt(60);
        gc.add(Calendar.DAY_OF_MONTH, randDay);
        gc.add(Calendar.HOUR, randHour);
        gc.add(Calendar.MINUTE, randMinute);
        return gc.getTime();
    }

    public static Date generateRandomActualArrival(Date d) {
        GregorianCalendar gc=new GregorianCalendar();
        gc.setTime(d);
        Random r=new Random();

        int randomDeviation=(int)Math.round(r.nextGaussian()*3.5);
        if (randomDeviation>7) randomDeviation=7;
        if (randomDeviation<-7) randomDeviation=-7;

        gc.add(Calendar.DAY_OF_MONTH, randomDeviation);
        return gc.getTime();
    }

    public static Date generateRandomDelay(Date d) {
        GregorianCalendar gc=new GregorianCalendar();
        gc.setTime(d);

        int max=10;
        int min=0;
        int range = max - min + 1;

        int rand = (int)(Math.random() * range) + min;
        gc.add(Calendar.DAY_OF_MONTH, rand);
        return gc.getTime();
    }
}
