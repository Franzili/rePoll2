import PollItemModel from "./PollItemModel";

class TextQuestionModel extends PollItemModel {
    constructor(id, title, charLimit = 255) {
        super();
        this.id = id;
        this.type = "TextQuestionModel";
        this.title = title;
        this.charLimit = charLimit;
    }

    static getDisplayType() {
        return "Text";
    }
}

export default TextQuestionModel;
