package widgets.handinfo;

import com.leapmotion.leap.Hand;
import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import leap.LeapHand;

import java.io.IOException;

/**
 * Created by Richard on 6/6/2015.
 */
public class HandInfoControl extends VBox{

    public boolean getIsLeftHand() {
        return isLeftHand.get();
    }

    public void setIsLeftHand(boolean isLeftHand) {
        System.out.println("isLeftHand");
        this.isLeftHand.set(isLeftHand);
    }

    public BooleanProperty isLeftHandProperty() {
        return isLeftHand;
    }

    private ColorAdjust handColorAdjust;
    private Label infoLabel = new Label();
    private ProgressIndicator grabStrength = new ProgressIndicator();
    private ProgressIndicator pinchStrength = new ProgressIndicator();

    private final int imageWidth = 50;

    @FXML
    private ImageView handImageView;

    @FXML
    private BooleanProperty isLeftHand = new SimpleBooleanProperty(true);

    public HandInfoControl(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/widgets/handinfo/handinfo.fxml"));
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
        setHandImageView("/icons/touchGestureIcons/Number-5.png", handImageView, handColorAdjust);
        isLeftHand.addListener(observable -> {
            setImageScale(isLeftHand.getValue());
        });
    }

    private void setImageScale(boolean isLeftHand){
        int scale = 1;
        if(!isLeftHand){
            scale *= -1;
        }
        System.out.println("Listeing: "+scale);
        handImageView.setScaleX(scale);
    }

    public void updateHand(LeapHand hand){
        infoLabel.setText("Grab Strenght: "+hand.getGrabStrenght()+"\n"+"Pinch Strength: "+hand.getPinchStrength());
    }

    private VBox createStrengthView(){
        VBox vBox = new VBox();

        HBox grabBox = new HBox();

        return vBox;
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

        setImageScale(isLeftHand.getValue());

        return handImageView;
    }

}
