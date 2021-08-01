package gui;

import actions.ActionManager;
import app.AppCore;

import controller.treeController;
import gui.guiComponents.SearchGUI;
import gui.table.Table;
import gui.tree.Tree;
import gui.tree.TreeCellRenderer;
import observer.Notification;
import observer.Subscriber;
import observer.enums.NotificationCode;
import resource.DBNode;
import resource.DBNodeComposite;
import resource.implementation.Entity;
import resource.implementation.InformationResource;


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class MainFrame extends JFrame implements Subscriber {

    private static MainFrame instance = null;

    private AppCore appCore;
    private ActionManager actionManager;
    private Toolbar toolbar;
    private int broj = 0;
    private JTabbedPane tp;
    private JTabbedPane tp2;
    private gui.table.TableModel tableModelzaSearch;
    private Table jTableA,jTableB;
    private JScrollPane jsp;
    private JPanel bottomStatus;
    private JTree tree;
    private DefaultTreeModel treeModel;
    private JButton pretragaButton;
    private InformationResource ir;
    private JScrollPane scrollPaneTREE;
    //private InformationResource ir = MainFrame.getInstance().getAppCore().loadResourceNODE();
    private SearchGUI searchGUI;

    private JSplitPane splitPaneV,splitPaneH;


    private MainFrame() {

    }

    public static MainFrame getInstance(){
        if (instance==null){
            instance=new MainFrame();
            instance.initialise();
        }
        return instance;
    }


    private void initialise() {

        this.setTitle("GUI INTERFEJS");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        actionManager = new ActionManager();
        toolbar = new Toolbar();

        tp = new JTabbedPane();
        tp2 = new JTabbedPane();

        tp2.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {

                JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
                int index = sourceTabbedPane.getSelectedIndex();
                if(index != -1) {
                   // System.out.println("RELATION Tab changed to: " + sourceTabbedPane.getTitleAt(index));
                    getAppCore().readDataFromTable2((sourceTabbedPane.getTitleAt(index)));
                }
            }
        });

        tp.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {

                JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
                int index = sourceTabbedPane.getSelectedIndex();
                System.out.println("Tab changed to: " + sourceTabbedPane.getTitleAt(index));
                getAppCore().readDataFromTable((sourceTabbedPane.getTitleAt(index)));


                tp2.removeAll();

                ArrayList<String> listaTabela = new ArrayList<>();
                String selektovano = getIr().getChildByName(tp.getTitleAt(tp.getSelectedIndex())).getChildAt(0).toString();
                //System.out.println("PK : " + selektovano);
                InformationResource ir = MainFrame.getInstance().getIr();
                java.util.List<DBNode> entity = ir.getChildren();
                for(DBNode o : entity){
                    List<DBNode> attribute =  ((DBNodeComposite) o).getChildren();
                    for(Object a : attribute){
                        if(a.toString().equalsIgnoreCase(selektovano)){
                            listaTabela.add(o.toString().toUpperCase());
                           // System.out.println("TABLEA = " + o.toString().toUpperCase());
                           // System.out.println("SELETKOVANO = " + a.toString().toUpperCase());
                        }
                    }
                }
               // System.out.println("______LISTA TABELA______ ");



                for(String s : listaTabela){
                    JTable table2 = new JTable();
                    table2.setPreferredScrollableViewportSize(new Dimension(500, 400));
                    table2.setFillsViewportHeight(true);
                   // System.out.println(s);
                    tp2.addTab(s,table2);
                }


            }
        });



        JPanel panelA = new JPanel();
        JPanel panelB1 = new JPanel();
        JPanel panelB2 = new JPanel();
        JPanel panelC1 = new JPanel();
        JPanel panelC2 = new JPanel();





        splitPaneH = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,panelB1,panelB2);
        splitPaneH.setDividerLocation(300);
        panelA.add(splitPaneH);
        splitPaneV = new JSplitPane(JSplitPane.VERTICAL_SPLIT,panelC1,panelC2);

        tree = new Tree();

        // SCROLLPANE JE PREMALI
        JScrollPane scrollPaneTREE = new JScrollPane(tree);
        scrollPaneTREE.setPreferredSize(new Dimension(200,800));
        //scrollPaneTREE.setMinimumSize(panelB1.getSize());
        panelB1.add(scrollPaneTREE);

        panelB2.add(splitPaneV);

        tp.setPreferredSize(new Dimension(0,22));
        tp2.setPreferredSize(new Dimension(0,22));


        jTableA = new Table();
        jTableA.setPreferredScrollableViewportSize(new Dimension(500, 400));
        jTableA.setFillsViewportHeight(true);

        jTableB = new Table();
        jTableB.setPreferredScrollableViewportSize(new Dimension(500, 400));
        jTableB.setFillsViewportHeight(true);



        JScrollPane scroll1 = new JScrollPane(jTableA);
        JScrollPane scroll2 = new JScrollPane(jTableB);

        panelC1.setLayout(new BoxLayout(panelC1, BoxLayout.Y_AXIS));
        panelC1.add(tp);
        panelC1.add(scroll1);
        panelC1.add(toolbar);


        panelC2.setLayout(new BoxLayout(panelC2, BoxLayout.Y_AXIS));
        panelC2.add(tp2);
        panelC2.add(scroll2);





        //this.getContentPane().add(panelA);
        this.getContentPane().add(splitPaneH);

       // this.add(new JScrollPane(jTableA));

        //velicina prozora je mala, mora da se poveca
        this.setSize(1200,1000);
        //this.pack();          //podesava velicinu frame-a onoliku kolike su njene komponente
        this.setLocationRelativeTo(null);
        this.setVisible(true);



    }

    public Table getjTableA() {
        return jTableA;
    }

    public Table getjTableB() {
        return jTableB;
    }

    public void setAppCore(AppCore appCore) {
        this.appCore = appCore;
        this.appCore.addSubscriber(this);
        this.ir = appCore.loadResourceNODE();
        this.jTableA.setModel(appCore.getTableModel());
        this.jTableB.setModel(appCore.getTableModel2());
        DefaultTreeModel treeModel = new DefaultTreeModel(ir);
        //this.tree = new Tree();
        this.tree.setModel(treeModel);
        this.tree.addTreeSelectionListener(new treeController());
        this.tableModelzaSearch = appCore.getTableModel();
    }

    public gui.table.TableModel getTableModelzaSearch() {
        return tableModelzaSearch;
    }

    public InformationResource getIr() {
        return ir;
    }

    @Override
    public void update(Notification notification) {

        if (notification.getCode() == NotificationCode.RESOURCE_LOADED){
            System.out.println((InformationResource)notification.getData());
        }

        else{
            jTableA.setModel((TableModel) notification.getData());
            jTableB.setModel((TableModel) notification.getData());
        }

    }

    public AppCore getAppCore() {
        return appCore;
    }

    public ActionManager getActionManager() {
        return actionManager;
    }

    public JTabbedPane getTp2() {
        return tp2;
    }

    public JTabbedPane getTp(){return tp;}
}
