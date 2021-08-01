package gui.table;

import resource.data.Row;
import resource.implementation.Attribute;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Array;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class TableModel extends DefaultTableModel implements TableModelListener {

    private List<Row> rows;


    private void updateModel(){



        int columnCount = rows.get(0).getFields().keySet().size();

        Vector columnVector = DefaultTableModel.convertToVector(rows.get(0).getFields().keySet().toArray());
        Vector dataVector = new Vector(columnCount);

        for (int i=0; i<rows.size(); i++){
            dataVector.add(DefaultTableModel.convertToVector(rows.get(i).getFields().values().toArray()));
        }
        setDataVector(dataVector, columnVector);
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
        updateModel();
    }

    @Override
    public TableModelListener[] getTableModelListeners() {
        return super.getTableModelListeners();
    }


    @Override
    public void tableChanged(TableModelEvent e) {

    }
}
