import PollItemModel from "./PollItemModel";

class ScaleQuestionModel extends PollItemModel {
    constructor(title) {
        super();
        this.type = "ScaleQuestionModel";
        this.title = title;
    }

    static getDisplayType() {
        return "Scale";
    }
}

export default ScaleQuestionModel;
