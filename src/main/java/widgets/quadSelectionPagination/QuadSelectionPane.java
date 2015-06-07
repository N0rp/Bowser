package widgets.quadSelectionPagination;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
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

        this.addEventFilter(LeapEvent.LEAP_ALL, event -> {

        });

        b1.addEventFilter(LeapEvent.LEAP_ALL, new ToggleEventHandler(b1));
        b2.addEventHandler(LeapEvent.LEAP_ALL, new ToggleEventHandler(b2));
        b3.addEventFilter(LeapEvent.LEAP_ALL, new ToggleEventHandler(b3));
        b4.addEventHandler(LeapEvent.LEAP_ALL, new ToggleEventHandler(b4));
    }

    public class ToggleEventHandler implements EventHandler<LeapEvent>{

        private ToggleButton button;

        public ToggleEventHandler(ToggleButton button){
            this.button = button;
        }

        @Override
        public void handle(LeapEvent event) {
            button.requestFocus();
        }
    }


}
