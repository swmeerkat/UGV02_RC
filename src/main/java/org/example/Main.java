package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.swing.JPanel;
import lombok.extern.slf4j.Slf4j;
import org.example.clients.esp32.ESP32Client;
import org.example.gui.MainWindow;

@Slf4j
public class Main extends JPanel implements ActionListener {

    MainWindow mw;

  public static void main(String[] args) {
    new Main();
  }

  public Main() {
    this.mw = new MainWindow();
    final Properties properties = loadProperties();
    String host = properties.get("UGV02.host").toString();
    ESP32Client ugv02 = initUgv02Client(host);
  }

  public void actionPerformed(ActionEvent event) {

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
    mw.console.append("ugv02 host: " + host + "\n");
    JsonNode response = ugv02.cmd_gimbal_ctrl_simple(0, 0);
    mw.console.append("cmd_gimbal_ctrl_simple(0, 0): " + response.toString() + "\n");
    //ugv02.cmd_speed_control(0.1f, 0.1f);
    return ugv02;
  }

}
