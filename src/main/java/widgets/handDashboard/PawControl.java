package widgets.handDashboard;

import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Hand;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import leap.LeapHand;

import java.io.IOException;

/**
 * Created by Richard on 6/6/2015.
 */
public class PawControl extends AnchorPane{

    public boolean getIsLeftHand() {
        return isLeftHand.get();
    }

    public void setIsLeftHand(boolean isLeftHand) {
        this.isLeftHand.set(isLeftHand);
    }

    public BooleanProperty isLeftHandProperty() {
        return isLeftHand;
    }

    private ColorAdjust handColorAdjust;
    private ColorAdjust handGestureAdjust;

    @FXML
    private BooleanProperty isLeftHand = new SimpleBooleanProperty(true);

    @FXML
    private Circle index;
    @FXML
    private Circle middle;
    @FXML
    private Circle ring;
    @FXML
    private Circle pinky;

    private Circle thumb;
    @FXML
    private Circle thumbLeft;
    @FXML
    private Circle thumbRight;
    @FXML
    private Circle palm;


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
        handColorAdjust = new ColorAdjust();
        handGestureAdjust = new ColorAdjust();
        isLeftHand.addListener(observable -> {
            refreshActiveThumb(isLeftHand.get());
        });
        refreshActiveThumb(isLeftHand.get());
    }

    private void refreshActiveThumb(boolean isLeftHand){
        thumb = thumbRight;
        Circle inactiveThumb = thumbLeft;
        if(!isLeftHand){
            thumb = thumbLeft;
            inactiveThumb = thumbRight;
        }
        thumb.setVisible(true);
        inactiveThumb.setVisible(false);
    }

    public void updatePaw(LeapHand hand){
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
    }

    private void setFingerVisibility(boolean isVisible){
        index.setVisible(isVisible);
        middle.setVisible(isVisible);
        ring.setVisible(isVisible);
        pinky.setVisible(isVisible);
        thumb.setVisible(isVisible);
    }



}
