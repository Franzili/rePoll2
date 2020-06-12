import PollItemModel from "./PollItemModel";

class MultiChoiceQuestionModel extends PollItemModel {
    constructor(id) {
        super();
        this.id = id;
        this.type = "MultiChoiceQuestion";
        this.title = "Multi Choice Question";
        this.choices = [
            {
                text: "Initial Choice 1"
            },
            {
                text: "Initial Choice 2"
            }
        ]
    }

    static getDisplayType() {
        return "Multi Choice";
    }
}

export default MultiChoiceQuestionModel;
