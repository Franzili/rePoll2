import TextQuestionModel from "./TextQuestionModel";

export { default as SectionHeader } from "./SectionHeaderModel"
export { default as TextQuestion } from "./TextQuestionModel"

export const questionTypes = [ TextQuestionModel ];

export function makeQuestion(question) {
    // TODO: return question types that are actually sensible.
    return new TextQuestionModel(question.title);
}
