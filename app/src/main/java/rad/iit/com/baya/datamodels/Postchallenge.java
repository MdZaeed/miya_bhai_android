
package rad.iit.com.baya.datamodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Postchallenge {

    @SerializedName("challenger")
    @Expose
    private String challenger;
    @SerializedName("question")
    @Expose
    private String question;

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
     *     The challenger
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
     *     The question
     */
    public void setQuestion(String question) {
        this.question = question;
    }

}
