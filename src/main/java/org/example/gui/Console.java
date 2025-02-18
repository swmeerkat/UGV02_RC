package org.example.gui;

import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Console {

  private JTextArea console;

  public JScrollPane createConsoleArea() {
    this.console = new JTextArea(10,60);
    this.console.setEditable(false);
    this.console.setBackground(Color.DARK_GRAY);
    this.console.setForeground(Color.GREEN);
    return new JScrollPane(console);
  }

  public void append(String text) {
    console.append(text);
  }
}
