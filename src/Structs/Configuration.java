package Structs;

public interface Configuration {
    String localhost    = "192.168.0.105";
    String RMI_HOSTNAME = "java.rmi.server.hostname";
    String ADMIN_NAME = "Admin";
    String SERVER_NAME = "Server";
    String PATH = "rmi://"+localhost;
    String ADMIN_PATH = PATH + "/"+ADMIN_NAME;
    String SERVER_PATH = PATH + "/"+SERVER_NAME;
}
