import java.util.concurrent.Semaphore;

public class BusStop {
    private static final int BUS_CAPACITY = 10;
    private Semaphore waitingRiders = new Semaphore(0);
    private Semaphore busMutex = new Semaphore(1);
    private Semaphore allAboard = new Semaphore(0);
    private int waitingRiderCount = 0;
}