package com.example.androidfinalgroupproject;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author Nick Hallarn
 * @version 1.3.
 */
public class DictionaryFragment extends Fragment {
    boolean isTablet;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle dataFromActivity = getArguments();
        String word = dataFromActivity.getString("word" );


        // Inflate the layout for this fragment
        View result =  inflater.inflate(R.layout.activity_dictionary_fragment, container, false);

        //show the message
        //TextView message = (TextView)result.findViewById(R.id.messageText);
        //message.setText(dataFromActivity.getString(ChatRoomActivity.ITEM_SELECTED));

        //Dictionary d = new Dictionary();
        //d.search(word);
        //String hw = d.headWord.get(0);
        //String wt = d.wordType.get(0);
        //String pr = d.pronunciation.get(0);
        //String df = d.definition.get(0);

        TextView hW = result.findViewById(R.id.headWordFrag);
        //TextView pR = result.findViewById(R.id.pronunciationFrag);
        //TextView wT = result.findViewById(R.id.wordTypeFrag);
        //TextView dF = result.findViewById(R.id.definitionFrag);

        hW.setText(word);
        //pR.setText(pr);
        //wT.setText(wt);
        //dF.setText(df);


        return result;
    }

    public void setTablet(boolean tf){
        this.isTablet = tf;
    }
}
