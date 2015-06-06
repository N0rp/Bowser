package leap;

import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.HandList;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

/**
 * Created by Richard on 6/6/2015.
 */
public class LeapEvent extends Event {

    public static EventType<LeapEvent> LEAP_ALL = new EventType<>("LEAP_ALL");
    public static EventType<LeapEvent> BEFORE_STORE = new EventType<>(LEAP_ALL, "BEFORE_STORE");

    public LeapHand getHandLeft() {
        return handLeft;
    }

    public LeapHand getHandRight() {
        return handRight;
    }

    private LeapHand handLeft;

    private LeapHand handRight;

    /**
     * Create a new Leap Event
     *
     * @param eventType
     * @param handLeft
     * @param handRight
     */
    public LeapEvent(EventType<? extends Event> eventType, LeapHand handLeft, LeapHand handRight) {
        super(eventType);
        this.handLeft = handLeft;
        this.handRight = handRight;
    }

    /**
     * Create a new Leap Event
     *
     * @param source
     * @param target
     * @param eventType
     * @param handLeft
     * @param handRight
     */
    public LeapEvent(Object source, EventTarget target, EventType<? extends Event> eventType, LeapHand handLeft, LeapHand handRight) {
        super(source, target, eventType);
        this.handLeft = handLeft;
        this.handRight = handRight;
    }
}
