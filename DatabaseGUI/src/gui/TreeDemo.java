package gui;

import app.AppCore;
import database.Database;
import database.MSSQLrepository;
import database.Repository;
import resource.DBNode;
import resource.DBNodeComposite;
import resource.implementation.InformationResource;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.xml.crypto.Data;

public class TreeDemo{

    // Treba da napravimo tree , negde da ga inicijalizujemo
    // treba da loadujemo resurs nekako
    // treba da ...

    // svaki objekat iz ir ispitaj da li je entity ( tabela ) i dodaj u listu dece ir ( to radi metoda ir.addChild )
    /*for(Object o : ir){
        root.add(ir.addChild(o));
    }*/

    // unutar MSSQLrepository imamo dalje smernice



    public static void main(String[] args){

        AppCore appCore = new AppCore();
        MainFrame.getInstance().setAppCore(appCore);

        InformationResource ir = MainFrame.getInstance().getAppCore().loadResourceNODE();
        DefaultTreeModel treeModel = new DefaultTreeModel(ir);
        JTree tree = new JTree(treeModel);
        //System.out.println(treeModel.getChild(treeModel.getRoot(),1));
        //getChild prikazuje da ir(root) nema decu


        //Tree tree = new Tree();
        //tree.setModel(treeModel);

        

        //DefaultMutableTreeNode root = new DefaultMutableTreeNode(ir);
        //TreeModel model = new TreeModel(ir);


        /*for(int i = 0 ; i < 10 ; i ++){
            StringBuilder sb = new StringBuilder();
            sb.append(i);
            DefaultMutableTreeNode child = new DefaultMutableTreeNode(sb.toString());
            root.add(child);
            for(int j = 0 ; j < 10 ; j ++){
                StringBuilder sbJ = new StringBuilder();
                sbJ.append(j+10);
                DefaultMutableTreeNode childJ = new DefaultMutableTreeNode(sbJ.toString());
                child.add(childJ);
            }
        }*/

        //private DBNode node;


        JFrame frame = new JFrame();
        frame.setVisible(true);
        //frame.pack();
        frame.setSize(300,500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new JScrollPane(tree));





    }



}
