package Structs;
import java.io.Serializable;
/**
 * host - identifier of server
 * connections - current amount of users
 * maxConnections - max value of users, which can connect to the server
 * password - protection of server. If password equals "", then all people on the LAN can connect to the server
 * mod - The level of access
 * id - identifier of server
 * */

public class ServerInfo implements Serializable
{
    private transient static final long serialVersionUID = 1L;
    private String host;
    private int connections;
    private int maxConnections;
    private int id=1;
    private String password;

    public int getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHost()
    {
        return host;
    }

    public void setHost(String host)
    {
        this.host = host;
    }

    public int getConnections()
    {
        return connections;
    }

    public void setConnections(int connections)
    {
        this.connections = connections;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public ServerInfo(String host,int connections,String password)
    {
        this.maxConnections = connections;
        this.host = host;
        this.password = password;
    }

    @Override
    public String toString()
    {
        String tabs ="          ";
        String pas="Private";
        if(password.equals(""))
            pas = "Open";
        String result="";
        result+=host + tabs.substring(0,tabs.length() - host.length())+"/";
        String conTabs = tabs.substring(0,tabs.length()-
                Integer.toString(connections).length()-
                Integer.toString(maxConnections).length()-1);
        result+=connections+"/"+maxConnections+conTabs+"/";
        String pasTabs = tabs.substring(0,tabs.length()-pas.length());
        result+=pas+pasTabs+"/";
        return result;
    }

    @Override
    public boolean equals(Object info)
    {
        ServerInfo inf = (ServerInfo) info;
        return this.getHost().equals(inf.getHost());
    }
}
