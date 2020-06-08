import PollItemModel from "./PollItemModel";

class SingleChoiceQuestionModel extends PollItemModel {
    constructor(id, title) {
        super();
        this.id = id;
        this.type = "SingleChoiceQuestion";
        this.title = title;
    }

    static getDisplayType() {
        return "Single Choice";
    }
}

export default SingleChoiceQuestionModel;
