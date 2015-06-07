package widgets.handDashboard;

import com.leapmotion.leap.Finger;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import leap.gesture.LeapHand;

import java.io.IOException;

/**
 * Created by Richard on 6/6/2015.
 */
public class PawControl extends VBox {
    /*
        Getter and Setter
     */

    public boolean getIsLeftHand() {
        return isLeftHand.get();
    }

    public void setIsLeftHand(boolean isLeftHand) {
        this.isLeftHand.set(isLeftHand);
    }

    public BooleanProperty isLeftHandProperty() {
        return isLeftHand;
    }

    public float getCircleRadius() {
        return circleRadius.get();
    }

    public FloatProperty circleRadiusProperty() {
        return circleRadius;
    }

    public void setCircleRadius(float circleRadius) {
        this.circleRadius.set(circleRadius);
    }

    public Paint getPawColor() {
        return pawColor.get();
    }

    public ObjectProperty<Paint> pawColorProperty() {
        return pawColor;
    }

    public void setPawColor(Paint pawColor) {
        this.pawColor.set(pawColor);
    }

    /*
        Properties
     */
    @FXML
    private BooleanProperty isLeftHand = new SimpleBooleanProperty(true);

    @FXML
    private FloatProperty circleRadius = new SimpleFloatProperty(15);

    @FXML
    private final ObjectProperty<Paint> pawColor = new SimpleObjectProperty<Paint>(this, "pawColor", Color.RED);


    /*
        FXML
     */
    private Circle index;
    private Circle middle;
    private Circle ring;
    private Circle pinky;
    @FXML
    private Circle finger1;
    @FXML
    private Circle finger2;
    @FXML
    private Circle finger3;
    @FXML
    private Circle finger4;

    private Circle thumb;
    @FXML
    private Circle thumbLeft;
    @FXML
    private Circle thumbRight;
    @FXML
    private Circle palm;

    private Label indexLabel;
    private Label middleLabel;
    private Label ringLabel;
    private Label pinkyLabel;
    @FXML
    private Label finger1Label;
    @FXML
    private Label finger2Label;
    @FXML
    private Label finger3Label;
    @FXML
    private Label finger4Label;

    @FXML
    private ImageView handGestureView;
    @FXML
    private Label handGestureLabel;
    @FXML
    private HBox fingerBox;

    /*
        Normal variables
    */
    private ColorAdjust handGestureAdjust;

    private Image imageFist;
    private Image imagePinch;
    private Image imageRock;
    private Image imageOk;
    private Image imageFive;
    private Image imageFour;
    private Image imageThree;
    private Image imageTwo;
    private Image imageOne;
    private Image imageThumbRight;

    public PawControl(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/widgets/handDashboard/paw.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }


    @FXML
    private void initialize() {
        handGestureAdjust = new ColorAdjust();
        refreshFingers(isLeftHand.get());
        loadImages();
        setHandImageView(imageFive, handGestureView, handGestureAdjust);

        initListeners();
    }

    public void updatePaw(LeapHand hand){
        // update finger visibility
        setFingerVisibility(true);
        hideMissingFingers(hand);
        setFingerLength(hand);

        // update hand gesture
        updateGestureImage(hand);
    }

    private void initListeners(){
        isLeftHand.addListener(observable -> {
            refreshFingers(isLeftHand.get());
        });

        circleRadius.addListener((observable, oldValue, newValue) -> {
            updateSizes(newValue.doubleValue());
        });

        pawColor.addListener((observable, oldValue, newValue) -> {
            updatePawColor(newValue);
        });
    }

    private void updatePawColor(Paint newColor){
        palm.setFill(newColor);
        finger1.setFill(newColor);
        finger2.setFill(newColor);
        finger3.setFill(newColor);
        finger4.setFill(newColor);
        thumbLeft.setFill(newColor);
        thumbRight.setFill(newColor);
    }

    private void updateSizes(double newRadius){
        double smallRadius = newRadius / 3;
        double imageWidth = newRadius * 2;

        palm.setRadius(newRadius);
        finger1.setRadius(smallRadius);
        finger2.setRadius(smallRadius);
        finger3.setRadius(smallRadius);
        finger4.setRadius(smallRadius);
        thumbLeft.setRadius(smallRadius);
        thumbRight.setRadius(smallRadius);
        fingerBox.setSpacing(smallRadius);

        handGestureView.setFitWidth(imageWidth);
    }

    private void loadImages(){
        imagePinch = new Image(getClass().getResourceAsStream("/icons/touchGestureIcons/Pinch.png"));
        imageFist = new Image(getClass().getResourceAsStream("/icons/touchGestureIcons/Knock.png"));
        imageRock = new Image(getClass().getResourceAsStream("/icons/touchGestureIcons/Rock-&-Roll.png"));
        imageOk = new Image(getClass().getResourceAsStream("/icons/touchGestureIcons/Okay.png"));
        imageFive = new Image(getClass().getResourceAsStream("/icons/touchGestureIcons/Number-5.png"));
        imageFour = new Image(getClass().getResourceAsStream("/icons/touchGestureIcons/Number-4.png"));
        imageThree = new Image(getClass().getResourceAsStream("/icons/touchGestureIcons/Number-3.png"));
        imageTwo = new Image(getClass().getResourceAsStream("/icons/touchGestureIcons/Number-2.png"));
        imageOne = new Image(getClass().getResourceAsStream("/icons/touchGestureIcons/Number-1.png"));
        imageThumbRight = new Image(getClass().getResourceAsStream("/icons/touchGestureIcons/Thumb-right.png"));
    }

