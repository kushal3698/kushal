#!/usr/bin/env node
/**
 * Seed the Firestore `missions` collection with flavorful globetrotting objectives.
 * Works against both the local emulator (export FIRESTORE_EMULATOR_HOST=127.0.0.1:8080)
 * and a real Firebase project (requires application default credentials or service account).
 */
const admin = require('firebase-admin');

const PROJECT_ID = process.env.FIREBASE_PROJECT_ID || 'globetrotter-placeholder';

const missions = [
  {
    id: 'sun-crown',
    name: 'Istanbul, Turkey',
    lat: 41.0082,
    lon: 28.9784,
    era: 'Treta',
    riddle: 'Find the city where Europa shakes hands with Asia, and minarets guard the Bosphorus.',
    success: 'Recovered the Crown of the Sun from the cistern beneath Hagia Sophia.',
    reward: '+8 fuel, Solar Thread',
    difficulty: 'medium'
  },
  {
    id: 'skyline-quill',
    name: 'New York City, USA',
    lat: 40.7128,
    lon: -74.006,
    era: 'Kali',
    riddle: 'Seek the harbor of copper giants, where Broadway dreams collide with Atlantic winds.',
    success: 'Secured the Skyline Quill hidden inside an old ticker-tape machine on Wall Street.',
    reward: '+6 fuel, Ledger Glyphs',
    difficulty: 'medium'
  },
  {
    id: 'andes-compass',
    name: 'Cusco, Peru',
    lat: -13.53195,
    lon: -71.967463,
    era: 'Treta',
    riddle: 'Climb to the navel of an empire where condors trace the Sacred Valley.',
    success: 'Unearthed the Andes Compass amid sun-drenched stones of Sacsayhuamán.',
    reward: '+10 fuel, Incan Alloy',
    difficulty: 'hard'
  },
  {
    id: 'aurora-lyre',
    name: 'Reykjavík, Iceland',
    lat: 64.1466,
    lon: -21.9426,
    era: 'Dvapara',
    riddle: 'Where basalt halls meet northern lights, a lyre hums beneath geothermal breath.',
    success: 'Tuned the Aurora Lyre using echoes from Hallgrímskirkja.',
    reward: '+7 fuel, Polar Resonance',
    difficulty: 'medium'
  },
  {
    id: 'spice-astrolabe',
    name: 'Zanzibar, Tanzania',
    lat: -6.1659,
    lon: 39.2026,
    era: 'Dvapara',
    riddle: 'Chart the island perfumed with clove and story, gateway between dhows and empires.',
    success: 'Restored the Spice Astrolabe inside the Old Fort.',
    reward: '+5 fuel, Monsoon Charts',
    difficulty: 'easy'
  },
  {
    id: 'sahara-hourglass',
    name: 'Marrakesh, Morocco',
    lat: 31.6295,
    lon: -7.9811,
    era: 'Kali',
    riddle: 'Follow the call of Gnawa strings until dunes trade secrets with spice souks.',
    success: 'Stabilized the Sahara Hourglass beneath the Koutoubia minaret.',
    reward: '+6 fuel, Rift Sand',
    difficulty: 'medium'
  },
  {
    id: 'jade-drum',
    name: "Xi'an, China",
    lat: 34.3416,
    lon: 108.9398,
    era: 'Dvapara',
    riddle: 'Trace the Silk Road to walls that remember terracotta armies at attention.',
    success: 'Echoed the Jade Drum inside the Bell Tower to awaken compass spirits.',
    reward: '+9 fuel, Silk Resonators',
    difficulty: 'hard'
  },
  {
    id: 'delta-orchid',
    name: 'Ho Chi Minh City, Vietnam',
    lat: 10.8231,
    lon: 106.6297,
    era: 'Treta',
    riddle: 'Where dragon boats weave through river palms, seek the bloom that tastes of monsoon.',
    success: "Cultivated the Delta Orchid within Bến Thành Market's hidden greenhouse.",
    reward: '+5 fuel, Mekong Spores',
    difficulty: 'easy'
  },
  {
    id: 'midnight-chronometer',
    name: 'Greenwich, United Kingdom',
    lat: 51.4826,
    lon: 0.0077,
    era: 'Kali',
    riddle: 'Stand upon the prime divide where clocks bow to a brass meridian.',
    success: 'Rewound the Midnight Chronometer inside the Royal Observatory.',
    reward: '+8 fuel, Meridian Pulse',
    difficulty: 'medium'
  },
  {
    id: 'coral-astarium',
    name: 'Great Barrier Reef, Australia',
    lat: -18.2871,
    lon: 147.6992,
    era: 'Treta',
    riddle: "Dive where reef labyrinths glow and parrotfish chisel the continent's edge.",
    success: 'Calibrated the Coral Astarium with bio-luminescent star charts.',
    reward: '+9 fuel, Tide Sapphire',
    difficulty: 'hard'
  },
  {
    id: 'atlas-scroll',
    name: 'Petra, Jordan',
    lat: 30.3285,
    lon: 35.4444,
    era: 'Dvapara',
    riddle: 'Find the rose city carved by desert winds where trade once rode camel shadows.',
    success: "Decoded the Atlas Scroll inside Al-Khazneh's shadowed vault.",
    reward: '+7 fuel, Nabataean Ink',
    difficulty: 'medium'
  },
  {
    id: 'auric-loom',
    name: 'Jaipur, India',
    lat: 26.9124,
    lon: 75.7873,
    era: 'Treta',
    riddle: 'Follow the honeycombed palace where mirrored halls trap monsoon light.',
    success: "Repaired the Auric Loom within the Hawa Mahal's whispering gallery.",
    reward: '+6 fuel, Monsoon Filament',
    difficulty: 'easy'
  }
];

async function main() {
  if (admin.apps.length === 0) {
    admin.initializeApp({ projectId: PROJECT_ID });
  }

  const db = admin.firestore();
  const batch = db.batch();

  missions.forEach((mission) => {
    const ref = db.collection('missions').doc(mission.id);
    batch.set(ref, mission, { merge: true });
  });

  await batch.commit();
  console.log(`Seeded ${missions.length} missions into project ${PROJECT_ID}`);
  process.exit(0);
}

main().catch((error) => {
  console.error('Mission seeding failed:', error);
  process.exit(1);
});
