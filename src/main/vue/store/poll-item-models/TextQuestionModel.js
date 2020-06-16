import PollItemModel from "./PollItemModel";

class TextQuestionModel extends PollItemModel {
    constructor(id) {
        super();
        this.id = id;
        this.type = "TextQuestion";
        this.title = "Text Question";
        this.charLimit = 256;
    }

    static getDisplayType() {
        return "Text";
    }
}

export default TextQuestionModel;
