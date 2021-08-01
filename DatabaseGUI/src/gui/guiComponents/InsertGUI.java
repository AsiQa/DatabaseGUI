package gui.guiComponents;
import database.MSSQLrepository;
import gui.MainFrame;
import resource.DBNode;
import resource.DBNodeComposite;
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

public class InsertGUI extends JFrame implements ItemListener {

    static JFrame frame = new JFrame("INSERT");
    static JLabel label1, label2;
    static Boolean ispitano = false;
    static int brojac_poziva_statusChanged = 0;
    static JComboBox tabele;
    static DBNodeComposite entity,node;
    static boolean pokrenut = false;
    static int brojacLABELA = 0;

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
        label1 = new JLabel("INSERT INTO");
        label2 = new JLabel("VALUES");
        label1.setForeground(Color.BLUE);
        label2.setForeground(Color.red);
        JButton searchButton = new JButton("INSERT");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                java.util.List<Component> lc = getAllComponents(frame.getRootPane());
                StringBuilder str = new StringBuilder();
                System.out.println("------------------------------");
                for (Component c : lc){
                    if(c instanceof JLabel){
                        if(((JLabel) c).getText().equals("INSERT INTO") || ((JLabel) c).getText().equals("VALUES") || ((JLabel) c).getText().equals("(") || ((JLabel) c).getText().equals(")") || ((JLabel) c).getText().equals(","))
                        str.append(((JLabel) c).getText().trim() + " ");
                    }
                    else if(c instanceof JComboBox){
                        str.append(((JComboBox) c).getSelectedItem().toString() + " ");
                    }
                    else if(c instanceof JTextField){
                        str.append(((JTextField) c).getText() + " ");
                    }
                }
                System.out.println(str);
                System.out.println("------------------------------");

                MainFrame.getInstance().getAppCore().getDatabase().addRow(str.toString());
                frame.revalidate();
            }
        });
        frame.add(searchButton);
        frame.add(label1);  //0
        frame.add(tabele);  //1
        frame.add(label2);  //2
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
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
                    int brojkomponenata = frame.getContentPane().getComponentCount();
                    for(int i = 4 ; i <brojkomponenata  ; i++){
                        frame.getContentPane().remove(4);
                    }
                }
                frame.add(new JLabel("("));
                for(Object o : entityList){
                    if(!(o.toString().equalsIgnoreCase(entityList.get(0).toString()))){
                        if(o instanceof Attribute){
                            frame.add(new JLabel(","));
                            dodajLabel((TreeNode) o);
                            brojacLABELA++;
                        }
                    }else {
                        if(o instanceof Attribute){
                            dodajLabel((TreeNode) o);
                            brojacLABELA++;
                        }
                    }
                }
                frame.add(new JLabel(")"));
                frame.pack();
                frame.revalidate();
                ispitano =  true;
            }
        }
    }
    void dodajLabel(TreeNode kolona){
        frame.add(new JLabel(kolona.toString() + " ="));
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(50,25));
        frame.add(textField);
    }
}

