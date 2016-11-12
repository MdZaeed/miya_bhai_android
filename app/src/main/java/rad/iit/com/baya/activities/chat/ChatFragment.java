package rad.iit.com.baya.activities.chat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import rad.iit.com.baya.R;
import rad.iit.com.baya.activities.HomePageActivity;
import rad.iit.com.baya.adapters.MessageAdapter;
import rad.iit.com.baya.data.constants.ApplicationConstants;
import rad.iit.com.baya.datamodels.Message;
import rad.iit.com.baya.datamodels.User;
import rad.iit.com.baya.utils.CustomToast;


/**
 * A chat fragment containing messages view and input form.
 */
public class ChatFragment extends Fragment {

    {
        try {
            mSocket = IO.socket(ApplicationConstants.CHAT_SERVER_URL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return mSocket;
    }

    private static final int REQUEST_LOGIN = 0;

    private static final int TYPING_TIMER_LENGTH = 600;

    private RecyclerView mMessagesView;
    private EditText mInputMessageView;
    private List<Message> mMessages = new ArrayList<Message>();
    private RecyclerView.Adapter mAdapter;
    private String sender =ApplicationConstants.user;
    private String receiver ="mamu";
    private Socket mSocket;

    private Boolean isConnected = true;

    public ChatFragment() {
        super();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mAdapter = new MessageAdapter(activity, mMessages);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mSocket = getSocket();
        mSocket.on(Socket.EVENT_CONNECT,onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT,onDisconnect);
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.on("chat", onNewMessage);
//        mSocket.on("init", onUserJoined);
//        mSocket.on("user_left", onUserLeft);
        mSocket.connect();
        sender = ApplicationConstants.user;
        attemptLogin();
        readPreviousMessages();
    }

    private void readPreviousMessages(){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();
        final CustomToast customToast = new CustomToast(getActivity());
        JSONObject userJsonObject=null;
        mMessages = new ArrayList<Message>();
        try {
            userJsonObject = new JSONObject("{\"user\":=\""+sender+"\"}");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest chatRequest = new JsonObjectRequest(Request.Method.GET, ApplicationConstants.CHAT_URL+sender,userJsonObject ,new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Log.d("Res", jsonObject.toString());
                    try {
                        JSONArray chatArray = jsonObject.getJSONArray("chat");
                        for (int i = 0; i < chatArray.length(); i++) {
                            JSONObject chatJSObject = chatArray.getJSONObject(i);
                            String sender = chatJSObject.getString("sender");
                            String receiver = chatJSObject.getString("receiver");
                            String message = chatJSObject.getString("message");

                            mMessages.add(new Message.Builder()
                                    .username(sender).message(message).receiver(receiver).build());

                            Log.d("MSG",sender+","+receiver+","+message);
                        }
                        mAdapter = new MessageAdapter(getActivity(),mMessages);
                        mMessagesView.setAdapter(mAdapter);
                        scrollToBottom();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            },new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Log.d("Err", volleyError.toString());
                customToast.showLongToast("Error! Check your internet connection.");
                }
            }) ;
             Volley.newRequestQueue(getActivity()).add(chatRequest);
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mSocket.disconnect();

        mSocket.off(Socket.EVENT_CONNECT, onConnect);
        mSocket.off(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.off("chat", onNewMessage);
     //   mSocket.off("init", onUserJoined);
     //   mSocket.off("user_left", onUserLeft);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMessagesView = (RecyclerView) view.findViewById(R.id.messages);
        mMessagesView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMessagesView.setAdapter(mAdapter);

        mInputMessageView = (EditText) view.findViewById(R.id.message_input);
        mInputMessageView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (null == sender) return;
                if (!mSocket.connected()) return;
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        ImageButton sendButton = (ImageButton) view.findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSend();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_leave) {
            leave();
            return true;
        }
        else if(id == R.id.action_refresh){
            readPreviousMessages();
        }

        return super.onOptionsItemSelected(item);
    }

/*
    private void addLog(String message) {
        mMessages.add(new Message.Builder(Message.TYPE_LOG)
                .message(message).build());
        mAdapter.notifyItemInserted(mMessages.size() - 1);
        scrollToBottom();
    }
*/

  /*  private void addParticipantsLog(int numUsers) {
        addLog(getResources().getQuantityString(R.plurals.message_participants, numUsers, numUsers));
    }
*/
    private void addMessage(String username, String message, String receiver) {
        mMessages.add(new Message.Builder()
                .username(username).message(message).receiver(receiver).build());
        mAdapter.notifyItemInserted(mMessages.size() - 1);
        scrollToBottom();
    }

    private void attemptSend() {
        if (null == sender) return;
        if (!mSocket.connected()) {
            Toast.makeText(getActivity(),"Check your internet connection",Toast.LENGTH_LONG).show();
            return;
        }
        String message = mInputMessageView.getText().toString().trim();
        String message_json ="{\"sender\":\""+ sender +"\",\"receiver\":\""+receiver+"\",\"message\":\""+message+"\"}";
        if (TextUtils.isEmpty(message)) {
            mInputMessageView.requestFocus();
            return;
        }
        Log.d("message",message);
        mInputMessageView.setText("");
        addMessage(sender, message,receiver);

        // perform the sending message attempt.
//        mSocket.emit("online",sender);
        mSocket.emit("chat", message_json);
    }

    /**
     * Attempts to sign in the account specified by the login form.
     * If there are form errors (invalid username, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // perform the user login attempt.
        if (mSocket == null) return;
        mSocket.emit("online", sender);
    }
    private void leave() {
        sender = null;
        mSocket.disconnect();
/*
        mSocket.connect();
*/
        Intent intent = new Intent(getContext(),HomePageActivity.class);
        startActivity(intent);
    }

    private void scrollToBottom() {
        mMessagesView.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(!isConnected) {
                        if(null!= sender)
                            mSocket.emit("init", sender);
                        Toast.makeText(getActivity().getApplicationContext(),
                                R.string.connect, Toast.LENGTH_LONG).show();
                        isConnected = true;
                    }
                }
            });
        }
    };

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    isConnected = false;
         /*           Toast.makeText(getActivity().getApplicationContext(),
                            R.string.disconnect, Toast.LENGTH_LONG).show();
         */       }
            });
        }
    };

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity().getApplicationContext(),
                            R.string.error_connect, Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String sender ="s";
                    String receiver="r";
                    String message="m";
                    try {
                        sender = data.getString("sender");
                        receiver = data.getString("receiver");
                        message = data.getString("message");
                    } catch (JSONException e) {
                        return;
                    }
                    addMessage(sender, message, receiver);
                }
            });
        }
    };

/*
    private Emitter.Listener onUserJoined = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    int numUsers;
                    try {
                        username = data.getString("username");
                        numUsers = data.getInt("numUsers");
                    } catch (JSONException e) {
                        return;
                    }

                    addLog(getResources().getString(R.string.message_user_joined, username));
                    addParticipantsLog(numUsers);
                }
            });
        }
    };
*/

/*
    private Emitter.Listener onUserLeft = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    int numUsers;
                    try {
                        username = data.getString("username");
                        numUsers = data.getInt("numUsers");
                    } catch (JSONException e) {
                        return;
                    }

                    addLog(getResources().getString(R.string.message_user_left, username));
                    addParticipantsLog(numUsers);
                }
            });
        }
    };
*/
}

