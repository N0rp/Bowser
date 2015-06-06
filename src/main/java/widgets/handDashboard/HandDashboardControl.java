package widgets.handDashboard;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import leap.LeapHand;

import java.io.IOException;

/**
 * Created by Richard on 6/6/2015.
 */
public class HandDashboardControl extends HBox{

    public boolean getIsLeftHand() {
        return isLeftHand.get();
    }

    public void setIsLeftHand(boolean isLeftHand) {
        this.isLeftHand.set(isLeftHand);
        paw.setIsLeftHand(isLeftHand);
    }

    public BooleanProperty isLeftHandProperty() {
        return isLeftHand;
    }

    private ColorAdjust handColorAdjust;
    private ColorAdjust handGestureAdjust;

    @FXML
    private ProgressIndicator grabIndicator;
    @FXML
    private ProgressIndicator pinchIndicator;
    @FXML
    private ImageView handGestureView;
    @FXML
    private PawControl paw;

    @FXML
    private BooleanProperty isLeftHand = new SimpleBooleanProperty(true);

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
        handColorAdjust = new ColorAdjust();
        handGestureAdjust = new ColorAdjust();
        setHandImageView("/icons/touchGestureIcons/Pinch.png", handGestureView, handGestureAdjust);
        isLeftHand.addListener(observable -> {
            setImageScale(handGestureView, isLeftHand.getValue());
        });

        grabIndicator.setProgress(0);
        pinchIndicator.setProgress(0);
    }

    private void setImageScale(ImageView imageView, boolean isLeftHand){
        int scale = 1;
        if(!isLeftHand){
            scale *= -1;
        }
        imageView.setScaleX(scale);
    }

    public void updateHand(LeapHand hand){
        grabIndicator.setProgress(hand.getGrabStrenght());
        pinchIndicator.setProgress(hand.getPinchStrength());

        paw.updatePaw(hand);
    }

    private ImageView setHandImageView(String imagePath, ImageView handImageView, ColorAdjust handColorAdjust){
        // load the image
        Image handImage = new Image(getClass().getResourceAsStream(imagePath));
        // resizes the image to have width of 100 while preserving the ratio and using
        // higher quality filtering method; this ImageView is also cached to
        // improve performance
        handColorAdjust.setContrast(1);
        handColorAdjust.setHue(0.1);
        handColorAdjust.setBrightness(1);
        handColorAdjust.setSaturation(1);

        handImageView.setImage(handImage);
        handImageView.setFitWidth(imageWidth);
        handImageView.setPreserveRatio(true);
        handImageView.setSmooth(true);
        handImageView.setCache(true);
        handImageView.setEffect(handColorAdjust);

        return handImageView;
    }

}
