package com.example.cardgame.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.cardgame.R;
import com.example.cardgame.Records.Record;

import java.util.ArrayList;

import static android.graphics.Color.BLACK;

public class Fragment_Record_List extends Fragment {

    private CallBack_RecordList callBack_recordList;
    private ArrayList<Record> recordList;

    public void setCallBack_recordList(CallBack_RecordList callBack_recordList){
        this.callBack_recordList = callBack_recordList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_list, container, false);

        if(callBack_recordList != null){
            recordList = callBack_recordList.loadTopTenData().getList();
        }

        displayTopScores(view);

        return view;
    }

    public void displayTopScores(View view){
        if (recordList == null) {
            Toast.makeText(getActivity(), "Sorry, no records to display yet!", Toast.LENGTH_LONG).show();
            return;
        }

        LinearLayout scroll_view_layout = view.findViewById(R.id.scroll_view_layout);

        for(int i=1 ; i<=recordList.size() ; i++){              //create up to 10 text views describing top scores (depends if we have 10 yet or not)
            TextView textView = new TextView(getActivity());
            setTextViewProperties(textView,i);                  //set all needed attributes

            scroll_view_layout.addView(textView);
        }
    }

    public void setTextViewProperties(TextView textView,int place){
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        //set tags as the arraylist spots 0,1...n
        textView.setTag(new Integer(place-1));

        //set margin's
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(8, lp.topMargin, lp.rightMargin, 24);
        textView.setLayoutParams(lp);

        //text styling
        textView.setText("#"+place+" "+ recordList.get(place-1).getName()+"\n     "+recordList.get(place-1).getScore()+" pts");
        textView.setTextSize(36 - 2*place);   //the lower the rank,the lower the font.
        textView.setTextColor(BLACK);
        textView.setTypeface(null, Typeface.BOLD);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack_recordList.focusRecordOnMap((int)v.getTag());
            }
        });

    }
}
