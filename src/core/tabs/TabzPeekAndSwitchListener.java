package core.tabs;

import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;


/**
 * Created by Richard on 6/4/2015.
 */
public class TabzPeekAndSwitchListener implements EventHandler<KeyEvent>{

    private TabzPane pane;

    private final boolean animatePeek = true;

    public TabzPeekAndSwitchListener(TabzPane pane){
        this.pane = pane;
    }

    @Override
    public void handle(KeyEvent event) {
        event.consume();
        if(event.getCode() == KeyCode.SPACE) {
            handlePeek(event);
        }else if(event.getCode() == KeyCode.UP){
            handleHierarchyUp(event);
        }else if(event.getCode() == KeyCode.DOWN){
            handleHierarchyDown(event);
        }
    }

    private void handlePeek(KeyEvent event){
        if (event.getEventType() == javafx.scene.input.KeyEvent.KEY_RELEASED) {
            pane.stopPeekBehindActive(animatePeek);
        } else if (event.getEventType() == KeyEvent.KEY_PRESSED ) {
            pane.startPeekBehindActive(animatePeek);
        }
    }

    private void handleHierarchyUp(KeyEvent event){
        if (event.getEventType() == javafx.scene.input.KeyEvent.KEY_RELEASED) {
            pane.setActiveTabIndex(pane.getActiveTabIndex() + 1);
        }
    }

    private void handleHierarchyDown(KeyEvent event){
        if (event.getEventType() == javafx.scene.input.KeyEvent.KEY_RELEASED) {
            pane.setActiveTabIndex(pane.getActiveTabIndex() - 1);
        }
    }


}
