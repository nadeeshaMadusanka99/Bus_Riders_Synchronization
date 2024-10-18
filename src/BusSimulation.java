import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.Random;

public class BusSimulation {
    private static final double BUS_MEAN_ARRIVAL_TIME = 0.5; // 20 minutes
    private static final double RIDER_MEAN_ARRIVAL_TIME = 0.05; // 30 seconds

    public static void main(String[] args) {
        BusStop busStop = new BusStop();
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(100);
        Random random = new Random();

    }
}