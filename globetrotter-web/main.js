import { initializeApp } from 'https://www.gstatic.com/firebasejs/11.0.1/firebase-app.js';
import {
  getAuth,
  signInAnonymously,
  connectAuthEmulator
} from 'https://www.gstatic.com/firebasejs/11.0.1/firebase-auth.js';
import {
  getFirestore,
  doc,
  getDoc,
  collection,
  getDocs,
  connectFirestoreEmulator
} from 'https://www.gstatic.com/firebasejs/11.0.1/firebase-firestore.js';
import {
  getFunctions,
  httpsCallable,
  connectFunctionsEmulator
} from 'https://www.gstatic.com/firebasejs/11.0.1/firebase-functions.js';

const firebaseConfig = {
  apiKey: 'demo-key',
  authDomain: 'globetrotter-placeholder.firebaseapp.com',
  projectId: 'globetrotter-placeholder',
  appId: '1:111111111111:web:demo'
};

const USE_EMULATORS = window.location.hostname === 'localhost';

const DEFAULT_MISSIONS = [
  {
    id: 'sun-crown',
    name: 'Istanbul, Turkey',
    era: 'Treta',
    lat: 41.0082,
    lon: 28.9784,
    riddle: 'Find the city where Europa shakes hands with Asia, and minarets guard the Bosphorus.',
    success: 'Recovered the Crown of the Sun from the cistern beneath Hagia Sophia.',
    reward: '+8 fuel, Solar Thread',
    difficulty: 'medium'
  },
  {
    id: 'skyline-quill',
    name: 'New York City, USA',
    era: 'Kali',
    lat: 40.7128,
    lon: -74.006,
    riddle: 'Seek the harbor of copper giants, where Broadway dreams collide with Atlantic winds.',
    success: 'Secured the Skyline Quill hidden inside an old ticker-tape machine on Wall Street.',
    reward: '+6 fuel, Ledger Glyphs',
    difficulty: 'medium'
  },
  {
    id: 'andes-compass',
    name: 'Cusco, Peru',
    era: 'Treta',
    lat: -13.53195,
    lon: -71.967463,
    riddle: 'Climb to the navel of an empire where condors trace the Sacred Valley.',
    success: 'Unearthed the Andes Compass amid sun-drenched stones of Sacsayhuamán.',
    reward: '+10 fuel, Incan Alloy',
    difficulty: 'hard'
  },
  {
    id: 'aurora-lyre',
    name: 'Reykjavík, Iceland',
    era: 'Dvapara',
    lat: 64.1466,
    lon: -21.9426,
    riddle: 'Where basalt halls meet northern lights, a lyre hums beneath geothermal breath.',
    success: 'Tuned the Aurora Lyre using echoes from Hallgrímskirkja.',
    reward: '+7 fuel, Polar Resonance',
    difficulty: 'medium'
  },
  {
    id: 'spice-astrolabe',
    name: 'Zanzibar, Tanzania',
    era: 'Dvapara',
    lat: -6.1659,
    lon: 39.2026,
    riddle: 'Chart the island perfumed with clove and story, gateway between dhows and empires.',
    success: 'Restored the Spice Astrolabe inside the Old Fort.',
    reward: '+5 fuel, Monsoon Charts',
    difficulty: 'easy'
  },
  {
    id: 'sahara-hourglass',
    name: 'Marrakesh, Morocco',
    era: 'Kali',
    lat: 31.6295,
    lon: -7.9811,
    riddle: 'Follow the call of Gnawa strings until dunes trade secrets with spice souks.',
    success: 'Stabilized the Sahara Hourglass beneath the Koutoubia minaret.',
    reward: '+6 fuel, Rift Sand',
    difficulty: 'medium'
  },
  {
    id: 'jade-drum',
    name: "Xi'an, China",
    era: 'Dvapara',
    lat: 34.3416,
    lon: 108.9398,
    riddle: 'Trace the Silk Road to walls that remember terracotta armies at attention.',
    success: 'Echoed the Jade Drum inside the Bell Tower to awaken compass spirits.',
    reward: '+9 fuel, Silk Resonators',
    difficulty: 'hard'
  },
  {
    id: 'delta-orchid',
    name: 'Ho Chi Minh City, Vietnam',
    era: 'Treta',
    lat: 10.8231,
    lon: 106.6297,
    riddle: 'Where dragon boats weave through river palms, seek the bloom that tastes of monsoon.',
    success: "Cultivated the Delta Orchid within Bến Thành Market's hidden greenhouse.",
    reward: '+5 fuel, Mekong Spores',
    difficulty: 'easy'
  },
  {
    id: 'midnight-chronometer',
    name: 'Greenwich, United Kingdom',
    era: 'Kali',
    lat: 51.4826,
    lon: 0.0077,
    riddle: 'Stand upon the prime divide where clocks bow to a brass meridian.',
    success: 'Rewound the Midnight Chronometer inside the Royal Observatory.',
    reward: '+8 fuel, Meridian Pulse',
    difficulty: 'medium'
  },
  {
    id: 'coral-astarium',
    name: 'Great Barrier Reef, Australia',
    era: 'Treta',
    lat: -18.2871,
    lon: 147.6992,
    riddle: "Dive where reef labyrinths glow and parrotfish chisel the continent's edge.",
    success: 'Calibrated the Coral Astarium with bio-luminescent star charts.',
    reward: '+9 fuel, Tide Sapphire',
    difficulty: 'hard'
  },
  {
    id: 'atlas-scroll',
    name: 'Petra, Jordan',
    era: 'Dvapara',
    lat: 30.3285,
    lon: 35.4444,
    riddle: 'Find the rose city carved by desert winds where trade once rode camel shadows.',
    success: "Decoded the Atlas Scroll inside Al-Khazneh's shadowed vault.",
    reward: '+7 fuel, Nabataean Ink',
    difficulty: 'medium'
  },
  {
    id: 'auric-loom',
    name: 'Jaipur, India',
    era: 'Treta',
    lat: 26.9124,
    lon: 75.7873,
    riddle: 'Follow the honeycombed palace where mirrored halls trap monsoon light.',
    success: "Repaired the Auric Loom within the Hawa Mahal's whispering gallery.",
    reward: '+6 fuel, Monsoon Filament',
    difficulty: 'easy'
  }
];

