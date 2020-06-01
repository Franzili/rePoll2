import TextQuestion from "./TextQuestion";

export { default as SectionHeader } from "./SectionHeader"
export { default as TextQuestion } from "./TextQuestion"

export const questionTypes = [ TextQuestion ];

export function makeQuestion(question) {
    // TODO: return question types that are actually sensible.
    return new TextQuestion(question.title);
}
