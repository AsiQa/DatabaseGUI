package actions;

import gui.guiComponents.CountAndAverage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class CountAndAverageAction extends AbstractDBAction {

    private CountAndAverage countAndAverage;

    public CountAndAverageAction(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        putValue(NAME, "Count & Average ");
        putValue(SHORT_DESCRIPTION, "count and average");
    }



    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        countAndAverage = new CountAndAverage();
        countAndAverage.pokreni();

    }
}