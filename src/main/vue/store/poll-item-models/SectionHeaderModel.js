import PollItemModel from "./PollItemModel";

class SectionHeaderModel extends PollItemModel {
    constructor(id) {
        super()
        this.id = id;
        this.type = "SectionHeader";
        this.title = "Section Header";
        this.description = "A description for this Section Header."
    }

    static getDisplayType() {
        return "Section"
    }
}

export default SectionHeaderModel;
