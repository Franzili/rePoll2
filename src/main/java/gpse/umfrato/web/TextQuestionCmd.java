package gpse.umfrato.web;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("TextQuestion")
public class TextQuestionCmd extends QuestionCmd {
    // A text question does not need any attributes
}
