package rad.iit.com.baya.datamodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Question {

    @SerializedName("askedBy")
    @Expose
    private String askedBy;
    @SerializedName("categoryId")
    @Expose
    private String categoryId;
    @SerializedName("question")
    @Expose
    private String question;

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
     *     The askedBy
     */
    public void setAskedBy(String askedBy) {
        this.askedBy = askedBy;
    }

    /**
     * 
     * @return
     *     The categoryId
     */
    public String getCategoryId() {
        return categoryId;
    }

    /**
     * 
     * @param categoryId
     *     The categoryId
     */
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
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
     *     The question
     */
    public void setQuestion(String question) {
        this.question = question;
    }

}
