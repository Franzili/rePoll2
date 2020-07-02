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

    @Column
    private String logoPosition;

    @Lob
    @Column
    private String logo;


    public Design() {
        this.font = "Arial";
        this.textColour = "#000000";
        this.backgroundColour = "#ffffff";
        this.logoPosition = "left";
        this.logo = "";
    }

    public Design(Design design) {
        this.id = null;
        this.font = design.font;
        this.textColour = design.textColour;
        this.backgroundColour = design.backgroundColour;
        this.logoPosition = design.logoPosition;
        this.logo = design.logo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public String getLogoPosition() {
        return logoPosition;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setLogoPosition(String logoPosition) {
        this.logoPosition = logoPosition;
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
