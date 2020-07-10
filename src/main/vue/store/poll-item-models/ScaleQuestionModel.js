import PollItemModel from "./PollItemModel";

class ScaleQuestionModel extends PollItemModel {
    constructor(id) {
        super();
        this.id = id;
        this.type = "ScaleQuestion";
        this.title = "Scale Question";
        this.min = 1;
        this.max = 10;
        this.stepCount = 1;
        this.scaleNameLeft = "Lower Bound";
        this.scaleNameRight = "Upper Bound";
    }

    static getDisplayType() {
        return "Scale";
    }
}

export default ScaleQuestionModel;
