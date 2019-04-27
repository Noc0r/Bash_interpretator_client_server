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
import java.util.ArrayList;

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
    private ArrayList<Client> users = new ArrayList<>();

    public ArrayList<Client> getUsers() {
        return users;
    }

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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    private String answer;

    public Server(ServerInfo info) throws Exception
    {
        super();
        this.info = info;
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

    @Override
    public void addClient(Client client) throws RemoteException {
        System.out.println(client);
        synchronized (users) {
            users.add(client);
        }
        System.out.println(users.size());
        System.out.println(client.getClientId());
    }

    @Override
    public void removeClient(Client client) throws RemoteException {
        System.out.println(client);
        synchronized (users) {
            users.removeIf((c1)->{
                return   c1.getClientId().equals(client.getClientId());
            });
        }
        System.out.println(users.size());
        System.out.println(client.getClientId());
    }

    public void start()throws Exception
    {
        System.setProperty(RMI_HOSTNAME, localhost);
        AdminFunctional admin = (AdminFunctional) Naming.lookup(Configuration.ADMIN_PATH);
        int id =admin.getFreeID();
        Server server = new Server(info);
        admin.createServer(server.info);
        String serviceName = Configuration.SERVER_NAME+id;
        System.out.println("Initializing " + serviceName);
        if(id==1) {
            Registry registry = LocateRegistry.createRegistry(1098);
            registry.rebind(serviceName, server);
        }
        else{
            Registry registry = LocateRegistry.getRegistry(1098);
            registry.rebind(serviceName, server);
        }


        System.out.println("Start " + serviceName);
    }
    public static void main(String[] args) throws Exception
    {
        Server s = new Server(new ServerInfo("Noc0r",2,"1234"));
        s.start();
    }
}
