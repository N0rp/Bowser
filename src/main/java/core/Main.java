package core;

import core.tabs.TabzPane;
import core.tabs.TabzPaneCameraFactory;
import core.tabs.TabzPeekAndSwitchListener;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.ObservableFaceArray;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.control.*;
import org.controlsfx.control.cell.ColorGridCell;
import org.controlsfx.tools.Borders;

import java.util.LinkedList;
import java.util.Random;

public class Main extends Application {

    private final Group root = new Group();

    @Override
    public void start(Stage primaryStage) throws Exception{

        AnchorPane pane = new AnchorPane();

        //Parent rooty = FXMLLoader.load(getClass().getResource("../tabs/tabs.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(pane, 800, 600));
        primaryStage.show();

        addShapes(root);


        AnchorPane hud = new AnchorPane();
        hud.setStyle("-fx-background-color: rgba(0, 100, 100, 0.5); -fx-background-radius: 20;");
        Button brCorner = new Button("BR Cornery button");
        brCorner.setTranslateX(750);
        brCorner.setTranslateY(550);
        hud.getChildren().addAll(new Button("foo"), brCorner);


        TabzPane tabzPane = new TabzPane();

        Camera camera = TabzPaneCameraFactory.getDefaultCamera();
        Group root3D = new Group();
        root3D.getChildren().addAll(camera, tabzPane);
        SubScene subScene = new SubScene(root3D, 800, 600, true, SceneAntialiasing.BALANCED);
        subScene.setCamera(camera);
        pane.getChildren().addAll(hud, subScene);

        addButtons(tabzPane);
        addGridView(tabzPane);
        addSegmentedButton(tabzPane);
        addRating(tabzPane);
        addTabLevels(tabzPane);


        EventHandler peekListener = new TabzPeekAndSwitchListener(tabzPane);

        primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, peekListener);
        primaryStage.addEventFilter(KeyEvent.KEY_RELEASED, peekListener);
        primaryStage.addEventFilter(ScrollEvent.SCROLL, peekListener);

        tabzPane.setActiveTabIndex(0);

    }

    private void addGridView(TabzPane tabzPane){
        ObservableList<Color> list = FXCollections.observableList(new LinkedList<>());

        Random r = new Random(System.currentTimeMillis());
        for(int i = 0; i < 10; i++) {
            list.add(new Color(r.nextDouble(), r.nextDouble(), r.nextDouble(), 1.0));
        }

        GridView<Color> myGrid = new GridView<>(list);
        myGrid.setCellFactory(gridView -> new ColorGridCell());

        tabzPane.getTabs().addAll( Borders.wrap(myGrid).lineBorder().buildAll());
    }

    private void addButtons(TabzPane tabzPane){
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(new Button("Level 1"));
        anchorPane.prefHeight(500);
        Button b3 = new Button("Level 3");
        Label l4 = new Label ("Level 4");

        tabzPane.getTabs().addAll(Borders.wrap(anchorPane).lineBorder().buildAll());
        tabzPane.getTabs().addAll(Borders.wrap(new Button("Level 2")).lineBorder().buildAll(),
                b3, l4, new Button("Level 5"));
        tabzPane.getTabs().addAll(new RangeSlider(0, 100, 10, 90));

        b3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                l4.setText("Clicked 3");
            }
        });
    }

    private void addRating(TabzPane tabzPane){
        final Rating rating = new Rating();
        rating.setPartialRating(true);
        rating.setUpdateOnHover(true);

        tabzPane.getTabs().addAll(rating);
    }

    private void addSegmentedButton(TabzPane tabzPane){
        ToggleButton b1 = new ToggleButton("day");
        ToggleButton b2 = new ToggleButton("week");
        ToggleButton b3 = new ToggleButton("month");
        ToggleButton b4 = new ToggleButton("year");

        SegmentedButton segmentedButton = new SegmentedButton();
        segmentedButton.getButtons().addAll(b1, b2, b3, b4);

        tabzPane.getTabs().addAll(segmentedButton);
    }

    private void addTabLevels(TabzPane tabzPane){

        AnchorPane level1 = new AnchorPane();
        final Box red = new Box(200, 200, 1);
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setSpecularColor(Color.ORANGE);
        redMaterial.setDiffuseColor(Color.RED);
        red.setMaterial(redMaterial);
        level1.getChildren().addAll(new Button("Test"), red);


        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.BLUE);
        blueMaterial.setSpecularColor(Color.LIGHTBLUE);
        AnchorPane level2 = new AnchorPane();
        final Box blue = new Box(200, 200, 1);
        blue.setMaterial(blueMaterial);
        level2.getChildren().addAll(new Button("Testy"), blue);

        tabzPane.getTabs().addAll(level1, level2);
    }

    private void addShapes(Group root){
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setSpecularColor(Color.ORANGE);
        redMaterial.setDiffuseColor(Color.RED);

        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.BLUE);
        blueMaterial.setSpecularColor(Color.LIGHTBLUE);

        final PhongMaterial greyMaterial = new PhongMaterial();
        greyMaterial.setDiffuseColor(Color.AQUA);
        greyMaterial.setSpecularColor(Color.PINK);

        final Box red = new Box(400, 400, 400);
        red.setMaterial(redMaterial);
        red.setTranslateY(100);

        final Sphere blue = new Sphere(200);
        blue.setMaterial(blueMaterial);

        final Cylinder grey = new Cylinder(5, 100);
        grey.setMaterial(greyMaterial);
        grey.setTranslateX(-100);

        root.getChildren().addAll(red, blue, grey);

        // Define an event filter
        EventHandler filter = new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Filtering out event " + event.getEventType());
                event.consume();
            }
        };

        red.addEventFilter(MouseEvent.MOUSE_CLICKED, filter);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