let missions = DEFAULT_MISSIONS.slice();

const MAX_FUEL = 100;
const COST_PER_JUMP = 5;
const SUCCESS_RADIUS_KM = 50;

const riddleText = document.getElementById('riddleText');
const feedbackText = document.getElementById('feedbackText');
const fuelValue = document.getElementById('fuelValue');
const museumList = document.getElementById('museumList');
const travelBtn = document.getElementById('travelBtn');
const resetBtn = document.getElementById('resetBtn');

let map;
let guessMarker;
let currentMissionIndex = 0;
let fuel = MAX_FUEL;
let recoveredArtifacts = new Set();
let lastKnownPosition = { lat: 0, lon: 0 };

let auth;
let db;
let functions;
let playerDocRef;
let syncPlayerStateFn;
let logTelemetryFn;

bootstrap();

async function bootstrap() {
  try {
    await initFirebase();
    await loadMissionsFromCloud();
    await hydratePlayerFromCloud();
    setFeedback('Systems online. Data link secured.');
  } catch (error) {
    console.warn('Firebase bootstrap failed, operating offline.', error);
    setFeedback('Offline mode: progress will not sync.');
  }

  initGame();
}

function initGame() {
  initMap();
  bindUI();
  loadMission(currentMissionIndex);
  updateFuel(0);
  updateMuseum();
}

function initMap() {
  map = L.map('map', {
    worldCopyJump: true,
    zoomControl: false
  }).setView([20, 0], 2);

  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '&copy; OpenStreetMap contributors'
  }).addTo(map);

  L.control.zoom({ position: 'bottomright' }).addTo(map);

  map.on('click', (event) => handleGuess(event.latlng));
}

