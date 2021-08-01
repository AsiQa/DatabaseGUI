package actions;

import gui.guiComponents.SearchGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
//komentar
public class SearchAction extends AbstractDBAction {

    SearchGUI searchGUI;

    public SearchAction(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        putValue(NAME, "    Search    ");
        putValue(SHORT_DESCRIPTION, "Search FRAME");

    }



    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        searchGUI = new SearchGUI();
        searchGUI.pokreni();

    }



}