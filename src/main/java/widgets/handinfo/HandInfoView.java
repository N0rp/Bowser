package widgets.handinfo;

import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Created by Richard on 6/6/2015.
 */
public class HandInfoView extends HBox{
    @FXML
    private BooleanProperty isLeftHand;

    @FXML
    private ImageView handImageView;

    @FXML
    private void initialize() {
        isLeftHand.addListener(observable -> {
            int scale = 1;
            if(!isLeftHand.getValue()){
                scale *= -1;
            }
            handImageView.setScaleX(scale);
        });
    }

    public BooleanProperty isLeftHandProperty(){
        return isLeftHand;
    }

}
