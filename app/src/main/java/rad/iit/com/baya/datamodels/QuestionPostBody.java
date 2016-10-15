package rad.iit.com.baya.datamodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionPostBody {

    @SerializedName("question")
    @Expose
    private Question question;

    /**
     * 
     * @return
     *     The question
     */
    public Question getQuestion() {
        return question;
    }

    /**
     * 
     * @param question
     *     The question
     */
    public void setQuestion(Question question) {
        this.question = question;
    }

}
