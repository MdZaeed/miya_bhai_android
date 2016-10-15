
package rad.iit.com.baya.datamodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddChallengeBody {

    @SerializedName("challenge")
    @Expose
    private Postchallenge postchallenge;

    public Postchallenge getPostchallenge() {
        return postchallenge;
    }

    public void setPostchallenge(Postchallenge postchallenge) {
        this.postchallenge = postchallenge;
    }
}
