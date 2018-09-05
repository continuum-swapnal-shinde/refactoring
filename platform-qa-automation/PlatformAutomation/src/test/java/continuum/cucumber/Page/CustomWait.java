package continuum.cucumber.Page;

import java.util.concurrent.atomic.AtomicBoolean;

import org.awaitility.Duration;
import org.awaitility.core.ConditionFactory;
import static org.awaitility.Awaitility.await;

import java.util.concurrent.TimeUnit;

public class CustomWait {


    public static final ConditionFactory WAIT = await()
            .atMost(new Duration(30, TimeUnit.SECONDS))
            .pollInterval(Duration.ONE_SECOND)
            .pollDelay(Duration.ONE_SECOND);

    public static void executeWait(ConditionFactory condition) {
        AtomicBoolean finished = new AtomicBoolean(false);
        new Thread(() -> {
            try {
                finished.set(true);
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
        }).run();
        condition.untilTrue(finished);
    }

    /**
     * Set sleep wait
     * @param seconds
     */
    public static void sleep(int seconds){

        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            //System.out.println(e.getMessage());
        }
    }
    
    public static void customWaitForPolling(int atmost){
    ConditionFactory condition = await()
            .pollDelay(new Duration(10, TimeUnit.SECONDS))
            .atMost(new Duration(atmost, TimeUnit.SECONDS))
            ;
    CustomWait.executeWait(condition);
    }
    
    
}
