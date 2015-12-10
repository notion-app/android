package com.tylorgarrett.notion.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.tylorgarrett.notion.MainActivity;
import com.tylorgarrett.notion.R;
import com.tylorgarrett.notion.data.NotionData;
import com.tylorgarrett.notion.models.Note;
import com.tylorgarrett.notion.models.Notebook;
import com.tylorgarrett.notion.models.Topic;
import com.tylorgarrett.notion.models.UpdateNoteBody;
import com.tylorgarrett.notion.services.NotionService;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class NoteEditFragment extends Fragment implements TextWatcher {

    public static String TAG = "NoteEditFragment";

    MainActivity mainActivity;

    NotionData notionData;

    WebSocketClient socket;

    @Bind(R.id.note_edit_edittext)
    EditText noteContent;

    Note note;
    String notebookID;
    String noteID;
    Topic topic;

    public static NoteEditFragment newInstance(String noteID, String notebookID) {
        NoteEditFragment fragment = new NoteEditFragment();
        Bundle args = new Bundle();
        args.putString("noteID", noteID);
        args.putString("notebookID", notebookID);
        fragment.setArguments(args);
        return fragment;
    }

    public NoteEditFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mainActivity = (MainActivity) getActivity();
        notionData = NotionData.getInstance();
        noteID = getArguments().getString("noteID");
        notebookID = NotionData.getInstance().getNotebookByNoteId(noteID).getId();
        note = notionData.getNoteByNoteId(noteID);
        topic = notionData.getTopicByID(note.getTopic_id());
        connectToWebSocket();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_note_edit, container, false);
        ButterKnife.bind(this, v);
        noteContent.setText(note.getContent());
        noteContent.removeTextChangedListener(this);
        noteContent.addTextChangedListener(this);
        return v;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_save:
                InputMethodManager imm = (InputMethodManager)mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mainActivity.getCurrentFocus().getWindowToken(), 0);
                updateNote();
                mainActivity.onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        mainActivity.toolbar.setTitle(note.getTitle());
        inflater.inflate(R.menu.menu_note_edit, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        note.setContent(s.toString());
    }

    public void updateNote(){
        Call<Topic> updateNoteCall = NotionService.getApi().updateNote(mainActivity.getCurrentUser().getFb_auth_token(), notebookID , note.getId(), new UpdateNoteBody(note.getContent()));
        updateNoteCall.enqueue(new Callback<Topic>() {
            @Override
            public void onResponse(Response<Topic> response, Retrofit retrofit) {
                mainActivity.debugToast("Success on updateNote " + response.raw());
            }

            @Override
            public void onFailure(Throwable t) {
                mainActivity.debugToast("Failure on UpdateNote " + t.getMessage());
            }
        });
    }

    public void connectToWebSocket(){
        URI uri;
        try {
            uri = new URI("ws://notion-api-dev.herokuapp.com:80/v1/note/" + noteID + "/ws?token=" + mainActivity.getCurrentUser().getFb_auth_token());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        socket = new WebSocketClient(uri, new Draft_17()) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                mainActivity.debugToast("Web Socket Connected: " + handshakedata.getHttpStatusMessage());
            }

            @Override
            public void onMessage(String message) {
                mainActivity.debugToast("Web Socket Incoming Message: " + message);
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                mainActivity.debugToast("Web Socket Closed: " + reason);
            }

            @Override
            public void onError(Exception ex) {
                mainActivity.debugToast("Web Socket Connected: " + ex.getMessage());
            }
        };
        socket.connect();
        keepAlive(socket);
    }

    public void keepAlive(WebSocketClient socket){

    }

    @Override
    public void onPause() {
        super.onPause();
        if ( socket != null ){
            socket.close();
        }
    }
}
