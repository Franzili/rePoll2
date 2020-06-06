import PollItemModel from "./PollItemModel";

class ScaleQuestionModel extends PollItemModel {
    constructor(id, title) {
        super();
        this.id = id;
        this.type = "ScaleQuestionModel";
        this.title = title;
    }

    static getDisplayType() {
        return "Scale";
    }
}

export default ScaleQuestionModel;
