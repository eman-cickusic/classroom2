# Classroom 2.0

Modern classroom interaction through QR attendance, live quizzes, AI explanations, and real-time learning insights.

## Problem

Traditional classrooms still rely on manual attendance and passive lectures. Professors often do not know who is present or whether students understand the lesson in real time.

Manual classroom workflows create four problems:

1. Attendance wastes time.
2. Students stay passive.
3. Professors lack instant comprehension feedback.
4. Learning data is scattered or lost.

## Solution

Classroom 2.0 is a native Android application that helps professors and students interact during class through QR attendance, live quizzes, AI-powered explanations, and student engagement features.

With Classroom 2.0, a professor can start an attendance session, launch a live quiz, understand class comprehension, and motivate participation in seconds.

## One-Sentence Pitch

Classroom 2.0 helps professors turn any class into an interactive, measurable, and AI-assisted learning experience in seconds.

## Features

### Required Features

#### QR Code Digital Attendance

- Professor creates an attendance session.
- App generates a session-specific QR code.
- Student scans the QR code.
- Attendance is saved.
- Professor sees attendance results live or near-live.
- Duplicate attendance is prevented.
- QR code includes an expiration timestamp.

#### Live Quiz With Instant Results

- Professor creates a quick multiple-choice quiz.
- Students answer in real time.
- Professor sees answer distribution.
- Professor sees correct answer percentage.
- Quiz results are used to generate teaching insights.

### Extra Features

#### AI Concept Explainer

Students can ask for help understanding a difficult class concept.

Modes:

- Explain like I am 12
- Give an example
- Summarize in 3 bullets
- Generate a mini quiz

#### Professor Insight Dashboard

After a quiz, the professor sees:

- Participation rate
- Correct answer percentage
- Most missed answer
- Confusing topic
- Recommended follow-up explanation

#### Gamification and Smart Points

Students earn points for:

- Attendance
- Quiz participation
- Correct answers
- Streaks

The app also includes:

- Student leaderboard
- Badge system
- Personal progress card

## Screenshots

Add screenshots here:

### Professor Dashboard

![Professor Dashboard](screenshots/professor-dashboard.png)

### QR Attendance

![QR Attendance](screenshots/qr-attendance.png)

### Student Attendance Success

![Attendance Success](screenshots/attendance-success.png)

### Live Quiz

![Live Quiz](screenshots/live-quiz.png)

### Quiz Results

![Quiz Results](screenshots/quiz-results.png)

### Professor Insight Dashboard

![Insight Dashboard](screenshots/insight-dashboard.png)

### AI Concept Explainer

![AI Explainer](screenshots/ai-explainer.png)

### Leaderboard

![Leaderboard](screenshots/leaderboard.png)

## Tech Stack

- Kotlin
- Android Native
- Jetpack Compose
- Material 3
- Firebase Authentication
- Firebase Firestore
- Firestore real-time listeners
- QR code generation
- MVVM architecture

## Architecture

The app uses MVVM architecture with separate data, domain, and presentation layers.

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
│   ├── quiz
│   ├── attendance
│   ├── ai
│   └── components
├── ui
│   ├── theme
│   └── navigation
└── util
```

## Data Model

Core models include:

- User
- ClassSession
- AttendanceRecord
- Quiz
- QuizAnswer
- InsightSummary
- Badge
- LeaderboardEntry

## Firebase Structure

```text
users/{userId}

sessions/{sessionId}
sessions/{sessionId}/attendance/{studentId}

quizzes/{quizId}
quizzes/{quizId}/answers/{studentId}

leaderboards/{classId}/students/{studentId}
```

## How To Run

1. Clone the repository.
2. Open the project in Android Studio.
3. Create a Firebase project.
4. Add the Android app to Firebase.
5. Download `google-services.json`.
6. Place `google-services.json` in the app module.
7. Sync Gradle.
8. Run the app on an emulator or Android device.

## Demo Flow

1. Select Professor role.
2. Start an attendance session.
3. QR code appears.
4. Select Student role.
5. Scan QR code or use Demo Scan.
6. Student is marked present and earns points.
7. Professor sees attendance update.
8. Professor starts a live quiz.
9. Student answers the quiz.
10. Professor sees instant results.
11. Professor opens the insight dashboard.
12. Student uses AI concept explainer.
13. Student views points and leaderboard.

## AI Tools Used

AI tools were used to help:

- Plan the product strategy
- Generate app architecture
- Create README content
- Assist with UI copy
- Speed up implementation

The final app uses native Android logic, Firebase persistence, and structured MVVM code.

## Security and Anti-Cheating

The app includes basic anti-cheating logic:

- Session-specific QR payloads
- Expiration timestamp
- One attendance record per student per session
- One quiz answer per student per quiz
- Active session validation

Future production hardening would include:

- Strong Firebase Auth rules
- Professor ownership validation
- Student enrollment validation
- Server-side timestamps
- Rotating QR codes
- Cloud Functions for trusted scoring

## Future Improvements

- LMS integration
- Push notifications
- Export attendance as CSV/PDF
- Advanced analytics
- Multi-class support
- Real AI API integration
- Offline support with Room
- Professor-created class rosters
- Rotating QR codes every 30 seconds

## Team

Built for the Classroom 2.0 Hackathon.
