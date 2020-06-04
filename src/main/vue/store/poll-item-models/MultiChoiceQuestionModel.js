import PollItemModel from "./PollItemModel";

class MultiChoiceQuestionModel extends PollItemModel {
    constructor(title) {
        super();
        this.type = "MultiChoiceQuestionModel";
        this.title = title;
    }

    static getDisplayType() {
        return "Multi Choice";
    }
}

export default MultiChoiceQuestionModel;
