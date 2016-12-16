package com.leo.test.swing;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;

/**
 * Created by Senchenko Victor on 16.12.2016.
 */
public class DragAndDrop {
    File file = new File("test.txt");

    public static void main(String... args) {
        new DragAndDrop().start();
    }

    private void start() {
        JFrame frame = new JFrame("Swing File Chooser");
        // file chooser
        JFileChooser chooser = new JFileChooser(file.getAbsoluteFile().getParentFile());
        JButton button = new JButton(file.getName());
        button.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                switch (chooser.showOpenDialog(frame)) {
                    case JFileChooser.APPROVE_OPTION:
                        setFile(chooser.getSelectedFile(), chooser, button);
                }
            }
        });
        UIManager.put("ToolTip.font", new FontUIResource(null, Font.BOLD, 14));
        button.setToolTipText("Press to change a file or just drag and drop file");
        JPanel panel = new JPanel(new BorderLayout());

        // set drop target listener
        panel.setDropTarget(new DropTarget() {

            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_MOVE);
                    for (File f : (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor)) {
                        setFile(f, chooser, button);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        panel.add(button, BorderLayout.CENTER);
        panel.add(new JLabel("WEST"), BorderLayout.WEST);
        panel.add(new JLabel("NORTH"), BorderLayout.NORTH);
        panel.add(new JLabel("SOUTH"), BorderLayout.SOUTH);
        panel.add(new JLabel("EAST"), BorderLayout.EAST);

        frame.add(panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setVisible(true);
    }

    private void setFile(File f, JFileChooser chooser, JButton button) {
        file = f;
        chooser.setCurrentDirectory(file);
        button.setText(file.getName());
    }
}
