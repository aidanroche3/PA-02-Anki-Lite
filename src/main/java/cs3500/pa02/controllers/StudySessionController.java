package cs3500.pa02.controllers;

import cs3500.pa02.Difficulty;
import cs3500.pa02.fileutilities.WriteFilesToPath;
import cs3500.pa02.models.StudySessionModel;
import cs3500.pa02.questionutilities.FormatQuestions;
import cs3500.pa02.questionutilities.Question;
import cs3500.pa02.questionutilities.RandomizeQuestions;
import cs3500.pa02.questionutilities.ReadAsQuestions;
import cs3500.pa02.readers.InputReader;
import cs3500.pa02.views.StudySessionView;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Controller for controlling a study session
 */
public class StudySessionController implements Controller {

  private State state = State.InitialInputPhase;
  private final StudySessionView studySessionView = new StudySessionView();
  private final InputReader inputReader = new InputReader();
  private StudySessionModel studySessionModel;
  private Path inputPath;
  private ArrayList<Question> questions;
  private int numQuestions;

  /**
   * Initiates the controller
   */
  public void run() {
    if (this.state.equals(State.InitialInputPhase)) {
      acceptPath();
      setState(State.GenerateQuestion);
      generateQuestions(this.inputPath);
      setState(State.InitialInputPhase);
      acceptNumQuestions();
      studySessionView.begin();
      studySessionModel = new StudySessionModel(questions, numQuestions);
      setState(State.StudySession);
      studySession();
    } else {
      throw new IllegalStateException("Invalid state, cannot run controller.");
    }
  }

  /**
   * Accepts a valid input path from the user
   */
  private void acceptPath() {
    if (this.state.equals(State.InitialInputPhase)) {
      studySessionView.welcome();
      InputReader inputReader = new InputReader();
      String input = inputReader.read(new InputStreamReader(System.in));
      this.inputPath = Path.of(input);
      while (!this.inputPath.toFile().exists() || !this.inputPath.toString().endsWith(".sr")) {
        studySessionView.invalidPath();
        input = inputReader.read(new InputStreamReader(System.in));
        this.inputPath = Path.of(input);
      }
    }
  }

  /**
   * Accepts a valid number of questions from the user
   */
  private void acceptNumQuestions() {
    if (this.state.equals(State.InitialInputPhase)) {
      studySessionView.initialPrompt();
      String input = inputReader.read(new InputStreamReader(System.in));
      while (true) {
        try {
          this.numQuestions = Integer.parseInt(input);
          if (this.numQuestions < 1) {
            studySessionView.invalidNumberPrompt(questions.size());
            input = inputReader.read(new InputStreamReader(System.in));
          } else {
            break;
          }
        } catch (NumberFormatException e) {
          studySessionView.invalidNumberPrompt(questions.size());
          input = inputReader.read(new InputStreamReader(System.in));
        }
      }
    } else {
      throw new IllegalStateException("Cannot accept number of questions.");
    }
  }

  /**
   * Generates a list of questions from the given input
   *
   * @param inputPath the given input path for the .sr file
   */
  private void generateQuestions(Path inputPath) {
    if (this.state.equals(State.GenerateQuestion)) {
      studySessionView.generating();
      ReadAsQuestions readAsQuestions = new ReadAsQuestions(inputPath.toFile());
      ArrayList<Question> questions = readAsQuestions.generateListOfQuestions();
      RandomizeQuestions randomizeQuestions = new RandomizeQuestions(questions);
      this.questions = randomizeQuestions.getRandomizedQuestions();
    } else {
      throw new IllegalStateException("Cannot generate questions.");
    }
  }

  /**
   * Runs the study session
   */
  private void studySession() {
    studySessionView.options();
    while (state.equals(State.StudySession)) {
      try {
        Question next = studySessionModel.nextQuestion();
        int currentQuestion = studySessionModel.getCurrent();
        studySessionView.displayQuestion(next, currentQuestion);
        String input = inputReader.read(new InputStreamReader(System.in));
        handleInput(input, next);
      } catch (IllegalArgumentException e) {
        end();
      }
    }
  }

  /**
   * Handles user input during the study session
   */
  public void handleInput(String input, Question current) {
    switch (input.toLowerCase()) {
      case "h" -> studySessionModel.setDifficulty(current, Difficulty.HARD);
      case "e" -> studySessionModel.setDifficulty(current, Difficulty.EASY);
      case "a" -> studySessionView.answer(current);
      case "t" -> end();
      default -> {
        studySessionView.options();
        handleInput(inputReader.read(new InputStreamReader(System.in)), current);
      }
    }
  }

  /**
   * Changes the state of the controller
   */
  private void setState(State state) {
    this.state = state;
  }

  /**
   * Ends the controller
   */
  private void end() {
    if (this.state.equals(State.StudySession)) {
      this.state = State.Stats;
      FormatQuestions formatQuestions = new FormatQuestions(questions);
      String srFormat = formatQuestions.formatAsSr();
      WriteFilesToPath writeFilesToPath = new WriteFilesToPath();
      try {
        writeFilesToPath.writeAtPath(inputPath, srFormat);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      studySessionView.stats(studySessionModel.getCurrent(), studySessionModel.getEasyToHard(),
          studySessionModel.getHardToEasy(), formatQuestions.getNumHard(),
          formatQuestions.getNumEasy());
      studySessionView.goodbye();
    } else {
      throw new IllegalArgumentException("Cannot end session yet.");
    }
  }

}

