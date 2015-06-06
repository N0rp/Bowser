package widgets.quadSelectionPagination;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Hand;
import com.sun.javafx.robot.FXRobot;
import com.sun.javafx.robot.FXRobotFactory;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import leap.LeapEvent;
import leap.LeapHand;
import leap.LeapHandState;
import leap.RichLeapListener;

/**
 * Created by Richard on 6/6/2015.
 */
public class QuadSelectionSample  extends Application {
    private RichLeapListener listener = null;
    private Controller controller = null;

    private Label leftHandText = new Label();
    private Label rightHandText = new Label();
    private Circle leftHandCircle = new Circle(10, Color.GREEN);
    private Circle rightHandCircle = new Circle(10, Color.RED);
    private Label leftHandHud = new Label();
    private Label rightHandHud = new Label();

    public static void main(String[] params){
        launch(params);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        final int appWidth = 800;
        final int appHeight = 600;

        listener = new RichLeapListener(appWidth, appHeight);
        controller = new Controller();
        controller.addListener(listener);

        AnchorPane rootPane = new AnchorPane();
        primaryStage.setTitle("Quad Selection Example");
        primaryStage.setScene(new Scene(rootPane, appWidth, appHeight));
        primaryStage.show();

        leftHandHud.setTranslateX(0);
        leftHandHud.setTranslateY(500);

        rightHandHud.setTranslateX(650);
        rightHandHud.setTranslateY(500);

        QuadSelectionPane quadSelection = FXMLLoader.load(getClass().getResource("/widgets/quadSelectionPagination/quadSelection.fxml"));
        rootPane.getChildren().addAll(quadSelection, leftHandCircle, rightHandCircle,
                leftHandText, rightHandText, leftHandHud, rightHandHud);

        FXRobot robot = FXRobotFactory.createRobot(primaryStage.getScene());


        listener.doneListProperty().addListener((ov, b, b1)->{
            if(listener.getLeftHand() != null) {
                setHandIndicator(leftHandCircle, leftHandText, leftHandHud, listener.getLeftHand());
            }
            if(listener.getRightHand() != null) {
                quadSelection.getColorAdjust().setSaturation(listener.getRightHand().getPinchStrength());
                setHandIndicator(rightHandCircle, rightHandText, rightHandHud, listener.getRightHand());
            }

            LeapEvent myEvent = new LeapEvent(LeapEvent.LEAP_ALL, listener.getLeftHand(), listener.getRightHand());
            quadSelection.fireEvent(myEvent);
        });
    }

    private void setHandIndicator(Circle handCircle, Label handText, Label hud, LeapHand hand){
        final int palmX = (int)hand.palmPositionNormalized().getX();
        final int palmY = (int)hand.palmPositionNormalized().getY();
        final int extendedFingers = hand.getFingers().extended().count();

        final String text;
        switch (hand.getHandState()){
            case PINCH:
                Finger pinchingFinger = hand.getPinchingFinger();
                text = pinchingFinger.type()+"";
                break;
            case FIST:
                text = "F"; // Fist
                break;
            case NORMAL:
                text = extendedFingers + "";
                break;
            default:
                text = "I";
        }

        Platform.runLater(() -> {
            handCircle.setCenterX(palmX);
            handCircle.setCenterY(palmY);
            handText.setTranslateX(palmX - (handCircle.getRadius() / 2));
            handText.setTranslateY(palmY - (handCircle.getRadius() / 2));
            handText.setText(text);

            hud.setText("Grab Strenght: "+hand.getGrabStrenght()+"\n"+"Pinch Strength: "+hand.getPinchStrength());
        });
    }


    @Override
    public void stop(){
        controller.removeListener(listener);
    }
}
