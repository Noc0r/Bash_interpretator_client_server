package Logic;

import Logic.Functionals.AdminFunctional;
import Logic.Functionals.ServerFunctional;
import Structs.Configuration;
import Structs.ServerInfo;

import javax.swing.*;
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
    public String send(String clientID, String commands)
    {
        try
        {
            for (Client cl:users) {
                if(cl.getClientId().equals(clientID))
                    return executor.executeCommands(commands);
            }
            return "You don't have access to the server";
        }
        catch (IOException|InterruptedException e)
        {
            e.printStackTrace();
        }
        return "";
    }

    private void updateAdmin()
    {
        try {
            AdminFunctional admin = (AdminFunctional) Naming.lookup(Configuration.ADMIN_PATH);
            for(ServerInfo s:admin.getList())
            {
                if(s.getId() == info.getId())
                {
                    admin.getList().remove(s);
                    s.setConnections(info.getConnections());
                    admin.getList().add(s);
                }
            }
        }catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null,ex.getMessage());
        }
    }

    @Override
    public boolean addClient(Client client) {
        System.out.println(client);
        if(users.size()<info.getMaxConnections()) {
            synchronized (users) {
                users.add(client);
                info.setConnections(info.getConnections() + 1);
            }
            updateAdmin();
            System.out.println(users.size());
            System.out.println(client.getClientId());
            return true;
        }
        return false;
    }

    @Override
    public void removeClient(Client client) {
        System.out.println(client);
        synchronized (users) {
            users.removeIf((c1)->{
                return   c1.getClientId().equals(client.getClientId());
            });
            info.setConnections(info.getConnections()-1);
        }
        updateAdmin();
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
