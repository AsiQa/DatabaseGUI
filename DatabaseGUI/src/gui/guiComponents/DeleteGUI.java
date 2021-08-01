package gui.guiComponents;
import database.MSSQLrepository;
import gui.MainFrame;
import resource.DBNode;
import resource.DBNodeComposite;
import resource.enums.ConstraintType;
import resource.implementation.Attribute;
import resource.implementation.AttributeConstraint;

import javax.swing.*;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

    public class DeleteGUI extends JFrame implements ItemListener {

        static JFrame frame = new JFrame("DELETE");
        static JLabel label1, label2;
        static Boolean ispitano = false;
        static int brojac_poziva_statusChanged = 0;
        static JComboBox tabele;
        static DBNodeComposite entity,node;
        static boolean pokrenut = false;

        public static void main(String[] args){
            DeleteGUI deleteGUI = new DeleteGUI();
            deleteGUI.pokreni();
        }
        public void pokreni(){
            pokrenut = true;
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
            label1 = new JLabel("DELETE FROM");
            label2 = new JLabel("WHERE");
            label1.setForeground(Color.BLUE);
            label2.setForeground(Color.red);
            JButton searchButton = new JButton("DELETE");
            frame.setLocationRelativeTo(null);
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

                    MainFrame.getInstance().getAppCore().getDatabase().deleteRow(str.toString());

                    frame.revalidate();
                }
            });
            frame.add(searchButton);
            frame.add(label1);
            frame.add(tabele);
            frame.add(label2);
            frame.pack();
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
                    node = (DBNodeComposite) entity.getChildByName(tabele.getSelectedItem().toString());
                    List<DBNode> entityList = node.getChildren();
                    for(Object o : entityList){
                        int brojac = ((Attribute) o).getChildCount();
                        for(int i = 0 ; i < brojac ; i ++){
                            AttributeConstraint atconst = (AttributeConstraint) ((Attribute) o).getChildAt(i);
                            if((atconst).getConstraintType() == ConstraintType.PRIMARY_KEY){
                                dodajLabel(atconst.getParent());
                                frame.revalidate();
                                frame.pack();
                                ispitano = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
        void dodajLabel(TreeNode primaryKey){
            if(ispitano){
                int brojac = frame.getContentPane().getComponentCount() - 2;
                frame.getContentPane().remove(brojac);
                frame.getContentPane().remove(brojac);
            }
            frame.add(new JLabel(primaryKey.toString() + " ="));
            JTextField textField = new JTextField();
            textField.setPreferredSize(new Dimension(50,25));
            frame.add(textField);
        }
    }

