package widgets.quadSelectionPagination;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import leap.LeapEvent;
import org.controlsfx.control.SegmentedButton;

import java.net.URL;
import java.util.Observable;

/**
 * Created by Richard on 6/6/2015.
 */
public class QuadSelectionPane extends BorderPane {


    private ObservableList<ToggleButton> children = FXCollections.emptyObservableList();

    private ColorAdjust colorAdjust;

    public QuadSelectionPane(){
        ToggleButton b1 = new ToggleButton("day");
        ToggleButton b2 = new ToggleButton("week");
        ToggleButton b3 = new ToggleButton("month");
        ToggleButton b4 = new ToggleButton("year");

        SegmentedButton segmentedButton = new SegmentedButton();
        segmentedButton.getButtons().addAll(b1, b2, b3, b4);

        AnchorPane centerPane = new AnchorPane();
        centerPane.getChildren().addAll(segmentedButton);
        setCenter(centerPane);

        // load the image
        URL imageUrl = getClass().getResource("/icons/touchGestureIcons/Pinch.png");
        String imagePath = imageUrl.getPath();
        Image image = new Image(getClass().getResourceAsStream("/icons/touchGestureIcons/Pinch.png"));
        // resizes the image to have width of 100 while preserving the ratio and using
        // higher quality filtering method; this ImageView is also cached to
        // improve performance
        colorAdjust = new ColorAdjust();
        colorAdjust.setContrast(1);
        colorAdjust.setHue(0.1);
        colorAdjust.setBrightness(0.5);
        colorAdjust.setSaturation(1);

        ImageView iv2 = new ImageView();
        iv2.setImage(image);
        iv2.setFitWidth(50);
        iv2.setPreserveRatio(true);
        iv2.setSmooth(true);
        iv2.setCache(true);
        iv2.setY(100);
        iv2.setEffect(colorAdjust);

        centerPane.getChildren().addAll(iv2);

        this.addEventHandler(LeapEvent.LEAP_ALL, event -> {

        });
    }

    public ColorAdjust getColorAdjust(){
        return colorAdjust;
    }
}
