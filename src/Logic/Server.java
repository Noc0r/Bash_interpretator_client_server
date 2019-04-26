package Logic;

import Logic.Functionals.AdminFunctional;
import Logic.Functionals.ServerFunctional;
import Structs.Configuration;
import Structs.ServerInfo;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import static Structs.Configuration.localhost;
import static Structs.Configuration.RMI_HOSTNAME;

/**
 * The start of RMI connection between Clients
 * This class allow organize remote work with bash
 *
 * */
public class Server extends UnicastRemoteObject implements ServerFunctional, Serializable
{
    private transient static final long serialVersionUID = 1L;
    private BashExecutor executor = new BashExecutor();
    private ServerInfo info;
    private String clientID;

    public BashExecutor getExecutor() {
        return executor;
    }

    public void setExecutor(BashExecutor executor) {
        this.executor = executor;
    }

    public ServerInfo getInfo() {
        return info;
    }

    public void setInfo(ServerInfo info) {
        this.info = info;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    private String answer;

    public Server(String host, int connections, String password) throws Exception
    {
        super();
        info = new ServerInfo(host,connections,password);
    }

    @Override
    public String send(String clientID, String commands) throws RemoteException
    {
        try
        {
            return executor.executeCommands(commands);
        }
        catch (IOException|InterruptedException e)
        {
            e.printStackTrace();
        }
        return "";
    }

    public void start()throws Exception
    {
        System.setProperty(RMI_HOSTNAME, localhost);
        AdminFunctional admin = (AdminFunctional) Naming.lookup(Configuration.ADMIN_PATH);
        int id =admin.getFreeID();
        Server server = new Server(info.getHost(),info.getConnections(),info.getPassword());
        server.info.setId(id);
        admin.createServer(server.info);
        String serviceName = Configuration.SERVER_NAME+id;
        System.out.println("Initializing " + serviceName);
        Registry registry = LocateRegistry.createRegistry(1098);
        registry.rebind(serviceName,server);
        System.out.println("Start " + serviceName);
    }
    public static void main(String[] args) throws Exception
    {
        Server s = new Server("Noc0r",2,"1234");
        s.start();
    }
}
