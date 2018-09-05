package continuum.cucumber.PageKafkaExecutorsHelpers;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecutorHelper {

    public static boolean stopExecutor(ExecutorService executor) {
        boolean isFinished = false;
        try {
            executor.shutdown();
            final boolean done = executor.awaitTermination(10, TimeUnit.SECONDS);
            System.out.println("is everything was executed? {}"+ done);
            isFinished = done;
        } catch (InterruptedException e) {
            System.err.println("termination interrupted");
        } finally {
            if (!executor.isTerminated()) {
            	System.out.println("killing non-finished tasks");
            }
            final List<Runnable> rejected = executor.shutdownNow();
            System.out.println("rejected: {}" + rejected.size());
            isFinished = rejected.isEmpty();
        }
        return isFinished;
    }
}
