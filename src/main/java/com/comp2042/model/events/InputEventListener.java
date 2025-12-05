package com.comp2042.model.events;

import com.comp2042.logic.gravity.DownData;
import com.comp2042.logic.board.ViewData;

public interface InputEventListener {
    DownData onTick();
    DownData onDownEvent(MoveEvent event);
    ViewData onLeftEvent(MoveEvent event);
    ViewData onRightEvent(MoveEvent event);
    ViewData onRotateEvent(MoveEvent event);
    ViewData onHoldBrickEvent(MoveEvent event);
    void createNewGame();
}
