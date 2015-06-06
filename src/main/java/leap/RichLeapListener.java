package leap;


import com.leapmotion.leap.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * Created by Richard on 6/6/2015.
 */
public class RichLeapListener extends Listener {

    private final BooleanProperty doneList = new SimpleBooleanProperty(false);

    private LeapHand leftHand = null;
    private LeapHand rightHand = null;

    private int appWidth;
    private int appHeight;

    public RichLeapListener(int appWidth, int appHeight){
        this.appWidth = appWidth;
        this.appHeight = appHeight;
    }

    @Override
    public void onFrame(Controller controller) {
        Frame frame = controller.frame();
        leftHand = null;
        rightHand = null;

        doneList.set(false);
        if (!frame.hands().isEmpty()) {
            Screen screen = controller.locatedScreens().get(0);
            if (screen != null && screen.isValid()){
                // if you can find previous hands again
                // frame.hand(handID);

                for(Hand h: frame.hands()){
                    if(h.isValid()){
                        if(h.isLeft()){
                            leftHand = new LeapHand(h, frame.interactionBox(), appWidth, appHeight);
                        }else if(h.isRight()){
                            rightHand = new LeapHand(h, frame.interactionBox(), appWidth, appHeight);
                            LeapHandState handState = rightHand.getHandState();
                        }
                    }
                }
            }
        }
        doneList.set(true);
    }

    /**
     *
     * @return the left hand or <code>null</code> if none was found
     */
    public LeapHand getLeftHand(){
        return leftHand;
    }

    /**
     *
     * @return the right hand or <code>null</code> if none was found
     */
    public LeapHand getRightHand(){
        return rightHand;
    }

    public BooleanProperty doneListProperty() {
        return doneList;
    }

}
