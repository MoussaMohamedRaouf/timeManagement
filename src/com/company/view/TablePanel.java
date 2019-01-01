package com.company.view;

import com.company.model.Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class TablePanel extends JPanel {
    private JTable table;
    private TableProjectModel projectModel;
    private JPopupMenu popupMenu;
    private ProjectTableListener projectTableListener;
    public TablePanel(){
        projectModel = new TableProjectModel();
        table = new JTable(projectModel);
        this.setLayout(new BorderLayout());


        popupMenu = new JPopupMenu();
        JMenuItem removeItem = new JMenuItem("supprimer un projet");
        popupMenu.add(removeItem);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                table.getSelectionModel().setSelectionInterval(row, row);
                // the right button
                if(e.getButton() == MouseEvent.BUTTON3){
                    popupMenu.show(table, e.getX(), e.getY());
                }
            }
        });
        removeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if(projectTableListener != null){
                    projectTableListener.rowDeleted(row);
                    projectModel.fireTableRowsDeleted(row, row);
                }
            }
        });





        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void setData(List<Project> data) {
        projectModel.setDataModel(data);
    }

    public void refresh() {
        projectModel.fireTableDataChanged();
    }

    public void setProjectTableListener(ProjectTableListener listener){
        this.projectTableListener = listener;
    }
}
