import PollItemModel from "./PollItemModel";

class TextQuestionModel extends PollItemModel {
    constructor(title, charLimit = 255) {
        super();
        this.type = "TextQuestionModel";
        this.title = title;
        this.charLimit = charLimit;
    }

    static getDisplayType() {
        return "Text";
    }
}

export default TextQuestionModel;
