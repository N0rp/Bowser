package widgets.imagemagazine;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import leap.LeapEvent;
import leap.gesture.LeapHand;
import leap.gesture.LeapHandState;

import java.io.IOException;

/**
 * Created by Richard on 6/6/2015.
 */
public class ImageMagazineControl extends VBox {


    /*
        FXML
     */

    @FXML
    private ImageView centerImageView;

    private ColorAdjust centerImageAdjust;

    private int imageWidth = 500;

    public ImageMagazineControl(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/widgets/imagemagazine/imageMagazine.fxml"));
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
        Image centerImage = new Image(getClass().getResourceAsStream("/icons/touchGestureIcons/Dice.png"));
        //Image centerImage = new Image(getClass().getResourceAsStream("/icons/touchGestureIcons/Pinch.png"));
        centerImageAdjust = new ColorAdjust();
        setHandImageView(centerImage, centerImageView, centerImageAdjust);
        initListeners();

        centerImageView.setTranslateX(100);
        centerImageView.setTranslateY(100);
    }

    private void initListeners(){
        centerImageView.addEventFilter(LeapEvent.LEAP_ALL, event -> {
            LeapHand right = event.getHandRight();
            if(right != null){
                if(right.getHandState() != LeapHandState.FIST){
                    centerImageAdjust.setSaturation(right.getGrabStrength());
                }else {
                    double imageX = right.palmPositionNormalized().getX() - (centerImageView.getFitWidth() / 2);
                    centerImageView.setTranslateX(imageX);
                }
            }
        });
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
        handImageView.setFitWidth(imageWidth);
        handImageView.setPreserveRatio(true);
        //handImageView.setSmooth(true);
        //handImageView.setCache(true);
        handImageView.setEffect(handColorAdjust);

        return handImageView;
    }



}
