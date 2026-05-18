# Classroom 2.0

> Modern classroom interaction through QR attendance, live quizzes, AI explanations, and real-time learning insights.

A native Android hackathon project built for the **Classroom 2.0 Hackathon**.

---

## One-Sentence Pitch

Classroom 2.0 helps professors turn any class into an interactive, measurable, and AI-assisted learning experience in seconds.

## Problem

Traditional classrooms still rely on manual attendance and passive lectures. Professors often do not know who is present or whether students understand the lesson in real time.

1. Attendance wastes time.
2. Students stay passive.
3. Professors lack instant comprehension feedback.
4. Learning data is scattered or lost.

## Solution

Classroom 2.0 lets a professor start an attendance session, launch a live quiz, understand class comprehension, and motivate participation — all in seconds.

---

## Features

### Required

| | |
|---|---|
| **QR Code Digital Attendance** | Professor generates a session-specific QR with expiration. Student scans (real camera) or taps Demo Scan. Duplicate check-ins are blocked, live count updates on the professor dashboard. |
| **Live Quiz With Instant Results** | Professor types a question + four options (or taps *Use demo question* for the polymorphism quiz). Students answer once. Professor sees the bar-chart distribution and correct percentage live. |

### Winning Extras

- **AI Concept Explainer** — four modes (Like I'm 12, Example, 3 bullets, Mini quiz), templated locally so it works offline; bundled high-quality polymorphism response for the demo.
- **Professor Insight Dashboard** — class-understanding headline, participation/correct chips, most-missed answer, recommended next step, suggested follow-up question. Generated deterministically from quiz answers.
- **Gamification + Smart Points** — +10 attendance / +5 participation / +15 correct / +5 streak, FirstCheckIn / QuizStarter / SharpMind / StreakHero / Top 3 badges, live leaderboard with current-student highlight, dashboard stats.

### Bonus

- Attendance history with per-session attendance %.
- Demo Scan fallback + manual paste field — demo always survives even in emulators without a webcam.
- Floating *Pro view ⇄ Student view* button in both dashboards so the full demo runs on one device.
- Seed leaderboard preloaded with Eman, Lejla, Amar, Sara, Tarik, Hana — never an empty leaderboard.

---

## Tech Stack

- **Language:** Kotlin 2.0.21
- **UI:** Jetpack Compose + Material 3
- **Architecture:** MVVM with StateFlow
- **Navigation:** Navigation Compose
- **Persistence:** Firebase Firestore (primary) with in-memory StateFlow fallback
- **QR generation:** ZXing
- **QR scanning:** CameraX + ML Kit Barcode
- **Permissions:** Accompanist Permissions
- **Serialization:** kotlinx.serialization for QR payloads

## Architecture

```
com.classroom2.app
├── data
│   ├── remote          FirebaseInitializer, FirestorePaths, InMemoryStore, ServiceLocator
│   └── repository      Attendance / Quiz / Insight / Gamification / Auth (interface + Firestore + Local impls)
├── domain
│   └── model           User, ClassSession, AttendanceRecord, Quiz, QuizAnswer, InsightSummary, Badge, …
├── presentation
│   ├── onboarding      RoleSelectionScreen
│   ├── professor       ProfessorDashboardScreen
│   ├── student         StudentDashboardScreen
│   ├── attendance      ProfessorAttendanceScreen, StudentScannerScreen, AttendanceSuccessScreen, AttendanceViewModel
│   ├── quiz            CreateQuizScreen, StudentQuizScreen, QuizResultsScreen, QuizViewModel
│   ├── insight         InsightDashboardScreen
│   ├── ai              AIExplainerScreen
│   ├── leaderboard     LeaderboardScreen
│   ├── history         AttendanceHistoryScreen
│   └── components      ClassroomCard, PrimaryActionButton, StatCard, OptionCard, LeaderboardRow, BadgeCard, …
├── ui
│   ├── theme           ClassroomTheme, Color, Type
│   └── navigation      Routes, AppNavGraph
└── util                QRCodeUtil (ZXing), TimeUtil, DemoData, AIExplainer, AppResult
```

Every repository is an interface with two implementations:

- `Firestore*Repository` — uses `addSnapshotListener` wrapped in `callbackFlow` for real-time reads, suspending writes via `kotlinx-coroutines-play-services`.
- `Local*Repository` — backed by `InMemoryStore` MutableStateFlows, same observer semantics.

`ServiceLocator` decides which one to use at runtime: if `FirebaseInitializer` detects a real (non-placeholder) project id, Firestore wins; otherwise the local backend handles everything so the demo always works.

## Firebase Schema

```
users/{userId}
sessions/{sessionId}
sessions/{sessionId}/attendance/{studentId}
quizzes/{quizId}
quizzes/{quizId}/answers/{studentId}
leaderboards/demo-class/students/{studentId}
```

Document IDs use `studentId` in subcollections so the database itself prevents duplicate attendance / duplicate answers.

`firestore.rules` is included at the repo root with permissive hackathon rules and notes on production hardening.

---

## How To Run

1. **Clone**

   ```bash
   git clone <repo>
   cd classroom2
   ```

2. **Open in Android Studio** (Hedgehog or newer). Let Gradle sync (it will fetch the wrapper on first sync).

3. **Choose a backend:**

   **Option A — In-memory backend (zero setup):**
   The repo ships with a placeholder `app/google-services.json` (project id `classroom2-demo-placeholder`). At runtime `FirebaseInitializer` detects the placeholder and routes every repo call through `InMemoryStore`. App runs end-to-end with no external dependencies. Best for the demo.

   **Option B — Real Firebase:**
   1. Create a Firebase project at https://console.firebase.google.com.
   2. Add an Android app with package `com.classroom2.app`.
   3. Download the real `google-services.json` and replace `app/google-services.json`.
   4. Enable Cloud Firestore in the Firebase console.
   5. Paste the contents of `firestore.rules` into the Firestore Rules tab.
   6. Re-sync Gradle and run.

4. **Run** on an emulator or device (minSdk 24).

> If `gradlew` is missing from the checkout, run `gradle wrapper` once or let Android Studio generate it on first sync.

## Demo Flow (2 minutes)

1. **Role select** → tap *Professor*.
2. *Start attendance* → QR code renders, timer counting down, "0 of 6 present".
3. Top-right *Student view* → student dashboard with 120 pts, 3-day streak.
4. *Scan attendance QR* → tap **Demo Scan** (always works) — success screen, +15 points, streak +1.
5. Top-right *Pro view* → present count is now 1, Eman shows in the live list.
6. *Start live quiz* → tap **Use demo question** ("What does polymorphism mean?") → *Start quiz* → results screen empty.
7. *Student view* → quiz auto-appears → tap *One interface having many forms* → submit → "Correct! 🎯 +20 points".
8. *Pro view* → results bar chart filled, green bar on the correct option.
9. *View insight* → "Class understanding 100%", participation chip, recommendation card.
10. From either side: *Leaderboard* → Eman now at the top, badges earned, classmates seeded.
11. *Student view → AI explainer* → "polymorphism" + *Like I'm 12* → templated answer beneath.

The 2-minute pitch script is in `docs/handoff/09_DEMO_SCRIPT.md`.

## Screenshots

Capture before submission and drop into `screenshots/`:

- `screenshots/professor-dashboard.png`
- `screenshots/qr-attendance.png`
- `screenshots/attendance-success.png`
- `screenshots/live-quiz.png`
- `screenshots/quiz-results.png`
- `screenshots/insight-dashboard.png`
- `screenshots/ai-explainer.png`
- `screenshots/leaderboard.png`

## Security and Anti-Cheating

App-level guards already in place:

- Session-specific QR payload (sessionId + professorId + classId + expiresAt).
- QR expiration check on every scan.
- Duplicate attendance blocked (Firestore document id = studentId).
- Duplicate quiz answer blocked (Firestore document id = studentId).
- Active session / active quiz required.

Production hardening backlog: Firebase Auth, professor-ownership rules, enrollment validation, server timestamps, rotating QR every 30–60s, Cloud Functions for trusted point updates.

## Future Improvements

- LMS integration
- Push notifications
- Export attendance as CSV / PDF
- Advanced analytics
- Multi-class support
- Real AI API integration
- Room offline cache
- Rotating QR codes

## AI Tools Used

Claude (Anthropic) assisted with product strategy review, README polish, and accelerating implementation. The final code is hand-reviewed Kotlin with Jetpack Compose, MVVM, and a dual Firestore/in-memory backend.

## Repo Layout

- `docs/handoff/` — original 12-document hackathon spec pack used as the source of truth.
- `app/` — Android module.
- `firestore.rules` — Cloud Firestore security rules (demo + hardening notes).
- `screenshots/` — drop captured PNGs here for README rendering.
