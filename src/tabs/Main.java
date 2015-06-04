package tabs;

import core.tabs.TabzPane;
import core.tabs.TabzPaneCameraFactory;
import core.tabs.TabzPeekAndSwitchListener;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class Main extends Application {

    private final Group root = new Group();

    @Override
    public void start(Stage primaryStage) throws Exception{

        AnchorPane pane=new AnchorPane();

        Parent rooty = FXMLLoader.load(getClass().getResource("tabs.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(pane, 800, 600));
        primaryStage.show();

        addShapes(root);


        TabzPane tabzPane = new TabzPane();
        tabzPane.setActiveTabIndex(0);

        Camera camera = TabzPaneCameraFactory.getDefaultCamera();
        Group root3D = new Group();
        root3D.getChildren().addAll(camera, tabzPane);
        SubScene subScene = new SubScene(root3D, 800, 600, true, SceneAntialiasing.BALANCED);
        subScene.setCamera(camera);
        pane.getChildren().addAll(subScene);

        addTabLevels(tabzPane);
        tabzPane.getTabs().addAll(new Button("Level 1"), new Button("Level 2"), new Button("Level 3"), new Button("Level 4"), new Button("Level 5"));

        EventHandler peekListener = new TabzPeekAndSwitchListener(tabzPane);

        primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, peekListener);
        primaryStage.addEventFilter(KeyEvent.KEY_RELEASED, peekListener);
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
