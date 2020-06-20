package gpse.repoll.domain.poll;


import javax.persistence.Entity;

@Entity
public class Design extends Poll {

    private String font;

    public Design() {
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }
}
