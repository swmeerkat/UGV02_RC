package org.example.gui;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MainWindow {

  public JTextArea console;

  public MainWindow() {
    JFrame frame = new JFrame("UGV02");
    JScrollPane consoleArea = createConsoleArea();
    frame.getContentPane().add(consoleArea, "South");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setForeground(Color.LIGHT_GRAY);
    frame.setBackground(Color.DARK_GRAY);
    frame.addWindowListener(
        new WindowAdapter() {
          public void windowClosing(WindowEvent e) {
            Object[] possibleValues = {"Yes", "No"};
            int option = JOptionPane.showOptionDialog(
                frame,
                "Close UGV02?",
                "UGV02",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                possibleValues,
                possibleValues[0]);
            if (option == JOptionPane.YES_OPTION) {
              System.exit(0);
            }
          }
        }
    );
    //frame.pack();
    frame.setSize(800, 600);
    frame.setVisible(true);
  }

  private JScrollPane createConsoleArea() {
    this.console = new JTextArea(10,60);
    this.console.setEditable(false);
    this.console.setBackground(Color.DARK_GRAY);
    this.console.setForeground(Color.GREEN);
    return new JScrollPane(console);
  }
}
