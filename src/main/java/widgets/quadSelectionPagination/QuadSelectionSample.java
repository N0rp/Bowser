package widgets.quadSelectionPagination;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import core.UiUtil;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import leap.LeapEvent;
import leap.gesture.LeapHand;
import leap.RichLeapListener;
import leap.gesture.LeapMagic;
import widgets.handDashboard.HandDashboardControl;
import widgets.handDashboard.MagicDashboardControl;

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
    private HandDashboardControl leftHandHud;
    private HandDashboardControl rightHandHud;
    private MagicDashboardControl magicDashboard;

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

        leftHandHud = new HandDashboardControl();
        leftHandHud.setIsLeftHand(true);
        leftHandHud.setPawColor(Color.GREEN);
        rightHandHud = new HandDashboardControl();
        rightHandHud.setIsLeftHand(false);
        rightHandHud.setPawColor(Color.RED);

        magicDashboard = new MagicDashboardControl();

        AnchorPane.setBottomAnchor(leftHandHud, 8.0);
        AnchorPane.setLeftAnchor(leftHandHud, 5.0);
        AnchorPane.setBottomAnchor(rightHandHud, 8.0);
        AnchorPane.setRightAnchor(rightHandHud, 5.0);
        AnchorPane.setTopAnchor(magicDashboard, 8.0);
        AnchorPane.setRightAnchor(magicDashboard, 8.0);

        QuadSelectionPane quadSelection = FXMLLoader.load(getClass().getResource("/widgets/quadSelectionPagination/quadSelection.fxml"));
        rootPane.getChildren().addAll(quadSelection, leftHandCircle, rightHandCircle,
                leftHandText, rightHandText, leftHandHud, rightHandHud, magicDashboard);

        listener.doneListProperty().addListener((observable, oldValue, newValue)->{
            final LeapMagic leapMagic = listener.getLeapMagic();
            if(leapMagic != null) {
                final LeapHand leftHand = leapMagic.getLeftHand();
                final LeapHand rightHand = leapMagic.getRightHand();

                if (leftHand != null) {
                    setHandIndicator(leftHandCircle, leftHandText, leftHandHud, leftHand);
                }
                if (rightHand != null) {
                    setHandIndicator(rightHandCircle, rightHandText, rightHandHud, rightHand);
                    Node target = UiUtil.getTarget(quadSelection,
                            rightHand.palmPositionNormalized().getX(), rightHand.palmPositionNormalized().getY());
                    if(target != null){
                        Platform.runLater(() -> {
                            LeapEvent leapEvent = new LeapEvent(listener, target, LeapEvent.LEAP_ALL, leapMagic);
                            target.fireEvent(leapEvent);
                        });
                    }
                }

                Platform.runLater(() -> {
                    magicDashboard.updateHand(leapMagic);
                });
            }
        });
    }

    private void setHandIndicator(Circle handCircle, Label handText, final HandDashboardControl hudController, final LeapHand hand){
        final int palmX = (int)hand.palmPositionNormalized().getX();
        final int palmY = (int)hand.palmPositionNormalized().getY();
        final int extendedFingers = hand.getFingers().extended().count();

        final String text;
        switch (hand.getHandState()){
            case PINCH:
                Finger pinchingFinger = hand.getPinchingFinger();
                text = "Pinch: " + pinchingFinger.type()+"";
                break;
            case FIST:
                text = "Fist"; // Fist
                break;
            case UNKNOWN:
                text = "Unknown: "+extendedFingers + "";
                break;
            case SIGN:
                text = "Sign";
                break;
            case SIGN_ONE_MISSING:
                text = "Sign: "+hand.getMissingFingers();
                break;
            case SIGN_OK:
                text = "OK";
                break;
            case SIGN_ROCK:
                text = "Rock";
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

            hudController.updateHand(hand);
        });
    }


    @Override
    public void stop(){
        controller.removeListener(listener);
    }
}
