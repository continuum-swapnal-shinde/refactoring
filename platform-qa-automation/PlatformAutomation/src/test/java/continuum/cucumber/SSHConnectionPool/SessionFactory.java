package continuum.cucumber.SSHConnectionPool;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;
import continuum.cucumber.SSHConnectionPool.ConnectionInfo;

import org.apache.commons.pool.BaseKeyedPoolableObjectFactory;
import org.grep4j.core.command.linux.JschUserInfo;

import static continuum.cucumber.PageKafkaExecutorsHelpers.FileManager.getAbsPath;

/**
 * 
 * 
 *This class is used to handle ssh session inside the pool
 */

public class SessionFactory extends BaseKeyedPoolableObjectFactory<ConnectionInfo, Session> {

	/**
	 * This creates a session if not already present in the pool
	 */
    @Override
    public Session makeObject(ConnectionInfo connectionInfo) {
        Session session = null;

        try {
            session = createSessionObject(connectionInfo);
            //            session.connect();
        } catch (Exception e) {
            throw new RuntimeException(
                    "ERROR: Unrecoverable error when trying to connect to :  " + connectionInfo, e);
        }
        return session;
    }

    private Session createSessionObject(ConnectionInfo connectionInfo) throws JSchException {
        Session session;
        JSch jsch = new JSch();

        if (connectionInfo.getPrivateKeyLocation()!=null) {
            jsch.addIdentity(getAbsPath(connectionInfo.getPrivateKeyLocation()));
        }

        session = jsch.getSession(
                connectionInfo.getUserName(),
                connectionInfo.getRemoteAddress(),
                connectionInfo.getRemotePort() != null? connectionInfo.getRemotePort() : 22);
        if (connectionInfo.getUserPassword()!= null) {
            session.setPassword(connectionInfo.getUserPassword());
        }
        session.setConfig("StrictHostKeyChecking", "no"); //
        UserInfo userInfo = new JschUserInfo(connectionInfo.getUserName(), connectionInfo.getUserPassword());

        session.setUserInfo(userInfo);
        session.setTimeout(600000);
        //session.setPassword(serverDetails.getPassword());

        if (connectionInfo.isPortLForwardingEnabled()) {
            session.setPortForwardingL(
                    connectionInfo.getLocalPort(),
                    connectionInfo.getLocalAddress(),
                    connectionInfo.getTargetPort());
        }
        return session;
    }


    /**
     * This is called when closing the pool object
     */
    @Override
    public void destroyObject(ConnectionInfo ConnectionInfo, Session session) {
        session.disconnect();
    }
//
    @Override
    public boolean validateObject(ConnectionInfo key, Session obj) {
        if (!obj.isConnected()) {
            super.validateObject(key, obj);
            return false;
        }
        return true;
    }
//
    @Override
    public void activateObject(ConnectionInfo key, Session session) throws Exception {
//        Session session = createSessionObject(key.getConnectionInfo());
        if (!validateObject(key, session)) {
            session.connect();
        }
        super.activateObject(key, session);
    }

    @Override
    public void passivateObject(ConnectionInfo key, Session obj) throws Exception {
        obj.disconnect();
        super.passivateObject(key, obj);
    }
}