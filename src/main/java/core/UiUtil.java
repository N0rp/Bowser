package core;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import sun.plugin.util.UIUtil;

/**
 * Created by Richard on 6/7/2015.
 */
public class UiUtil {



    public static Node getTarget(Node currentNode, double localX, double localY){
        Node target = null;

        if(currentNode instanceof Parent){
            Parent currentParent = (Parent) currentNode;
            for(Node node : currentParent.getChildrenUnmodifiable()){
                // TODO respect z-order
                Point2D childPoint = node.parentToLocal(localX, localY);
                target = UiUtil.getTarget(node, childPoint.getX(), childPoint.getY());
                if(target != null) {
                    break;
                }
            }
        }

        if(target == null && currentNode.contains(localX, localY)){
            target = currentNode;
        }

        return target;
    }
}
