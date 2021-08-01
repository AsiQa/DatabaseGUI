package gui.guiComponents;
import database.MSSQLrepository;
import gui.MainFrame;
import resource.DBNode;
import resource.DBNodeComposite;
import resource.implementation.Attribute;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

public class UpdateGUI extends JFrame implements ItemListener {

    static JFrame frame = new JFrame("UPDATE");
    static JLabel label1, label2;
    static Boolean ispitano = false;
    static int brojac_poziva_statusChanged = 0;
    static JComboBox tabele;
    static DBNodeComposite entity,node;

    public static void main(String[] args){
        UpdateGUI updateGUI = new UpdateGUI();
        updateGUI.pokreni();
    }
    public void pokreni(){
        ispitano = false;
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
        label1 = new JLabel("UPDATE");
        label2 = new JLabel("SET");
        label1.setForeground(Color.BLUE);
        label2.setForeground(Color.red);
        JButton searchButton = new JButton("UPDATE");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                java.util.List<Component> lc = getAllComponents(frame.getRootPane());
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
                System.out.println(str);
                System.out.println("------------------------------");

                MainFrame.getInstance().getAppCore().getDatabase().updateRow(str.toString());
            }
        });
        frame.add(searchButton);
        frame.add(label1);  //0
        frame.add(tabele);  //1
        frame.add(label2);  //2
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
            if(e.getSource() == tabele){
                ArrayList<String> data = new ArrayList<>();
                node = (DBNodeComposite) entity.getChildByName(tabele.getSelectedItem().toString());
                List<DBNode> entityList = node.getChildren();
                if(ispitano){
                    int broj = frame.getContentPane().getComponentCount();
                    for(int i = 4 ; i < broj ; i ++){
                        frame.getContentPane().remove(4);
                    }
                }
                for(Object o : entityList){
                    if(o instanceof Attribute){
                        data.add(o.toString());
                    }
                }
                String test[] = data.toArray(new String[0]);
                dodajLabel(test);
                dodajKolone(test);
                ispitano = true;
                frame.pack();
                frame.revalidate();
            }
        }
    }
    void dodajKolone(String[] kolona){
        frame.add(new JLabel("WHERE"));
        JComboBox comboBox = new JComboBox();
        for(int i = 0 ; i < kolona.length ; i ++){
            comboBox.addItem(kolona[i]);
        }
        frame.add(comboBox);
        frame.add(new JLabel(" = "));
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(50,25));
        frame.add(textField);

    }
    void dodajLabel(String[] kolona){
        frame.add(new JLabel(kolona[0] + " = "));
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(50,25));
        frame.add(textField);
        for(int i = 1 ; i < kolona.length ; i ++ ){
            frame.add(new JLabel(" , " + kolona[i] + " = "));
            JTextField textField1 = new JTextField();
            textField1.setPreferredSize(new Dimension(50,25));
            frame.add(textField1);
        }
    }
}

