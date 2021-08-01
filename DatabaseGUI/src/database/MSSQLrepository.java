package database;

import database.settings.Settings;
import resource.DBNode;
import resource.DBNodeComposite;
import resource.data.Row;
import resource.enums.AttributeType;
import resource.enums.ConstraintType;
import resource.implementation.Attribute;
import resource.implementation.AttributeConstraint;
import resource.implementation.Entity;
import resource.implementation.InformationResource;
import utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.*;
import java.util.List;

public class MSSQLrepository implements Repository{

    private Settings settings;
    private Connection connection;

    public MSSQLrepository(Settings settings) {
        this.settings = settings;
    }

    private void initConnection() throws SQLException, ClassNotFoundException{
        Class.forName("net.sourceforge.jtds.jdbc.Driver");
        String ip = (String) settings.getParameter("147.91.175.155");               //mssql_ip
        String database = (String) settings.getParameter("tim_44_bp2020");          //mssql_database
        String username = (String) settings.getParameter("tim_44_bp2020");          //mssql_username
        String password = (String) settings.getParameter("M66S4pRT");               //mssql_password
        Class.forName("net.sourceforge.jtds.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:jtds:sqlserver://"+ip+"/"+database,username,password);
    }

    private void closeConnection(){
        try{
            connection.close();
        }
        catch (SQLException e){
            e.printStackTrace();
            showDialog(e);
        }
        finally {
            connection = null;
        }
    }

    @Override
    public DBNode getSchema() {

        try{
            this.initConnection();
            System.out.println("Veza uspostavljena");

            DatabaseMetaData metaData = connection.getMetaData();
            InformationResource ir = new InformationResource(Constants.MSSQL_DATABASE);  //RAF_BR_Primer

            String tableType[] = {"TABLE"};
            ResultSet tables = metaData.getTables(connection.getCatalog(), null, null, tableType);

            while (tables.next()) {

                String tableName = tables.getString("TABLE_NAME");
                // zasto ovaj if ?
                // zato sto cemo u suprotnom u stablu imati dodatne korisniku nepotrebne tabele
                if (tableName.contains("trace")) continue;

                Entity newTable = new Entity(tableName, ir);
                ir.addChild(newTable);

                //Koje atribute imaja ova tabela?

                ResultSet columns = metaData.getColumns(connection.getCatalog(), null, tableName, null);

                while (columns.next()) {

                    String columnName = columns.getString("COLUMN_NAME");
                    String columnType = columns.getString("TYPE_NAME");

                    String isNullable = columns.getString("IS_NULLABLE");
                    String hasDefault = columns.getString("COLUMN_DEF");
                    //String dataType = columns.getString("DATA_TYPE");
                    //String maxCharacterLength = columns.getString("CHARACTER_MAXIMUM_LENGTH");

                    int columnSize = Integer.parseInt(columns.getString("COLUMN_SIZE"));
                    //Attribute attribute = new Attribute(columnName, newTable, AttributeType.valueOf(columnType.toUpperCase()), columnSize);
                    Attribute attribute = new Attribute(columnName, newTable, AttributeType.valueOf(columnType.toUpperCase()), columnSize);
                    if (hasDefault != null) {
                        attribute.addChild(new AttributeConstraint("IS_DEFAULT", attribute, ConstraintType.DEFAULT_VALUE));
                    }
                    if(isNullable.equalsIgnoreCase("no")){
                        attribute.addChild(new AttributeConstraint("NOT_NULL",attribute, ConstraintType.NOT_NULL));
                    }else{
                        attribute.addChild(new AttributeConstraint("NULLABLE",attribute, ConstraintType.NULLABLE));
                    }



                    newTable.addChild(attribute);

                    // mora ogranicenja da se odrade


                }

                ResultSet primaryKeys = metaData.getPrimaryKeys(connection.getCatalog(), null, tableName);
                while (primaryKeys.next()) {
                    String name = primaryKeys.getString("COLUMN_NAME");
                    for (DBNode node : newTable.getChildren()) {
                        if (name.equalsIgnoreCase(node.getName())) {
                            DBNodeComposite nc = (DBNodeComposite) node;
                            nc.addChild(new AttributeConstraint("PRIMARY_KEY", nc, ConstraintType.PRIMARY_KEY));
                        }
                    }
                }
            }

            for(DBNode entity : ir.getChildren()){
                ResultSet foreignKeys = metaData.getImportedKeys(connection.getCatalog(), null, entity.getName());
                while(foreignKeys.next()) {


                    String fkTableName = foreignKeys.getString("FKTABLE_NAME");
                    String fkColumnName = foreignKeys.getString("FKCOLUMN_NAME");
                    String pkTableName = foreignKeys.getString("PKTABLE_NAME");
                    String pkColumnName = foreignKeys.getString("PKCOLUMN_NAME");

                    Attribute attributePK = null;
                    for(DBNode child : ((Entity) entity).getChildren()){
                        for(DBNode constraint : ((Attribute)child).getChildren()){
                            if(constraint.toString().equals("PRIMARY_KEY")){
                                attributePK = (Attribute) child;

                                break;
                            }
                        }
                        if(child.getName().equalsIgnoreCase(fkColumnName)){
                            Attribute attribute = (Attribute) child;
                            attribute.addChild(new AttributeConstraint("FOREIGN_KEY", attribute, ConstraintType.FOREIGN_KEY));
                            attributePK.getInRelationWith().add(attribute);
                            //System.out.println("TABELA : " + entity + " | PK : " + attributePK + " | RELATIONSHIP : " + attribute);
                            break;
                        }
                    }
                }


            }



                //TODO Ogranicenja nad kolonama? Relacije?

                return ir;
            // String isNullable = columns.getString("IS_NULLABLE");
            // ResultSet foreignKeys = metaData.getImportedKeys(connection.getCatalog(), null, table.getName());
            // ResultSet primaryKeys = metaData.getPrimaryKeys(connection.getCatalog(), null, table.getName());

        }
        catch (Exception e) {
            e.printStackTrace();
            showDialog(e);
        }
        finally {
            this.closeConnection();
            System.out.println("Veza prekinuta");
        }

        return null;
    }

