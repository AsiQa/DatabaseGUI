package actions;

import gui.guiComponents.UpdateGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class UpdateAction extends AbstractDBAction {
    UpdateGUI updateGUI;
    public UpdateAction(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        putValue(NAME, " Update ");
        putValue(SHORT_DESCRIPTION, "Update values");
    }



    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        updateGUI = new UpdateGUI();
        updateGUI.pokreni();
    }
}
