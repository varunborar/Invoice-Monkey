package org.invoice.monkey.utils;

import javafx.animation.RotateTransition;
import javafx.scene.Node;
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
}
