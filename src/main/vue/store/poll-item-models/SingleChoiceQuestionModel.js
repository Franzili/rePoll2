import PollItemModel from "./PollItemModel";

class SingleChoiceQuestionModel extends PollItemModel {
    constructor(title) {
        super();
        this.type = "SingleChoiceQuestionModel";
        this.title = title;
    }

    static getDisplayType() {
        return "Single Choice";
    }
}

export default SingleChoiceQuestionModel;
