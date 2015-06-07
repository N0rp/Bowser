package leap;

import com.leapmotion.leap.Finger;
import com.leapmotion.leap.FingerList;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.InteractionBox;
import javafx.geometry.Point2D;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Richard on 6/6/2015.
 */
public class LeapHand {

    /*
        Hand
     */
    public Hand getHand() {
        return hand;
    }

    public Point2D palmPositionNormalized(){
        return palmPositionNormalized;
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

    /*
        Fingers
     */
    public FingerList getFingers() {
        return hand.fingers();
    }

    public List<Finger.Type> getMissingFingers() {
        return missingFingers;
    }

    public Finger getPinchingFinger() {
        return pinchingFinger;
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
    private final LeapHandState handState;

    private List<Finger.Type> missingFingers;
    private final Finger pinchingFinger;

    private Finger fingerIndex = null;
    private Finger fingerMiddle = null;
    private Finger fingerRing = null;
    private Finger fingerPinky = null;

    /**
     * Create a new Leap Hand
     *
     * @param hand
     * @param iBox
     * @param appWidth
     * @param appHeight
     */
    public LeapHand(Hand hand, InteractionBox iBox, int appWidth, int appHeight){
        this.hand = hand;
        this.palmPositionNormalized = LeapUtil.leapToApp(hand.palmPosition(), iBox, appWidth, appHeight);
        this.pinchingFinger = LeapUtil.getPinchingFinger(hand);
        this.missingFingers = createAllFingerTypes();
        setFingers(hand);

        this.handState = createHandState(hand);
    }

    /**
     * Returns if all fingers are missing
     *
     * @param exactMatch if <code>true</code> only the given fingers can be missing and not more than that
     * @param missing
     * @return
     */
    public boolean areMissingFingers(boolean exactMatch, Finger.Type... missing){
        boolean areMissing = missingFingers.containsAll(Arrays.asList(missing));
        if(exactMatch){
            areMissing &= missingFingers.size() == missing.length;
        }
        return areMissing;
    }

    private List<Finger.Type> createAllFingerTypes(){
        List<Finger.Type> allFingers = new LinkedList<>();
        allFingers.addAll(Arrays.asList(new Finger.Type[]{
                Finger.Type.TYPE_INDEX,
                Finger.Type.TYPE_MIDDLE,
                Finger.Type.TYPE_PINKY,
                Finger.Type.TYPE_RING,
                Finger.Type.TYPE_THUMB}));
        return allFingers;
    }

    private void setFingers(Hand hand){
        FingerList fingers = hand.fingers();
        for(Finger f: fingers){
            if(f.isFinger() && f.isValid()){
                switch(f.type()){
                    case TYPE_INDEX: fingerIndex = f; break;
                    case TYPE_MIDDLE: fingerMiddle = f;break;
                    case TYPE_RING: fingerRing = f; break;
                    case TYPE_PINKY: fingerPinky = f;  break;
                }
                if(f.isExtended()){
                    missingFingers.remove(f.type());
                }
            }
        }
    }

    private LeapHandState createHandState(Hand hand){
        LeapHandState handState = LeapHandState.NORMAL;
        if(!hand.isValid()){
            handState = LeapHandState.INVALID;
        }else if(hand.grabStrength() >= 0.95){
            handState = LeapHandState.FIST;
            if(missingFingers.size() == 4 && !missingFingers.contains(Finger.Type.TYPE_THUMB)){
                handState = LeapHandState.SIGN_VOTE_START;
            }
        }else if(hand.pinchStrength() >= 0.8 || missingFingers.size() > 0){
            handState = LeapHandState.SIGN;
            if(missingFingers.size() == 1){
                handState = LeapHandState.SIGN_ONE_MISSING;
                if(missingFingers.get(0).equals(Finger.Type.TYPE_THUMB)){
                    handState = LeapHandState.SIGN_NUMBER_FOUR;
                }

            }else if(areMissingFingers(true, Finger.Type.TYPE_RING, Finger.Type.TYPE_PINKY)){
                handState = LeapHandState.SIGN_NUMBER_THREE;
            }else if(areMissingFingers(true, Finger.Type.TYPE_MIDDLE, Finger.Type.TYPE_RING, Finger.Type.TYPE_PINKY)){
                handState = LeapHandState.SIGN_NUMBER_TWO;
            }else if(areMissingFingers(true, Finger.Type.TYPE_THUMB, Finger.Type.TYPE_MIDDLE, Finger.Type.TYPE_RING, Finger.Type.TYPE_PINKY)){
                handState = LeapHandState.SIGN_NUMBER_ONE;
            }else if(areMissingFingers(true, Finger.Type.TYPE_RING, Finger.Type.TYPE_PINKY)){
                handState = LeapHandState.SIGN_NUMBER_THREE;
            }else if(areMissingFingers(true, Finger.Type.TYPE_THUMB, Finger.Type.TYPE_INDEX)){
                handState = LeapHandState.SIGN_OK;
            }else if(areMissingFingers(true, Finger.Type.TYPE_THUMB, Finger.Type.TYPE_MIDDLE, Finger.Type.TYPE_RING)){
                handState = LeapHandState.SIGN_ROCK;
            }else if(areMissingFingers(true, Finger.Type.TYPE_THUMB, Finger.Type.TYPE_MIDDLE, Finger.Type.TYPE_RING)){
                handState = LeapHandState.SIGN_ROCK;
            }else if(areMissingFingers(true, Finger.Type.TYPE_INDEX, Finger.Type.TYPE_MIDDLE, Finger.Type.TYPE_RING, Finger.Type.TYPE_PINKY)){
                handState = LeapHandState.SIGN_VOTE_START;
            }
        }else{
            handState = LeapHandState.SIGN_NUMBER_FIVE;
        }

        return handState;
    }

}
