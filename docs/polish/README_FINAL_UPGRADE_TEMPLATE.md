# Classroom 2.0

**Turn every class into a live learning experience.**

Classroom 2.0 is a native Android app that helps professors make classes interactive with QR attendance, live quizzes, AI explanations, real-time teaching insights, and student gamification.

## The Problem

Most classrooms still rely on manual attendance and passive lectures. Professors often do not know who is present or who understood the lesson until after class is over.

## The Solution

Classroom 2.0 gives professors and students a real-time classroom operating system:

- Professors capture attendance in seconds.
- Students check in with a QR scan.
- Professors launch live quizzes.
- Students answer instantly.
- Insights show what the class understood.
- AI helps explain difficult concepts.
- Points and badges motivate participation.

## Features

### Required Features

| Feature | Description |
|---|---|
| QR Attendance | Professor generates a session-specific QR code. Students scan to check in instantly. |
| Live Quiz | Professor creates live multiple-choice quizzes and sees real-time results. |

### Extra Features

| Feature | Description |
|---|---|
| AI Concept Explainer | Students can ask for simple explanations, examples, summaries, or mini quizzes. |
| Professor Insight Dashboard | Quiz results become teaching recommendations and comprehension insights. |
| Gamification | Students earn points, badges, streaks, and leaderboard ranking. |
| Attendance History | Professors can review past attendance sessions. |
| Demo Mode | The app works without Firebase setup using an in-memory backend. |

## Screenshots

| Professor Dashboard | QR Attendance | Student Success |
|---|---|---|
| ![](screenshots/professor_dashboard.png) | ![](screenshots/qr_attendance.png) | ![](screenshots/student_success.png) |

| Live Quiz | Insight Dashboard | AI Explainer |
|---|---|---|
| ![](screenshots/live_quiz.png) | ![](screenshots/insight_dashboard.png) | ![](screenshots/ai_explainer.png) |

| Leaderboard | Attendance History | Student Dashboard |
|---|---|---|
| ![](screenshots/leaderboard.png) | ![](screenshots/attendance_history.png) | ![](screenshots/student_dashboard.png) |

## Demo Flow

The app is designed to be demoed on one device.

1. Choose Professor.
2. Start attendance.
3. Show the generated QR session.
4. Switch to Student.
5. Use Demo Scan or paste payload.
6. See attendance success and points earned.
7. Switch back to Professor.
8. See live attendance update.
9. Create and launch a live quiz.
10. Switch to Student.
11. Answer the quiz.
12. Switch back to Professor.
13. View live results and teaching insight.
14. Show AI Explainer and Leaderboard.

## Why Classroom 2.0 Stands Out

| Judging Area | How We Address It |
|---|---|
| Required Features | QR attendance and live quiz are implemented end-to-end. |
| Creativity | AI explanations, professor insights, gamification, badges, and leaderboard. |
| Technical Quality | Kotlin, Jetpack Compose, MVVM, repositories, Firebase/in-memory backend abstraction. |
| UX/UI | Polished role-based dashboards, modern cards, smooth states, clear demo flow. |
| Completeness | Demo mode, attendance history, seeded data, README, and screenshot-ready screens. |

## Tech Stack

- Kotlin
- Android Native
- Jetpack Compose
- Material 3
- Navigation Compose
- MVVM architecture
- Firebase Firestore
- In-memory demo backend
- CameraX
- ML Kit barcode scanning
- ZXing QR generation

## Architecture

The app uses MVVM with clear separation between UI, state, repositories, and backend implementation.

```text
com.classroom2.app
├── data
│   ├── model
│   ├── repository
│   └── remote
├── domain
│   ├── model
│   └── usecase
├── presentation
│   ├── onboarding
│   ├── professor
│   ├── student
│   ├── attendance
│   ├── quiz
│   ├── ai
│   └── components
├── ui
│   ├── theme
│   └── navigation
└── util
```

## Firebase and Demo Mode

The project supports two backend modes:

1. **Demo Mode:** Uses the in-memory backend and runs without Firebase setup.
2. **Firebase Mode:** Replace the placeholder `google-services.json` with a real Firebase config to use Firestore live sync.

This makes the project reliable for hackathon judging while still showing a realistic production architecture.

## How To Run

1. Clone the repository.
2. Open it in Android Studio.
3. Sync Gradle.
4. Run the app on an emulator or Android device.
5. Use Demo Mode immediately, or add a real `google-services.json` for Firebase.

If Gradle wrapper is missing, run:

```bash
gradle wrapper
```

Then:

```bash
./gradlew assembleDebug
```

## AI Tools Used

AI tools were used to assist with:

- Product strategy
- UX planning
- Code scaffolding
- README drafting
- Demo scripting
- QA checklist generation

The final implementation focuses on native Android quality, clean architecture, and a polished demo experience.

## Future Improvements

- LMS integration
- Push notifications
- Export attendance as CSV/PDF
- Multi-class support
- Real AI API integration
- Advanced analytics over time
- Professor-created class rosters

## Pitch

Classroom 2.0 replaces passive classrooms with interactive, measurable, AI-assisted learning.