function bindUI() {
  travelBtn.addEventListener('click', () => {
    if (!missions.length) {
      setFeedback('Mission database unavailable. Try again after sync.');
      return;
    }
    currentMissionIndex = (currentMissionIndex + 1) % missions.length;
    loadMission(currentMissionIndex);
    setFeedback('Mission advanced. New riddle decrypted.');
    void pushState('advance');
  });

  resetBtn.addEventListener('click', () => {
    recoveredArtifacts.clear();
    updateMuseum();
    fuel = MAX_FUEL;
    updateFuel(0);
    loadMission(0);
    setFeedback('Systems rebooted. Fuel tanks topped.');
    void pushState('reset');
  });
}

function loadMission(index) {
  if (!missions.length) {
    currentMissionIndex = 0;
    riddleText.textContent = 'No missions available. Sync to retrieve new objectives.';
    travelBtn.disabled = true;
    return;
  }

  travelBtn.disabled = false;
  const boundedIndex = ((index % missions.length) + missions.length) % missions.length;
  currentMissionIndex = boundedIndex;
  const mission = missions[boundedIndex];
  riddleText.textContent = mission.riddle;
  highlightTargetArea();
}

function highlightTargetArea() {
  // Optional future use: pulse animations or overlays.
}

function handleGuess(latlng) {
  if (fuel <= 0) {
    setFeedback('Travel budget depleted. Reset to continue.');
    return;
  }

  updateFuel(-COST_PER_JUMP);

  const mission = missions[currentMissionIndex];
  if (!mission) {
    setFeedback('Mission data missing. Resync required.');
    return;
  }
  const distance = haversine(latlng.lat, latlng.lng, mission.lat, mission.lon);

  placeGuessMarker(latlng, distance);
  lastKnownPosition = { lat: latlng.lat, lon: latlng.lng };

  if (distance <= SUCCESS_RADIUS_KM) {
    if (!recoveredArtifacts.has(mission.id)) {
      recoveredArtifacts.add(mission.id);
      updateMuseum();
    }
    setFeedback(`${mission.success} (±${distance.toFixed(1)} km)`);
    void sendTelemetry('mission_success', {
      missionId: mission.id,
      distance
    });
  } else {
    const hint = distance <= 150 ? 'Getting warmer.' : 'Too cold.';
    setFeedback(`${hint} You are ${distance.toFixed(1)} km away.`);
    void sendTelemetry('guess_miss', {
      missionId: mission.id,
      distance
    });
  }

  void pushState('guess', lastKnownPosition);
}

function placeGuessMarker(latlng, distance) {
  if (guessMarker) {
    guessMarker.remove();
  }
  guessMarker = L.marker([latlng.lat, latlng.lng]).addTo(map);
  guessMarker.bindPopup(`Guess<br/>Δ ${distance.toFixed(1)} km`).openPopup();
}

function updateFuel(delta) {
  fuel = Math.max(0, Math.min(MAX_FUEL, fuel + delta));
  fuelValue.textContent = fuel;
}

function setFeedback(message) {
  feedbackText.textContent = message;
}

function updateMuseum() {
  museumList.innerHTML = '';
  if (recoveredArtifacts.size === 0) {
    const li = document.createElement('li');
    li.textContent = 'No artifacts recovered yet.';
    li.style.borderLeftColor = '#cbb79d';
    museumList.appendChild(li);
    return;
  }

  recoveredArtifacts.forEach((id) => {
    const mission = findMissionById(id);
    if (!mission) return;
    const li = document.createElement('li');
    li.innerHTML = `<span>${mission.name}</span><span class="badge">Secured</span>`;
    museumList.appendChild(li);
  });
}

function haversine(lat1, lon1, lat2, lon2) {
  const R = 6371; // Earth radius in km
  const dLat = degToRad(lat2 - lat1);
  const dLon = degToRad(lon2 - lon1);
  const a =
    Math.sin(dLat / 2) ** 2 +
    Math.cos(degToRad(lat1)) *
      Math.cos(degToRad(lat2)) *
      Math.sin(dLon / 2) ** 2;
  const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
  return R * c;
}

function degToRad(value) {
  return (value * Math.PI) / 180;
}

function isMissionRecord(record) {
  return (
    typeof record === 'object' &&
    record !== null &&
    typeof record.id === 'string' &&
    typeof record.name === 'string' &&
    typeof record.era === 'string' &&
    typeof record.lat === 'number' &&
    typeof record.lon === 'number' &&
    typeof record.riddle === 'string' &&
    typeof record.success === 'string'
  );
}

