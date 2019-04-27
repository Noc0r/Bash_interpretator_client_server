package GUI;

import GUI.Styles.Style;
import Logic.Client;
import Logic.Functionals.ServerFunctional;
import Structs.Configuration;
import Structs.ServerInfo;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.rmi.Naming;

public class ClientWorkWindow{
    JFrame frame;
    JButton ok = new JButton("Ok");
    JTextPane cmd = new JTextPane();
    ServerInfo serverInfo;
    Client cl;
    public ClientWorkWindow(ServerInfo server)
    {
        serverInfo =server;
        init();
        events();
        frame.setVisible(true);
        inputPassword(server.getPassword());
        check();
    }

    private void inputPassword(String s)
    {
        if(!s.equals("")) {
            InputDialog dialog = new InputDialog(frame, s);
            dialog.setVisible(true);
            if (!dialog.correct) {
                frame.setVisible(false);
                frame.dispose();
            }
        }
    }
    private void check()
    {
        synchronized (serverInfo) {
            System.out.println(serverInfo.getMaxConnections());
            System.out.println(serverInfo.getConnections());
            if (serverInfo.getConnections() < serverInfo.getMaxConnections()) {
                serverInfo.setConnections(serverInfo.getConnections()+1);
                try {
                    ServerFunctional s = (ServerFunctional) Naming.lookup(
                            Configuration.PATH+":1098/"+Configuration.SERVER_NAME+ serverInfo.getId());
                    s.addClient(cl);
                }
                catch (Exception e)
                {
                    JOptionPane.showMessageDialog(frame,e.getMessage());
                }
            }
        }
    }

    private void events() {

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                try {
                    ServerFunctional s = (ServerFunctional) Naming.lookup(
                            Configuration.PATH+":1098/"+Configuration.SERVER_NAME+ serverInfo.getId());
                    s.removeClient(cl);
                }
                catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(frame,ex.getMessage());
                }

            }
        });
        ok.addActionListener((e)->
        {
            int len = cmd.getDocument().getLength();
            cmd.setCaretPosition(len);
            cmd.replaceSelection("\n"+cl.request(cmd.getText()));
        });
    }

    private void init()
    {
        frame = new JFrame("Client window");
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setBounds(50,50,500,430);
        JLabel label = new JLabel("Write commands");
        label.setBounds(180,0,200,30);
        cmd.setBackground(Style.cmd_background);
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet asset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Style.textStyle);
        asset = sc.addAttribute(asset, StyleConstants.FontFamily, "Lucida Console");
        asset = sc.addAttribute(asset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
        int len = cmd.getDocument().getLength();
        cmd.setCaretPosition(len);
        cmd.setCharacterAttributes(asset, false);
        cmd.setBounds(30,30,450,300);
        ok.setBounds(200,350,100,30);
        frame.add(ok);
        frame.add(label);
        frame.add(cmd);
        try {
            cl = new Client(InetAddress.getLocalHost().toString(),serverInfo.getId(),frame);
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(frame,e.getMessage());
        }
    }

    public static void main(String[] args)
    {
        EventQueue.invokeLater(()-> {
                ClientWorkWindow window = new ClientWorkWindow(
                        new ServerInfo("Noc0r",2,"1234"));
        });
    }
}
