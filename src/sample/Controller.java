package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;

public class Controller {

    @FXML
    private Text actiontarget;


    @FXML
    private Slider slider;

    @FXML
    private void initialize() {
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                                Number oldValue, Number newValue) {
                int value = newValue.intValue() * 50;
                actiontarget.setTranslateZ(value);
                actiontarget.setText("Slide value is " + value);
            }
        });
    }

    @FXML
    private void handleSubmitButtonAction(ActionEvent event)
    {
        actiontarget.setText("Sign in button pressed");
    }
}
