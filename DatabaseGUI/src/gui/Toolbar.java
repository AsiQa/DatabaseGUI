package gui;

import javax.swing.*;
import java.awt.*;

public class Toolbar extends JToolBar {

    public Toolbar(){
        super();
        addSeparator(new Dimension(300,50));
        add(MainFrame.getInstance().getActionManager().getDeleteAction());
        add(MainFrame.getInstance().getActionManager().getUpdateAction());
        add(MainFrame.getInstance().getActionManager().getAddAction());
        add(MainFrame.getInstance().getActionManager().getCountAndAverageAction());
        add(MainFrame.getInstance().getActionManager().getSearchAction());
        add(MainFrame.getInstance().getActionManager().getFilterAction());
        add(MainFrame.getInstance().getActionManager().getOrderByAction());


    }
}
