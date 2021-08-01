package database;

import resource.DBNode;
import resource.data.Row;
import resource.implementation.Attribute;
import resource.implementation.InformationResource;

import java.util.ArrayList;
import java.util.List;

public interface Database{

    DBNode loadResource();
    List<Row> readDataFromTable(String tableName);
    List<Row> searchData(String from);
    void deleteRow(String from);
    void addRow(String from);
    void updateRow(String from);
    List<Row> avgAndCount(String from);
    List<Row> sort(String from);
    List<Row> get(String from);
    List<Row> getRelationship(String from, Attribute pk , List<Attribute> relacije , Object value, InformationResource ir);

}
