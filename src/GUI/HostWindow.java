package GUI;

import GUI.Styles.Style;
import Logic.Client;
import Logic.Functionals.AdminFunctional;
import Logic.Functionals.ServerFunctional;
import Structs.Configuration;
import Structs.ServerInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.rmi.Naming;

import static Structs.Configuration.RMI_HOSTNAME;
import static Structs.Configuration.localhost;

/**
 * Frame for control clients by user-host
 *
 * */
public class HostWindow {
    private JFrame frame;
    private ServerInfo host;
    private JList<Client> userList;
    private DefaultListModel<Client> model = new DefaultListModel<>();
    private JButton update = new JButton("Update");
    private JButton kick = new JButton("Kick");

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public HostWindow(ServerInfo host)
    {
        this.host = host;
        System.out.println(host.getId());
        init();
        events();
        updateList();
    }
    private  void events()
    {

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                try {
                    AdminFunctional adm = (AdminFunctional)Naming.lookup(Configuration.ADMIN_PATH);
                    adm.removeServer(host);
                    System.out.println(adm.getList().size());
                }
                catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(frame,ex.getMessage());
                }
            }
        });

        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateList();
                System.out.println(model.size());
            }
        });

        kick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EventQueue.invokeLater(()-> {
                    if(!userList.isSelectionEmpty())
                    {
                        try {
                            Client cl =userList.getSelectedValue();
                            ClientWorkWindow fr = cl.getGui();
                            JOptionPane.showMessageDialog(fr.getFrame(),"You was kicked by the server");
                            ServerFunctional s = (ServerFunctional) Naming.lookup(
                                    Configuration.PATH+":1098/"+Configuration.SERVER_NAME+ host.getId());
                            s.removeClient(cl);
                            updateList();
                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(frame,ex.getStackTrace());
                        }

                    }
                });
            }
        });
    }

    private void init()
    {
        System.setProperty(RMI_HOSTNAME, localhost);
        frame = new JFrame("Server Controller");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setBackground(Style.background);
        frame.setLayout(null);
        frame.setResizable(false);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(30, 40, 260, 250);
        frame.add(scrollPane);
        userList = new JList<>(model);
        JLabel listLabel = new JLabel("Users");
        listLabel.setBounds(140,10,200,30);
        userList.setToolTipText("");
        userList.setFont(new Font("Serif", Font.BOLD,12));
        userList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        userList.setVisibleRowCount(1000);
        userList.setBounds(105, 105, 1, 1);
        update.setBounds(110,330,100,30);
        kick.setBounds(110,390,100,30);
        scrollPane.setViewportView(userList);
        frame.add(listLabel);
        frame.add(update);
        frame.add(kick);
        frame.setBounds(50,50,320,500);
    }

    private void updateList()
    {
        try {
            model.clear();
            ServerFunctional s = (ServerFunctional) Naming.lookup(
                    Configuration.PATH+":1098/"+Configuration.SERVER_NAME+ host.getId());
            model.addAll(s.getUsers());
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(frame,ex.getMessage());
        }
    }
    public static void main(String[] args)
    {
        try {
            HostWindow cl = new HostWindow(new ServerInfo("Noc0r",2,"1234"));
            cl.frame.setVisible(true);
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }
}
