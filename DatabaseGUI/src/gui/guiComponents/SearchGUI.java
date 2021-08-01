package gui.guiComponents;

import database.MSSQLrepository;
import gui.MainFrame;
import resource.DBNode;
import resource.DBNodeComposite;
import resource.enums.AttributeType;
import resource.implementation.Attribute;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

public class SearchGUI extends JFrame implements ItemListener {


    static JPanel panel;
    static JFrame frame = new JFrame("SEARCH");
    static JLabel label1, label2;
    static Boolean ispitano = false;
    static Boolean ispitanComboString = false;
    static Boolean ispitanComboNotString = false;
    static Boolean ispitanComboAndOr = false;
    static int brojac_poziva_statusChanged = 0;
    static JComboBox tabele,kolone;
    static JComboBox comboBoxAndOr,comboBoxZnaci,comboBoxString;
    static DBNodeComposite entity,node;
    static Attribute kolona;
    static boolean pokrenut = false;

    public static void main(String[] args){
        SearchGUI searchGUI = new SearchGUI();
        searchGUI.pokreni();
    }
    public void pokreni(){
        frame.setLocationRelativeTo(null);
        pokrenut = true;
        frame.getContentPane().removeAll();
        frame.setLayout(new FlowLayout());
        entity =  MainFrame.getInstance().getIr();
        List<DBNode> entityList = entity.getChildren();
        ArrayList<String> data = new ArrayList<>();
        for(Object o : entityList){
            data.add(o.toString());
        }
        String test[] = data.toArray(new String[0]);
        tabele = new JComboBox(test);
        tabele.addItemListener(this);
        label1 = new JLabel("SELECT * FROM");
        label2 = new JLabel("where");
        label1.setForeground(Color.BLUE);
        label2.setForeground(Color.red);
        JButton searchButton = new JButton("SEARCH");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Component> lc = getAllComponents(frame.getRootPane());
                StringBuilder str = new StringBuilder();
                System.out.println("------------------------------");

                for (Component c : lc){
                    if(c instanceof JLabel){
                        str.append(((JLabel) c).getText() + " ");
                    }
                    else if(c instanceof JComboBox && !(c.toString().startsWith("javax"))){
                        str.append(((JComboBox) c).getSelectedItem().toString() + " ");
                    }
                    else if(c instanceof JTextField){
                        str.append(((JTextField) c).getText() + " ");
                    }
                }
                String from = str.substring(14);
                System.out.println(from);
                System.out.println("------------------------------");
                MainFrame.getInstance().getAppCore().getTableModel().setRows(MainFrame.getInstance().getAppCore().getDatabase().searchData(from));

            }
        });
        frame.add(searchButton);
        frame.add(label1);  //0
        frame.add(tabele);  //1
        frame.add(label2);  //2
        frame.pack();
        frame.setVisible(true);
    }
    static List<Component> getAllComponents(final Container c) {
        Component[] comps = c.getComponents();
        List<Component> compList = new ArrayList<Component>();
        for (Component comp : comps) {
            compList.add(comp);
            if (comp instanceof Container)
                compList.addAll(getAllComponents((Container) comp));
        }
        return compList;
    }
    @Override
    public void itemStateChanged(ItemEvent e) {
        brojac_poziva_statusChanged++;
        if(brojac_poziva_statusChanged % 2 == 1){
            if(e.getSource() == comboBoxAndOr){
                if(!ispitanComboAndOr){
                    ispitano = false;
                    ispitanComboAndOr = true;
                    ArrayList<String> data = new ArrayList<>();
                    node = (DBNodeComposite) entity.getChildByName(tabele.getSelectedItem().toString());
                    List<DBNode> entityList = node.getChildren();
                    for(Object o : entityList){
                        data.add(o.toString());
                    }
                    String test[] = data.toArray(new String[0]);
                    dodajKolonu(test);
                    ispitanComboAndOr = true;
                    ispitanComboNotString = false;
                    ispitanComboString = false;
                    ispitano = true;
                }
            }
            if(e.getSource() == comboBoxZnaci){
                ispitanComboNotString = true;
            }
            if(e.getSource() == comboBoxString){
                ispitanComboString = true;
            }
            if(e.getSource() == tabele){
                ArrayList<String> data = new ArrayList<>();
                node = (DBNodeComposite) entity.getChildByName(tabele.getSelectedItem().toString());
                List<DBNode> entityList = node.getChildren();
                for(Object o : entityList){
                    data.add(o.toString());
                }
                String test[] = data.toArray(new String[0]);
                dodajKolonu(test);
                ispitano = true;
                ispitanComboAndOr = false;
            }
            if(e.getSource() == kolone){
                ispitano = true;
                kolona = (Attribute) node.getChildByName(kolone.getSelectedItem().toString());
                AttributeType atribut = kolona.getAttributeType();
                if(atribut.toString().equalsIgnoreCase("DATE") || atribut.toString().equalsIgnoreCase("INT")
                        || atribut.toString().equalsIgnoreCase("SMALLINT") || atribut.toString().equalsIgnoreCase("FLOAT")
                        || atribut.toString().equalsIgnoreCase("TIME") || atribut.toString().equalsIgnoreCase("NUMERIC")
                        || atribut.toString().equalsIgnoreCase("DECIMAL") || atribut.toString().equalsIgnoreCase("REAL")
                        || atribut.toString().equalsIgnoreCase("DATETIME") || atribut.toString().equalsIgnoreCase("BIT")
                        || atribut.toString().equalsIgnoreCase("BIGINT") || atribut.toString().equalsIgnoreCase("SMALLINT")) {
                   dodajBoxZnaci();
                   dodajAndOr();
                   ispitanComboNotString = true;
                   ispitanComboAndOr = false;
                }
                if(atribut.toString().equalsIgnoreCase("CHAR") || atribut.toString().equalsIgnoreCase("VARCHAR")
                        || atribut.toString().equalsIgnoreCase("TEXT") || atribut.toString().equalsIgnoreCase("NVARCHAR")) {
                    dodajBoxString();
                    dodajAndOr();
                    ispitanComboString = true;
                    ispitanComboAndOr = false;
                }
            }
        }
    }
    void dodajKolonu(String test[]){
        if(ispitano){
            int compCount = frame.getContentPane().getComponentCount();
            for(int i = 4; i < compCount ; i++){
                frame.getContentPane().remove(4);
            }
            if(ispitanComboAndOr) {
                ispitanComboNotString = true;
                ispitanComboString = true;
            }else{
                ispitanComboString = false;
                ispitanComboNotString = false;
            }
            ispitanComboAndOr = true;
            ispitano = false;
        }
        kolone = new JComboBox(test);
        kolone.addItemListener(this);
        frame.add(kolone);
        frame.pack();
        frame.revalidate();
    }
    void dodajBoxZnaci(){
        if((ispitanComboString || ispitanComboNotString) && !ispitanComboAndOr){
            int compCount = frame.getContentPane().getComponentCount();
            for(int i = 5; i < compCount ; i++){
                frame.getContentPane().remove(5);
            }
            ispitanComboString = false;
            ispitanComboNotString = false;
        }
        if((ispitanComboString || ispitanComboNotString) && ispitanComboAndOr){
            int compCount = frame.getContentPane().getComponentCount();
            if(compCount != 5) {
                for(int i = compCount - 3; i < compCount ; i++){
                    frame.getContentPane().remove(compCount - 3);
                }
            }
            ispitanComboString = false;
            ispitanComboNotString = false;
        }
        String znaci[] = {"<" , ">" , "=" , "<=" , ">=" , "!="};
        comboBoxZnaci = new JComboBox(znaci);
        comboBoxZnaci.addItemListener(this);
        frame.add(comboBoxZnaci);
        JTextField textField = new JTextField(" ");
        textField.setPreferredSize(new Dimension(30,25));
        textField.setEditable(true);
        frame.add(textField);
        frame.pack();
        frame.revalidate();
    }
    void dodajBoxString(){
         if((ispitanComboString || ispitanComboNotString) && !ispitanComboAndOr){
             int compCount = frame.getContentPane().getComponentCount();
             for(int i = 5; i < compCount ; i++){
                 frame.getContentPane().remove(5);
             }
             ispitanComboString = false;
             ispitanComboNotString = false;
         }
         if((ispitanComboString || ispitanComboNotString) && ispitanComboAndOr){
             int compCount = frame.getContentPane().getComponentCount();
             if(compCount != 5) {
                 for(int i = compCount - 3; i < compCount ; i++){
                     frame.getContentPane().remove(compCount - 3);
                 }
             }
             ispitanComboString = false;
             ispitanComboNotString = false;
         }
        String znaci[]= {"=" , "LIKE"};
        comboBoxString = new JComboBox(znaci);
        frame.add(comboBoxString);
        JTextField textField = new JTextField(" ");
        textField.setPreferredSize(new Dimension(50,25));
        textField.setEditable(true);
        frame.add(textField);
        frame.pack();
        frame.revalidate();
    }
    void dodajAndOr(){
        String andOr[] = {" " , "AND" , "OR"};
        comboBoxAndOr = new JComboBox(andOr);
        comboBoxAndOr.addItemListener(this);
        frame.add(comboBoxAndOr);
        frame.pack();
        frame.revalidate();
    }
}
