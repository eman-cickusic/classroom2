# Classroom 2.0 — Feature Implementation Guide

## Implementation Order

Build in this exact order.

1. Project setup
2. Theme and reusable components
3. Navigation
4. Role selection
5. Demo users
6. Professor dashboard
7. Student dashboard
8. Attendance session creation
9. QR generation
10. Attendance scan / demo scan
11. Live attendance list
12. Quiz creation
13. Student quiz answering
14. Quiz results
15. Insight dashboard
16. AI explainer
17. Gamification and leaderboard
18. Attendance history
19. README and screenshots
20. Demo polish

## Phase 1 — Project Setup

### Goals

- Create native Android project
- Set up Kotlin and Compose
- Set up Firebase or local demo fallback
- Create package structure
- Add theme
- Add navigation

### Tasks

- Create project named `Classroom2`
- Package: `com.classroom2.app`
- Enable Jetpack Compose
- Add Material 3
- Add Navigation Compose
- Add Firebase dependencies
- Add Firestore
- Add Firebase Auth
- Add QR dependency
- Create folder/package structure
- Create `Routes.kt`
- Create `AppNavGraph.kt`
- Create `ClassroomTheme`

### Done When

- App launches
- Onboarding screen appears
- Navigation compiles
- Theme is applied

## Phase 2 — Design System

### Goals

Make the app visually consistent.

### Build Components

- `ClassroomCard`
- `PrimaryActionButton`
- `StatCard`
- `EmptyState`
- `LoadingState`
- `SuccessCheck`
- `SectionHeader`
- `OptionCard`
- `LeaderboardRow`
- `BadgeCard`

### Done When

- Dashboards use reusable cards
- Buttons look consistent
- Empty/loading/success states exist

## Phase 3 — Role Selection

### User Story

As a user, I choose whether I am a professor or student so the app shows the right experience.

### Tasks

- Build `RoleSelectionScreen`
- Add two role cards
- Save selected role in ViewModel or local state
- Navigate to professor or student dashboard

### Acceptance Criteria

- Professor role navigates to professor dashboard
- Student role navigates to student dashboard
- Selected role is visually highlighted

## Phase 4 — Demo Users

### Goal

Avoid login complexity.

Create demo users:

```kotlin
val demoProfessor = User(
    id = "prof-demo",
    name = "Professor Ada",
    role = UserRole.PROFESSOR
)

val demoStudent = User(
    id = "student-demo",
    name = "Eman",
    role = UserRole.STUDENT,
    points = 120,
    streak = 3
)
```

Also create demo classmates:

- Lejla
- Amar
- Sara
- Tarik
- Hana

### Acceptance Criteria

- App can run without manual signup
- Professor and student identity are stable
- Demo data looks realistic

## Phase 5 — Professor Dashboard

### Tasks

Build screen with:

- Greeting
- Class summary
- Stats cards
- Action cards

Actions:

- Start Attendance
- Start Live Quiz
- View Insights
- Leaderboard

### Acceptance Criteria

- Screen looks polished
- Main demo actions are obvious
- No dead buttons unless marked as coming soon

## Phase 6 — Student Dashboard

### Tasks

Build screen with:

- Greeting
- Points card
- Streak card
- Scan Attendance
- Join Quiz
- AI Explainer
- Leaderboard

### Acceptance Criteria

- Student can access all demo features
- Current points visible
- Looks consistent with professor dashboard

## Phase 7 — Attendance Session Creation

### User Story

As a professor, I can start an attendance session so students can check in.

### Data

Create `ClassSession`.

Fields:

- id
- professorId
- classId
- classTitle
- createdAt
- expiresAt
- active

### Repository Methods

```kotlin
suspend fun createAttendanceSession(professorId: String): ClassSession
fun observeActiveSession(classId: String): Flow<ClassSession?>
fun observeAttendance(sessionId: String): Flow<List<AttendanceRecord>>
suspend fun endSession(sessionId: String)
```

### UI

Professor Attendance Screen:

- Create or use active session
- Show QR
- Show timer
- Show present count
- Show student list
- End session button

### Acceptance Criteria

- Professor can create active session
- Session is saved
- Session can be ended
- Active session is observable

## Phase 8 — QR Generation

### QR Payload

```json
{
  "sessionId": "abc123",
  "professorId": "prof-demo",
  "classId": "demo-class",
  "expiresAt": 1716150000000
}
```

### Tasks

- Create `AttendanceQrPayload`
- Convert payload to JSON string
- Generate QR bitmap
- Display QR in Compose

### Acceptance Criteria

- QR code displays clearly
- QR contains session id and expiration
- QR screen looks demo-ready

## Phase 9 — Student QR Scan / Demo Scan

### Preferred

Use CameraX / ML Kit barcode scanning.

### If Camera Is Too Slow

Implement demo fallback:

- Student taps “Demo Scan Latest QR”
- App gets latest active session
- Validates session
- Marks attendance

Optional manual fallback:

- Text field to paste QR payload

### Repository Method

```kotlin
suspend fun markAttendance(
    sessionId: String,
    student: User
): AppResult<AttendanceRecord>
```

### Validation

- Session exists
- Session active
- Session not expired
- Student not already checked in

### Acceptance Criteria

- Student can mark attendance
- Duplicate check-in blocked
- Success screen appears
- Professor list updates

## Phase 10 — Attendance Success

### Tasks

Build success screen with:

