package rad.iit.com.baya.datamodels;

import rad.iit.com.baya.data.constants.ApplicationConstants;

public class Message {

    public static final int TYPE_MESSAGE_USER = 0;
    public static final int TYPE_MESSAGE_ADMIN = 1;

    private int mType;
    private String mMessage;
    private String mSender;
    private String mReceiver;
    private Message() {}

    public int getType() {
        if (ApplicationConstants.user.equals(mSender) ){
            mType = TYPE_MESSAGE_USER;
        }
        else {
            mType = TYPE_MESSAGE_ADMIN;
        }
        return mType;
    }

    public String getMessage() {
        return mMessage;
    };

    public String getSender() {
        return mSender;
    };

    public String getReceiver() {
        return mReceiver;
    };


    public static class Builder {
        private String sender;
        private String receiver;
        private String message;


        public Builder username(String sender) {
            this.sender = sender;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }
        public Builder receiver(String receiver) {
            this.receiver = receiver;
            return this;
        }

        public Message build() {
            Message msg = new Message();
            msg.mSender = sender;
            msg.mMessage = message;
            msg.mReceiver = receiver;
            return msg;
        }
    }
}
