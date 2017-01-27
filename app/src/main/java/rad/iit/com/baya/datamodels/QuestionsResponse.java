
package rad.iit.com.baya.datamodels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionsResponse {

    @SerializedName("challenges")
    @Expose
    private List<Challenge> challenges = new ArrayList<Challenge>();

    /**
     * 
     * @return
     *     The challenges
     */
    public List<Challenge> getChallenges() {
        return challenges;
    }

    /**
     * 
     * @param challenges
     *     The challenges
     */
    public void setChallenges(List<Challenge> challenges) {
        this.challenges = challenges;
    }

    public static QuestionsResponse mockData()
    {
        QuestionsResponse questionsResponse=new QuestionsResponse();

        List<Challenge> challenges=new ArrayList<>();
        for(int i=0;i<5;i++)
        {
            challenges.add(Challenge.getMock(true));
        }
        questionsResponse.setChallenges(challenges);
        return questionsResponse;
    }

}
