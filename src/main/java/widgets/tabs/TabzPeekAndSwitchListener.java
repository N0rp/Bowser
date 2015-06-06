package widgets.tabs;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;


/**
 * Created by Richard on 6/4/2015.
 */
public class TabzPeekAndSwitchListener implements EventHandler<Event>{

    private TabzPane pane;

    private final boolean animatePeek = true;

    public TabzPeekAndSwitchListener(TabzPane pane){
        this.pane = pane;
    }

    @Override
    public void handle(Event event) {
        if(event instanceof KeyEvent){
            handleKey((KeyEvent) event);
        }else if(event instanceof ScrollEvent){
            handleScroll((ScrollEvent) event);
        }
    }

    private void handleKey(KeyEvent event){
        event.consume();
        if(event.getCode() == KeyCode.SPACE) {
            handlePeek(event);
        }else if(event.getCode() == KeyCode.UP){
            handleHierarchyUp(event);
        }else if(event.getCode() == KeyCode.DOWN){
            handleHierarchyDown(event);
        }
    }

    private void handleScroll(ScrollEvent event){
        event.consume();
        if(event.getDeltaY() > 0) {
            pane.setActiveTabIndex(pane.getActiveTabIndex() + 1);
        }else {
            pane.setActiveTabIndex(pane.getActiveTabIndex() - 1);
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
