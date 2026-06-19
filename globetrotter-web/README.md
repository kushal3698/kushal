# Globetrotter: The Lost Coordinates

Minimal browser prototype for the clue-driven exploration game. Runs as a static site, so any lightweight HTTP server works.

## Features
- Leaflet-powered world map with pan/zoom and click-to-guess.
- Five handcrafted riddles with success text and 50 km tolerance.
- Fuel counter that drains with each jump.
- Museum list that tracks recovered artifacts.

## Running locally
1. `cd globetrotter-web`
2. Serve the folder with any static server (examples below):
   - Python: `python -m http.server 4173`
   - Bun: `bunx serve .`
   - Node (if installed): `npx serve .`
3. Visit `http://localhost:4173` (or whichever port your server prints).

**Note:** Fetching local files such as the Leaflet tiles requires an HTTP server; opening `index.html` directly via `file://` may block scripts.

## Connecting to the Firebase backend

The browser client now syncs player state (fuel, artifacts, mission progress) and sends telemetry through Firebase Cloud Functions. When running locally:

1. From `globetrotter-backend/`, run `firebase emulators:start --project globetrotter-placeholder` (requires the Firebase CLI).
2. Keep the emulators running; the web client auto-detects `localhost` and connects to Auth (9099), Firestore (8080), and Functions (5001).
3. Serve the web folder (`python -m http.server 4173`) and open `http://localhost:4173`.

All progress will be written to the emulated Firestore (`players/{uid}` documents). When you are ready to hit a real Firebase project, replace the placeholder `firebaseConfig` in `main.js` with your project credentials and remove the emulator connections.
