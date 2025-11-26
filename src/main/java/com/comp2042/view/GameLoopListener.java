package com.comp2042.view;

import com.comp2042.logic.gravity.DownData;

public interface GameLoopListener {
    void onTick(DownData downData);
    void onGameOver();
}