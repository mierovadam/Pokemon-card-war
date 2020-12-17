package com.example.cardgame.Records;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

public class TopTen {
    private final int LIST_LENGTH = 10;
    private ArrayList<Record> list;


    public TopTen(ArrayList<Record> list) {
        this.list = list;
    }

    public TopTen(){}

    public ArrayList<Record> getList() {
        return list;
    }

    public boolean enterNewScore(Record record){
        if(list.isEmpty()) {  //case list is empty
            list.add(record);
            return true;
        }else if(list.size()<10){   //case list isnt full yet (no 10 records)
            list.add(record);
            Collections.sort(list,new RecordComparatorByScore());
            return true;
        }

        for(int i = 0; i<list.size() ; i++){
            if (record.getScore() > list.get(i).getScore()){
                list.add(i,record);
                list.remove(10);
                return true;
            }
        }
        return false;
    }

}
