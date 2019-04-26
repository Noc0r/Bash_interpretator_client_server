package GUI;

import GUI.Styles.Style;
import Logic.Client;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;

public class ClientWorkWindow{
    JFrame frame;
    JButton ok = new JButton("Ok");
    JTextPane cmd = new JTextPane();
    Client cl;
    public ClientWorkWindow(String password)
    {
        this();
        inputPassword(password);
    }

    public ClientWorkWindow()
    {
        init();
        events();
        frame.setVisible(true);
    }

    private void inputPassword(String s)
    {
        InputDialog dialog = new InputDialog(frame,s);
        dialog.setVisible(true);
        if(!dialog.correct)
        {
            frame.setVisible(false);
            frame.dispose();
        }
    }
    private void events()
    {
    }

    private void init()
    {
        frame = new JFrame("Client window");
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setBounds(50,50,500,430);
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
        frame.add(cmd);
    }

    public static void main(String[] args)
    {
        EventQueue.invokeLater(()-> {
                ClientWorkWindow window = new ClientWorkWindow("1234");
        });
    }
}
