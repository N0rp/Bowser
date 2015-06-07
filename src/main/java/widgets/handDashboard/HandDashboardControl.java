package widgets.handDashboard;

import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import leap.gesture.LeapHand;

import java.io.IOException;

/**
 * Created by Richard on 6/6/2015.
 */
public class HandDashboardControl extends VBox {
    /*
        Getter/Setter
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
    private BooleanProperty isLeftHand = new SimpleBooleanProperty(true);
    private FloatProperty circleRadius = new SimpleFloatProperty(15);
    private final ObjectProperty<Paint> pawColor = new SimpleObjectProperty<Paint>(this, "pawColor", Color.RED);

    /*
        FXML
     */

    @FXML
    private ProgressIndicator grabIndicator;
    @FXML
    private ProgressIndicator pinchIndicator;
    @FXML
    private ProgressIndicator confidenceIndicator;
    @FXML
    private ProgressIndicator rollAngle;
    @FXML
    private PawControl paw;

    private final int imageWidth = 50;

    public HandDashboardControl(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/widgets/handDashboard/handDashboard.fxml"));
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
        grabIndicator.setProgress(0);
        pinchIndicator.setProgress(0);
        confidenceIndicator.setProgress(0);
        rollAngle.setProgress(0);

        initListeners();
    }

    private void initListeners(){
        isLeftHand.addListener((observable, oldValue, newValue) -> {
            paw.setIsLeftHand(newValue);
        });

        circleRadius.addListener((observable, oldValue, newValue) -> {
            paw.setCircleRadius(newValue.floatValue());
        });

        pawColor.addListener((observable, oldValue, newValue) -> {
            paw.setPawColor(newValue);
        });
    }

    public void updateHand(LeapHand hand){
        grabIndicator.setProgress(hand.getGrabStrenght());
        pinchIndicator.setProgress(hand.getPinchStrength());
        confidenceIndicator.setProgress(hand.getConfidence());
        rollAngle.setProgress(Math.abs(hand.getPalmRoll()));

        paw.updatePaw(hand);
    }


}
