package resource;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public abstract class DBNodeComposite extends DBNode {

    private List<DBNode> children;


    public DBNodeComposite(String name, DBNode parent) {
        super(name, parent);
        this.children = new ArrayList<>();
    }

    @Override
    public DBNode getParent() {
        return super.getParent();
    }

    public DBNodeComposite(String name, DBNode parent, ArrayList<DBNode> children) {
        super(name, parent);
        this.children = children;
    }


    public abstract void addChild(DBNode child);

    public DBNode getChildByName(String name) {
        for (DBNode child : this.getChildren()) {
            if (name.equals(child.getName())) {
                return child;
            }
        }
        return null;
    }


    public List<DBNode> getChildren() {
        return children;
    }

    public void setChildren(List<DBNode> children) {
        this.children = children;
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        return children.get(childIndex);
    }

    @Override
    public int getChildCount() {
        return children.size();
    }

    @Override
    public int getIndex(TreeNode node) {
        return children.indexOf(node);
    }

    @Override
    public boolean getAllowsChildren() {
        return true;
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public Enumeration children() {
        return (Enumeration) children;
    }

}
