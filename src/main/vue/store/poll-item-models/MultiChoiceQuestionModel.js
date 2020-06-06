import PollItemModel from "./PollItemModel";

class MultiChoiceQuestionModel extends PollItemModel {
    constructor(id, title) {
        super();
        this.id = id;
        this.type = "MultiChoiceQuestionModel";
        this.title = title;
    }

    static getDisplayType() {
        return "Multi Choice";
    }
}

export default MultiChoiceQuestionModel;
