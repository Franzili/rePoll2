package gpse.repoll.web.command;

public class DesignCmd {

    private String font;

    private String textColour;

    private String backgroundColour;

    public String getTextColour() {
        return textColour;
    }

    public String getBackgroundColour() {
        return backgroundColour;
    }

    public void setBackgroundColour(String backgroundColour) {
        this.backgroundColour = backgroundColour;
    }

    public void setTextColour(String textColour) {
        this.textColour = textColour;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }
}
