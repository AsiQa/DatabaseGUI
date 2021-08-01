package gui.guiComponents;
import database.MSSQLrepository;
import gui.MainFrame;
import resource.DBNode;
import resource.DBNodeComposite;
import resource.enums.AttributeType;
import resource.implementation.Attribute;

import javax.swing.*;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

public class CountAndAverage extends JFrame implements ItemListener {

    static JFrame frame = new JFrame("COUNT & AVERAGE");
    static JLabel label1, label2;
    static Boolean ispitano = false;
    static Boolean ispitanComboString = false;
    static Boolean ispitanComboNotString = false;
    static int brojac_poziva_statusChanged = 0;
    static JComboBox avgCount;
    static JComboBox tabele;
    static JComboBox whereIliNista,groupByIliNista,comboBoxZnaci,comboBoxString,whereIliNistaKolone,groupByIliNistaContent;
    static DBNodeComposite entity,node;
    static Attribute kolona;
    static boolean pokrenut = false;
    static JComboBox countiliAVG = new JComboBox();
    static boolean grupByBioSelektovan = false;

    public void pokreni(){
        pokrenut = true;
        frame.getContentPane().removeAll();
        frame.setLayout(new FlowLayout());
        entity =  MainFrame.getInstance().getIr();
        java.util.List<DBNode> entityList = entity.getChildren();
        ArrayList<String> data = new ArrayList<>();
        for(Object o : entityList){
            data.add(o.toString());
        }
        String test[] = data.toArray(new String[0]);
        tabele = new JComboBox(test);
        tabele.addItemListener(this);
        String[] arraylist = {"AVG","COUNT"};
        avgCount = new JComboBox(arraylist);
        avgCount.addItemListener(this);
        label1 = new JLabel("SELECT");
        label2 = new JLabel("FROM");
        String[] arraylist1 = {" ","WHERE","GROUP BY"};
        String[] arraylist2 = {" ","GROUP BY"};
        groupByIliNista = new JComboBox(arraylist2);
        whereIliNista = new JComboBox(arraylist1);
        groupByIliNista.addItemListener(this);
        whereIliNista.addItemListener(this);
        label1.setForeground(Color.BLUE);
        label2.setForeground(Color.red);
        JButton searchButton = new JButton("VIEW RESULT");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                java.util.List<Component> lc = getAllComponents(frame.getRootPane());
                StringBuilder str = new StringBuilder();
                System.out.println("------------------------------");
                for (Component c : lc){
                    if(c instanceof JLabel){
                        str.append(((JLabel) c).getText().trim() + " ");
                    }
                    else if(c instanceof JComboBox && !(c.toString().startsWith("javax"))){
                        str.append(((JComboBox) c).getSelectedItem().toString() + " ");
                    }
                    else if(c instanceof JTextField){
                        str.append(((JTextField) c).getText() + " ");
                    }
                }
                System.out.println(str);
                System.out.println("------------------------------");
                MainFrame.getInstance().getAppCore().getTableModel().setRows(MainFrame.getInstance().getAppCore().getDatabase().avgAndCount(str.toString()));
                frame.revalidate();
            }
        });
        frame.add(searchButton);
        frame.add(label1);  //0
        frame.add(avgCount);
        frame.add(new JLabel("("));
        frame.add(countiliAVG);
        frame.add(new JLabel(")"));
        frame.add(label2);  //2
        frame.add(tabele);  //1
        frame.add(whereIliNista);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    static java.util.List<Component> getAllComponents(final Container c) {
        Component[] comps = c.getComponents();
        java.util.List<Component> compList = new ArrayList<Component>();
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
            if(e.getSource() == whereIliNista){
                if(grupByBioSelektovan){
                    frame.getContentPane().remove(frame.getContentPane().getComponentCount()-1);
                    grupByBioSelektovan = false;
                }
                ArrayList<String> data = new ArrayList<>();
                node = (DBNodeComposite) entity.getChildByName(tabele.getSelectedItem().toString());
                List<DBNode> entityList = node.getChildren();
                for(Object o : entityList){
                    data.add(o.toString());
                }
                String test[] = data.toArray(new String[0]);
                System.out.println(test);
                if(!whereIliNista.getSelectedItem().equals("GROUP BY")){
                    System.out.println(tabele.getSelectedItem());
                    if(whereIliNista.getSelectedItem().toString().equals(" ")){
                        System.out.println("NIJE WHERE");
                        int broj = frame.getContentPane().getComponentCount();
                        for(int i = 9 ; i < broj;i++){
                            frame.getContentPane().remove(9);
                        }
                    }else{
                        System.out.println("USAO U WHERE");
                        dodajKolonu1(test);
                        ispitanComboNotString = false;
                        ispitanComboString = false;
                    }
                    frame.pack();
                    frame.revalidate();
                }else{
                    grupByBioSelektovan = true;
                    int broj = frame.getContentPane().getComponentCount();
                    for(int i = 9 ; i < broj ; i ++){
                        frame.getContentPane().remove(9);
                    }
                    dodajKolonuIzGroupBy(test);
                }
            }
            if(e.getSource() == groupByIliNista){
                if(groupByIliNista.getSelectedItem().equals("GROUP BY")){
                    ArrayList<String> data = new ArrayList<>();
                    node = (DBNodeComposite) entity.getChildByName(tabele.getSelectedItem().toString());
                    List<DBNode> entityList = node.getChildren();
                    for(Object o : entityList){
                        data.add(o.toString());
                    }
                    String test[] = data.toArray(new String[0]);
                    dodajKolonuIzGroupBy(test);
                }else{
                    frame.getContentPane().remove(frame.getContentPane().getComponentCount()-1);
                }
            }
            if(e.getSource() == whereIliNistaKolone){
                kolona = (Attribute) node.getChildByName(whereIliNistaKolone.getSelectedItem().toString());
                AttributeType atribut = kolona.getAttributeType();
                if(atribut.toString().equalsIgnoreCase("DATE") || atribut.toString().equalsIgnoreCase("INT")
                        || atribut.toString().equalsIgnoreCase("SMALLINT") || atribut.toString().equalsIgnoreCase("FLOAT")
                        || atribut.toString().equalsIgnoreCase("TIME") || atribut.toString().equalsIgnoreCase("NUMERIC")
                        || atribut.toString().equalsIgnoreCase("DECIMAL") || atribut.toString().equalsIgnoreCase("REAL")
                        || atribut.toString().equalsIgnoreCase("DATETIME") || atribut.toString().equalsIgnoreCase("BIT")
                        || atribut.toString().equalsIgnoreCase("BIGINT") || atribut.toString().equalsIgnoreCase("SMALLINT")) {
                    dodajBoxZnaci();
                    frame.add(groupByIliNista);
                    frame.pack();
                    frame.revalidate();
                    ispitanComboNotString = true;
                }
                if(atribut.toString().equalsIgnoreCase("CHAR") || atribut.toString().equalsIgnoreCase("VARCHAR")
                        || atribut.toString().equalsIgnoreCase("TEXT") || atribut.toString().equalsIgnoreCase("NVARCHAR")) {
                    dodajBoxString();
                    frame.add(groupByIliNista);
                    frame.pack();
                    frame.revalidate();
                    ispitanComboString = true;
                }
            }
            if(e.getSource() == avgCount){
                ArrayList<String> data = new ArrayList<>();
                node = (DBNodeComposite) entity.getChildByName(tabele.getSelectedItem().toString());
                List<DBNode> entityList = node.getChildren();
                countiliAVG.removeAllItems();
                if(avgCount.getSelectedItem().equals("AVG")){
                    for(Object o : entityList){
                        System.out.println(o.toString());
                        String attributeType = ((Attribute)o).getAttributeType().toString();
                        if(attributeType.equalsIgnoreCase("DATE") || attributeType.equalsIgnoreCase("INT") ||
                                attributeType.equalsIgnoreCase("SMALLINT") || attributeType.equalsIgnoreCase("NUMERIC") ||
                                attributeType.equalsIgnoreCase("DECIMAL") || attributeType.equalsIgnoreCase("REAL")||
                                attributeType.equalsIgnoreCase("DATETIME") || attributeType.equalsIgnoreCase("BIT")||
                                attributeType.equalsIgnoreCase("BIGINT") || attributeType.equalsIgnoreCase("TIME") ||
                                attributeType.equalsIgnoreCase("FLOAT")){
                            data.add(o.toString());
                        }
                    }
                }else{
                    for(Object o : entityList){
                        data.add(o.toString());
                    }
                }
                String test[] = data.toArray(new String[0]);
                dodajKolonu(test);

            }
            if(e.getSource() == tabele){
                int broj = frame.getContentPane().getComponentCount();
                for(int i = 9 ; i < broj;i++){
                    frame.getContentPane().remove(9);
                }
                ArrayList<String> data = new ArrayList<>();
                node = (DBNodeComposite) entity.getChildByName(tabele.getSelectedItem().toString());
                List<DBNode> entityList = node.getChildren();
                countiliAVG.removeAllItems();
                if(avgCount.getSelectedItem().equals("AVG")){
                    for(Object o : entityList){
                        String attributeType = ((Attribute)o).getAttributeType().toString();
                        if(attributeType.equalsIgnoreCase("DATE") || attributeType.equalsIgnoreCase("INT") ||
                        attributeType.equalsIgnoreCase("SMALLINT") || attributeType.equalsIgnoreCase("NUMERIC") ||
                        attributeType.equalsIgnoreCase("DECIMAL") || attributeType.equalsIgnoreCase("REAL")||
                        attributeType.equalsIgnoreCase("DATETIME") || attributeType.equalsIgnoreCase("BIT")||
                        attributeType.equalsIgnoreCase("BIGINT") || attributeType.equalsIgnoreCase("TIME") ||
                        attributeType.equalsIgnoreCase("FLOAT")){
                            data.add(o.toString());
                        }
                    }
                }else{
                    for(Object o : entityList){
                        data.add(o.toString());
                    }
                }
                String test[] = data.toArray(new String[0]);
                System.out.println(test);
                dodajKolonu(test);
                ispitano =  true;
            }
        }
        frame.pack();
        frame.revalidate();
    }
    void dodajKolonuIzGroupBy(String test[]){
        groupByIliNistaContent = new JComboBox();
        for(int i = 0; i < test.length ; i++){
            groupByIliNistaContent.addItem(test[i]);
        }
        frame.add(groupByIliNistaContent);
        frame.pack();
        frame.revalidate();
    }
    void dodajKolonu1(String test[]){
        whereIliNistaKolone = new JComboBox();
        for(int i = 0; i < test.length ; i++){
            whereIliNistaKolone.addItem(test[i]);
        }
        whereIliNistaKolone.addItem("*");
        whereIliNistaKolone.addItemListener(this);
        frame.add(whereIliNistaKolone);
        frame.pack();
        frame.revalidate();
    }
    void dodajKolonu(String test[]){
        for(int i = 0; i < test.length ; i++){
            countiliAVG.addItem(test[i]);
        }
        countiliAVG.addItem("*");
        countiliAVG.addItemListener(this);
        frame.pack();
        frame.revalidate();
    }
    void dodajBoxString(){
        if(ispitanComboString || ispitanComboNotString){
            int broj = frame.getContentPane().getComponentCount();
            for(int i = 10 ; i<broj ; i ++){
                frame.getContentPane().remove(10);
            }
        }
        String znaci[]= {"=" , "LIKE"};
        comboBoxString = new JComboBox(znaci);
        frame.add(comboBoxString);
        JTextField textField = new JTextField(" ");
        textField.setPreferredSize(new Dimension(30,20));
        textField.setEditable(true);
        frame.add(textField);
        frame.pack();
        frame.revalidate();
    }
    void dodajBoxZnaci(){
        if(ispitanComboString || ispitanComboNotString){
            int broj = frame.getContentPane().getComponentCount();
            for(int i = 10 ; i<broj ; i ++){
                frame.getContentPane().remove(10);
            }
        }
        String znaci[] = {"<" , ">" , "=" , "<=" , ">=" , "!="};
        comboBoxZnaci = new JComboBox(znaci);
        comboBoxZnaci.addItemListener(this);
        frame.add(comboBoxZnaci);
        JTextField textField = new JTextField(" ");
        textField.setPreferredSize(new Dimension(30,20));
        textField.setEditable(true);
        frame.add(textField);
        frame.pack();
        frame.revalidate();
    }
}