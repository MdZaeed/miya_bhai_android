
package rad.iit.com.baya.datamodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Challenge implements Serializable{

    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("Challenger")
    @Expose
    private String challenger;
    @SerializedName("Question")
    @Expose
    private String question;
    @SerializedName("QuestionDate")
    @Expose
    private String questionDate;
    @SerializedName("Answer")
    @Expose
    private String answer;
    @SerializedName("AnswerDate")
    @Expose
    private String answerDate;

    /**
     * 
     * @return
     *     The iD
     */
    public String getID() {
        return iD;
    }

    /**
     * 
     * @param iD
     *     The ID
     */
    public void setID(String iD) {
        this.iD = iD;
    }

    /**
     * 
     * @return
     *     The challenger
     */
    public String getChallenger() {
        return challenger;
    }

    /**
     * 
     * @param challenger
     *     The Challenger
     */
    public void setChallenger(String challenger) {
        this.challenger = challenger;
    }

    /**
     * 
     * @return
     *     The question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * 
     * @param question
     *     The Question
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * 
     * @return
     *     The questionDate
     */
    public String getQuestionDate() {
        return questionDate;
    }

    /**
     * 
     * @param questionDate
     *     The QuestionDate
     */
    public void setQuestionDate(String questionDate) {
        this.questionDate = questionDate;
    }

    /**
     * 
     * @return
     *     The answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * 
     * @param answer
     *     The Answer
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * 
     * @return
     *     The answerDate
     */
    public String getAnswerDate() {
        return answerDate;
    }

    /**
     * 
     * @param answerDate
     *     The AnswerDate
     */
    public void setAnswerDate(String answerDate) {
        this.answerDate = answerDate;
    }

}
