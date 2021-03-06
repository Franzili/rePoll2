import Vue from 'vue';

import api from "../api";

import SectionHeaderModel from "./poll-item-models/SectionHeaderModel";

import participants from "./participants";
import iterations from "./iterations";

/**
 * currentPoll holds the state of the Poll that is currently open, or otherwise in focus.
 * Can be used for PollTabbed pages (Config, Edit, Stats), and other pages that focus on
 * exactly one poll.
 */
const currentPoll = {
    modules: {
        participants: participants,
        iterations: iterations
    },
    state: {
        /**
         * The current poll object.
         */
        poll: {
            questions: [],
            pollSections: [],
            pollEntries: '',
            pollIterations: [],
            design: {
                font: '',
                textColour: '',
                backgroundColour: '',
                logoPosition: '',
                logo: ''
            },
            pollConsistencyGroups: []
        },
        answers: [],
        pollAnswers: [],
        /**
         * The statistics belonging to that poll object.
         */
        statistics: [],
        iterationStats: [],
        entries: [],
        tmpDownload: [],
        downloadFileName: '',
        iteration: 0,
        trendCharts: [{
            questionId: '',
            labels: [],
            datasets: [],
        }],
        modeCharts : [{
            questionId: '',
            xLabels: [],
            yLabels: [],
            data: [],
        }],
        barTrends: [{
            questionId: '',
            labels: [],
            datasets: [{
                label: '',
                backgroundColour: '',
                data: []
            }]
        }]
    },

    getters: {
        getIterationById: state => {
            return state.poll.pollIterations.find(iteration => iteration.id === state.iteration)
        },

        getStatByIteration: state => {
            let res = []
            state.statistics.forEach(stats => {
                if (stats.pollIteration.id === state.iteration) {
                    res =  stats
                }
            })
            if (res !== []) {
                return res.questionStats
            }
            return undefined

        },

        getIterationsListFormed: state => {
            if (state.poll.pollIterations !== undefined
                && state.poll.pollIterations !== null
                && state.poll.pollIterations.length > 0) {
                let iterationList = []
                state.poll.pollIterations.forEach(iteration => {
                    if (new Date(iteration.start) < Date.now()) {
                        iterationList.push({
                            value: iteration.id,
                            text: iteration.start
                        })
                    }
                })
                iterationList.reverse()
                return iterationList;
            }
            return null;
        },
        getIterationId: state => {
            return state.iteration
        },
        /**
         * Gets the poll structure as a flat array.
         * Array members are either questions or section headers, as defined in the PollItemModel classes
         * in ./poll-item-models/
         */
        pollStructureFlat: state => {
            let result = [];
            state.poll.pollSections.forEach(section => {
                result.push(new SectionHeaderModel(section.id, section.title, section.description));
                let tmp = []
                section.questions.forEach(q => {
                    let questionObject = state.poll.questions.find(item => item.id === q.id);
                    //res.push(makeQuestion(questionObject));
                    tmp.push(questionObject);
                    tmp = tmp.sort((q1, q2) => q1.questionOrder - q2.questionOrder);
                });
                result = result.concat(tmp);
            });
            return result;
        },


        /**
         * return the entries in form: [{section.title, [{questionId, question, answer}]}]
         * for a given user with a provided user id
         * */
        entriesWithSections: (state) => {
            return (participantId) => {
                let res = [];
                let userEntry = {entry: state.entries.find(entry => entry.participant.id === participantId)};
                let associations = null;
                let answers = [];       //used in MultiChoiceAnswer

                let orderedAnswers = [];  //ordered answers after questionsIDs
                let orderedQuestionsWithIds = [];
                let idQA = [];            //array in form [(questionId, Question, answer)]
                let sectionsWithQId = []; //array in form [(section.title, [question ids])]

                //first get all answers orderes after question IDs in one array
                if (userEntry.entry) {
                    associations = {associations: userEntry.entry.associations};

                    for (var prop in associations.associations) {
                        let answerAndId = null;
                        switch (associations.associations[prop].type) {
                            case 'TextAnswer':
                                answerAndId = {qId: prop, answer: associations.associations[prop].text};
                                break;
                            case 'SingleChoiceAnswer' :
                                answerAndId = {qId: prop, answer: associations.associations[prop].choice.text};
                                break;
                            case 'MultiChoiceAnswer' :
                                for (let i = 0; i < associations.associations[prop].choices.length; i++) {
                                    answers.push(associations.associations[prop].choices[i].text);
                                }
                                answerAndId = {qId: prop, answer: answers.toString()};
                                break;
                            case 'ScaleAnswer' :
                                answerAndId = {qId: prop, answer: associations.associations[prop].scaleNumber};
                                break;
                        }
                        orderedAnswers.push(answerAndId);
                    }
                }


                //then get all questions with question ids in one array
                for (let i = 0; i < state.poll.questions.length; i++) {
                    let tmpQ = state.poll.questions[i];
                    let idQObj = {qId: tmpQ.id, question: tmpQ.title};
                    orderedQuestionsWithIds.push(idQObj);
                }

                //combine the upper two arrays into desired form
                for (let i = 0; i < orderedAnswers.length; i++) {
                    let tmpQId = orderedQuestionsWithIds[i]['qId'];
                    let ans = '';
                    for (let j = 0; j < orderedAnswers.length; j++) {
                        if (parseInt(orderedAnswers[j].qId, 10) === parseInt(tmpQId, 10)) {
                            ans = orderedAnswers[j].answer;
                            break;
                        }
                    }
                    let IdQAObj = {qId: tmpQId, question: orderedQuestionsWithIds[i]['question'], answer: ans};
                    idQA.push(IdQAObj);
                }

                //here sectionsWithQId is filled with objects of form: {section.title, [question ids]}
                for (let section in state.poll.pollSections) {

                    let qIds = [];

                    for (let i = 0; i < state.poll.pollSections[section].questions.length; i++) {
                        qIds.push(state.poll.pollSections[section].questions[i].id);
                    }

                    let sectionsWithQIdObj = {section: state.poll.pollSections[section].title, qIds: qIds};

                    sectionsWithQId.push(sectionsWithQIdObj);
                }

                //finally bring all together in the final return form
                for (let i = 0; i < sectionsWithQId.length; i++) {
                    let tmpIdQA = [];
                    for (let j = 0; j < sectionsWithQId[i].qIds.length; j++) {
                        let idQAObj = idQA.find(idQAObj => idQAObj.qId === sectionsWithQId[i].qIds[j]);
                        tmpIdQA.push(idQAObj);
                    }
                    let finalObj = {section: sectionsWithQId[i].section, idQA: tmpIdQA};
                    res.push(finalObj);
                }

                return res;
            }
        },

        /**
         * return every username in entries
         * */
        entriesUserNames: state => {
            let res = [];
            state.entries.forEach(entry => {
                let name = entry.participant.fullName
                if (name === null) {
                    name = 'Anonymous'
                }
                let entryUser = {text: name, value: entry.participant.id};
                res.push(entryUser)
            });
            return res;
        },

        statStructureObj: state => {
            let strObj = [];
            let itStats = state.statistics.find(statistic => statistic.pollIteration.id === state.iteration)
            if (itStats !== undefined) {
                state.poll.pollSections.forEach(section => {
                    let statObjList = []
                    section.questions.forEach(q => {
                        let stats = itStats.questionStats.find(stat => stat.question.id === q.id)
                        statObjList.push(stats)
                    })
                    let secObj = {title: section.title, id: section.id, statistics: statObjList}
                    strObj.push(secObj)
                })
            }
            return strObj

        },

        pollStructureObj: state => {
            let strObj = [];
            state.poll.pollSections.forEach(section => {
                let qObjList = []
                section.questions.forEach(q => {
                    let choice = {text: q.title, value: q.id}
                    qObjList.push(choice)
                })
                let secObj = {label: section.title, options: qObjList, sId: section.id}
                strObj.push(secObj)
            })
            return strObj
        },

        // returns array of objects usable for table
        getAnswerSetByID: (state) => {
            return (id) => {
                let match = Object.entries((state.pollAnswers.find(answerSet => answerSet.question.id === id))
                    .participantAnswerMap)
                let tableObj = []
                if (match[0][1].type === 'TextAnswer') {
                    for (let i = 0; i < match.length; i++) {
                        let entry = {Username: match[i][0], Answers: match[i][1].text}
                        tableObj = [...tableObj, entry]
                    }
                    return tableObj

                } else if (match[0][1].type === 'SingleChoiceAnswer') {
                    for (let i = 0; i < match.length; i++) {
                        let entry = {Username: match[i][0], Answers: match[i][1].choice.text}
                        tableObj = [...tableObj, entry]
                    }
                    return tableObj

                } else if (match[0][1].type === 'ScaleAnswer') {
                    for (let i = 0; i < match.length; i++) {
                        let entry = {Username: match[i][0], Answers: match[i][1].scaleNumber}
                        tableObj = [...tableObj, entry]
                    }
                    return tableObj

                } else {
                    for (let i = 0; i < match.length; i++) {
                        let answers = ''
                        for (let j = 0; j < match[i][1].choices.length; j++) {
                            answers = match[i][1].choices[j].text + ', ' + answers
                        }
                        let entry = {Username: match[i][0], Answers: answers}
                        tableObj = [...tableObj, entry]
                    }
                    return tableObj
                }
            }
        },
        transformToChartData: () => {
            return (val) => {
                let labels = []
                let absFrq = []
                let relFrq = []
                for (let i = 0; i < val.frequencies.length; i++) {
                    labels.push(val.frequencies[i].choice.text);
                    absFrq.push(val.frequencies[i].absolute)
                    let rawRelFrq = val.frequencies[i].relative
                    relFrq.push(Math.round(rawRelFrq * 100));
                }
                let boxplot = []
                if (val.question.type === 'ScaleQuestion') {
                    for (let item in val.boxplot) {
                        boxplot.push(val.boxplot[item])
                    }
                    boxplot.splice(2, 0, val.median)
                }
                return {
                    label: val.question.title,
                    qType: val.question.type,
                    labels: labels,
                    data: absFrq,
                    absFrq: absFrq,
                    relFrq: relFrq,
                    boxplot: boxplot,
                    tableAnswers: [],
                    currentChart: 'bar'
                }
            }
        }
    },

    mutations: {

        setBarTrend(state, iterationStatistics) {
            let tmpBarTrends = []
            let first = iterationStatistics[0]

            first.questionStats.forEach(qStat => {
                tmpBarTrends.push({
                    questionId: qStat.question.id,
                    labels: [],
                    datasets: []
                })
            })
            iterationStatistics.forEach(itStats => {
                for (let i = 0; i < itStats.questionStats.length; i++) {
                    let tmpLabels = []
                    if (itStats.questionStats[i].question.type !== 'TextQuestion') {
                        itStats.questionStats[i].frequencies.forEach(frq => {
                            tmpLabels.push(frq.choice.text)
                        })
                    }
                    tmpBarTrends[i].labels = tmpLabels
                }
            })
            for (let i = 0; i < tmpBarTrends.length; i++) {
                iterationStatistics.forEach(itStats => {
                    let tmpData = []
                    itStats.questionStats[i].frequencies.forEach(frq => {
                        tmpData.push(frq.absolute)
                    })
                    tmpBarTrends[i].datasets.push({
                        label: new Intl.DateTimeFormat('en', {
                            year: 'numeric',
                            month: 'short',
                            day: '2-digit',
                            weekday: 'short',
                            hour: 'numeric',
                            minute: 'numeric',
                        }).format(new Date(itStats.pollIteration.start)),
                        backgroundColor: '',
                        data: tmpData
                    })
                })
            }
            state.barTrends = tmpBarTrends
        },

        setModes(state, iterationStatistics) {
            let tmpModes = []
            let tmpXLabels = []
            iterationStatistics.forEach(itStats => {
                tmpXLabels.push(new Intl.DateTimeFormat('en', {
                    year: 'numeric',
                    month: 'short',
                    day: '2-digit',
                    weekday: 'short',
                    hour: 'numeric',
                    minute: 'numeric',
                }).format(new Date(itStats.pollIteration.start)))
            })
            let first  = iterationStatistics[0]
            first.questionStats.forEach(qStat => {
                let tmpYLabels = ['']
                if (qStat.question.type !== 'TextQuestion' && qStat.question.type !== 'ScaleQuestion') {
                    qStat.question.choices.forEach(choice => {
                        tmpYLabels.push(choice.text)
                    })
                }
                if (qStat.question.type === 'ScaleQuestion') {
                    qStat.frequencies.forEach(frq => {
                        tmpYLabels.push(frq.choice.text)
                    })
                }
                tmpModes.push({
                    questionId: qStat.question.id,
                    xLabels: tmpXLabels,
                    yLabels: tmpYLabels,
                    data: []
                })
            })
            iterationStatistics.forEach(itStats => {
                for (let i = 0; i < itStats.questionStats.length; i++) {
                    if (itStats.questionStats[i].mode[0] !== undefined) {
                        tmpModes[i].data.push(itStats.questionStats[i].mode[0].text);
                    }
                }
            })
            state.modeCharts = tmpModes
        },

        setTrends(state, iterationStatistics) {
            let tmpLabels = []
            iterationStatistics.forEach(itStats => {
                tmpLabels.push(new Intl.DateTimeFormat('en', {
                    year: 'numeric',
                    month: 'short',
                    day: '2-digit',
                    weekday: 'short',
                    hour: 'numeric',
                    minute: 'numeric',
                }).format(new Date(itStats.pollIteration.start)))
            })
            let tmpTrends = []
            let first  = iterationStatistics[0]
            first.questionStats.forEach(qStat => {
                tmpTrends.push({
                    questionId: qStat.question.id,
                    labels: tmpLabels,
                    datasets: [],
                })
            })
            first.questionStats.forEach(qStat => {
                let tmpTrend = tmpTrends.find(trend => trend.questionId === qStat.question.id)
                if (qStat.question.type !== 'TextQuestion'){
                    tmpTrend.datasets.push({fill: true,
                        data: [],
                        label: qStat.frequencies[0].choice.text,
                        backgroundColor: '',
                        borderColor: ''})
                    for(let i = 1; i< qStat.frequencies.length; i++) {
                        tmpTrend.datasets.push({fill: '-1',
                            data: [],
                            label: qStat.frequencies[i].choice.text,
                            backgroundColor: '',
                            borderColor: ''})
                    }
                }
            })
            iterationStatistics.forEach(itStats => {
                itStats.questionStats.forEach(qStat => {
                    let tmpTrend = tmpTrends.find(trend => trend.questionId === qStat.question.id)
                    for(let i = 0; i< qStat.frequencies.length; i++) {
                        tmpTrend.datasets[i].data.push(qStat.frequencies[i].absolute)
                    }
                })
            })
            state.trendCharts = tmpTrends
        },

        setIterationId(state, newIteration) {
            state.iteration = newIteration
        },

        tmpDownloadSet(state, newDownload) {
            state.tmpDownload = newDownload;
        },

        downloadFileNameSet(state, fileName) {
            state.downloadFileName = fileName;
        },

        /**
         * Sets the new current poll.
         */
        set(state, newPoll) {
            state.poll = newPoll
        },

        setEntries(state, newEntries) {
            state.entries = newEntries
        },

        /**
         * Updates some poll parameters.
         * The pollCmd object may not be a full poll object. All parameters will be copied, so
         * a partial object can be supplied.
         */
        update(state, pollCmd) {
            Object.assign(state.poll, pollCmd)
        },

        updateDesign(state, designCmd) {
            if(designCmd.font != null) {
                state.poll.design.font = designCmd.font
            }
            if(designCmd.backgroundColour != null) {
                state.poll.design.backgroundColour = designCmd.backgroundColour
            }
            if(designCmd.textColour != null) {
                state.poll.design.textColour = designCmd.textColour
            }
            if(designCmd.logoPosition != null) {
                state.poll.design.logoPosition = designCmd.logoPosition
            }
            if(designCmd.logo != null) {
                state.poll.design.logo = designCmd.logo
            }
        },

        addPollSection(state, pollSection) {
            state.poll.pollSections.push(pollSection);
        },

        updatePollSection(state, pollSectionCmd) {
            let index = state.poll.pollSections.findIndex(section => section.id === pollSectionCmd.id);
            let pollSection = state.poll.pollSections[index];
            Object.assign(pollSection, pollSectionCmd);
            // we need to use Vue.set in order to maintain reactivity.
            Vue.set(state.poll.pollSections, index, pollSection);
        },

        updateStructureFromFlat(state, structure) {
            /* this is basically a duplicate of the updateStructure action, but because PollStructureCmd.java and
               state.poll.pollStructure require two slightly different formats, we cannot really avoid it. :(  */

            if (structure[0].type !== 'SectionHeader') {
                console.warn("[RePoll] Invalid poll structure format: first element needs to be header. abort.");
                return;
            }

            // assign the right questionOrders.
            for (let i = 0; i < structure.length; i++) {
                if (structure[i].type !== 'SectionHeader') {
                    structure[i].questionOrder = i + 1;
                }
            }

            let pollSections = []
            let currentSection = null;

            structure.forEach((item) => {
                if (item.type === 'SectionHeader') {
                    if (currentSection != null) {
                        pollSections.push(currentSection);
                    }
                    currentSection = item;
                    currentSection.questions = [];
                } else {
                    currentSection.questions.push(item);
                }
            })
            pollSections.push(currentSection);

            Vue.set(state.poll, 'pollSections', pollSections);
        },

        setPollStats(state, newPollStats) {
            state.statistics = newPollStats
        },

        setIterationStats(state, newStats) {
            state.iterationStats = newStats
        },

        addQuestion(state, question) {
            state.poll.questions.push(question);
        },

        setPollAnswers(state, newPollAnswers) {
            state.pollAnswers = newPollAnswers
        },
    },

    actions: {
        /**
         * Loads a poll from backend and sets it as the current one.
         */
        load({commit}, id) {
            return new Promise((resolve, reject) => {
                api.poll.get(id).then(function (res) {
                    commit('set', res.data);
                    resolve(res.data);
                }).catch(function (error) {
                    console.log(error);
                    reject();
                });
            });
        },

        loadEntries({commit}, getCmd) {
            return new Promise((resolve, reject) => {
                api.entries.list(getCmd).then(function (res) {
                    commit('setEntries', res.data);
                    resolve(res.data);
                }).catch(function (error) {
                    console.log(error);
                    reject();
                });
            });
        },

        /**
         * Updates some poll parameters. And sends them to the backend.
         * The response sent from the backend will be the new poll object.
         * The pollCmd object does not need to be a full poll,
         * but at least the id and the parameters to be changed should be set.
         */
        update({commit}, pollCmd) {
            return new Promise((resolve, reject) => {
                commit('update', pollCmd)
                api.poll.update(pollCmd)
                    .then((res) => {
                        resolve(res.data);
                    })
                    .catch((error) => {
                        console.log(error);
                        reject(error);
                    })
            });
        },

        updateDesign({commit}, designUpdate) {
            return new Promise((resolve, reject) => {
                api.design.updateDesign(designUpdate)
                    .then(function (res) {
                        commit('updateDesign', res.data)
                        resolve(res.data);
                    })
                    .catch((error) => {
                        console.log(error);
                        reject(error);
                    })
            });
        },

        loadPollStats({commit}, id) {
            return new Promise((resolve, reject) => {
                api.statistics.list(id).then(function (res) {
                    commit('setPollStats', res.data);
                    commit('setTrends', res.data);
                    commit('setModes', res.data);
                    commit('setBarTrend', res.data);
                    resolve(res.data);
                }).catch(function (error) {
                    console.log(error);
                    reject();
                })
            })
        },
        loadIterationStats({commit}, getCmd) {
            return new Promise((resolve,reject) => {
                api.statistics.byIteration(getCmd).then(function (res) {
                    commit('setIterationStats', res.data);
                    resolve(res.data);
                }).catch(function (error) {
                    console.log(error);
                    reject();
                })
            })
        },

        updatePollItem({commit, state}, pollItem) {
            if (pollItem.type === 'SectionHeader') {
                let pollSectionCmd = {
                    id: pollItem.id,
                    title: pollItem.title,
                    description: pollItem.description
                }
                commit('updatePollSection', pollSectionCmd);
                return new Promise(function(resolve, reject) {
                    api.poll.updatePollSection(state.poll.id, pollSectionCmd).then(() => {
                        resolve();
                    }).catch(function(error) {
                        console.log(error);
                        reject();
                    })
                })
            }

            else {
                return new Promise(function(resolve, reject) {
                    api.poll.updateQuestion(state.poll.id, pollItem).then(() => {
                        resolve();
                    }).catch(function(error) {
                        console.log(error);
                        reject();
                    })
                })
            }
        },

        async updateStructure({state, dispatch, commit}, newStructure) {
            if (newStructure[0].type !== 'SectionHeader') {
                console.warn("[RePoll] New poll structure does not start with a header. Aborting.");
                return;
            }

            /* first, assign question orders */
            for (let i = 0; i < newStructure.length; i++) {
                if (newStructure[i].type !== 'SectionHeader') {
                    newStructure[i].questionOrder = i + 1;
                }
            }

            /* then, check if there are any new poll items that have not been POSTed to the server yet */
            let questionPromises = [];
            let questionUpdateIndices = [];
            let pollSectionPromises = [];
            let pollSectionUpdateIndices = [];
            for (let i = 0; i < newStructure.length; i++) {
                if (newStructure[i].id === -1) {
                    if (newStructure[i].type === 'SectionHeader') {
                        pollSectionPromises.push(api.poll.addPollSection(state.poll.id, newStructure[i]))
                        pollSectionUpdateIndices.push(i);
                    } else {
                        questionPromises.push(api.poll.addQuestion(state.poll.id, newStructure[i]))
                        questionUpdateIndices.push(i);
                    }
                }
            }

            /* now update the newly POSTed questions to have a proper ID */
            let questionsServerResponse = await Promise.all(questionPromises)
            for (let i = 0; i < questionPromises.length; i++) {
                let updateIndex = questionUpdateIndices[i];
                let updatedQuestion = questionsServerResponse[i].data;
                newStructure[updateIndex] = updatedQuestion;
                commit('addQuestion', updatedQuestion);
            }

            /* ...and also update poll Sections from server responses. */
            let sectionsServerResponse = await Promise.all(pollSectionPromises);
            for (let i = 0; i < pollSectionPromises.length; i++) {
                let updateIndex = pollSectionUpdateIndices[i];
                let updatedSection = sectionsServerResponse[i].data;

                // adding PollItem type because the backend does not do that for us.
                updatedSection.type = 'SectionHeader';

                newStructure[updateIndex] = updatedSection;
                commit('addPollSection', updatedSection);
            }

            let structureCmd = {};
            let currentSectionId = "";

            newStructure.forEach(function (elem) {
                if (elem.type === 'SectionHeader') {
                    currentSectionId = elem.id;
                    structureCmd[currentSectionId] = [];
                } else {
                    structureCmd[currentSectionId].push(elem.id);
                }
            });

            commit('updateStructureFromFlat', newStructure)

            // send all questions to the backend to update question order. This is suuuper bad.-
            let promises = newStructure
                .filter(item => item.type !== 'SectionHeader')
                .map(item => dispatch('updatePollItem', item))

            await Promise.all(promises);
            await api.poll.updateStructure(state.poll.id, structureCmd);
        },

        async removePollItem({state, getters, dispatch}, id) {
            let structure = getters.pollStructureFlat;
            let index = structure.findIndex(item => item.id === id);
            let item = structure[index]
            if (index === 0) {
                console.warn("[RePoll] Attempting to remove first Item which is probably a Section Header. Aborting.");
                return new Promise((resolve) => resolve());
            }
            structure.splice(index, 1);
            await dispatch('updateStructure', structure);

            if (item.type === 'SectionHeader') {
                return new Promise((resolve, reject) => {
                    api.poll.removePollSection(state.poll.id, id).then(function () {
                        resolve();
                    }).catch(function(err) {
                        console.log(err);
                        reject(err);
                    });
                });
            } else {
                return new Promise((resolve, reject) => {
                    api.poll.removeQuestion(state.poll.id, id).then(function() {
                        resolve();
                    }).catch(function(err) {
                        console.log(err);
                        reject(err);
                    });
                });
            }
        },

        loadPollAnswers({commit}, getCmd) {
            return new Promise((resolve, reject) => {
                api.statistics.getPollAnswers(getCmd).then(function (res) {
                    commit('setPollAnswers', res.data);
                    resolve(res.data);
                }).catch(function (error) {
                    console.log(error);
                    reject();
                })
            })
        },

        loadAnswersById({commit}, getCmd) {
            return new Promise((resolve, reject) => {
                api.statistics.getAnswersById(getCmd).then(function (res) {
                    commit('setAnswersById', res.data);
                    resolve(res.data);
                }).catch(function (error) {
                    console.log(error);
                    reject();
                })
            })
        },

        /**
         * cmd has form: {pollId, type, format}
         * */
        async download({commit, state}, cmd,) {
            await new Promise(((resolve, reject) => {
                cmd.id = state.poll.id;
                let res = [];

                api.poll.download(cmd).then((response) => {
                    res = response.data;
                    console.log(state.poll);
                    commit('tmpDownloadSet', res);
                    commit('downloadFileNameSet', state.poll.title + '_' + cmd.type + '.' + cmd.format);
                    resolve(res);
                }).catch(function (error) {
                    console.log(error);
                    reject();
                })
            }));

            let fileURL = window.URL.createObjectURL(new Blob([state.tmpDownload]));
            let fileLink = document.createElement('a');

            fileLink.href = fileURL;
            fileLink.setAttribute('download', state.downloadFileName);
            document.body.appendChild(fileLink);

            fileLink.click();
        },

        launch({dispatch, state}) {
            let pollCmd = {
                id: state.poll.id,
                status: "LAUNCHED"
            };
            return dispatch("update", pollCmd);
        }
    },

    namespaced: true
};

export default currentPoll;
