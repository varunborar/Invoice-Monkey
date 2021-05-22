package org.invoice.monkey.utils;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class Animation {

    public void simpleRefreshAnimation(Node node)
    {
        RotateTransition rotate = new RotateTransition();
        rotate.setAxis(Rotate.Z_AXIS);
        rotate.setByAngle(360);
        rotate.setCycleCount(3);
        rotate.setDuration(Duration.millis(1250));
        rotate.setAutoReverse(false);

        rotate.setNode(node);
        rotate.play();
    }

    public void slideInLeftAnimation(StackPane parent, AnchorPane child)
    {
        child.translateXProperty().set(-parent.getWidth());
        child.toFront();

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(child.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.millis(250), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }
}
