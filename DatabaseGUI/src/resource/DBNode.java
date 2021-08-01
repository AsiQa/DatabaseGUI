package resource;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.util.Enumeration;


public abstract class DBNode extends DefaultMutableTreeNode implements TreeNode{


    private String name;
    private DBNode parent;


    public DBNode(String name, DBNode parent) {
        this.name = name;
        this.parent = parent;
    }

    public DBNode getParent(){
        return parent;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParent(DBNode parent) {
        this.parent = parent;
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        return null;
    }

    @Override
    public int getChildCount() {
        return 0;
    }

    @Override
    public int getIndex(TreeNode node) {
        return 0;
    }

    @Override
    public boolean getAllowsChildren() {
        return false;
    }

    @Override
    public boolean isLeaf() {
        return true;
    }

    @Override
    public Enumeration children() {
        return null;
    }


    @Override
    public String toString() {
        return name;
    }
}
