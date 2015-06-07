package leap;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import leap.gesture.LeapHand;
import leap.gesture.LeapMagic;

/**
 * Created by Richard on 6/6/2015.
 */
public class LeapEvent extends Event {

    public static EventType<LeapEvent> LEAP_ALL = new EventType<>("LEAP_ALL");
    public static EventType<LeapEvent> BEFORE_STORE = new EventType<>(LEAP_ALL, "BEFORE_STORE");

    public LeapHand getHandLeft() {
        return leapMagic.getLeftHand();
    }

    public LeapHand getHandRight() {
        return leapMagic.getRightHand();
    }

    public LeapMagic getLeapMagic() {
        return leapMagic;
    }

    private LeapMagic leapMagic;

    /**
     * Create a new Leap Event
     *
     * @param eventType
     * @param leapMagic
     */
    public LeapEvent(EventType<? extends Event> eventType, LeapMagic leapMagic) {
        super(eventType);
        this.leapMagic = leapMagic;
    }

    /**
     * Create a new Leap Event
     *
     * @param source
     * @param target
     * @param eventType
     * @param leapMagic
     */
    public LeapEvent(Object source, EventTarget target, EventType<? extends Event> eventType, LeapMagic leapMagic) {
        super(source, target, eventType);
        this.leapMagic = leapMagic;
    }
}
