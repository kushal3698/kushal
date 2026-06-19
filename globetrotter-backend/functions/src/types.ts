export type Era = 'Treta' | 'Dvapara' | 'Kali';

export interface PlayerState {
  uid: string;
  currentEra: Era;
  fuel: number;
  artifacts: string[];
  lastMissionId: string;
  position: {
    lat: number;
    lon: number;
  };
  updatedAt: FirebaseFirestore.Timestamp;
}

export interface Mission {
  id: string;
  name: string;
  era: Era;
  lat: number;
  lon: number;
  riddle: string;
  success: string;
  reward: string;
  difficulty: 'easy' | 'medium' | 'hard';
}

export interface LeaderboardEntry {
  score: number;
  durationMs: number;
  accuracyKm: number;
  timestamp: FirebaseFirestore.Timestamp;
}
