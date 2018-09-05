package continuum.cucumber.SSHConnectionPool;

import com.jcraft.jsch.Session;

import java.util.concurrent.ConcurrentHashMap;


/**
 * This helper class to store barrowed connections and return them.
 */
public class SSHConnectionPool {

    private ConcurrentHashMap<String, MappedSessions> mappedSessions;


    private SSHConnectionPool() {
        this.mappedSessions = new ConcurrentHashMap<>();
    }

    private static class SingletonHolder {
        public static final SSHConnectionPool INSTANCE = new SSHConnectionPool();
    }

    public static SSHConnectionPool sshConnectionPool() {
        return SingletonHolder.INSTANCE;
    }

    private class MappedSessions {
        ConnectionInfo connectionInfo;
        Session session;
        MappedSessions(ConnectionInfo connectionInfo, Session session) {
            this.connectionInfo = connectionInfo;
            this.session = session;
        }
    }

    public void addConnection(String alias, ConnectionInfo connectionInfo) {
        this.mappedSessions.put(alias, new MappedSessions(connectionInfo, null));
    }

    public Session barrowConnection(String alias) {
        MappedSessions mappedSession = getConnectionInfo(alias);

        try {
            Session barrowedSession = StackSessionPool
                    .getInstance()
                    .getPool()
                    .borrowObject(mappedSession.connectionInfo);

            mappedSession.session = barrowedSession;
            return barrowedSession;
        } catch (Exception e) {
        	System.err.println(e.getMessage());
        }
        return null;
    }

    public void returnConnection(String alias) {
        MappedSessions barrowedSession = getConnectionInfo(alias);
        try {
            StackSessionPool.getInstance().getPool()
                    .returnObject(barrowedSession.connectionInfo, barrowedSession.session);
        } catch (Exception e) {
        	System.err.println(e.getMessage());
        }

    }

    private MappedSessions getConnectionInfo(String alias) {
        return mappedSessions.get(alias);
    }

    public void closeConnectionPool() {
        try {
            StackSessionPool.getInstance().getPool().close();
        } catch (Exception e) {
        	System.err.println(e.getMessage());
        }
    }

}
