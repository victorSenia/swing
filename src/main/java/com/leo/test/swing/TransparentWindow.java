package com.leo.test.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Senchenko Victor on 16.12.2016.
 */
public class TransparentWindow {

    public static void main(String... args) {
        new TransparentWindow().start();
        //        new TransparentWindow().startW();
    }

    private void startW() {
        JWindow window = new JWindow();
        window.setOpacity(.2f);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        window.setSize((int) screenSize.getWidth(), (int) screenSize.getHeight());

        window.setVisible(true);
    }

    private void start() {
        JFrame frame = new JFrame();
        // set transparent
        frame.setUndecorated(true);
        frame.setOpacity(.01f);
        //        //remove from taskbar
        //        frame.setType(Window.Type.UTILITY);

        JPanel panel = new JPanel();

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.isAltDown() && e.getKeyCode() == KeyEvent.VK_TAB)
                    super.keyReleased(e);
            }
        });
        frame.add(panel);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize((int) screenSize.getWidth(), (int) screenSize.getHeight());

        frame.setVisible(true);
    }
}
