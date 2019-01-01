package com.company.view;

import com.company.model.Project;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableProjectModel extends AbstractTableModel {
    private List<Project> data;
    private String[] colNames = {"Project Name", "Project Type",
            "Date de début", "Date de fin", "Jours restants", "Session par jour",
    "Sessions par projet"};


    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return 7;
    }


    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Project project = data.get(rowIndex);
        switch (columnIndex){
            case 0:
                return project.getName();
            case 1:
                return project.getType();
            case 2:
                return project.getStartTime();
            case 3:
                return project.getEndTime();
            case 4:
                return project.getElapsedTime();
            case 5:
                return project.getSessionByDay();
            case 6:
                return project.getSessionsByProject();
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if(data == null) return;
        Project project = data.get(rowIndex);
        switch (columnIndex){
            case 1:
                project.setName((String) aValue);
                break;
            case 2:
                project.setType((String) aValue);
                break;
            default:
                return;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch (columnIndex){
            case 1:
                return true;
            case 2:
                return true;
            default:
                return false;
        }
    }

    public void setDataModel(List<Project> data){
        this.data = data;
    }
}
