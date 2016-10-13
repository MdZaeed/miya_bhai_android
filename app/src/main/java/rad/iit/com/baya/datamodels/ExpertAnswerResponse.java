
package rad.iit.com.baya.datamodels;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExpertAnswerResponse {

    @SerializedName("expertise_answers")
    @Expose
    private List<ExpertiseAnswer> expertiseAnswers = new ArrayList<ExpertiseAnswer>();

    /**
     * 
     * @return
     *     The expertiseAnswers
     */
    public List<ExpertiseAnswer> getExpertiseAnswers() {
        return expertiseAnswers;
    }

    /**
     * 
     * @param expertiseAnswers
     *     The expertise_answers
     */
    public void setExpertiseAnswers(List<ExpertiseAnswer> expertiseAnswers) {
        this.expertiseAnswers = expertiseAnswers;
    }

}