function findMissionById(id) {
  return missions.find((mission) => mission.id === id) || DEFAULT_MISSIONS.find((mission) => mission.id === id);
}

async function initFirebase() {
  const app = initializeApp(firebaseConfig);
  auth = getAuth(app);
  db = getFirestore(app);
  functions = getFunctions(app);

  if (USE_EMULATORS) {
    connectAuthEmulator(auth, 'http://127.0.0.1:9099');
    connectFirestoreEmulator(db, '127.0.0.1', 8080);
    connectFunctionsEmulator(functions, '127.0.0.1', 5001);
  }

  const credential = await signInAnonymously(auth);
  playerDocRef = doc(db, 'players', credential.user.uid);
  syncPlayerStateFn = httpsCallable(functions, 'syncPlayerState');
  logTelemetryFn = httpsCallable(functions, 'logTelemetry');
}

async function loadMissionsFromCloud() {
  if (!db) {
    missions = DEFAULT_MISSIONS.slice();
    return;
  }

  try {
    const snapshot = await getDocs(collection(db, 'missions'));
    if (snapshot.empty) {
      missions = DEFAULT_MISSIONS.slice();
      return;
    }

    missions = snapshot.docs
      .map((docRef) => {
        const data = docRef.data();
        return { ...data, id: data.id ?? docRef.id };
      })
      .filter(isMissionRecord)
      .sort((a, b) => a.name.localeCompare(b.name));
  } catch (error) {
    console.warn('Mission fetch failed; fallback list engaged.', error);
    missions = DEFAULT_MISSIONS.slice();
  }
}

async function hydratePlayerFromCloud() {
  if (!playerDocRef) return;
  const snapshot = await getDoc(playerDocRef);
  if (!snapshot.exists()) {
    await pushState('bootstrap');
    return;
  }

  const data = snapshot.data();
  if (typeof data.fuel === 'number') {
    fuel = data.fuel;
  }
  if (Array.isArray(data.artifacts)) {
    recoveredArtifacts = new Set(data.artifacts);
  }
  if (typeof data.lastMissionId === 'string') {
    const savedIndex = missions.findIndex((mission) => mission.id === data.lastMissionId);
    currentMissionIndex = savedIndex >= 0 ? savedIndex : 0;
  }
  if (data.position) {
    lastKnownPosition = data.position;
  }
}

async function pushState(trigger, guessedPosition) {
  if (!syncPlayerStateFn || !playerDocRef) {
    return;
  }

  const mission = missions[currentMissionIndex] || DEFAULT_MISSIONS[0];
  if (!mission) {
    return;
  }
  const safePosition = getSafePosition(guessedPosition ?? lastKnownPosition, mission);
  lastKnownPosition = safePosition;
  const payload = {
    currentEra: mission.era || 'Treta',
    fuel,
    artifacts: Array.from(recoveredArtifacts),
    lastMissionId: mission.id,
    position: safePosition
  };

  try {
    await syncPlayerStateFn(payload);
  } catch (error) {
    console.warn('Failed to sync player state', error);
  }

  void sendTelemetry(trigger, { missionId: mission.id });
}

async function sendTelemetry(eventName, payload = {}) {
  if (!logTelemetryFn) {
    return;
  }
  try {
    await logTelemetryFn({ event: eventName, payload });
  } catch (error) {
    console.warn('Telemetry call failed', error);
  }
}

function getSafePosition(candidate, mission) {
  const isValid = (pos) =>
    pos &&
    typeof pos.lat === 'number' &&
    typeof pos.lon === 'number' &&
    Number.isFinite(pos.lat) &&
    Number.isFinite(pos.lon) &&
    Math.abs(pos.lat) <= 90 &&
    Math.abs(pos.lon) <= 180;

  if (isValid(candidate)) {
    return candidate;
  }
  if (mission && typeof mission.lat === 'number' && typeof mission.lon === 'number') {
    return { lat: mission.lat, lon: mission.lon };
  }
  return { lat: 0, lon: 0 };
}
