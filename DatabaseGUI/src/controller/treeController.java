package controller;

import gui.MainFrame;
import gui.table.TableModel;
import resource.DBNode;
import resource.DBNodeComposite;
import resource.implementation.Attribute;
import resource.implementation.Entity;
import resource.implementation.InformationResource;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class treeController implements TreeSelectionListener {


    @Override
    public void valueChanged(TreeSelectionEvent e) {

        TreePath path = e.getPath();
        boolean b = false;
        int i=0;

        if(path.getLastPathComponent() instanceof Entity){

            JTabbedPane tp = MainFrame.getInstance().getTp();
            JTable table = new JTable();
            table = new JTable();
            table.setPreferredScrollableViewportSize(new Dimension(500, 400));
            table.setFillsViewportHeight(true);
            for(i=0; i<tp.getTabCount(); i++){
                if(tp.getTitleAt(i).equals(path.getLastPathComponent().toString().toUpperCase())){
                    b = true;
                    break;
                };
            }
            if(b) {
                MainFrame.getInstance().getAppCore().readDataFromTable((path.getLastPathComponent().toString().toUpperCase()));
                tp.setSelectedIndex(i);
            }
            else {
                tp.addTab(path.getLastPathComponent().toString().toUpperCase(), table);
                tp.setPreferredSize(new Dimension(0,22));
                MainFrame.getInstance().getAppCore().readDataFromTable((path.getLastPathComponent().toString().toUpperCase()));
                tp.setSelectedIndex(tp.getTabCount()-1);
            }
            JTabbedPane tp2 = MainFrame.getInstance().getTp2();
            tp2.removeAll();

            ArrayList<String> listaTabela = new ArrayList<>();
            String selektovano = ((Entity) path.getLastPathComponent()).getChildAt(0).toString();
            //System.out.println("PK : " + selektovano);
            InformationResource ir = MainFrame.getInstance().getIr();
            List<DBNode> entity = ir.getChildren();
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
            //System.out.println("______LISTA TABELA______ ");
            for(String s : listaTabela){
                JTable table2 = new JTable();
                table2.setPreferredScrollableViewportSize(new Dimension(500, 400));
                table2.setFillsViewportHeight(true);
                //System.out.println(s);
                tp2.addTab(s,table2);
            }
        }
        if(path.getLastPathComponent() instanceof Attribute){

            JTabbedPane tp2 = MainFrame.getInstance().getTp2();
            tp2.removeAll();

            ArrayList<String> listaTabela = new ArrayList<>();
            String selektovano = path.getLastPathComponent().toString();
            InformationResource ir = MainFrame.getInstance().getIr();
            List<DBNode> entity = ir.getChildren();
            for(DBNode o : entity){
                List<DBNode> attribute =  ((DBNodeComposite) o).getChildren();
                for(Object a : attribute){
                    if(a.toString().equalsIgnoreCase(selektovano)){
                        listaTabela.add(o.toString().toUpperCase());
                        //System.out.println("TABLEA = " + o.toString().toUpperCase());
                       // System.out.println("SELETKOVANO = " + a.toString().toUpperCase());
                    }
                }
            }
            //System.out.println("______LISTA TABELA______ ");

            for(String s : listaTabela){
                JTable table = new JTable();
                table.setPreferredScrollableViewportSize(new Dimension(500, 400));
                table.setFillsViewportHeight(true);
                //System.out.println(s);
                tp2.addTab(s,table);
            }

            // for koji ide po tabelama
            // for za svaku kolonu u tabeli
            // if(seletovano == svaka kolona)
            // ubaciti trenutnu tabelu u listu tabela
            // end for
            // end for
            // izvuci tablemodel2 i pozovi readDataFromTable2
            // for dokle got traje lista tabela
            // readdatafromtable2 i ubaci u tab
            //tp2.addTab(path.getLastPathComponent().toString().toUpperCase(), table);
            //MainFrame.getInstance().getAppCore().readDataFromTable2((path.getLastPathComponent().toString().toUpperCase()));
        }
    }
}
