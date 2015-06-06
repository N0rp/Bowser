package leap;

import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.InteractionBox;
import com.leapmotion.leap.Vector;
import javafx.geometry.Point2D;

/**
 * Created by Richard on 6/6/2015.
 */
public class LeapUtil {


    public static Finger getPinchingFinger(Hand hand){
        Finger pinchingFinger = null;

        if(!hand.fingers().fingerType(Finger.Type.TYPE_THUMB).isEmpty()){
            Finger thumb = hand.fingers().fingerType(Finger.Type.TYPE_THUMB).get(0);
            for(Finger finger : hand.fingers()){
                if(finger.type() != Finger.Type.TYPE_THUMB) {
                    if (pinchingFinger == null) {
                        pinchingFinger = finger;
                    } else if (finger.isValid()) {
                        float oldDistance = pinchingFinger.tipPosition().distanceTo(thumb.tipPosition());
                        float newDistance = finger.tipPosition().distanceTo(thumb.tipPosition());
                        if (newDistance < oldDistance) {
                            pinchingFinger = finger;
                        }
                    }
                }
            }
        }

        return pinchingFinger;
    }

    public static Point2D leapToApp(Vector leapPoint, InteractionBox iBox, int appWidth, int appHeight){
        Vector normalizedPoint = iBox.normalizePoint(leapPoint, false);

        float appX = normalizedPoint.getX() * appWidth;
        float appY = (1 - normalizedPoint.getY()) * appHeight;

        return new Point2D(appX, appY);
    }

    public static Vector leapToWorld(Vector leapPoint, InteractionBox iBox){
        leapPoint.setZ(leapPoint.getZ() * -1.0f); //right-hand to left-hand rule
        Vector normalized = iBox.normalizePoint(leapPoint, false);
        normalized = normalized.plus(new Vector(0.5f, 0f, 0.5f)); //recenter origin
        return normalized.times(100.0f); //scale
    }
}
