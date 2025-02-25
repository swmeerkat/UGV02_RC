package org.example;

import java.awt.event.KeyEvent;
import lombok.extern.slf4j.Slf4j;
import org.example.clients.esp32.ESP32Client;

/*
 Apple key codes:
  - command: 157
  - option: 18
  - cursor up: 38
  - cursor down: 40
  - cursor right: 39
  - cursor left: 37
  - numpad '0': 96
 */
@Slf4j
public class KeyboardControl {

  private final ESP32Client esp32Client;
  private boolean optionKeyPressed = false;

  public KeyboardControl(ESP32Client esp32Client) {
    this.esp32Client = esp32Client;
  }

  public void keyTyped(KeyEvent e) {
    // do nothing
  }

  public void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
      case 18:
        optionKeyPressed = true;
        break;
      case 37:
        if (optionKeyPressed) {
          esp32Client.pan_left();
        } else {
          esp32Client.cmd_speed_control(-0.15, 0.15);
        }
        break;
      case 39:
        if (optionKeyPressed) {
          esp32Client.pan_right();
        } else {
          esp32Client.cmd_speed_control(0.15, -0.15);
        }
        break;
      case 38:
        if (optionKeyPressed) {
          esp32Client.tilt_up();
        } else {
          esp32Client.cmd_speed_control(0.1, 0.1);
        }
        break;
      case 40:
        if (optionKeyPressed) {
          esp32Client.tilt_down();
        } else {
          esp32Client.cmd_speed_control(-0.1, -0.1);
        }
        break;
      case 96:
        esp32Client.cmd_speed_control(-0, -0);
        esp32Client.cmd_gimbal_ctrl_stope();
        break;
      default:
        log.info("unexpected key pressed: char={} code={}, ignored",
            e.getKeyChar(), e.getKeyCode());
        break;
    }
  }

  public void keyReleased(KeyEvent e) {
    if (e.getKeyCode() == 18) {
      optionKeyPressed = false;
    }
  }
}
