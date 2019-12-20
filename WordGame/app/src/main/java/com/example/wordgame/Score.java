package com.example.wordgame;

public class Score {
    private int score = 0;
    private ChangeListener listener;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        if (listener != null) listener.onChange();
    }

    public void increaseScore(int score){
        this.score += score;
        if (listener != null) listener.onChange();
    }

    public ChangeListener getListener() {
        return listener;
    }

    public void setListener(ChangeListener listener) {
        this.listener = listener;
    }

    public interface ChangeListener {
        void onChange();
    }
}