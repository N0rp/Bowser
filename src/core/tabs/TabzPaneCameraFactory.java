package core.tabs;

import javafx.scene.Camera;
import javafx.scene.PerspectiveCamera;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 * Factory to create a default Camera for the {@link TabzPane}. {@link TabzPane} requires a camera for the 3D positioning to work.
 * <br/><br/>
 * Add the camera together with the tabzPane to any kind of group to enable it, like so: <br/>
 * Group root3D = new Group();<br/>
 * root3D.getChildren().addAll(camera, tabzPane);<br/>
 * SubScene subScene = new SubScene(root3D, 800, 600, true, SceneAntialiasing.BALANCED);<br/>
 * subScene.setCamera(camera);<br/>
 *
 * Created by Richard on 6/4/2015.
 */
public class TabzPaneCameraFactory {

    private final static Rotate cameraXRotate = new Rotate(0,0,0,0,Rotate.X_AXIS);
    private final static Rotate cameraYRotate = new Rotate(0,0,0,0,Rotate.Y_AXIS);
    private final static Translate cameraPosition = new Translate(-100,-550,-200);

    public static Camera getDefaultCamera(){
        final PerspectiveCamera camera = new PerspectiveCamera();
        camera.setFieldOfView(60);
        camera.getTransforms().addAll(cameraXRotate, cameraYRotate, cameraPosition);

        return camera;
    }
}
