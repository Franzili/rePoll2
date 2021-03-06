import PollItemModel from "./PollItemModel";

class SectionHeaderModel extends PollItemModel {
    constructor(id, title = "Section Header", description = "A description for this Section Header") {
        super()
        this.id = id;
        this.type = "SectionHeader";
        this.title = title;
        this.description = description;
    }

    static getDisplayType() {
        return "Section"
    }
}

export default SectionHeaderModel;
