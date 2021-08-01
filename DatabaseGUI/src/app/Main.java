package app;

import gui.MainFrame;
import gui.tree.TreeCellRenderer;
import resource.DBNode;
import resource.data.Row;
import resource.implementation.InformationResource;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        AppCore appCore = new AppCore();
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.setAppCore(appCore);

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
