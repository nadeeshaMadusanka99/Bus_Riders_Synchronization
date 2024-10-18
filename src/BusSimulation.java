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

        // Schedule rider arrivals with exponential distribution
        scheduleRiderArrivals(executorService, busStop, random);

        // Run simulation for a specific duration
        try {
            TimeUnit.MINUTES.sleep(2);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        try {
            // Wait for all tasks to complete after shutdown request
            if (!executorService.awaitTermination(1, TimeUnit.MINUTES)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }

        System.out.println("All tasks completed, Simulation ended");

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

    private static void scheduleRiderArrivals(ScheduledExecutorService executorService, BusStop busStop,
            Random random) {
        Runnable riderScheduler = new Runnable() {
            @Override
            public void run() {
                executorService.execute(new Rider(busStop));
                long delay = (long) (Math.log(1 - random.nextDouble()) / (-1.0 / RIDER_MEAN_ARRIVAL_TIME) * 60 * 1000);
                executorService.schedule(this, delay, TimeUnit.MILLISECONDS);
            }
        };
        executorService.submit(riderScheduler);
    }

}