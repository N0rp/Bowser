package core;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import widgets.tabs.TabzPane;
import widgets.tabs.TabzPaneCameraFactory;
import widgets.tabs.TabzPeekAndSwitchListener;

import java.io.IOException;

/**
 * Created by Richard on 6/6/2015.
 */
public class Bowser extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        AnchorPane rooPane = new AnchorPane();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(rooPane, 800, 600));
        primaryStage.show();

        Node hud = createHud();
        SubScene tabScene = createTabzScene(primaryStage);
        rooPane.getChildren().addAll(hud, tabScene);
    }

    private SubScene createTabzScene(Stage primaryStage) throws IOException {
        TabzPane tabzPane = FXMLLoader.load(getClass().getResource("/explorer/explorer.fxml"));
        tabzPane.setActiveTabIndex(0);

        Camera camera = TabzPaneCameraFactory.getDefaultCamera();
        Group root3D = new Group();
        root3D.getChildren().addAll(camera, tabzPane);

        SubScene subScene = new SubScene(root3D, 800, 600, true, SceneAntialiasing.BALANCED);
        subScene.setCamera(camera);

        EventHandler peekListener = new TabzPeekAndSwitchListener(tabzPane);
        primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, peekListener);
        primaryStage.addEventFilter(KeyEvent.KEY_RELEASED, peekListener);
        primaryStage.addEventFilter(ScrollEvent.SCROLL, peekListener);

        return subScene;
    }

    private Node createHud(){
        AnchorPane hud = new AnchorPane();
        hud.setStyle("-fx-background-color: rgba(0, 100, 100, 0.5); -fx-background-radius: 20;");
        Button brCorner = new Button("BR Cornery button");
        brCorner.setTranslateX(750);
        brCorner.setTranslateY(550);
        hud.getChildren().addAll(new Button("foo"), brCorner);

        return hud;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
