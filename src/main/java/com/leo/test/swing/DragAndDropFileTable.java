package com.leo.test.swing;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
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
import java.util.Vector;

/**
 * Created by Senchenko Victor on 16.12.2016.
 */
public class DragAndDropFileTable {
    Vector<File> files = new Vector<>();

    JTable table;

    public static void main(String... args) {
        new DragAndDropFileTable().start();
    }

    private void start() {
        JFrame frame = new JFrame("Swing File Chooser");
        UIManager.put("ToolTip.font", new FontUIResource(null, Font.BOLD, 14));
        JPanel panel = new JPanel(new BorderLayout());

        // set drop target listener
        panel.setDropTarget(new DropTarget() {
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_MOVE);
                    for (File f : (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor)) {
                        addFile(f, null);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        //TODO
        table = new JTable();
        table.setAutoCreateColumnsFromModel(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panel.add(table, BorderLayout.CENTER);

        table.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                popupMenu(e);
            }

            public void mousePressed(MouseEvent e) {
                popupMenu(e);
            }
        });
        DefaultTableModel tableModel=new DefaultTableModel(files,5);
        table.setModel(tableModel);
        JButton start = new JButton("Start");
        start.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
//                File file = table.getSelectedValue();
//                if (file != null)
//                    System.out.println(file);
            }
        });
        start.setToolTipText("Start running");

        panel.add(start, BorderLayout.SOUTH);
        panel.add(new JLabel("WEST"), BorderLayout.WEST);
        panel.add(new JLabel("NORTH"), BorderLayout.NORTH);
        panel.add(new JLabel("EAST"), BorderLayout.EAST);

        frame.add(panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setVisible(true);
        addFile(new File("test.txt").getAbsoluteFile(), null);
    }

    private void popupMenu(MouseEvent e) {
        if (e.isPopupTrigger()) {
            JPopupMenu menu = new JPopupMenu();
            JMenuItem item = new JMenuItem("Delete");
//            item.addActionListener(e1 -> removeFile(table.getSelectedValue()));
            menu.add(item);
//            table.setSelectedIndex(table.locationToIndex(e.getPoint()));
            menu.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    private void addFile(File f, JFileChooser chooser) {
        files.add(f);
//        chooser.setCurrentDirectory(f.getParentFile());
//        table.setListData(files.toArray(new File[files.size()]));
    }

    private void removeFile(File f) {
        files.remove(f);
//        table.setListData(files.toArray(new File[files.size()]));
    }
}
