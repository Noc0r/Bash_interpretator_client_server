package Logic;
import Logic.Functionals.ServerFunctional;
import Structs.Configuration;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import static Structs.Configuration.RMI_HOSTNAME;
import static Structs.Configuration.localhost;

/**
 * Client class, organize work with server
 * */

public class Client{
    private String clientId="Noc0r";
    private int serverId;

    public String getClientId()
    {
        return clientId;
    }
    public void setClientId(String clientId)
    {
        this.clientId = clientId;
    }

    public Client()
    {
    }
    public Client(String clientID,int id)
    {
        clientId=clientID;
        serverId = id;
    }
    public void request(String commands)
    {
        try {
            System.setProperty(RMI_HOSTNAME, localhost);
            ServerFunctional server = (ServerFunctional)Naming.lookup(
                    Configuration.PATH+":1098/"+Configuration.SERVER_NAME+ serverId);
            System.out.println(server.send(clientId,commands));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            System.err.println("NotBoundException : " +
                    e.getMessage());
        }
    }
    public static void main(String[] args)
    {
        Client c = new Client();
        c.request("cd /home/noc0r\nls\npwd");
    }
}
