package org.invoice.monkey.utils.invoicegenerator.background;

public enum BackgroundType {

    NONE("none.png"),
    WAVES("waves.png"),
    BLOCKS("blocks.png"),
    BORDER("border.png"),
    CIRCLES("circles.png"),
    FLOWERS("flowers.png"),
    FOOD("food.png"),
    HEX("hex.png"),
    RECTANGLES("rectangles.png"),
    RIBBON("ribbon.png"),
    SPLASH("splash.png"),;

    private final String Type;

    BackgroundType(String Type)
    {
        this.Type = Type;
    }

    public String getType()
    {
        return this.Type;
    }

    public String getURL()
    {
        return Background.class.getResource(this.Type).toString();
    }
}
