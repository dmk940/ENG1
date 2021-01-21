package com.maingame;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import org.junit.After;
import org.junit.Before;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.GL20;
import com.maingame.game.MainGame;

import org.mockito.Mockito;

/**
 * This is the base class for all game tests. It takes care of starting
 * the game headlessly and mocking OpenGL. Simply extend it for each class
 * that requires initialisation of a game instance!
 */
public abstract class UnitTestInit {
	
	protected Application app;

	@Before
	public void init() {
		app = new HeadlessApplication(new MainGame());
		Gdx.gl20 = Mockito.mock(GL20.class);
		Gdx.gl = Gdx.gl20;
		// mocks the OpenGL methods since we're running headlessly 
		//(no graphics context)
	}

	@After
	public void exitApp() {
		app.exit();
	}
}
