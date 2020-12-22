package com.maingame.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Manages which state is shown to the user and transition between states using a stack.
 */
public class GameStateManager {
    private final Deque<State> states;

    public GameStateManager() {
        states = new ArrayDeque<>();
    }

    /**
     * Puts the input state at the top of the stack.
     * @param state a class of all the game logic and assets at a given position in the game.
     * @see State
     */
    public void push(State state) {
        states.push(state);
    }

    /**
     * Removes the top state on the stack.
     */
    public void pop() {
        states.pop().dispose();
    }

    /**
     * Replaces the top state on the stack with the input state.
     * @param state a class of all the game logic and assets at a given position in the game.
     * @see State
     */
    public void set(State state) {
        pop();
        states.push(state);
    }

    /**
     * Updates the delta time of the top state on the stack
     * @param dt the time between each start of a render()
     */
    public void update(float dt) {
        if (states.peek() != null) {
            states.peek().update(dt);
        }
    }

    /**
     * Renders the top state on the stack
     * @param sb a batch for drawing objects
     * @see State#update(float)
     */
    public void render(SpriteBatch sb) {
        if (states.peek() != null) {
            states.peek().render(sb);
        }
    }
}
