package gpse.repoll.domain.poll;


import javax.persistence.Entity;

@Entity
public class Design extends Poll {

    private String schriftart;

    public Design() {
    }

    public String getSchriftart() {
        return schriftart;
    }

    public void setSchriftart(String schriftart) {
        this.schriftart = schriftart;
    }
}
