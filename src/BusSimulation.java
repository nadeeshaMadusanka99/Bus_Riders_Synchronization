import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.Random;

public class BusSimulation {
    private static final double BUS_MEAN_ARRIVAL_TIME = 0.5; // 20 minutes
    private static final double RIDER_MEAN_ARRIVAL_TIME = 0.05; // 30 seconds

    public static void main(String[] args) {
        BusStop busStop = new BusStop();
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(100);
        Random random = new Random();

        // Schedule bus arrivals with exponential distribution
        scheduleBusArrivals(executorService, busStop, random);

    }

    private static void scheduleBusArrivals(ScheduledExecutorService executorService, BusStop busStop, Random random) {
        Runnable busScheduler = new Runnable() {
            @Override
            public void run() {
                executorService.execute(new Bus(busStop));
                long delay = (long) (Math.log(1 - random.nextDouble()) / (-1.0 / BUS_MEAN_ARRIVAL_TIME) * 60 * 1000);
                executorService.schedule(this, delay, TimeUnit.MILLISECONDS);
            }
        };
        executorService.submit(busScheduler);
    }

}