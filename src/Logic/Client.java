package Logic;
import GUI.ClientWorkWindow;
import Logic.Functionals.ServerFunctional;
import Structs.Configuration;

import javax.swing.*;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import static Structs.Configuration.RMI_HOSTNAME;
import static Structs.Configuration.localhost;

/**
 * Client class, organize work with server
 * */

public class Client implements Serializable {
    private transient static final long serialVersionUID = 1L;
    private String clientId="Noc0r";
    private int serverId;
    private ClientWorkWindow gui;

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public ClientWorkWindow getGui() {
        return gui;
    }

    public void setGui(ClientWorkWindow gui) {
        this.gui = gui;
    }

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

    public Client(String clientID,int id,ClientWorkWindow gui)
    {
        clientId=clientID;
        serverId = id;
        this.gui = gui;
    }

    public String request(String commands)
    {
        try {
            System.setProperty(RMI_HOSTNAME, localhost);
            ServerFunctional server = (ServerFunctional)Naming.lookup(
                    Configuration.PATH+":1098/"+Configuration.SERVER_NAME+ serverId);
            return server.send(clientId,commands);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            System.err.println("NotBoundException : " +
                    e.getMessage());
        }
        return "";
    }

    @Override
    public String toString()
    {
        return clientId;
    }
    public static void main(String[] args)
    {
        Client c = new Client("Vasya",1,null);
        c.request("cd /home/noc0r\nls\npwd");
    }
}
