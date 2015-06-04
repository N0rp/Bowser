package core.tabs;

import javafx.animation.TranslateTransition;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 * A pane which displays nodes in z-Space. Requires a camera to be set. <br/><br/>
 *
 * For peek to work you need to add the respective Listener to the primary stage:<br/>
 * EventHandler peekListener = new TabzPeekAndSwitchListener(tabzPane);<br/>
 * primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, peekListener);<br/>
 * primaryStage.addEventFilter(KeyEvent.KEY_RELEASED, peekListener);<br/>
 *
 * Created by Richard on 6/4/2015.
 */
public class TabzPane extends AnchorPane {

    public int getTabLevelZDistance() {
        return tabLevelZDistance;
    }

    public void setTabLevelZDistance(int tabLevelZDistance) {
        this.tabLevelZDistance = tabLevelZDistance;
    }

    public int getActiveTabIndex(){
        return activeTabIndex;
    }

    public void setActiveTabIndex(int index){
        int previousTabIndex = activeTabIndex;
        activeTabIndex = Math.min(index, getTabs().size() - 1);
        activeTabIndex = activeTabIndex < 0 ? 0 : activeTabIndex;
        boolean transitionDown = false;
        if(activeTabIndex < previousTabIndex){
            transitionDown = true;
        }
        refreshActiveTab(animateActiveTabTransition, transitionDown);
    }

    private int activeTabIndex = 0;
    private int tabLevelZDistance = 200;

    private final int peekYTranslate = 150;

    private final boolean animateActiveTabTransition = true;

    public TabzPane(){
        initListeners();
    }

    public ObservableList<Node> getTabs(){
        return getChildren();
    }

    private void refreshActiveTab(boolean animateTransition, boolean transitionDown){
        int tabsFoundAfterIndex = 0;

        for(int i = 0; i < getTabs().size(); i++){
            Node tab = getTabs().get(i);
            if(i < activeTabIndex){
                tab.setVisible(false);
            }else if(i == activeTabIndex){
                tab.setVisible(true);
                if(transitionDown) {
                    tab.setTranslateZ(-tabLevelZDistance * 2);
                }
                transitionZ(tab, 0, animateTransition);
                tab.requestFocus();
            }else{
                tabsFoundAfterIndex++;
                tab.setVisible(true);
                transitionZ(tab, tabLevelZDistance * tabsFoundAfterIndex, animateTransition);
            }
        }
    }

    public void startPeekBehindActive(boolean animatePeek){
        Node activeTab = getTabs().get(getActiveTabIndex());
        transitionY(activeTab, peekYTranslate, animatePeek);
        if(getActiveTabIndex() + 1 < getTabs().size()){
            Node nextTab = getTabs().get(getActiveTabIndex() + 1);
            nextTab.requestFocus();
        }
    }

    public void stopPeekBehindActive(boolean animatePeek) {
        Node activeTab = getTabs().get(getActiveTabIndex());
        transitionY(activeTab, 0, animatePeek);
        activeTab.requestFocus();
    }

    private void transitionY(Node node, int translateByY, boolean animate){
        if(animate) {
            TranslateTransition tt = new TranslateTransition(Duration.millis(1000), node);
            tt.setToY(translateByY);
            tt.setCycleCount(0);
            tt.setAutoReverse(false);
            tt.setRate(10);
            tt.play();
        }else{
            node.setTranslateY(translateByY);
        }
    }

    private void transitionZ(Node node, int translateByZ, boolean animate){
        if(animate) {
            TranslateTransition tt = new TranslateTransition(Duration.millis(1000), node);
            tt.setToZ(translateByZ);
            tt.setCycleCount(0);
            tt.setAutoReverse(false);
            tt.setRate(10);
            tt.play();
        }else{
            node.setTranslateZ(translateByZ);
        }
    }

    private void initListeners(){
        ListChangeListener<Node> listChangeListener = new ListChangeListener<Node>() {
            @Override
            public void onChanged(Change<? extends Node> c) {
                setActiveTabIndex(activeTabIndex);
            }
        };
        getTabs().addListener(listChangeListener);
    }

}
