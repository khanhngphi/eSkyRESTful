package com.lyonguyen.esky.ws.models.request;

import java.util.List;

public class LessonProgress {

    private int progress;

    private List<String> remains;

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public List<String> getRemains() {
        return remains;
    }

    public void setRemains(List<String> remains) {
        this.remains = remains;
    }
}
