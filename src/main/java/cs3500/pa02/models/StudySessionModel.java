package cs3500.pa02.models;

import cs3500.pa02.Difficulty;
import cs3500.pa02.questionutilities.Question;
import java.util.ArrayList;

/**
 * Class for modeling a study session
 */
public class StudySessionModel {

  private final ArrayList<Question> questions;
  private final int numQuestions;
  private int currentQuestion;
  private int easyToHard;
  private int hardToEasy;

  /**
   * Instantiates a study session
   */
  public StudySessionModel(ArrayList<Question> questions, int numQuestions) {
    this.questions = questions;
    this.numQuestions = numQuestions;
    this.currentQuestion = 0;
  }

  /**
   * Gets the next question in the list
   *
   * @return the next question in the list
   */
  public Question nextQuestion() {
    if (currentQuestion < numQuestions && currentQuestion < questions.size()) {
      int current = currentQuestion;
      this.currentQuestion++;
      return questions.get(current);
    } else {
      throw new IllegalArgumentException("No more questions left.");
    }
  }

  /**
   * Gets the current question
   *
   * @return the current index
   */
  public int getCurrent() {
    return this.currentQuestion;
  }

  /**
   * Gets the current question
   *
   * @return the current index
   */
  public int getEasyToHard() {
    return this.easyToHard;
  }

  /**
   * Gets the current question
   *
   * @return the current index
   */
  public int getHardToEasy() {
    return this.hardToEasy;
  }

  /**
   * Sets the difficulty of the question and updates the stats
   *
   * @param difficulty the difficulty to set the question
   */
  public void setDifficulty(Question current, Difficulty difficulty) {
    if (current.getDifficulty().equals(Difficulty.EASY) && difficulty.equals(Difficulty.HARD)) {
      easyToHard++;
      questions.get(currentQuestion).setDifficulty(difficulty);
    }
    if (current.getDifficulty().equals(Difficulty.HARD) && difficulty.equals(Difficulty.EASY)) {
      hardToEasy++;
      questions.get(currentQuestion).setDifficulty(difficulty);
    }
  }

}
