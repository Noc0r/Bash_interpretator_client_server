package GUI;

import GUI.Styles.Style;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GraphicsMenu
{
    private JFrame frame;
    private JMenuBar menuBar;
    private JMenu settings;
    private JMenu info;
    private JMenuItem active;
    private JMenuItem list;
    private JMenuItem quit;
    private JMenuItem clientSettings;
    private JMenuItem serverSettings;
    private JButton clientButton;
    private JButton serverButton;
    private JLabel menuLabel;
    private JButton quitButton;

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public GraphicsMenu()
    {
        init();
        events();
    }

    private void events()
    {
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int res =JOptionPane.showConfirmDialog(frame,"Are you sure?","Accept quit",2);
                if(res == JOptionPane.OK_OPTION)
                    frame.dispose();
            }
        });
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quit.doClick();
            }
        });
        clientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GraphicsClient g = new GraphicsClient();

            }
        });
    }

    private void init()
    {
        frame = new JFrame("Quite Fox(remote)");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        menuBar = new JMenuBar();
        frame.getContentPane().setBackground(Style.background);
        frame.getContentPane().setLayout(null);
        frame.setJMenuBar(menuBar);
        settings = new JMenu("Settings");
        info = new JMenu("Information");
        menuBar.add(info);
        menuBar.add(settings);
        active = new JMenuItem("Active connections");
        list = new JMenuItem("Active users");
        quit = new JMenuItem("Quit");
        info.add(active);
        info.add(list);
        info.add(quit);
        clientSettings = new JMenuItem("Audio");
        serverSettings = new JMenuItem("Styles");
        settings.add(clientSettings);
        settings.add(serverSettings);
        menuLabel = new JLabel("Menu");
        menuLabel.setBounds(290,70,100,30);
        clientButton = new JButton("Client");
        serverButton = new JButton("Server");
        quitButton = new JButton("Quit");
        clientButton.setBounds(160,140,100,30);
        serverButton.setBounds(360,140,100,30);
        quitButton.setBounds(260,220,100,30);
        frame.getContentPane().add(clientButton);
        frame.getContentPane().add(serverButton);
        frame.getContentPane().add(quitButton);
        frame.getContentPane().add(menuLabel);
        frame.setSize(600,400);
        frame.setResizable(false);
    }

    public static void main(String[] args)
    {
        GraphicsMenu menu = new GraphicsMenu();
        menu.frame.setVisible(true);
    }
}
