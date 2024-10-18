import java.util.concurrent.Semaphore;

public class BusStop {
    private static final int BUS_CAPACITY = 50;
    private Semaphore waitingRiders = new Semaphore(0);
    private Semaphore busMutex = new Semaphore(1);
    private Semaphore allAboard = new Semaphore(0);
    private int waitingRiderCount = 0;

    public synchronized void riderArrives() throws InterruptedException {
        waitingRiderCount++;
        System.out.println("BusStop: Rider arrived. Waiting riders: " + waitingRiderCount);
    }

    // Rider boarding process
    public void boardBus() throws InterruptedException {
        waitingRiders.acquire();
        allAboard.release();
    }

    public void busArrives() throws InterruptedException {
        busMutex.acquire();
        int boarding = Math.min(waitingRiderCount, BUS_CAPACITY);
        System.out.println("Bus: Boarding " + boarding + " riders");

        for (int i = 0; i < boarding; i++) {
            // Release waiting riders one by one
            waitingRiders.release();
        }

        for (int i = 0; i < boarding; i++) {
            // Wait for all riders to board
            allAboard.acquire();
        }

        waitingRiderCount -= boarding;
    }

    public void busDeparts() {
        busMutex.release();
    }
}