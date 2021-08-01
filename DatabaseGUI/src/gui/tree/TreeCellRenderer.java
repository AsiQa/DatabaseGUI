package gui.tree;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;


import resource.DBNode;
import resource.DBNodeComposite;
import resource.implementation.Attribute;
import resource.implementation.AttributeConstraint;
import resource.implementation.Entity;
import resource.implementation.InformationResource;

public class TreeCellRenderer extends DefaultTreeCellRenderer{

    JTree tree;

    public TreeCellRenderer(JTree tree) {
        this.tree = tree;
    }

    public TreeCellRenderer() {
        // TODO Auto-generated constructor stub
        //this.tree = tree;
    }

    @Override
    public Component getTreeCellRendererComponent(
            JTree tree,
            Object value,
            boolean sel,
            boolean expanded,
            boolean leaf,
            int row,
            boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        Object userObject = node.getUserObject();
//        System.out.println(userObject.toString());

        if (userObject instanceof InformationResource) {
            setIcon(new ImageIcon("icons/IR.png"));
        }
        if (userObject instanceof Entity) {
            setIcon(new ImageIcon("icons/ENTITY.png"));
        }
        if (userObject instanceof Attribute) {
            setIcon(new ImageIcon("icons/ATTRIBUTE.png"));
        }
        if (userObject instanceof AttributeConstraint) {
            if(((AttributeConstraint) userObject).getConstraintType().toString().equalsIgnoreCase("PRIMARY_KEY"))
                setIcon(new ImageIcon("icons/PK.png"));

            if(((AttributeConstraint) userObject).getConstraintType().toString().equalsIgnoreCase("FOREIGN_KEY"))
                setIcon(new ImageIcon("icons/FK.png"));

            if(((AttributeConstraint) userObject).getConstraintType().toString().equalsIgnoreCase("NULLABLE"))
                setIcon(new ImageIcon("icons/NULLABLE.png"));

            if(((AttributeConstraint) userObject).getConstraintType().toString().equalsIgnoreCase("NOT_NULL"))
                setIcon(new ImageIcon("icons/NOT_NULL.png"));
        }
        return this;
    }


}