- Big checkmark
- “You are checked in”
- Points earned
- Streak
- Back to dashboard button

### Acceptance Criteria

- Screen feels rewarding
- Points are visible
- Demo audience understands immediately

## Phase 11 — Live Attendance List

### Tasks

Professor screen observes attendance records.

Show:

- Student name
- Timestamp
- Present count

### Acceptance Criteria

- New attendance appears live or after refresh
- Empty state appears before scans
- Count updates correctly

## Phase 12 — Quiz Creation

### User Story

As a professor, I can start a quick quiz to measure understanding.

### UI Fields

- Question
- Option A
- Option B
- Option C
- Option D
- Correct answer

Add button:

- Use Demo Question

Demo question:

```text
What does polymorphism mean in programming?
```

Options:

1. A variable changing value
2. One interface having many forms
3. A loop inside another loop
4. Storing data permanently

Correct index:

1

### Repository Methods

```kotlin
suspend fun createQuiz(quiz: Quiz): Quiz
fun observeActiveQuiz(classId: String): Flow<Quiz?>
suspend fun endQuiz(quizId: String)
```

### Acceptance Criteria

- Professor can create quiz
- Quiz saved as active
- Student can see active quiz

## Phase 13 — Student Quiz Answering

### Tasks

Student quiz screen:

- Observe active quiz
- Show question
- Show option cards
- Allow one selected option
- Submit answer
- Show submitted confirmation

Repository method:

```kotlin
suspend fun submitAnswer(
    quiz: Quiz,
    student: User,
    selectedIndex: Int
): AppResult<QuizAnswer>
```

Validation:

- Quiz active
- Answer once only
- Selected index valid

Points:

- Participation: +5
- Correct: +15

### Acceptance Criteria

- Student can answer
- Correctness calculated
- Duplicate answer blocked
- Confirmation shown

## Phase 14 — Quiz Results

### Tasks

Professor observes answers.

Show:

- Total answers
- Correct percentage
- Option distribution
- Most selected answer
- Button to open insight dashboard

### Acceptance Criteria

- Results update live or near-live
- Correct percentage accurate
- Visual distribution visible

## Phase 15 — Insight Dashboard

### Tasks

Generate insight from quiz answers.

Show:

- Participation rate
- Correct percentage
- Most missed answer
- Confusing topic
- Recommended explanation
- Suggested follow-up question

### Insight Rules

```text
If correct >= 80:
Students understand the topic. Continue with a harder follow-up.

If correct between 50 and 79:
Some confusion exists. Give one example and ask a simpler follow-up.

If correct < 50:
Pause and reteach the concept with a visual example.
```

### Acceptance Criteria

- Insight feels intelligent
- Screen is visually impressive
- Demo can explain why this helps professors

## Phase 16 — AI Concept Explainer

### Tasks

Build screen:

- Concept text field
- Mode chips
- Explain button
- Result card

Modes:

- Explain like I am 12
- Give an example
- Summarize in 3 bullets
- Generate mini quiz

### Local Template Implementation

Use this if no AI API:

```kotlin
fun explain(concept: String, mode: ExplanationMode): String {
    return when (mode) {
        ExplanationMode.LIKE_I_AM_12 ->
            "$concept means an idea that can be understood step by step. Imagine explaining it with a simple everyday example."
        ExplanationMode.EXAMPLE ->
            "Example of $concept: In programming, polymorphism lets different objects respond to the same action in their own way."
        ExplanationMode.THREE_BULLETS ->
            "• $concept is an important class concept.\n• It helps explain how ideas connect.\n• A simple example makes it easier to remember."
        ExplanationMode.MINI_QUIZ ->
            "Mini quiz: What is the main idea of $concept?\nA) Memorizing only\nB) Understanding how it works\nC) Ignoring examples\nD) Guessing"
    }
}
```

Customize output for common demo concept:

```text
Polymorphism means one thing can take many forms. In programming, the same method can behave differently depending on the object using it.
```

### Acceptance Criteria

- AI explainer looks useful
- Works offline with template responses
- Does not crash on empty input

## Phase 17 — Gamification

### Tasks

Award points:

- Attendance: +10
- Quiz participation: +5
- Correct answer: +15
- Streak bonus: +5

Build:

- Points card
- Badges
- Leaderboard

Badges:

- First Check-In
- Quiz Starter
- Sharp Mind
- Streak Hero
- Top 3

### Acceptance Criteria

- Student sees points
- Leaderboard visible
- Badges visible
- Feature is easy to explain in demo

## Phase 18 — Attendance History

### Tasks

Professor history screen:

- List completed sessions
- Date
- Present count
- Class title
- Attendance percentage

### Acceptance Criteria

- History shows saved sessions
- Empty state if none
- Screen is polished

## Phase 19 — README and Screenshots

### Tasks

- Add README.md
- Add feature list
- Add tech stack
- Add architecture
- Add setup steps
- Add screenshots
- Add demo flow
- Add future improvements

### Acceptance Criteria

- README looks professional
- Screenshots are visible
- Judges can understand app quickly

## Phase 20 — Demo Polish

### Tasks

- Test full demo flow
- Add realistic fake data
- Remove broken buttons
- Remove TODO labels
- Fix spacing
- Fix colors
- Add loading/success states
- Prepare pitch

### Acceptance Criteria

- 2-minute demo works
- Required features are visible
- Extra features are visible
- App does not crash
