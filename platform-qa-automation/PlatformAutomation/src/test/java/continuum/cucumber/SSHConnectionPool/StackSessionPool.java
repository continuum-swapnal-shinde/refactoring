package continuum.cucumber.SSHConnectionPool;

import org.apache.commons.pool.KeyedObjectPool;
import org.apache.commons.pool.impl.StackKeyedObjectPool;

import com.jcraft.jsch.Session;



public class StackSessionPool {

    private KeyedObjectPool<ConnectionInfo, Session> pool;

    private StackSessionPool()
    {
        startPool();
    }

    private static class SingletonHolder {
        private SingletonHolder() {
            //empty
        }
        static final StackSessionPool INSTANCE = new StackSessionPool();
    }

    public static StackSessionPool getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     *
     * @return the org.apache.commons.pool.KeyedObjectPool class
     */
    public KeyedObjectPool<ConnectionInfo, Session> getPool() {
        return pool;
    }

    /**
     *
     * @return the org.apache.commons.pool.KeyedObjectPool class
     */
    public void startPool() {
        pool = new StackKeyedObjectPool<>(new SessionFactory(), 10);
    }
}