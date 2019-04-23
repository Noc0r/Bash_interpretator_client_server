package Structs;

/**
 * host - identifier of server
 * connections - max value of users, which can connect to the server
 * password - protection of server. If password equals "", then all people on the LAN can connect to the server
 * mod - The level of access
 * */

public class ServerInfo
{
    private String host;
    private int connections;
    private String password;
    private SecurityLevel mod;

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

    public SecurityLevel getMod()
    {
        return mod;
    }

    public void setMod(SecurityLevel mod)
    {
        this.mod = mod;
    }

    public ServerInfo(String host,int connections,String password,SecurityLevel mod)
    {
        this.connections = connections;
        this.mod = mod;
        this.host = host;
        this.password = password;
    }

    /*public ServerInfo(String host,int connections,String password)
    {
        ServerInfo(host,connections,password,SecurityLevel.NORMAL);
    }*/
}
