package rad.iit.com.baya.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import rad.iit.com.baya.R;
import rad.iit.com.baya.activities.template.TemplateActivity;
import rad.iit.com.baya.datamodels.Challenge;
import rad.iit.com.baya.datamodels.OnRecyclerViewItemListener;


/**
 * Created by chandradasdipok on 5/17/2016.
 */
public class RecyclerViewListAdapter extends RecyclerView.Adapter<RecyclerViewListAdapter.MyViewHolder>{

    int cardID;
    ArrayList<Challenge> challenges;
    Context context;
    private OnQuestionClicked onQuestionClicked;

    public RecyclerViewListAdapter(Context context, int cardID, ArrayList<Challenge> challenges) {
        this.cardID = cardID;
        this.challenges=challenges;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(cardID,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Challenge challenge=challenges.get(position);

        holder.questionTextView.setText(challenge.getQuestion());
    }

    @Override
    public int getItemCount() {
        return challenges.size();
    }

    public OnQuestionClicked getOnQuestionClicked() {
        return onQuestionClicked;
    }

    public void setOnQuestionClicked(OnQuestionClicked onQuestionClicked) {
        this.onQuestionClicked = onQuestionClicked;
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
            onQuestionClicked.onIndividualQuestionClicked(getAdapterPosition());
        }
    }

    public interface OnQuestionClicked
    {
        void onIndividualQuestionClicked(int position);
    }
}
