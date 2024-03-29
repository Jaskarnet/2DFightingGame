package com.mygdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.FightingGame;
import com.mygdx.screens.HostOnlineGameScreen;
import com.mygdx.screens.OfflineGameScreen;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.mygdx.utils.GameAssetManager;

public class MainMenuScreen implements Screen {

    private final FightingGame game;
    private final Stage stage;
    private final Skin skin;
    private Texture backgroundTexture;
    private TextButton playOfflineButton;
    private TextButton hostOnlineGameButton;
    private TextButton joinOnlineGameButton;
    private TextButton exitButton;
    BitmapFont font;
    private Music gameMusic;
    private Sound hoverSound;

    public MainMenuScreen(FightingGame game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());

        // Pobieranie zasobów z GameAssetManager
        GameAssetManager assetManager = game.assetManager;
        backgroundTexture = assetManager.manager.get(GameAssetManager.backgroundImagePath, Texture.class);
        gameMusic = assetManager.manager.get(GameAssetManager.mainMenuMusicPath, Music.class);
        hoverSound = assetManager.manager.get(GameAssetManager.hoverSoundPath, Sound.class);
        skin = assetManager.manager.get(GameAssetManager.skinPath, Skin.class);
        font = assetManager.manager.get(GameAssetManager.fontPath, BitmapFont.class);

        createUI(assetManager);
    }

    private void createUI(GameAssetManager assetManager) {

        TextButtonStyle playOfflineStyle = new TextButtonStyle();
        playOfflineStyle.font = font;
        playOfflineStyle.up = new TextureRegionDrawable(new TextureRegion(assetManager.manager.get(GameAssetManager.playOfflineButtonUpPath, Texture.class)));
        playOfflineStyle.down = new TextureRegionDrawable(new TextureRegion(assetManager.manager.get(GameAssetManager.playOfflineButtonDownPath, Texture.class)));
        playOfflineStyle.over = new TextureRegionDrawable(new TextureRegion(assetManager.manager.get(GameAssetManager.playOfflineButtonOverPath, Texture.class)));

        TextButtonStyle hostOnlineStyle = new TextButtonStyle();
        hostOnlineStyle.font = font;
        hostOnlineStyle.up = new TextureRegionDrawable(new TextureRegion(assetManager.manager.get(GameAssetManager.hostOnlineButtonUpPath, Texture.class)));
        hostOnlineStyle.down = new TextureRegionDrawable(new TextureRegion(assetManager.manager.get(GameAssetManager.hostOnlineButtonDownPath, Texture.class)));
        hostOnlineStyle.over = new TextureRegionDrawable(new TextureRegion(assetManager.manager.get(GameAssetManager.hostOnlineButtonOverPath, Texture.class)));

        TextButtonStyle joinOnlineStyle = new TextButtonStyle();
        joinOnlineStyle.font = font;
        joinOnlineStyle.up = new TextureRegionDrawable(new TextureRegion(assetManager.manager.get(GameAssetManager.joinOnlineButtonUpPath, Texture.class)));
        joinOnlineStyle.down = new TextureRegionDrawable(new TextureRegion(assetManager.manager.get(GameAssetManager.joinOnlineButtonDownPath, Texture.class)));
        joinOnlineStyle.over = new TextureRegionDrawable(new TextureRegion(assetManager.manager.get(GameAssetManager.joinOnlineButtonOverPath, Texture.class)));

        TextButtonStyle exitStyle = new TextButtonStyle();
        exitStyle.font = font;
        exitStyle.up = new TextureRegionDrawable(new TextureRegion(assetManager.manager.get(GameAssetManager.exitButtonUpPath, Texture.class)));
        exitStyle.down = new TextureRegionDrawable(new TextureRegion(assetManager.manager.get(GameAssetManager.exitButtonDownPath, Texture.class)));
        exitStyle.over = new TextureRegionDrawable(new TextureRegion(assetManager.manager.get(GameAssetManager.exitButtonOverPath, Texture.class)));

        playOfflineButton = new TextButton("", playOfflineStyle);
        hostOnlineGameButton = new TextButton("", hostOnlineStyle);
        joinOnlineGameButton = new TextButton("", joinOnlineStyle);
        exitButton = new TextButton("", exitStyle);

        float buttonWidth = 400f;
        float buttonHeight = 80f;

        playOfflineButton.setSize(buttonWidth, buttonHeight);
        hostOnlineGameButton.setSize(buttonWidth, buttonHeight);
        joinOnlineGameButton.setSize(buttonWidth, buttonHeight);
        exitButton.setSize(buttonWidth, buttonHeight);

        // Set button listeners
        playOfflineButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Play Offline button clicked!");
                dispose();
                game.setScreen(new OfflineGameScreen(game));
            }
        });

        hostOnlineGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Host Online Game button clicked!");
                dispose();
                game.setScreen(new HostOnlineGameScreen(game));
            }
        });

        joinOnlineGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Join Online Game button clicked!");
                dispose();
                game.setScreen(new JoinOnlineGameScreen(game));
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Exit button clicked!");
                Gdx.app.exit(); // Close the application
            }
        });

        ClickListener hoverSoundListener = new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (pointer == -1) { // -1 pointer means the mouse entered without a touch down
                    hoverSound.play(0.3f); // Set the volume as needed
                }
            }
        };
        playOfflineButton.addListener(hoverSoundListener);
        hostOnlineGameButton.addListener(hoverSoundListener);
        joinOnlineGameButton.addListener(hoverSoundListener);
        exitButton.addListener(hoverSoundListener);
        // Add buttons to the stage
        Table table = new Table();
        table.right();
        table.setFillParent(true);
        table.defaults().pad(10);
        table.add(playOfflineButton).size(buttonWidth, buttonHeight).padBottom(10).row();
        table.add(hostOnlineGameButton).size(buttonWidth, buttonHeight).padBottom(10).row();
        table.add(joinOnlineGameButton).size(buttonWidth, buttonHeight).padBottom(10).row();
        table.add(exitButton).size(buttonWidth, buttonHeight).row();
        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        gameMusic.setVolume(0.2f);
        gameMusic.play();

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, (float) 0.54, 0, 1);
        game.batch.begin();
        game.batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        // Implement if needed
    }

    @Override
    public void resume() {
        // Implement if needed
    }

    @Override
    public void hide() {
        if (Gdx.input.getInputProcessor() == stage) {
            Gdx.input.setInputProcessor(null);
        }
    }

    @Override
    public void dispose() {
        System.out.println("~dispose(MainMenuGameScreen)");
        gameMusic.stop();
        stage.dispose();
    }
}
