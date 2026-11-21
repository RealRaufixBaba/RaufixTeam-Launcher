package git.raufixteam.launcher.input.gamepad;


import git.raufixteam.launcher.game.platform.input.PlatformGrabListener;

public interface GamepadDataProvider {
    GamepadMap getMenuMap();
    GamepadMap getGameMap();
    boolean isGrabbing();
    void attachGrabListener(PlatformGrabListener grabListener);
}
