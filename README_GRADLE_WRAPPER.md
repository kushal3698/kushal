.\gradlew.bat buildGradle wrapper scaffold

What I added
- `gradlew` (shell) and `gradlew.bat` (Windows) scripts — lightweight scaffolds that will execute the wrapper jar if it exists.
- `gradle/wrapper/gradle-wrapper.properties` — configured to use Gradle 9.2.

Important: the actual `gradle-wrapper.jar` (binary) is not committed; it must be generated or supplied by your environment. Running `./gradlew` (macOS/Linux) or `./gradlew.bat` (Windows) will now attempt to download the jar automatically using curl/wget or PowerShell before giving up.

How to complete the wrapper (two options)

1) Preferred — generate wrapper locally (recommended):

   If you have a local Gradle CLI installed, run from the repo root in PowerShell:

```powershell
gradle wrapper --gradle-version 9.2
```

   This will produce `gradlew`, `gradlew.bat`, `gradle/wrapper/gradle-wrapper.jar`, and update properties. After that you can run:

```powershell
.\gradlew.bat run
```

2) If you don't have Gradle installed:

   - Option A — use the IDE's Gradle integration: run the `wrapper` task from the Gradle view (VS Code / IntelliJ). The IDE will generate the wrapper JAR and scripts for you.

   - Option B — use the helper script included in this repo to fetch a prebuilt wrapper jar from Maven Central (convenient when you don't want to install Gradle):

```powershell
.\scripts\fetch-wrapper.ps1
# then
.\gradlew.bat build
.\gradlew.bat run
```

   - Option C — install Gradle via Chocolatey and generate the wrapper yourself:

```powershell
choco install gradle --version=9.2 -y
gradle wrapper --gradle-version 9.2
.\gradlew.bat run
```

Notes
- I added `build.gradle` dependencies for LibGDX earlier; keep `gdxVersion` in `build.gradle` if you want to change versions.
- If you want, I can attempt to add a pre-built `gradle-wrapper.jar` here, but generating it locally with your environment ensures compatibility to your setup.
- You can override the auto-download version by setting the `GRADLE_WRAPPER_VERSION` environment variable before running the wrapper scripts.
