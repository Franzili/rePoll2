package gpse.repoll.domain.questions;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

/**
 * A question that can be answered with a @link{RadioButtonAnswer}.
 */
@Entity
public class RadioButtonQuestion extends Question {

    List<String> choices = new ArrayList<>();

}
