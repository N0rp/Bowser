package leap;

/**
 * Created by Richard on 6/6/2015.
 */
public enum LeapHandState {
    INVALID,
    /** All five fingers shown **/
    NORMAL,
    FIST,
    PINCH,
    /** Hand is missing at least one finger **/
    SIGN,
    /** Four fingers show. Thumb is hidden **/
    SIGN_ONE_MISSING,
    /** Three fingers show. Index and thumb form an OK circle **/
    SIGN_OK,
    /** Two fingers show. Middle, Ring and thumb hidden  **/
    SIGN_ROCK
}
