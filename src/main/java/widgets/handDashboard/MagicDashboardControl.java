package widgets.handDashboard;

import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import leap.gesture.LeapHand;
import leap.gesture.LeapMagic;

import javax.swing.plaf.ProgressBarUI;
import java.io.IOException;

/**
 * Created by Richard on 6/6/2015.
 */
public class MagicDashboardControl extends VBox {
    /*
        Getter/Setter
    */
    /*
        FXML
     */

    @FXML
    private ProgressBar handDistance;
    @FXML
    private Label handDistanceLabel;

    public MagicDashboardControl(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/widgets/handDashboard/magicDashboard.fxml"));
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
        handDistance.setProgress(50);
    }

    public void updateHand(LeapMagic leapMagic){
        if(leapMagic.getHandDistance() != null) {
            float num = leapMagic.getTouchStrength();
            handDistance.setProgress(num);
            handDistanceLabel.setText("Distance: "+(int)num);
        }
    }


}
