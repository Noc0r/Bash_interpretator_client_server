package GUI;

import GUI.Styles.Style;
import Logic.Administrator;
import Logic.Client;
import Logic.Functionals.AdminFunctional;
import Structs.Configuration;
import Structs.ServerInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;

import static Structs.Configuration.RMI_HOSTNAME;
import static Structs.Configuration.localhost;

public class GraphicsClient
{
    private JFrame frame;
    private JList<ServerInfo> servers;
    private DefaultListModel<ServerInfo> model = new DefaultListModel<>();
    private JButton update = new JButton("Update");
    private JButton connect = new JButton("Connect");

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public GraphicsClient()
    {
        init();
        events();
        updateList();
    }
    private  void events()
    {
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateList();
                System.out.println(model.size());
            }
        });

        connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EventQueue.invokeLater(()-> {
                        if(!servers.isSelectionEmpty())
                        {
                            ClientWorkWindow window = new ClientWorkWindow(servers.getSelectedValue().getPassword());
                        }
                });
            }
        });
    }
    private void init()
    {
        System.setProperty(RMI_HOSTNAME, localhost);
        frame = new JFrame("Enabled servers");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setBackground(Style.background);
        frame.setLayout(null);
        frame.setResizable(false);
        servers = new JList<>(model);
        JLabel listLabel = new JLabel("Servers");
        JScrollPane scrollPane_2 = new JScrollPane();
        scrollPane_2.setBounds(30, 40, 260, 250);
        frame.add(scrollPane_2);
        servers.setToolTipText("");
        servers.setFont(new Font("Serif", Font.BOLD,12));
        servers.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        servers.setVisibleRowCount(1000);
        servers.setBounds(105, 105, 1, 1);
        scrollPane_2.setViewportView(servers);
        update.setBounds(110,330,100,30);
        connect.setBounds(110,390,100,30);
        frame.add(update);
        frame.add(connect);
        frame.setBounds(50,50,320,500);
    }

    private void updateList()
    {
        try {
            AdminFunctional admin = (AdminFunctional) Naming.lookup(Configuration.ADMIN_PATH);
            model.clear();
            model.addAll(admin.getList());
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(frame,ex.getMessage());
        }
    }

    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                GraphicsClient cl = new GraphicsClient();
                cl.frame.setVisible(true);
            }
        });
    }
}
