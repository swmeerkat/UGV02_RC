package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.example.clients.esp32.ESP32Client;

public class Main {

  public static void main(String[] args) {

    int x = 0;
    int y = 0;

    final Properties properties = new Properties();
    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    InputStream stream = loader.getResourceAsStream("application.properties");
    try {
      properties.load(stream);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    String host = properties.get("UGV02.host").toString();
    ESP32Client ugv02 = new ESP32Client(host);
    y = 20;
    ugv02.cmd_gimbal_ctrl_simple(x, y);
    x = -30;
    ugv02.cmd_gimbal_ctrl_simple(x, y);
    ugv02.cmd_speed_control(0.1f, 0.1f);
    x = 0;
    y = 0;
    ugv02.cmd_gimbal_ctrl_simple(x, y);
  }
}
