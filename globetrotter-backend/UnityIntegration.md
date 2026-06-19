doc.Listen(snapshot => {
# Unity Client Integration Cheatsheet

## 1. Import Firebase SDKs
- Download the Firebase Unity SDK (https://firebase.google.com/download/unity).
- Import the following `.unitypackage` files into your Unity project:
  - `FirebaseAuth`
  - `FirebaseFirestore`
  - `FirebaseFunctions`
  - (Optional) `FirebaseAnalytics` if you want Unity-side analytics in addition to telemetry collection.

## 2. Bootstrap Firebase + Anonymous Auth
Create a single GameObject (e.g., `FirebaseBootstrap`) in your first scene:
```csharp
using Firebase;
using Firebase.Auth;
using Firebase.Extensions;
using UnityEngine;

public class FirebaseBootstrap : MonoBehaviour {
    public static FirebaseAuth AuthInstance { get; private set; }

    async void Awake() {
        DontDestroyOnLoad(gameObject);

        var status = await FirebaseApp.CheckAndFixDependenciesAsync();
        if (status != DependencyStatus.Available) {
            Debug.LogError($"Firebase dependencies missing: {status}");
            return;
        }

        AuthInstance = FirebaseAuth.DefaultInstance;
        if (AuthInstance.CurrentUser == null) {
            await AuthInstance.SignInAnonymouslyAsync();
        }
    }
}
```

## 3. Data models shared with backend
```csharp
public enum Era { Treta, Dvapara, Kali }

[Serializable]
public class PlayerStateDto {
    public string currentEra;
    public int fuel;
    public string[] artifacts;
    public string lastMissionId;
    public Position position;
}

[Serializable]
public class Position {
    public double lat;
    public double lon;
}
```

## 4. Callable Cloud Functions
### Sync player state after gameplay events
```csharp
using Firebase.Functions;
using System.Collections.Generic;
using System.Threading.Tasks;

public class PlayerSyncService {
    readonly FirebaseFunctions functions = FirebaseFunctions.DefaultInstance;

    public async Task PushState(PlayerStateDto dto) {
        var data = new Dictionary<string, object> {
            { "currentEra", dto.currentEra },
            { "fuel", dto.fuel },
            { "artifacts", dto.artifacts },
            { "lastMissionId", dto.lastMissionId },
            { "position", new Dictionary<string, object> {
                { "lat", dto.position.lat },
                { "lon", dto.position.lon }
            }}
        };

        await functions.GetHttpsCallable("syncPlayerState").CallAsync(data);
    }
}
```

### Log telemetry for analytics funnels
```csharp
public async Task LogEvent(string eventName, object payload) {
    await FirebaseFunctions.DefaultInstance
        .GetHttpsCallable("logTelemetry")
        .CallAsync(new Dictionary<string, object> {
            { "event", eventName },
            { "payload", payload }
        });
}
```

## 5. Firestore listeners (daily mission + live leaderboard)
```csharp
using Firebase.Firestore;

public class DailyMissionWatcher : MonoBehaviour {
    ListenerRegistration registration;

    void Start() {
        var doc = FirebaseFirestore.DefaultInstance
            .Collection("dailyMission")
            .Document("active");

        registration = doc.Listen(snapshot => {
            if (!snapshot.Exists) return;
            var mission = snapshot.ConvertTo<DailyMission>();
            UpdateHUD(mission);
        });
    }

    void OnDestroy() => registration?.Stop();
}
```

## 6. Local testing against emulators
1. Run `firebase emulators:start` inside `globetrotter-backend`.
2. In Unity, before calling any Firebase services, set the relevant emulator hosts:
```csharp
FirebaseFirestore.DefaultInstance.Settings = new FirebaseFirestoreSettings {
    Host = "localhost:8080",
    PersistenceEnabled = false,
    SslEnabled = false
};

FirebaseFunctions.DefaultInstance.UseFunctionsEmulator("localhost", 5001);
FirebaseAuth.DefaultInstance.UseEmulator("http://localhost", 9099);
```
3. When you build for production, remove the emulator configuration so the client hits the live Firebase endpoints.

## 7. Verification checklist
- Anonymous auth succeeds (check Firebase console → Authentication → Users).
- After completing a mission, confirm `players/{uid}` is updated with fuel/artifact arrays.
- Telemetry events appear in the `telemetry` collection.
- `dailyMission/active` updates propagate to the HUD in real time.
- Submit a fake session document to `/players/{uid}/sessions` and ensure the `leaderboards/season-1/entries/{uid}` document is created by the trigger.
