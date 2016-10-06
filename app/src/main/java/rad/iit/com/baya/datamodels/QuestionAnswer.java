package rad.iit.com.baya.datamodels;

/**
 * Created by chandradasdipok on 10/6/2016.
 */
public class QuestionAnswer {
    public long questionAnswerID;
    public String question ="";
    public String answer = "";
    public int questionerID = -1;
    public int questionCategory = -1;

    public QuestionAnswer(long questionAnswerID, String question, String answer, int questionerID, int questionCategory) {
        this.questionAnswerID = questionAnswerID;
        this.question = question;
        this.answer = answer;
        this.questionerID = questionerID;
        this.questionCategory = questionCategory;
    }
}
