CRITICAL ERROR: JAVA NOT FOUND

The game cannot run because the "Java Development Kit" (JDK) is missing from your computer.
VS Code needs this to build the game.

=== HOW TO FIX ===

1.  DOWNLOAD JAVA
    Go to this link and download the installer:
    https://adoptium.net/temurin/releases/?version=17
    (Choose the .msi installer for Windows)

2.  INSTALL IT
    Run the installer.
    IMPORTANT: During installation, make sure to select "Set JAVA_HOME variable" and "Add to PATH".

3.  RESTART
    After installing, you MUST restart your computer (or at least restart VS Code completely).

4.  VERIFY
    Open a terminal and type: java -version
    If it shows a version number, you are ready!

5.  RUN THE GAME
    Open this folder in VS Code and run DesktopLauncher.java again.
