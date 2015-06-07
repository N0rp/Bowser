package leap.gesture;

/**
 * Created by Richard on 6/6/2015.
 */
public enum LeapHandState {
    INVALID,
    /** All five fingers shown **/
    UNKNOWN,
    FIST,
    PINCH,
    /** Hand is missing at least one finger **/
    SIGN,
    /** Four fingers show. One finger is hidden **/
    SIGN_ONE_MISSING,
    /** Five fingers show. **/
    SIGN_NUMBER_FIVE,
    /** Four fingers show. Thumb is hidden **/
    SIGN_NUMBER_FOUR,
    /** Three fingers show. Ring, pinky are hidden **/
    SIGN_NUMBER_THREE,
    /** Two fingers show: Thumb, index **/
    SIGN_NUMBER_TWO,
    /** One finger shows: index **/
    SIGN_NUMBER_ONE,
    /** One finger shows: thumb **/
    SIGN_VOTE_START,
    /** Three fingers show. Index and thumb form an OK circle **/
    SIGN_OK,
    /** Two fingers show. Middle, Ring and thumb hidden  **/
    SIGN_ROCK
}
