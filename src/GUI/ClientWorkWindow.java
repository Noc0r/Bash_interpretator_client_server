package GUI;

import GUI.Styles.Style;

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
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Style.textStyle);
        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
        int len = cmd.getDocument().getLength();
        cmd.setCaretPosition(len);
        cmd.setCharacterAttributes(aset, false);
        cmd.setBounds(30,30,450,300);
        cmd.replaceSelection(">Start work\n");
        ok.setBounds(200,350,100,30);
        frame.add(ok);
        frame.add(cmd);
    }

    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                ClientWorkWindow window = new ClientWorkWindow("1234");
                /*if(args.length!=0)
                    window = new ClientWorkWindow(args[0]);
                else
                    window = new ClientWorkWindow();*/
            }
        });
    }
}
