package gui.table;

import gui.MainFrame;
import resource.DBNode;
import resource.implementation.Attribute;
import resource.implementation.Entity;
import resource.implementation.InformationResource;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.List;

public class Table extends JTable implements ListSelectionListener {
    private int broj = 0;

    @Override
    public void clearSelection() {
        selectionModel.clearSelection();
        columnModel.getSelectionModel().clearSelection();
    }

    @Override
    public Object getValueAt(int row, int column) {
        return super.getValueAt(row, column);
    }

    @Override
    public int getSelectedRow() {
        return super.getSelectedRow();
    }

    @Override
    public boolean isColumnSelected(int column) {
        return super.isColumnSelected(column);
    }

    @Override
    public void setSelectionMode(int selectionMode) {
        super.setSelectionMode(selectionMode);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

        super.valueChanged(e);

            DBNode pk = null;
            if(broj%2==0){
                //System.out.println(getValueAt(getSelectedRow(), 0).toString());
                //Object o = getValueAt(getSelectedRow(),getSelectedColumn());
                String TableName = MainFrame.getInstance().getTp().getTitleAt(MainFrame.getInstance().getTp().getSelectedIndex());
                for(DBNode entity : MainFrame.getInstance().getIr().getChildren()){
                    if(entity.toString().equals(TableName)){
                        pk =(DBNode)entity.getChildAt(0);
                        break;
                    }
                }
                List<Attribute> relacije = ((Attribute) pk).getInRelationWith();
                int i;
                for(i = 0 ; i < getColumnCount() ; i ++ ){
                    if(pk.getName().equals(getColumnModel().getColumn(i).getHeaderValue())){
                        break;
                    }
                }
               // String PKName = MainFrame.getInstance().getjTableA().getColumn();
                Object value =  getValueAt(getSelectedRow(),i).toString();
                InformationResource ir = MainFrame.getInstance().getIr();
                //MainFrame.getInstance().getAppCore().getDatabase().getRelationship(TableName,(Attribute)pk,relacije,value,ir);
                System.out.println("TABELA : " + TableName + " | PK : " + pk.getName() + " = " + getValueAt(getSelectedRow(),i).toString());
                MainFrame.getInstance().getTp2().removeAll();
                MainFrame.getInstance().getAppCore().getTableModel2().setRows(MainFrame.getInstance().getAppCore().getDatabase().getRelationship(TableName,(Attribute)pk,relacije,value,ir));
            }

        broj++;
    }

    @Override
    protected void resizeAndRepaint() {
        super.resizeAndRepaint();
    }

    @Override
    public ListSelectionModel getSelectionModel() {
        return super.getSelectionModel();
    }

    public Table() {
    }
}
