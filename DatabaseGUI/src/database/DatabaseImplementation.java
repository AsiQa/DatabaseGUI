package database;

import resource.DBNode;
import resource.data.Row;
import resource.implementation.Attribute;
import resource.implementation.InformationResource;

import java.util.ArrayList;
import java.util.List;


public class DatabaseImplementation implements Database {

    private Repository repository;

    @Override
    public DBNode loadResource() {
        return repository.getSchema();
    }
    @Override
    public List<Row> readDataFromTable(String tableName) {
        return repository.get(tableName);
    }
    @Override
    public List<Row> searchData(String from) {
        return repository.get(from);
    }
    @Override
    public void deleteRow(String from) {
        repository.deleteRow(from);
    }
    @Override
    public void addRow(String from) {
        repository.addRow(from);
    }
    @Override
    public void updateRow(String from) {
        repository.updateRow(from);
    }
    @Override
    public List<Row> avgAndCount(String from) {
        return repository.avgAndCount(from);
    }
    @Override
    public List<Row> sort(String from) {
        return repository.sort(from);
    }
    @Override
    public List<Row> get(String from) {
        return repository.get(from);
    }
    @Override
    public List<Row> getRelationship(String from, Attribute pk, List<Attribute> relacije, Object value, InformationResource ir) {
        return repository.getRelationship(from,pk,relacije,value,ir);
    }
    public DatabaseImplementation(Repository repository) {
        this.repository = repository;
    }
}
