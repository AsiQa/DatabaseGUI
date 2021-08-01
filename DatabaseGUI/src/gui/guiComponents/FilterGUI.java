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

public class FilterGUI extends JFrame implements ItemListener {


    static JPanel panel;
    static JFrame frame = new JFrame("FILTER");
    static JLabel label1, label2;
    static Boolean ispitano = false;
    static Boolean ispitanComboString = false;
    static Boolean ispitanComboNotString = false;
    static Boolean ispitanComboAndOr = false;
    static int brojac_poziva_statusChanged = 0;
    static int brojac = 2;
    static MSSQLrepository repository;

    static JComboBox tabele,kolone;
    static JComboBox comboBoxAndOr,comboBoxZnaci,comboBoxString;
    static JTextField textField;
    static DBNodeComposite entity,node;
    static Attribute kolona;
    static boolean pokrenut = false;

    public boolean isPokrenut() {
        return pokrenut;
    }

    static JButton searchButton;

    public static JPanel getPanel() {
        return panel;
    }

    public void clearFrame(){
        frame.removeAll();
    }

    public void setPokrenut(boolean pokrenut) {
        SearchGUI.pokrenut = pokrenut;
    }

    public static void main(String[] args){
        DeleteGUI deleteGUI = new DeleteGUI();
        deleteGUI.pokreni();
    }

    public void pokreni(){
        frame.setLocationRelativeTo(null);
        pokrenut = true;
        ispitano = false;
        frame.getContentPane().removeAll();

        /*AppCore appCore = new AppCore();
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.setAppCore(appCore);
*/
        //frame = new JFrame("gui");
        frame.setLayout(new FlowLayout());
        //DropDownUpiti object = new DropDownUpiti();


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
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                java.util.List<Component> lc = getAllComponents(frame.getRootPane());

                StringBuilder str = new StringBuilder();

                System.out.println("------------------------------");



                for (Component c : lc){

                    if(c instanceof JLabel){
//                        System.out.println(((JLabel) c).getText());
                        str.append(((JLabel) c).getText() + " ");
                    }

                    else if(c instanceof JComboBox && !(c.toString().startsWith("javax"))){
                        str.append(((JComboBox) c).getSelectedItem().toString() + " ");
                    }

                    else if(c instanceof JTextField){
                        System.out.println(((JTextField) c).getText());
                        str.append(((JTextField) c).getText() + " ");
                    }
                }
                //String from = str.substring(0);
                System.out.println(str);
                System.out.println("------------------------------");
                //MainFrame.getInstance().getAppCore().getTableModel().setRows(MainFrame.getInstance().getAppCore().getDatabase().searchData(from));
                //MainFrame.getInstance().getTableModelzaSearch().setRows(repository.get(from));


            }
        });
        frame.add(searchButton);
        frame.add(label1);  //0
        frame.add(tabele);  //1
        frame.add(label2);  //2
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


        // dodati metodu koja uklanja panel iz dodajUpit u slucaju da se korisnik predomisli
        // npr. prvo selektujemo DEPARTMENTS -> pojavi nam se novi panel sa kolonama DEPARTMENTS tabele
        // drugo selektujemo JOB_HISTORY -> brise se panel za DEPARTMENTS kolone i dodaje se panel za JOB_HISTORY kolone


        brojac_poziva_statusChanged++;
        if(brojac_poziva_statusChanged % 2 == 1){
            if(e.getSource() == tabele){
                System.out.println(tabele.getSelectedItem());
                // node = (DBNodeComposite) MainFrame.getInstance().getAppCore().loadResourceNODE().getChildByName(tabele.getSelectedItem().toString());
                ArrayList<String> data = new ArrayList<>();
                node = (DBNodeComposite) entity.getChildByName(tabele.getSelectedItem().toString());
                List<DBNode> entityList = node.getChildren();
                for(Object o : entityList){
                    int brojac = ((Attribute) o).getChildCount();
                    for(int i = 0 ; i < brojac ; i ++){
                        AttributeConstraint atconst = (AttributeConstraint) ((Attribute) o).getChildAt(i);
                        if((atconst).getConstraintType() == ConstraintType.PRIMARY_KEY){
                            System.out.println(atconst.getParent().toString());
                            //pokreni addLabel funkciju
                            dodajLabel(atconst.getParent());
                            frame.revalidate();
                            frame.pack();
                            ispitano = true;
                            break;
                        }
                    }
                    //out.println("not_PRIMARY_KEY : " + o.toString());
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

