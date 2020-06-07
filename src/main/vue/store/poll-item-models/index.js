/**
 * Models for Questions. These are used if we create question objects ourselves.
 */

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

/**
 * All the item models - this can, for example, be used to build a question palette in the editor.
 */
export const models = [
    SectionHeaderModel,
    TextQuestionModel,
    SingleChoiceQuestionModel,
    MultiChoiceQuestionModel,
    ScaleQuestionModel
];

/**
 * Generate an appropriate question view model from the question object we got from the backend.
 * This function is responsible to do the model mapping. I.e. if something changes in backend, this function
 * should be adapted. This way, we don't have to change code in the frontend so often.
 */
export function makeQuestion(question) {
    // TODO: return question types that are actually sensible.
    return new TextQuestionModel(question.id, question.title);
}
