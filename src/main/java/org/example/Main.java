package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import lombok.extern.slf4j.Slf4j;
import org.example.clients.esp32.ESP32Client;
import org.example.gui.Console;

@Slf4j
public class Main extends JFrame implements KeyListener {

    static Console console;
    ESP32Client ugv02;

  public static void main(String[] args) {
    new Main("UGV02");
  }

  public Main(String name) {
    super(name);
    createAndShowGUI();
    final Properties properties = loadProperties();
    String host = properties.get("UGV02.host").toString();
    this.ugv02 = initUgv02Client(host);
  }

  private Properties loadProperties() {
    Properties properties = new Properties();
    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    InputStream stream = loader.getResourceAsStream("application.properties");
    try {
      properties.load(stream);
    } catch (IOException e) {
      log.error(e.getMessage());
      throw new RuntimeException(e);
    }
    return properties;
  }

  private ESP32Client initUgv02Client(String host) {
    ESP32Client ugv02 = new ESP32Client(host);
    console.append("ugv02 host: " + host + "\n");
    JsonNode response = ugv02.cmd_gimbal_ctrl_simple(0, 0);
    console.append("cmd_gimbal_ctrl_simple(0, 0): " + response.toString() + "\n");
    //ugv02.cmd_speed_control(0.1f, 0.1f);
    return ugv02;
  }

  public void createAndShowGUI(){
    console = new Console();
    JScrollPane consoleArea = console.createConsoleArea();
    getContentPane().add(consoleArea, "South");
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    setForeground(Color.LIGHT_GRAY);
    setBackground(Color.DARK_GRAY);
    setFocusable(true);
    addKeyListener(this);
    addWindowListener(
        new WindowAdapter() {
          @Override
          public void windowClosing(WindowEvent e) {
            Object[] possibleValues = {"Yes", "No"};
            int option = JOptionPane.showOptionDialog(
                Main.super.getContentPane(),
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
    this.setSize(800, 600);
    this.setVisible(true);
  }

  @Override
  public void setFocusable(boolean b) {
    super.setFocusable(b);
  }

  @Override
  public void keyTyped(KeyEvent e) {
    log.info("key char typed: {}", e.getKeyChar());
    log.info("key code typed: {}", e.getKeyCode());
  }

  @Override
  public void keyPressed(KeyEvent e) {
    log.info("key char pressed: {}", e.getKeyChar());
    log.info("key code pressed: {}", e.getKeyCode());
  }

  @Override
  public void keyReleased(KeyEvent e) {
    log.info("key char released: {}", e.getKeyChar());
    log.info("key code released: {}", e.getKeyCode());
  }
}
