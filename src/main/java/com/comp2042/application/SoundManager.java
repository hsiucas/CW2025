package com.comp2042.application;

import com.comp2042.application.AppNavigator.GameMode;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

public class SoundManager {
    private static SoundManager instance;

    private MediaPlayer backgroundMusic;

    private AudioClip moveSound;
    private AudioClip rotateSound;
    private AudioClip landSound;
    private AudioClip clearLineSound;
    private AudioClip clearTetrisSound;
    private AudioClip gravityFallSound;
    private AudioClip levelUpSound;
    private AudioClip garbageSound;
    private AudioClip gameOverNotifySound;
    private AudioClip selectSound;

    private boolean isMusicMuted = false;

    private String currentSong = "";

    private static final String TITLE_MUSIC         = "sounds/TITLE.mp3";
    private static final String INSTRUCTIONS_MUSIC  = "sounds/INSTRUCTIONS.mp3";
    private static final String CLASSIC_MUSIC       = "sounds/CLASSIC.mp3";
    private static final String ZEN_MUSIC           = "sounds/ZEN.mp3";
    private static final String SURVIVAL_MUSIC      = "sounds/SURVIVAL.mp3";
    private static final String FOUR_WAY_MUSIC      = "sounds/FOUR_WAY.mp3";
    private static final String GAME_OVER_MUSIC     = "sounds/GAME_OVER.mp3";

    private SoundManager() {
        try {
            moveSound           = loadSound("sounds/MOVE.mp3");
            rotateSound         = loadSound("sounds/ROTATE.mp3");
            landSound           = loadSound("sounds/LAND.mp3");
            clearLineSound      = loadSound("sounds/CLEARLINE.mp3");
            clearTetrisSound    = loadSound("sounds/CLEARTETRIS.mp3");
            gravityFallSound    = loadSound("sounds/GRAVITYFALL.mp3");
            levelUpSound        = loadSound("sounds/LEVELUP.mp3");
            garbageSound        = loadSound("sounds/GARBAGE.mp3");
            gameOverNotifySound = loadSound("sounds/GAMEOVERNOTIFY.mp3");
            selectSound         = loadSound("sounds/SELECT.mp3");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SoundManager getInstance() {
        if (instance == null) instance = new SoundManager();
        return instance;
    }

    public void playGameModeMusic(GameMode gameMode) {
        String path = switch (gameMode) {
            case CLASSIC    -> CLASSIC_MUSIC;
            case ZEN        -> ZEN_MUSIC;
            case SURVIVAL   -> SURVIVAL_MUSIC;
            case FOUR_WAY   -> FOUR_WAY_MUSIC;
        };
        playTrack(path, true);
    }

    public void playTitleMusic() {
        playTrack(TITLE_MUSIC, true);
    }

    public void playInstructionsMusic() {
        playTrack(INSTRUCTIONS_MUSIC, true);
    }

    public void playGameOverMusic() {
        playTrack(GAME_OVER_MUSIC, false);
    }

    public void playTrack(String path, boolean loop) {
        if (isMusicMuted) return;
        if (loop && currentSong.equals(path)) return;

        stopBackgroundMusic();

        try {
            URL resource = getClass().getClassLoader().getResource(path);
            if (resource != null) {
                backgroundMusic = new MediaPlayer(new Media(resource.toExternalForm()));

                if (loop) {
                    backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE);
                } else {
                    backgroundMusic.setCycleCount(1);
                }

                backgroundMusic.play();

                if (loop) {
                    currentSong = path;
                } else {
                    currentSong = "";
                }
            } else {
                System.err.println("Couldn't find resource " + path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopBackgroundMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
            backgroundMusic.dispose();
        }
        currentSong = "";
    }

    private AudioClip loadSound(String path) {
        URL url = getClass().getClassLoader().getResource(path);
        return (url != null) ? new AudioClip(url.toExternalForm()) : null;
    }

    private void playSound(AudioClip audioClip) {
        if (!isMusicMuted && audioClip != null) {
            audioClip.play();
        }
    }

    public void playMove() {
        playSound(moveSound);
    }

    public void playRotate() {
        playSound(rotateSound);
    }

    public void playLand() {
        playSound(landSound);
    }

    public void playGravityFall() {
        playSound(gravityFallSound);
    }

    public void playLevelUp() {
        playSound(levelUpSound);
    }

    public void playGarbage() {
        playSound(garbageSound);
    }

    public void playGameOver() {
        playSound(gameOverNotifySound);
    }

    public void playSelectSound() {
        playSound(selectSound);
    }

    public void playClear(int lines) {
        if (lines >= 4) {
            playSound(clearTetrisSound);
        } else if (lines >= 0) {
            playSound(clearLineSound);
        }
    }

    public void pauseBGM() {
        if (backgroundMusic != null && !isMusicMuted) {
            backgroundMusic.pause();
        }
    }

    public void resumeBGM() {
        if (backgroundMusic != null && !isMusicMuted) {
            backgroundMusic.play();
        }
    }
}

