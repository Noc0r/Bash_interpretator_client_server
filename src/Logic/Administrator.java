package Logic;
import Logic.Functionals.AdminFunctional;
import Structs.Configuration;
import Structs.ServerInfo;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import java.util.ArrayList;

import static Structs.Configuration.localhost;
import static Structs.Configuration.RMI_HOSTNAME;


public class Administrator extends UnicastRemoteObject implements AdminFunctional, Serializable {
    private ArrayList<ServerInfo> list = new ArrayList<>();
    private Integer id=0;
    private transient static final long serialVersionUID = 1L;

    public ArrayList<ServerInfo> getList() throws RemoteException{
        synchronized (list) {
            return list;
        }
    }

    public boolean createServer(ServerInfo server) throws RemoteException {
        synchronized (list) {
            System.out.println("Add server by "+server.getHost());
            System.out.println(server);
            return list.add(server);
        }
    }

    public Administrator() throws RemoteException
    {
        super();
    }

    @Override
    public boolean removeServer(ServerInfo server) throws RemoteException {
        synchronized (list) {
            return list.remove(server);
        }
    }
    @Override
    public int getFreeID() throws RemoteException
    {
        synchronized (id)
        {
            id++;
            return id;
        }
    }

    public static void main(String[] args) throws Exception
    {
        try {
            System.setProperty(RMI_HOSTNAME, localhost);
            Administrator admin= new Administrator();
            String serviceName = Configuration.ADMIN_NAME;
            System.out.println("Initializing " + serviceName);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind(serviceName, admin);
            System.out.println("Start " + serviceName);
        } catch (RemoteException e) {
            System.err.println("RemoteException : "+e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Exception : " + e.getMessage());
            System.exit(2);
        }
    }
}
