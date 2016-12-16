package com.leo.test.swing;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Senchenko Victor on 16.12.2016.
 */
public class DragAndDropFileList {
    Set<File> files = new LinkedHashSet<>();

    JList<File> list;

    public static void main(String... args) {
        new DragAndDropFileList().start();
    }

    private void start() {
        JFrame frame = new JFrame("Swing File Chooser");
        // file chooser
        JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(true);
        JButton button = new JButton("Add files");
        button.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                switch (chooser.showOpenDialog(frame)) {
                    case JFileChooser.APPROVE_OPTION:
                        for (File f : chooser.getSelectedFiles())
                            addFile(f, chooser);
                }
            }
        });
        UIManager.put("ToolTip.font", new FontUIResource(null, Font.BOLD, 14));
        button.setToolTipText("Press to add files or just drag and drop files");
        JPanel panel = new JPanel(new BorderLayout());

        // set drop target listener
        panel.setDropTarget(new DropTarget() {

            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_MOVE);
                    for (File f : (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor)) {
                        addFile(f, chooser);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        list = new JList<>();
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setListData(files.toArray(new File[files.size()]));
        JScrollPane scrollPane = new JScrollPane(list);
        panel.add(scrollPane, BorderLayout.CENTER);

        list.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                popupMenu(e);
            }

            public void mousePressed(MouseEvent e) {
                popupMenu(e);
            }
        });

        JButton start = new JButton("Start");
        start.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                File file = list.getSelectedValue();
                if (file != null)
                    System.out.println(file);
            }
        });
        start.setToolTipText("Start running");

        JPanel panelButtons = new JPanel();
        panelButtons.add(start);
        panelButtons.add(button);
        panel.add(panelButtons, BorderLayout.SOUTH);
        panel.add(new JLabel("WEST"), BorderLayout.WEST);
        panel.add(new JLabel("NORTH"), BorderLayout.NORTH);
        panel.add(new JLabel("EAST"), BorderLayout.EAST);

        frame.add(panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setVisible(true);
        addFile(new File("test.txt").getAbsoluteFile(), chooser);
    }

    private void popupMenu(MouseEvent e) {
        if (e.isPopupTrigger()) {
            JPopupMenu menu = new JPopupMenu();
            JMenuItem item = new JMenuItem("Delete");
            item.addActionListener(e1 -> removeFile(list.getSelectedValue()));
            menu.add(item);
            list.setSelectedIndex(list.locationToIndex(e.getPoint()));
            menu.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    private void addFile(File f, JFileChooser chooser) {
        files.add(f);
        chooser.setCurrentDirectory(f.getParentFile());
        list.setListData(files.toArray(new File[files.size()]));
    }

    private void removeFile(File f) {
        files.remove(f);
        list.setListData(files.toArray(new File[files.size()]));
    }
}
