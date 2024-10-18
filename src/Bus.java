import java.util.concurrent.TimeUnit;

public class Bus implements Runnable {
    private BusStop busStop;

    public Bus(BusStop busStop) {
        this.busStop = busStop;
    }

    @Override
    public void run() {
        try {
            System.out.println("Bus is arriving");
            busStop.busArrives();
            // custom boarding time
            TimeUnit.MILLISECONDS.sleep(500);
            busStop.busDeparts();
            System.out.println("Bus is departing");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}