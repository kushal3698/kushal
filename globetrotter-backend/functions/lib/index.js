"use strict";
var __createBinding = (this && this.__createBinding) || (Object.create ? (function(o, m, k, k2) {
    if (k2 === undefined) k2 = k;
    var desc = Object.getOwnPropertyDescriptor(m, k);
    if (!desc || ("get" in desc ? !m.__esModule : desc.writable || desc.configurable)) {
      desc = { enumerable: true, get: function() { return m[k]; } };
    }
    Object.defineProperty(o, k2, desc);
}) : (function(o, m, k, k2) {
    if (k2 === undefined) k2 = k;
    o[k2] = m[k];
}));
var __setModuleDefault = (this && this.__setModuleDefault) || (Object.create ? (function(o, v) {
    Object.defineProperty(o, "default", { enumerable: true, value: v });
}) : function(o, v) {
    o["default"] = v;
});
var __importStar = (this && this.__importStar) || (function () {
    var ownKeys = function(o) {
        ownKeys = Object.getOwnPropertyNames || function (o) {
            var ar = [];
            for (var k in o) if (Object.prototype.hasOwnProperty.call(o, k)) ar[ar.length] = k;
            return ar;
        };
        return ownKeys(o);
    };
    return function (mod) {
        if (mod && mod.__esModule) return mod;
        var result = {};
        if (mod != null) for (var k = ownKeys(mod), i = 0; i < k.length; i++) if (k[i] !== "default") __createBinding(result, mod, k[i]);
        __setModuleDefault(result, mod);
        return result;
    };
})();
Object.defineProperty(exports, "__esModule", { value: true });
exports.updateLeaderboard = exports.rotateDailyMission = exports.logTelemetry = exports.syncPlayerState = void 0;
const functions = __importStar(require("firebase-functions"));
const admin = __importStar(require("firebase-admin"));
const firestore_1 = require("firebase-admin/firestore");
const luxon_1 = require("luxon");
admin.initializeApp();
const db = admin.firestore();
const MAX_FUEL = 100;
const MAX_ARTIFACTS = 32;
const DAILY_MISSION_DOC = 'active';
exports.syncPlayerState = functions.https.onCall(async (data, context) => {
    if (!context.auth) {
        throw new functions.https.HttpsError('unauthenticated', 'Login required');
    }
    const payload = data;
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
    const sanitized = {
        uid,
        currentEra: payload.currentEra,
        fuel: payload.fuel,
        artifacts,
        lastMissionId: payload.lastMissionId ?? 'unknown',
        position: payload.position,
        updatedAt: firestore_1.Timestamp.now()
    };
    await db.collection('players').doc(uid).set(sanitized, { merge: true });
    return { status: 'ok' };
});
exports.logTelemetry = functions.https.onCall(async (data, context) => {
    if (!context.auth) {
        throw new functions.https.HttpsError('unauthenticated', 'Login required');
    }
    await db.collection('telemetry').add({
        uid: context.auth.uid,
        event: data.event ?? 'unknown',
        payload: data.payload ?? {},
        createdAt: firestore_1.Timestamp.now()
    });
    return { status: 'logged' };
});
exports.rotateDailyMission = functions.pubsub.schedule('every 24 hours').onRun(async () => {
    const missionsSnap = await db.collection('missions').get();
    if (missionsSnap.empty) {
        console.warn('No missions available to rotate');
        return null;
    }
    const list = missionsSnap.docs.map((doc) => doc.data());
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
        expiresAt: luxon_1.DateTime.utc().plus({ days: 1 }).toISO()
    });
    return null;
});
exports.updateLeaderboard = functions.firestore
    .document('players/{playerId}/sessions/{sessionId}')
    .onCreate(async (snap, context) => {
    const data = snap.data();
    if (!data || typeof data.score !== 'number') {
        return null;
    }
    const entry = {
        score: data.score,
        durationMs: data.durationMs ?? 0,
        accuracyKm: data.accuracyKm ?? 0,
        timestamp: firestore_1.Timestamp.now()
    };
    await db
        .collection('leaderboards')
        .doc('season-1')
        .collection('entries')
        .doc(context.params.playerId)
        .set(entry);
    return null;
});
