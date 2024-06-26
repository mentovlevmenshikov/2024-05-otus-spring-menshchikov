package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.util.List;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        // Получить вопросы из дао и вывести их с вариантами ответов
        getAndPrintTests();
    }

    private void  getAndPrintTests() {
        try {
            List<Question> questions = questionDao.findAll();
            printQuestions(questions);
        } catch (QuestionReadException e) {
            ioService.printLine(e.getMessage());
        } catch (Exception e) {
            ioService.printLine("Something went wrong.");
        }
    }

    private void printQuestions(List<Question> questions) {
        if (questions == null || questions.isEmpty()) {
            ioService.printLine("Qustions missing");
            return;
        }

        for (Question question : questions) {
            ioService.printFormattedLine("Question: %s", question.text());
            List<Answer> answers = question.answers();
            if (answers == null) {
                ioService.printLine("Write your answer: ");
                continue;
            }

            ioService.printFormattedLine("Сhoose answers:");
            for (Answer answer : question.answers()) {
                ioService.printFormattedLine("\t%s%s", answer.text(), answer.isCorrect() ? "*" : "");
            }
            ioService.printLine("");
        }
    }
}
