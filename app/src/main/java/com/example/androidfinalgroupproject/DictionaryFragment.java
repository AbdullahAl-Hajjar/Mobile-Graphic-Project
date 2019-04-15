package com.example.androidfinalgroupproject;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author Nick Hallarn
 * @version 1.3.
 */
public class DictionaryFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle dataFromActivity = getArguments();
        String word;
        String pron;
        String type;
        String def;

        if (dataFromActivity != null) {
            word = dataFromActivity.getString("word");
            pron = dataFromActivity.getString("pron");
            type = dataFromActivity.getString("type");
            def = dataFromActivity.getString("def");
        }else{
            word="Error...";
            pron="";
            type="";
            def = "";
        }

        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.activity_dictionary_fragment, container, false);
        TextView wd = result.findViewById(R.id.headWordFrag);
        TextView pr = result.findViewById(R.id.pronunciationFrag);
        TextView wt = result.findViewById(R.id.wordTypeFrag);
        TextView df = result.findViewById(R.id.definitionFrag);

        wd.setText(word);
        pr.setText(pron);
        wt.setText(type);
        df.setText(def);


        return result;
    }
}
