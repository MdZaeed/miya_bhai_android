package rad.iit.com.baya.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import rad.iit.com.baya.R;
import rad.iit.com.baya.datamodels.ExpertiseAnswer;


/**
 * Created by chandradasdipok on 5/17/2016.
 */
public class RecyclerViewListAdapterForExpertAnswers extends RecyclerView.Adapter<RecyclerViewListAdapterForExpertAnswers.MyViewHolder>{

    int cardID;
    private ArrayList<ExpertiseAnswer> expertiseAnswers;
    Context context;
    private OnExpertQuestionClicked onExpertQuestionClicked;

    public RecyclerViewListAdapterForExpertAnswers(Context context, int cardID, ArrayList<ExpertiseAnswer> expertiseAnswers) {
        this.cardID = cardID;
        this.setExpertiseAnswers(expertiseAnswers);
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(cardID,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ExpertiseAnswer expertiseAnswer= getExpertiseAnswers().get(position);
        holder.questionTextView.setText(expertiseAnswer.getQuestion());
    }

    @Override
    public int getItemCount() {
        return getExpertiseAnswers().size();
    }

    public OnExpertQuestionClicked getOnQuestionClicked() {
        return onExpertQuestionClicked;
    }

    public void setOnQuestionClicked(OnExpertQuestionClicked onExpertQuestionClicked) {
        this.onExpertQuestionClicked = onExpertQuestionClicked;
    }

    public ArrayList<ExpertiseAnswer> getExpertiseAnswers() {
        return expertiseAnswers;
    }

    public void setExpertiseAnswers(ArrayList<ExpertiseAnswer> expertiseAnswers) {
        this.expertiseAnswers = expertiseAnswers;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView questionTextView;
        public MyViewHolder(View itemView) {
            super(itemView);

            questionTextView= (TextView) itemView.findViewById(R.id.tv_question);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onExpertQuestionClicked.onIndividualQuestionClicked(expertiseAnswers.get(getAdapterPosition()));
        }
    }

    public interface OnExpertQuestionClicked
    {
        void onIndividualQuestionClicked(ExpertiseAnswer expertiseAnswer);
    }
}
