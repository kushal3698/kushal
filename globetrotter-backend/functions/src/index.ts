import * as functions from 'firebase-functions';
import * as admin from 'firebase-admin';
import { Timestamp } from 'firebase-admin/firestore';
import { DateTime } from 'luxon';
import { LeaderboardEntry, Mission, PlayerState } from './types';

admin.initializeApp();
const db = admin.firestore();

const MAX_FUEL = 100;
const MAX_ARTIFACTS = 32;
const DAILY_MISSION_DOC = 'active';

export const syncPlayerState = functions.https.onCall(async (data, context) => {
  if (!context.auth) {
    throw new functions.https.HttpsError('unauthenticated', 'Login required');
  }

  const payload = data as Partial<PlayerState>;
  const uid = context.auth.uid;

  if (typeof payload.fuel !== 'number' || payload.fuel < 0 || payload.fuel > MAX_FUEL) {
    throw new functions.https.HttpsError('invalid-argument', 'Fuel out of bounds');
  }

  if (!payload.currentEra) {
    throw new functions.https.HttpsError('invalid-argument', 'Era required');
  }

  if (!payload.position || Math.abs(payload.position.lat) > 90 || Math.abs(payload.position.lon) > 180) {
    throw new functions.https.HttpsError('invalid-argument', 'Invalid coordinates');
  }

  const artifacts = Array.isArray(payload.artifacts) ? payload.artifacts.slice(0, MAX_ARTIFACTS) : [];

  const sanitized: PlayerState = {
    uid,
    currentEra: payload.currentEra,
    fuel: payload.fuel,
    artifacts,
    lastMissionId: payload.lastMissionId ?? 'unknown',
    position: payload.position,
    updatedAt: Timestamp.now()
  };

  await db.collection('players').doc(uid).set(sanitized, { merge: true });
  return { status: 'ok' };
});

export const logTelemetry = functions.https.onCall(async (data, context) => {
  if (!context.auth) {
    throw new functions.https.HttpsError('unauthenticated', 'Login required');
  }

  await db.collection('telemetry').add({
    uid: context.auth.uid,
    event: data.event ?? 'unknown',
    payload: data.payload ?? {},
    createdAt: Timestamp.now()
  });

  return { status: 'logged' };
});

export const rotateDailyMission = functions.pubsub.schedule('every 24 hours').onRun(async () => {
  const missionsSnap = await db.collection('missions').get();
  if (missionsSnap.empty) {
    console.warn('No missions available to rotate');
    return null;
  }

  const list: Mission[] = missionsSnap.docs.map((doc) => doc.data() as Mission);
  const index = Math.floor(Math.random() * list.length);
  const mission = list[index];

  await db.collection('dailyMission').doc(DAILY_MISSION_DOC).set({
    missionId: mission.id,
    name: mission.name ?? mission.id,
    era: mission.era ?? 'Treta',
    lat: mission.lat,
    lon: mission.lon,
    riddle: mission.riddle,
    success: mission.success ?? '',
    reward: mission.reward,
    difficulty: mission.difficulty,
    expiresAt: DateTime.utc().plus({ days: 1 }).toISO()
  });

  return null;
});

export const updateLeaderboard = functions.firestore
  .document('players/{playerId}/sessions/{sessionId}')
  .onCreate(async (snap, context) => {
    const data = snap.data();
    if (!data || typeof data.score !== 'number') {
      return null;
    }

    const entry: LeaderboardEntry = {
      score: data.score,
      durationMs: data.durationMs ?? 0,
      accuracyKm: data.accuracyKm ?? 0,
      timestamp: Timestamp.now()
    };

    await db
      .collection('leaderboards')
      .doc('season-1')
      .collection('entries')
      .doc(context.params.playerId)
      .set(entry);

    return null;
  });
