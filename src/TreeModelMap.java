
import java.io.Serializable;
import java.util.HashMap;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Shatha Suliman
 */
public class TreeModelMap implements Serializable{
    DefaultTreeModel defaultTreeModel;
    HashMap<Integer, DefaultMutableTreeNode> treeMap;

    public DefaultTreeModel getDefaultTreeModel() {
        return defaultTreeModel;
    }

    public void setDefaultTreeModel(DefaultTreeModel defaultTreeModel) {
        this.defaultTreeModel = defaultTreeModel;
    }

    public HashMap<Integer, DefaultMutableTreeNode> getTreeMap() {
        return treeMap;
    }

    public void setTreeMap(HashMap<Integer, DefaultMutableTreeNode> treeMap) {
        this.treeMap = treeMap;
    }
    
}
