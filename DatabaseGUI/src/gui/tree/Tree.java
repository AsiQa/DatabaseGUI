package gui.tree;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreePath;


public class Tree extends JTree implements javax.swing.tree.TreeCellRenderer {

    public Tree() {
        setCellRenderer(new TreeCellRenderer());
    }


    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        return null;
    }
}
