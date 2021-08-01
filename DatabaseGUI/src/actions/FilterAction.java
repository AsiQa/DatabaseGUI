package actions;

import gui.guiComponents.FilterGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class FilterAction extends AbstractDBAction {

    private FilterGUI filterAction;

    public FilterAction(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        putValue(NAME, "  Filter  ");
        putValue(SHORT_DESCRIPTION, "Filter rows");
    }



    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        //radi nesto
        //System.out.println("DELETE");

        filterAction = new FilterGUI();
        filterAction.pokreni();

    }
}
