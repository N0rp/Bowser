package leap.gesture;

import com.leapmotion.leap.Vector;

/**
 * Created by Richard on 6/7/2015.
 */
public class LeapMagic {

    public LeapHand getLeftHand() {
        return leftHand;
    }

    public LeapHand getRightHand() {
        return rightHand;
    }

    public LeapMagicState getState() {
        return state;
    }

    public Vector getHandDistance() {
        return handDistance;
    }

    public float getTouchStrength() {
        return touchStrength;
    }

    private LeapHand leftHand;
    private LeapHand rightHand;

    private LeapMagicState state;

    private Vector handDistance;
    private float touchStrength;


    public LeapMagic(LeapHand leftHand, LeapHand rightHand){
        this.leftHand = leftHand;
        this.rightHand = rightHand;
        this.state = createState(leftHand, rightHand);
        if(leftHand != null && rightHand != null) {
            handDistance = leftHand.getHand().palmPosition().minus(rightHand.getHand().palmPosition());
            touchStrength = createTouchStrength(handDistance);
        }
    }

    private float createTouchStrength(Vector distance){
        float touchStrength = 1 - ((distance.magnitude() - 110) / 500);
        if(touchStrength > 1){
            touchStrength = 1;
        }else if(touchStrength < 0){
            touchStrength = 0;
        }
        return touchStrength;
    }

    private LeapMagicState createState(LeapHand leftHand, LeapHand rightHand){
        LeapMagicState state = LeapMagicState.UNKNOWN;
        if(leftHand == null || rightHand == null || leftHand.getHandState() == LeapHandState.INVALID
                || rightHand.getHandState() == LeapHandState.INVALID){
            state = LeapMagicState.INVALID;
        }else{

        }

        return state;
    }

}