    @Override
    public void deleteRow(String from){
        try{
            this.initConnection();
            String query = from;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();

        }catch (Exception e){
            e.printStackTrace();
            showDialog(e);
        }finally {
            this.closeConnection();
        }
    }

    @Override
    public void addRow(String from) {
        try{
            this.initConnection();
            String query = from;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();

        }catch (Exception e){
            e.printStackTrace();
            showDialog(e);
        }finally {
            this.closeConnection();
        }
    }

    public void showDialog(Exception e){
        JDialog dialog = new JDialog();
        JLabel error = new JLabel(e.getLocalizedMessage());
        error.setForeground(Color.RED);
        dialog.add(error);
        dialog.pack();
        dialog.revalidate();
        dialog.setVisible(true);
        dialog.setLocationRelativeTo(null);
    }

    @Override
    public void updateRow(String from) {
        try{
            this.initConnection();
            String query = from;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        }catch (Exception e){
            e.printStackTrace();
            showDialog(e);
        }finally {
            this.closeConnection();
        }
    }

    @Override
    public List<Row> avgAndCount(String from) {
        List<Row> rows = new ArrayList<>();


        try{
            this.initConnection();

            String query = from;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){

                Row row = new Row();
                row.setName(from);

                ResultSetMetaData resultSetMetaData = rs.getMetaData();
                //for int i = 1 ; i<= resultSetMetaData ....
                for (int i = 1; i<=resultSetMetaData.getColumnCount(); i++){
                    row.addField(resultSetMetaData.getColumnName(i), rs.getString(i));
                }
                rows.add(row);

            }
        }
        catch (Exception e) {
            e.printStackTrace();
            showDialog(e);
        }
        finally {
            this.closeConnection();
        }

        return rows;
    }

    @Override
    public List<Row> sort(String from) {
        return null;
    }

    @Override
    public List<Row> get(String from) {

        List<Row> rows = new ArrayList<>();


        try{
            this.initConnection();

            String query = "SELECT * FROM " + from;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){

                Row row = new Row();
                row.setName(from);

                ResultSetMetaData resultSetMetaData = rs.getMetaData();
                //for int i = 1 ; i<= resultSetMetaData ....
                for (int i = 1; i<=resultSetMetaData.getColumnCount(); i++){
                    row.addField(resultSetMetaData.getColumnName(i), rs.getString(i));
                }
                rows.add(row);

            }
        }
        catch (Exception e) {
            e.printStackTrace();
            showDialog(e);
        }
        finally {
            this.closeConnection();
        }

        return rows;
    }

    public List<Row> getRelationship(String from, Attribute pk , List<Attribute> relacije ,Object value,InformationResource ir){

        List<Row> rows = new ArrayList<>();

        ArrayList<DBNode> listaFK = new ArrayList<>();
        try{
            this.initConnection();
            int i;
            for(DBNode entity : ir.getChildren()){
                i = 0;
                for(DBNode attribute : ((Entity)entity).getChildren()){
                    if(attribute.toString().equalsIgnoreCase(pk.toString()) && !(entity.getChildAt(0).equals(pk))){
                       // System.out.println("ENTITY : " + entity + " | ATTRIBUTE " + attribute);
                        DBNode fk = (DBNode) entity.getChildAt(i);
                        listaFK.add(fk);
                        break;
                    }
                    i++;
                }
            }
            //System.out.println(listaFK);
            StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM " + from );
            for(i = 0 ; i < listaFK.size();i++){
                query.append(" FULL OUTER JOIN " + listaFK.get(i).getParent() + " ON ");
                query.append(pk.getParent() + "." + listaFK.get(i) + "=");
                query.append(listaFK.get(i).getParent() + "." + listaFK.get(i) + " ");
            }
            if(value instanceof String){
                query.append("WHERE " + from + "." + pk + " = '" + value + "'");
            }else{
                query.append("WHERE " + from + "." + pk + " = " + value);
            }
            System.out.println(query.toString());
            PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){

                Row row = new Row();
                row.setName(from);

                ResultSetMetaData resultSetMetaData = rs.getMetaData();
                //for int i = 1 ; i<= resultSetMetaData ....
                for ( i = 1; i<=resultSetMetaData.getColumnCount(); i++){
                    row.addField(resultSetMetaData.getColumnName(i), rs.getString(i));
                }
                rows.add(row);

            }
        }
        catch (Exception e) {
            e.printStackTrace();
            showDialog(e);
        }
        finally {
            this.closeConnection();
        }

        return rows;
    }

    //dodati update a ne add i delete, jer ADD + DELETE rade lokalno, dok UPDATE updejtuje tabelu/e


}
