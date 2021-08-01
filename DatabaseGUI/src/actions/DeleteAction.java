package actions;

import gui.guiComponents.DeleteGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class DeleteAction extends AbstractDBAction {

    private DeleteGUI deleteGUI;

    public DeleteAction(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        putValue(NAME, "  Delete  ");
        putValue(SHORT_DESCRIPTION, "Delete row");
    }



    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        //radi nesto
        System.out.println("DELETE");

        deleteGUI = new DeleteGUI();
        deleteGUI.pokreni();

    }
}
