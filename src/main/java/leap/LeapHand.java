package leap;

import com.leapmotion.leap.Finger;
import com.leapmotion.leap.FingerList;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.InteractionBox;
import javafx.geometry.Point2D;

/**
 * Created by Richard on 6/6/2015.
 */
public class LeapHand {

    public Hand getHand() {
        return hand;
    }

    public Point2D palmPositionNormalized(){
        return palmPositionNormalized;
    }

    public Finger getPinchingFinger() {
        return pinchingFinger;
    }

    public LeapHandState getHandState() {
        return handState;
    }

    public float getGrabStrenght() {
        return hand.grabStrength();
    }

    public float getPinchStrength() {
        return hand.pinchStrength();
    }

    public FingerList getFingers() {
        return hand.fingers();
    }

    public Finger getFingerIndex() {
        return fingerIndex;
    }

    public Finger getFingerMiddle() {
        return fingerMiddle;
    }

    public Finger getFingerRing() {
        return fingerRing;
    }

    public Finger getFingerPinky() {
        return fingerPinky;
    }

    private final Hand hand;
    private final Point2D palmPositionNormalized;
    private final Finger pinchingFinger;
    private final LeapHandState handState;

    private Finger fingerIndex = null;
    private Finger fingerMiddle = null;
    private Finger fingerRing = null;
    private Finger fingerPinky = null;

    public LeapHand(Hand hand, InteractionBox iBox, int appWidth, int appHeight){
        this.hand = hand;
        this.palmPositionNormalized = LeapUtil.leapToApp(hand.palmPosition(), iBox, appWidth, appHeight);
        this.pinchingFinger = LeapUtil.getPinchingFinger(hand);
        this.handState = createHandState(hand);

        setFingers(hand);
    }

    private void setFingers(Hand hand){

        FingerList fingers = hand.fingers();
        for(Finger f: fingers){
            if(f.isFinger() && f.isValid()){
                switch(f.type()){
                    case TYPE_INDEX: fingerIndex = f; break;
                    case TYPE_MIDDLE: fingerMiddle = f; break;
                    case TYPE_RING: fingerRing = f; break;
                    case TYPE_PINKY: fingerPinky = f; break;
                }
            }
        }
    }

    private LeapHandState createHandState(Hand hand){
        LeapHandState handState = LeapHandState.NORMAL;
        if(!hand.isValid()){
            handState = LeapHandState.INVALID;
        }else if(hand.pinchStrength() >= 0.8){
            handState = LeapHandState.PINCH;
        }else if(hand.grabStrength() >= 0.95){
            handState = LeapHandState.FIST;
        }

        return handState;
    }

}
