# Globetrotter Backend (Firebase)

Backend-as-a-Service scaffolding that covers the MVP priorities:

1. **Player persistence** (Auth + Firestore + callable `syncPlayerState`).
2. **Telemetry** via Firestore collection + callable `logTelemetry`.
3. **Daily missions + leaderboard** handled by schedule-driven Cloud Functions.

## Folder layout
- `.firebaserc` / `firebase.json` – core Firebase config.
- `firestore.rules` / `storage.rules` – locked-down security rules.
- `firestore.indexes.json` – leaderboard composite index.
- `functions/` – TypeScript Cloud Functions (`npm install`, `npm run build`).
- `UnityIntegration.md` – snippets for wiring the backend in Unity (same flow works in JS using Firebase Web SDK).

## Quick start
```bash
cd globetrotter-backend/functions
npm install
npm run build
firebase emulators:start
```

Deploy once you set your actual project id in `.firebaserc`:
```bash
firebase deploy --only functions,firestore:indexes,firestore:rules
```

### Seed the missions catalog
Populate Firestore with 12 globe-spanning objectives (works with emulators or a real project).

```cmd
cd globetrotter-backend\functions
set FIRESTORE_EMULATOR_HOST=127.0.0.1:8080 && npm run seed:missions
```

To seed a remote project instead, authenticate via `firebase login`, set `FIREBASE_PROJECT_ID`, and omit the emulator variable.

## Data model
```
players/{uid}
  currentEra: "Treta"
  fuel: 85
  artifacts: ["sun-crown"]
  lastMissionId: "mission-001"
  position: { lat: 41.0, lon: 28.9 }
  updatedAt: timestamp

dailyMission/active
  missionId: "mission-004"
  lat, lon, riddle, reward, difficulty, expiresAt

leaderboards/season-1/entries/{uid}
  score, durationMs, accuracyKm, timestamp

telemetry/{autoId}
  uid, event, payload, createdAt
```

## Next steps
- Feed real mission data into `missions/` collection (seed via Firebase console or script).
- Hook Unity/Web clients to call `syncPlayerState` + `logTelemetry`.
- After validation, enable the scheduled `rotateDailyMission` function and set up Cloud Scheduler in Firebase console.
- Extend `updateLeaderboard` trigger to compute weekly seasons or reward payouts.
