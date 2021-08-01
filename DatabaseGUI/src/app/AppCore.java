package app;

import database.Database;
import database.DatabaseImplementation;
import database.MSSQLrepository;
import database.settings.Settings;
import database.settings.SettingsImplementation;
import gui.table.TableModel;

import observer.Notification;
import observer.Publisher;
import observer.Subscriber;
import observer.enums.NotificationCode;
import observer.implementation.PublisherImplementation;
import resource.DBNode;
import resource.implementation.InformationResource;
import utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class AppCore extends PublisherImplementation {

    private Database database;
    private Settings settings;
    private TableModel tableModel;
    private TableModel tableModel2;

    public AppCore() {
        this.settings = initSettings();
        this.database = new DatabaseImplementation(new MSSQLrepository(this.settings));
        tableModel = new TableModel();
        tableModel2 = new TableModel();
    }

    public TableModel getTableModel2() {
        return tableModel2;
    }

    private Settings initSettings() {
        Settings settingsImplementation = new SettingsImplementation();
        settingsImplementation.addParameter("147.91.175.155", Constants.MSSQL_IP);      //mssql_ip
        settingsImplementation.addParameter("tim_44_bp2020", Constants.MSSQL_DATABASE); //mssql_database
        settingsImplementation.addParameter("tim_44_bp2020", Constants.MSSQL_USERNAME); //mssql_username
        settingsImplementation.addParameter("M66S4pRT", Constants.MSSQL_PASSWORD);      //mssql_password
        return settingsImplementation;
    }
    public void loadResource(){
        InformationResource ir = (InformationResource) this.database.loadResource();
        this.notifySubscribers(new Notification(NotificationCode.RESOURCE_LOADED,ir));
    }
    public InformationResource loadResourceNODE(){
        InformationResource ir = (InformationResource) this.database.loadResource();
        this.notifySubscribers(new Notification(NotificationCode.RESOURCE_LOADED,ir));
        return ir;
    }
    public void readDataFromTable(String fromTable){

        tableModel.setRows(this.database.readDataFromTable(fromTable));
        //Zasto ova linija moze da ostane zakomentarisana?
        //this.notifySubscribers(new Notification(NotificationCode.DATA_UPDATED, this.getTableModel()));
    }
    public void readDataFromTable2(String fromTable){

        tableModel2.setRows(this.database.readDataFromTable(fromTable));
        //Zasto ova linija moze da ostane zakomentarisana?
        //this.notifySubscribers(new Notification(NotificationCode.DATA_UPDATED, this.getTableModel()));
    }
    public Database getDatabase() {
        return database;
    }
    public TableModel getTableModel() {
        return tableModel;
    }



}
