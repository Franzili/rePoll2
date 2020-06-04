import PollItemModel from "./PollItemModel";

class TextQuestionModel extends PollItemModel {
    constructor(title) {
        super();
        this.type = "TextQuestionModel";
        this.title = title;
    }

    static getDisplayType() {
        return "Text";
    }
}

export default TextQuestionModel;
