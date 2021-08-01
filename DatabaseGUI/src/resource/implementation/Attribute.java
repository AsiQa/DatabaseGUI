package resource.implementation;


import resource.DBNode;
import resource.DBNodeComposite;
import resource.enums.AttributeType;
import resource.enums.ConstraintType;

import java.util.ArrayList;
import java.util.List;


public class Attribute extends DBNodeComposite {


    private AttributeType attributeType;
    private int length;
    //private Attribute inRelationWith;
    private ArrayList<Attribute> inRelationWith = new ArrayList<>();

    public Attribute(String name, DBNode parent) {
        super(name, parent);
    }

    public Attribute(String name, DBNode parent, AttributeType attributeType, int length) {
        super(name, parent);
        this.attributeType = attributeType;
        this.length = length;
    }

    @Override
    public void addChild(DBNode child) {
        if (child != null && child instanceof AttributeConstraint){
            AttributeConstraint attributeConstraint = (AttributeConstraint) child;
            this.getChildren().add(attributeConstraint);
        }
    }

    @Override
    public List<DBNode> getChildren() {
        return super.getChildren();
    }

    public AttributeType getAttributeType() {
        return attributeType;
    }

    public int getLength() {
        return length;
    }

    public ArrayList<Attribute> getInRelationWith() {
        return inRelationWith;
    }
}
