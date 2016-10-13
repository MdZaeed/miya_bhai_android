package rad.iit.com.baya.datamodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ExpertiseAnswer implements Serializable{

    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("AskedBy")
    @Expose
    private String askedBy;
    @SerializedName("CategoryID")
    @Expose
    private String categoryID;
    @SerializedName("Question")
    @Expose
    private String question;
    @SerializedName("QuestionDate")
    @Expose
    private String questionDate;
    @SerializedName("Answer")
    @Expose
    private Object answer;
    @SerializedName("AnswerDate")
    @Expose
    private Object answerDate;

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
     *     The askedBy
     */
    public String getAskedBy() {
        return askedBy;
    }

    /**
     * 
     * @param askedBy
     *     The AskedBy
     */
    public void setAskedBy(String askedBy) {
        this.askedBy = askedBy;
    }

    /**
     * 
     * @return
     *     The categoryID
     */
    public String getCategoryID() {
        return categoryID;
    }

    /**
     * 
     * @param categoryID
     *     The CategoryID
     */
    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
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
    public Object getAnswer() {
        return answer;
    }

    /**
     * 
     * @param answer
     *     The Answer
     */
    public void setAnswer(Object answer) {
        this.answer = answer;
    }

    /**
     * 
     * @return
     *     The answerDate
     */
    public Object getAnswerDate() {
        return answerDate;
    }

    /**
     * 
     * @param answerDate
     *     The AnswerDate
     */
    public void setAnswerDate(Object answerDate) {
        this.answerDate = answerDate;
    }

}
