package com.example.cardgame.Records;

import com.example.cardgame.Records.Record;

import java.util.Comparator;

public class RecordComparatorByScore implements Comparator<Record> {
    @Override
    public int compare(Record o1, Record o2) {
        return o2.getScore() - o1.getScore();
    }
}
