import PollItemModel from "./PollItemModel"

class SectionHeaderModel extends PollItemModel {
    constructor(title, description) {
        super();
        this.title = title;
        this.description = description;
    }
}

export default SectionHeaderModel;
