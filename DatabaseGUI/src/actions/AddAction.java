package actions;

import gui.guiComponents.InsertGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class AddAction extends AbstractDBAction {

    private InsertGUI insertGUI;

    public AddAction(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        putValue(NAME, "    Add    ");
        putValue(SHORT_DESCRIPTION, "Add row");
    }



    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        insertGUI = new InsertGUI();
        insertGUI.pokreni();
    }
}