package gpse.repoll.domain.poll;


import javax.persistence.*;

@Entity
public class Design {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String font;

    @Column
    private String textColour;

    @Column
    private String backgroundColour;

    public Design() {
        this.font = "hallo";
    }

    public String getBackgroundColour() {
        return backgroundColour;
    }

    public String getTextColour() {
        return textColour;
    }

    public void setTextColour(String textColour) {
        this.textColour = textColour;
    }

    public void setBackgroundColour(String backgroundColour) {
        this.backgroundColour = backgroundColour;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }
}