    private void refreshFingers(boolean isLeftHand){
        // thumb
        thumb = thumbRight;
        Circle inactiveThumb = thumbLeft;
        if(!isLeftHand){
            thumb = thumbLeft;
            inactiveThumb = thumbRight;
        }
        thumb.setVisible(true);
        inactiveThumb.setVisible(false);
        // fingers
        if(isLeftHand){
            index = finger4;
            middle = finger3;
            ring = finger2;
            pinky = finger1;

            indexLabel = finger4Label;
            middleLabel = finger3Label;
            ringLabel = finger2Label;
            pinkyLabel = finger1Label;
        }else{
            index = finger1;
            middle = finger2;
            ring = finger3;
            pinky = finger4;

            indexLabel = finger1Label;
            middleLabel = finger2Label;
            ringLabel = finger3Label;
            pinkyLabel = finger4Label;
        }
        // gesture image view
        if(isLeftHand){
            handGestureView.setScaleX(1);
        }else{
            handGestureView.setScaleX(-1);
        }
    }

    private void setFingerLength(LeapHand hand){
        for(Finger finger : hand.getFingers()){
            switch (finger.type()){
                case TYPE_INDEX:
                    indexLabel.setText((int)finger.length()+"");
                    break;
                case TYPE_MIDDLE:
                    middleLabel.setText((int)finger.length() + "");
                    break;
                case TYPE_RING:
                    ringLabel.setText((int)finger.length() + "");
                    break;
                case TYPE_PINKY:
                    pinkyLabel.setText((int)finger.length()+"");
                    break;
            }
        }
    }

    private void setFingerVisibility(boolean isVisible){
        index.setVisible(isVisible);
        middle.setVisible(isVisible);
        ring.setVisible(isVisible);
        pinky.setVisible(isVisible);
        thumb.setVisible(isVisible);

        indexLabel.setVisible(isVisible);
        middleLabel.setVisible(isVisible);
        ringLabel.setVisible(isVisible);
        pinkyLabel.setVisible(isVisible);
    }

    private void hideMissingFingers(LeapHand hand){
        for(Finger.Type type : hand.getMissingFingers()){
            switch (type){
                case TYPE_INDEX:
                    index.setVisible(false);
                    indexLabel.setVisible(false);
                    break;
                case TYPE_MIDDLE:
                    middle.setVisible(false);
                    middleLabel.setVisible(false);
                    break;
                case TYPE_RING:
                    ring.setVisible(false);
                    ringLabel.setVisible(false);
                    break;
                case TYPE_PINKY:
                    pinky.setVisible(false);
                    pinkyLabel.setVisible(false);
                    break;
                case TYPE_THUMB:
                    thumb.setVisible(false);
                    break;
            }
        }
    }

    private void updateGestureImage(LeapHand hand){
        handGestureView.setVisible(true);
        handGestureLabel.setVisible(false);
        switch (hand.getHandState()){
            case FIST:
                handGestureView.setImage(imageFist);
                break;
            case PINCH:
                handGestureView.setImage(imagePinch);
                break;
            case SIGN_OK:
                handGestureView.setImage(imageOk);
                break;
            case SIGN_ROCK:
                handGestureView.setImage(imageRock);
                break;
            case SIGN_VOTE_START:
                handGestureView.setImage(imageThumbRight);
                break;
            case SIGN_ONE_MISSING:
                handGestureView.setVisible(false);
                handGestureLabel.setVisible(true);
                handGestureLabel.setText(""+hand.getMissingFingers().get(0));
                break;
            case SIGN_NUMBER_FIVE:
                handGestureView.setImage(imageFive);
                break;
            case SIGN_NUMBER_FOUR:
                handGestureView.setImage(imageFour);
                break;
            case SIGN_NUMBER_THREE:
                handGestureView.setImage(imageThree);
                break;
            case SIGN_NUMBER_TWO:
                handGestureView.setImage(imageTwo);
                break;
            case SIGN_NUMBER_ONE:
                handGestureView.setImage(imageOne);
                break;
            case INVALID:
                handGestureView.setVisible(false);
                handGestureLabel.setVisible(true);
                handGestureLabel.setText("!");
                break;
            default:
                handGestureView.setVisible(false);
                handGestureLabel.setVisible(true);
                handGestureLabel.setText("?");
                break;
        }
    }

    private ImageView setHandImageView(Image handImage, ImageView handImageView, ColorAdjust handColorAdjust){
        // resizes the image to have width of 100 while preserving the ratio and using
        // higher quality filtering method; this ImageView is also cached to
        // improve performance
        handColorAdjust.setContrast(1);
        handColorAdjust.setHue(0.1);
        handColorAdjust.setBrightness(1);
        handColorAdjust.setSaturation(1);

        handImageView.setImage(handImage);
        handImageView.setFitWidth(palm.getRadius() * 2);
        handImageView.setPreserveRatio(true);
        handImageView.setSmooth(true);
        handImageView.setCache(true);
        handImageView.setEffect(handColorAdjust);

        return handImageView;
    }



}
