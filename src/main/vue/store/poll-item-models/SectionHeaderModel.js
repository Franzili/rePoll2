import PollItemModel from "./PollItemModel"

class SectionHeaderModel extends PollItemModel {
    constructor(title, description) {
        super();
        this.type = "SectionHeaderModel";
        this.title = title;
        this.description = description;
    }

    static getDisplayType() {
        return "Section"
    }
}

export default SectionHeaderModel;
