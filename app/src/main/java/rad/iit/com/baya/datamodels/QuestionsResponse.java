
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

    public static QuestionsResponse faqData()
    {
        QuestionsResponse questionsResponse=new QuestionsResponse();

        List<Challenge> challenges=new ArrayList<>();
/*        for(int i=0;i<5;i++)
        {
            challenges.add(Challenge.getMock(true));
        }*/
        Challenge challenge=Challenge.getMock(true);
        challenge.setQuestion("চ্যালেঞ্জার মামু এপটি কারা পরিচালনা করে?");
        challenge.setAnswer("দ্যা ক্যাম্পাস হিরো ক্যাফে, যা ব্রেভ ম্যান ক্যাম্পেইন এর একটি সংযোজন, রয়েছে চ্যালেঞ্জার মামু এপ এর মূলে। বাংলাদেশে ক্যাফেটি মূলত পরিচালনা করে সেন্টার ফর ম্যান এন্ড ম্যাস্কুলিনিটিস স্টাডিস (সিএমএমএস), সহযোগিতায় আছে, প্রমুন্ডো ইউ এস, ঢাকা বিশ্ববিদ্যালয় ও ইউনিস্যাব। ");
        challenges.add(challenge);

        challenge=Challenge.getMock(true);
        challenge.setQuestion("মামু কে?");
        challenge.setAnswer("মামু একটি ভার্চুয়াল চরিত্র যে বয়ঃসন্ধি ছেলেদের কাছে খুবই জনপ্রিয়। বয়ঃসন্ধিকালে শারীরিক ও মানসিক নানা ধরনের পরিবর্তনের মধ্য দিয়ে যায় কিশোর ছেলেরা। যৌনতা ও প্রজনন স্বাস্থ্য নিয়ে সে সময় তাদের মনে ঘুরপাক খেতে থাকে নানা ধরনের প্রশ্ন। যার বেশীরভাগই কারো সাথে বলা যায় না। যে কথা কারো সাথে বলা যায় না, তা বলা যায় মামুর সাথে। মামু সবসময়ই প্রস্তুত তার ভাইগ্না দের প্রশ্নের উত্তর দেয়ার জন্য। ");
        challenges.add(challenge);

        challenge=Challenge.getMock(true);
        challenge.setQuestion("মামুর সাথে দেখা করার উপায় কি?");
        challenge.setAnswer("মামুর সাথে শুধু কথা বলা যায়, দেখা করা যায় না। চ্যাটের পাশাপাশি জিজ্ঞাসার পরিধি বিবেচনায় রেখে হট লাইন নাম্বারে ফোন করে কথা বলে নিতে পারো মামুর সাথে। তবে তোমার বয়স যদি ১৪-২১ বছরের মধ্যে হয়ে থাকে তবে যোগদান করতে পারো ক্যাম্পাস হিরো ক্যাফেতে। যেখানে তুমি আলোচনায় অংশগ্রহণ করে পেয়ে যেতে পারো তোমার কাঙ্ক্ষিত উত্তর।  ");
        challenges.add(challenge);

        challenge=Challenge.getMock(true);
        challenge.setQuestion("এপ কি প্রতিদিন ২৪ ঘন্টাই চালু থাকে?");
        challenge.setAnswer("যে কোন দিন যে কোন সময় এপ এর চ্যাট অপশনের মাধ্যমে প্রশ্ন করা যাবে। যার উত্তর মামু প্রদান করবে। তবে হটলাইন নাম্বারে ফোন করার সময় প্রতি রবি থেকে বৃহস্পতি সকাল ৯ টা থেকে বিকাল ৫ টা পর্যন্ত। ");
        challenges.add(challenge);

        challenge=Challenge.getMock(true);
        challenge.setQuestion("সমস্যা অনেক জটিল হলে মামু তার সমাধান কিভাবে দিবে?");
        challenge.setAnswer("জটিল সমস্যার সমাধানের জন্য আছে আমাদের এক্সপার্ট মামু। এক্সপার্ট মামু কখনো চিকিৎসক, কখনো মনোরোগ বিশেষজ্ঞ। কারো বিশেষ কোন জিজ্ঞাসা বা সমস্যা থাকলে তার উত্তর প্রদান করবেন আমাদের এক্সপার্ট মামু!  ");
        challenges.add(challenge);

        questionsResponse.setChallenges(challenges);
        return questionsResponse;
    }

}
