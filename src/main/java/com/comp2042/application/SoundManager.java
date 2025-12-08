package com.comp2042.application;

import com.comp2042.application.AppNavigator.GameMode;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

/**
 * Responsible for managing and playing all audio in the game.
 * It handles background music (MediaPlayer) and sound effects (AudioClip).
 */

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

    /**
     * Returns the singleton instance of the SoundManager.
     * @return The SoundManager instance.
     */

    public static SoundManager getInstance() {
        if (instance == null) instance = new SoundManager();
        return instance;
    }

    /**
     * Plays the specific background music for a selected game mode.
     * @param gameMode The game mode to play music for.
     */

    public void playGameModeMusic(GameMode gameMode) {
        String path = switch (gameMode) {
            case CLASSIC    -> CLASSIC_MUSIC;
            case ZEN        -> ZEN_MUSIC;
            case SURVIVAL   -> SURVIVAL_MUSIC;
            case FOUR_WAY   -> FOUR_WAY_MUSIC;
        };
        playTrack(path, true);
    }

    /**
     * Plays the main title screen music.
     */

    public void playTitleMusic() {
        playTrack(TITLE_MUSIC, true);
    }

    /**
     * Plays instructions music.
     */

    public void playInstructionsMusic() {
        playTrack(INSTRUCTIONS_MUSIC, true);
    }

    /**
     * Plays game over jingle.
     */

    public void playGameOverMusic() {
        playTrack(GAME_OVER_MUSIC, false);
    }

    /**
     * Internal method to play a music track.
     * @param path The resource path to the music file.
     * @param loop Whether the music should loop indefinitely.
     */

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

    /**
     * Stops currently playing music.
     */

    public void stopBackgroundMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
            backgroundMusic.dispose();
        }
        currentSong = "";
    }

    /**
     * Loads sound effect.
     */

    private AudioClip loadSound(String path) {
        URL url = getClass().getClassLoader().getResource(path);
        return (url != null) ? new AudioClip(url.toExternalForm()) : null;
    }

    /**
     * Plays sound effect.
     */

    private void playSound(AudioClip audioClip) {
        if (!isMusicMuted && audioClip != null) {
            audioClip.play();
        }
    }

    /**
     * Plays movement SFX.
     */
    public void playMove() {
        playSound(moveSound);
    }

    /**
     * Plays rotating SFX.
     */
    public void playRotate() {
        playSound(rotateSound);
    }

    /**
     * Plays brick landing SFX.
     */
    public void playLand() {
        playSound(landSound);
    }

    /**
     * Plays gravity SFX.
     */
    public void playGravityFall() {
        playSound(gravityFallSound);
    }

    /**
     * Plays level-up SFX.
     */
    public void playLevelUp() {
        playSound(levelUpSound);
    }

    /**
     * Plays garbage row spawn SFX.
     */
    public void playGarbage() {
        playSound(garbageSound);
    }

    /**
     * Plays game over SFX.
     */
    public void playGameOver() {
        playSound(gameOverNotifySound);
    }

    /**
     * Plays selection SFX.
     */
    public void playSelectSound() {
        playSound(selectSound);
    }

    /**
     * Plays a sound effect based on the number of lines cleared.
     * Plays a special sound for a "Tetris" (4 lines).
     * @param lines The number of lines cleared.
     */
    public void playClear(int lines) {
        if (lines >= 4) {
            playSound(clearTetrisSound);
        } else if (lines >= 0) {
            playSound(clearLineSound);
        }
    }

    /**
     * Pauses the currently playing background music.
     * This is typically called when the game is paused or the window loses focus.
     */
    public void pauseBGM() {
        if (backgroundMusic != null && !isMusicMuted) {
            backgroundMusic.pause();
        }
    }

    /**
     * Resumes the background music from where it was paused.
     * Called when the game is unpaused.
     */
    public void resumeBGM() {
        if (backgroundMusic != null && !isMusicMuted) {
            backgroundMusic.play();
        }
    }
}

