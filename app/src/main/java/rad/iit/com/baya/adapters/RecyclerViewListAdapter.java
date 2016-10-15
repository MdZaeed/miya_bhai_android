package rad.iit.com.baya.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import rad.iit.com.baya.R;
import rad.iit.com.baya.datamodels.Challenge;


/**
 * Created by chandradasdipok on 5/17/2016.
 */
public class RecyclerViewListAdapter extends RecyclerView.Adapter<RecyclerViewListAdapter.MyViewHolder>{

    int cardID;
    private ArrayList<Challenge> challenges;
    Context context;
    private OnQuestionClicked onQuestionClicked;
    private String filterId;

    public RecyclerViewListAdapter(Context context, int cardID, ArrayList<Challenge> challenges) {
        this.cardID = cardID;
        this.setChallenges(challenges);
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(cardID,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Challenge challenge= getChallenges().get(position);

        holder.questionTextView.setText(challenge.getQuestion());
        try {
            holder.timeTextView.setText(formatToYesterdayOrToday(challenge.getQuestionDate()));
        } catch (ParseException e) {
            holder.timeTextView.setText(challenge.getQuestionDate());
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return getChallenges().size();
    }

    public OnQuestionClicked getOnQuestionClicked() {
        return onQuestionClicked;
    }

    public void setOnQuestionClicked(OnQuestionClicked onQuestionClicked) {
        this.onQuestionClicked = onQuestionClicked;
    }

    public String getFilterId() {
        return filterId;
    }

    public void setFilterId(String filterId) {
        this.filterId = filterId;
    }

    public ArrayList<Challenge> getChallenges() {
        return challenges;
    }

    public void setChallenges(ArrayList<Challenge> challenges) {
        this.challenges = challenges;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView questionTextView;
        public TextView timeTextView;
        public MyViewHolder(View itemView) {
            super(itemView);

            questionTextView= (TextView) itemView.findViewById(R.id.tv_question);
            timeTextView=(TextView) itemView.findViewById(R.id.tv_question_time);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onQuestionClicked.onIndividualQuestionClicked(getChallenges().get(getAdapterPosition()));
        }
    }

    public interface OnQuestionClicked
    {
        void onIndividualQuestionClicked(Challenge challenge);
    }

    public static String formatToYesterdayOrToday(String date) throws ParseException {
        Date dateTime = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss").parse(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        Calendar today = Calendar.getInstance();
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE, -5);
        DateFormat timeFormatter = new SimpleDateFormat("HH:mm");


        if (calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) && calendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) {
            return "Today " + timeFormatter.format(dateTime);
        } else if (calendar.get(Calendar.YEAR) == yesterday.get(Calendar.YEAR) && calendar.get(Calendar.DAY_OF_YEAR) == yesterday.get(Calendar.DAY_OF_YEAR)) {
            return "Yesterday " + timeFormatter.format(dateTime);
        } else {
            return date;
        }
    }
}
