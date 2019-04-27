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
import java.io.Serializable;
import java.net.InetAddress;
import java.rmi.Naming;

public class ClientWorkWindow implements Serializable {
    private transient static final long serialVersionUID = 1L;
    private JFrame frame;
    private JButton ok = new JButton("Request");
    private JTextPane cmd = new JTextPane();
    private JTextPane output = new JTextPane();
    private ServerInfo serverInfo;
    private Client cl;

    public JFrame getFrame() {
        return frame;
    }

    public ClientWorkWindow(ServerInfo server)
    {
        serverInfo = server;
        init();
        events();
        inputPassword(server.getPassword());
        if(!check()){
            frame.dispose();
        }
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
    private boolean check()
    {
        try {
            ServerFunctional s = (ServerFunctional) Naming.lookup(
                    Configuration.PATH+":1098/"+Configuration.SERVER_NAME+ serverInfo.getId());
            if(!s.addClient(cl))
            {
                JOptionPane.showMessageDialog(frame,"Max users on the server");
                return false;
            }
            return true;
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(frame,e.getMessage());
        }
        return false;
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
            output.selectAll();
            output.replaceSelection(cl.request(cmd.getText()));
        });
    }

    private void init()
    {
        frame = new JFrame("Client window");
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setBounds(50,50,1000,430);
        frame.setVisible(true);
        JLabel label = new JLabel("Write commands");
        label.setBounds(180,0,200,30);
        JLabel label1 = new JLabel("Output");
        label1.setBounds(720,0,200,30);
        cmd.setBackground(Style.cmd_background);
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet asset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Style.textStyle);
        asset = sc.addAttribute(asset, StyleConstants.FontFamily, "Lucida Console");
        asset = sc.addAttribute(asset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
        int len = cmd.getDocument().getLength();
        cmd.setCaretPosition(len);
        cmd.setCharacterAttributes(asset, false);
        cmd.setBounds(30,30,450,300);

        AttributeSet asset1 = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Style.textOutStyle);
        asset1 = sc.addAttribute(asset1, StyleConstants.FontFamily, "Lucida Console");
        asset1 = sc.addAttribute(asset1, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
        int len1 = output.getDocument().getLength();
        output.setCaretPosition(len1);
        output.setCharacterAttributes(asset1, false);
        output.setBounds(520,30,450,300);
        ok.setBounds(450,350,100,30);
        frame.add(ok);
        frame.add(label);
        frame.add(label1);
        frame.add(cmd);
        frame.add(output);
        try {
            cl = new Client(InetAddress.getLocalHost().toString(),serverInfo.getId(),this);
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
