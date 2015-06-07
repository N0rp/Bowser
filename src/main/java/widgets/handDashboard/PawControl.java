package widgets.handDashboard;

import com.leapmotion.leap.Finger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import leap.LeapHand;

import java.io.IOException;

/**
 * Created by Richard on 6/6/2015.
 */
public class PawControl extends VBox {

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

    @FXML
    private BooleanProperty isLeftHand = new SimpleBooleanProperty(true);

    @FXML
    private FloatProperty circleRadius = new SimpleFloatProperty(15);

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
    @FXML
    private ImageView handGestureView;

    private ColorAdjust handGestureAdjust;

    private Image imageFist;
    private Image imagePinch;
    private Image imageRock;
    private Image imageOk;
    private Image imageFive;

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
        isLeftHand.addListener(observable -> {
            refreshFingers(isLeftHand.get());
        });
        refreshFingers(isLeftHand.get());
        loadImages();
        setHandImageView(imageFive, handGestureView, handGestureAdjust);
    }

    private void loadImages(){
        imagePinch = new Image(getClass().getResourceAsStream("/icons/touchGestureIcons/Pinch.png"));
        imageFist = new Image(getClass().getResourceAsStream("/icons/touchGestureIcons/Knock.png"));
        imageRock = new Image(getClass().getResourceAsStream("/icons/touchGestureIcons/Rock-&-Roll.png"));
        imageOk = new Image(getClass().getResourceAsStream("/icons/touchGestureIcons/Okay.png"));
        imageFive = new Image(getClass().getResourceAsStream("/icons/touchGestureIcons/Number-5.png"));
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
        }else{
            index = finger1;
            middle = finger2;
            ring = finger3;
            pinky = finger4;
        }
    }

    public void updatePaw(LeapHand hand){
        // update finge visibility
        setFingerVisibility(true);
        for(Finger.Type type : hand.getMissingFingers()){
            switch (type){
                case TYPE_INDEX:
                    index.setVisible(false);
                    break;
                case TYPE_MIDDLE:
                    middle.setVisible(false);
                    break;
                case TYPE_RING:
                    ring.setVisible(false);
                    break;
                case TYPE_PINKY:
                    pinky.setVisible(false);
                    break;
                case TYPE_THUMB:
                    thumb.setVisible(false);
                    break;
            }
        }
        // update hand gesture
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
            default:
                handGestureView.setImage(imageFive);
                break;
        }
    }

    private void setFingerVisibility(boolean isVisible){
        index.setVisible(isVisible);
        middle.setVisible(isVisible);
        ring.setVisible(isVisible);
        pinky.setVisible(isVisible);
        thumb.setVisible(isVisible);
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
