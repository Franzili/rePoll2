import TextQuestionModel from "./TextQuestionModel";
import SingleChoiceQuestionModel from "./SingleChoiceQuestionModel"
import MultiChoiceQuestionModel from "./MultiChoiceQuestionModel"
import ScaleQuestionModel from "./ScaleQuestionModel"
import SectionHeaderModel from "./SectionHeaderModel";

export { default as SectionHeader } from "./SectionHeaderModel"
export { default as TextQuestionModel } from "./TextQuestionModel"
export { default as SingleChoiceQuestionModel } from "./ScaleQuestionModel"
export { default as MultiChoiceQuestionModel } from "./MultiChoiceQuestionModel"
export { default as ScaleQuestionModel } from "./ScaleQuestionModel"

export const models = [
    SectionHeaderModel,
    TextQuestionModel,
    SingleChoiceQuestionModel,
    MultiChoiceQuestionModel,
    ScaleQuestionModel
];

export function makeQuestion(question) {
    // TODO: return question types that are actually sensible.
    return new TextQuestionModel(question.id, question.title);
}
