import PollItemModel from "./PollItemModel";

class SingleChoiceQuestionModel extends PollItemModel {
    constructor(id) {
        super();
        this.id = id;
        this.type = "SingleChoiceQuestion";
        this.title = "Single Choice Question";
        this.displayVariant = "radio";
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
        return "Single Choice";
    }
}

export default SingleChoiceQuestionModel;
