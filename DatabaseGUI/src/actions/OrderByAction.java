package actions;

import gui.guiComponents.OrderByGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class OrderByAction extends AbstractDBAction {

    private OrderByGUI orderByAction;

    public OrderByAction(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        putValue(NAME, "  Sort  ");
        putValue(SHORT_DESCRIPTION, "Sort rows");
    }



    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        //radi nesto
        //System.out.println("DELETE");

        orderByAction = new OrderByGUI();
        orderByAction.pokreni();

    }
}
