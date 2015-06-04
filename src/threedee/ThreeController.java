package threedee;

import com.sun.javafx.sg.prism.NGPhongMaterial;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Slider;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.Text;

public class ThreeController {

    @FXML
    private StackPane foo;

    @FXML
    private void initialize() {
        Rectangle colors = createColours();
        Group blendModeGroup =
                new Group(new Group(new Rectangle(foo.getWidth(), foo.getHeight(),
                        Color.BLACK), createCircles()), colors);
        colors.setBlendMode(BlendMode.OVERLAY);
        //foo.getChildren().add(blendModeGroup);
        createShapes(foo);
    }

    private void createShapes(StackPane pane){
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setSpecularColor(Color.ORANGE);
        redMaterial.setDiffuseColor(Color.RED);

        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.BLUE);
        blueMaterial.setSpecularColor(Color.LIGHTBLUE);

        final PhongMaterial greyMaterial = new PhongMaterial();
        greyMaterial.setDiffuseColor(Color.DARKGREY);
        greyMaterial.setSpecularColor(Color.GREY);

        final Box red = new Box(400, 400, 400);
        red.setMaterial(redMaterial);

        final Sphere blue = new Sphere(200);
        blue.setMaterial(blueMaterial);

        final Cylinder grey = new Cylinder(5, 100);
        grey.setMaterial(greyMaterial);

        pane.getChildren().addAll(red, blue, grey);
    }

    private Group createCircles(){
        Group circles = new Group();
        for (int i = 0; i < 30; i++) {
            Circle circle = new Circle(150, Color.web("white", 0.05));
            circle.setStrokeType(StrokeType.OUTSIDE);
            circle.setStroke(Color.web("white", 0.16));
            circle.setStrokeWidth(4);
            circles.getChildren().add(circle);
        }
        circles.setEffect(new BoxBlur(10, 10, 3));
        return circles;
    }

    private Rectangle createColours(){
        Rectangle colors = new Rectangle(foo.getWidth(), foo.getHeight(),
                new LinearGradient(0f, 1f, 1f, 0f, true, CycleMethod.NO_CYCLE, new
                        Stop[]{
                        new Stop(0, Color.web("#f8bd55")),
                        new Stop(0.14, Color.web("#c0fe56")),
                        new Stop(0.28, Color.web("#5dfbc1")),
                        new Stop(0.43, Color.web("#64c2f8")),
                        new Stop(0.57, Color.web("#be4af7")),
                        new Stop(0.71, Color.web("#ed5fc2")),
                        new Stop(0.85, Color.web("#ef504c")),
                        new Stop(1, Color.web("#f2660f")),}));
        colors.widthProperty().bind(foo.widthProperty());
        colors.heightProperty().bind(foo.heightProperty());
        return colors;
    }

}
