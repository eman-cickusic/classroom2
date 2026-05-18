# Classroom 2.0 — Technical Architecture

## Overview

Build a native Android app using Kotlin, Jetpack Compose, Firebase, and MVVM.

The app should be simple, clean, and reliable.

## Recommended Stack

- Kotlin
- Jetpack Compose
- Navigation Compose
- Firebase Authentication
- Firebase Firestore
- Firebase Firestore snapshot listeners
- Optional Firebase Storage
- Optional Room for offline cache
- CameraX or ML Kit for QR scanning
- QR generation library
- Material 3

## Architecture Pattern

Use MVVM with repository pattern.

```text
UI Screen
↓
ViewModel
↓
Repository
↓
Firebase / Local Data Source
```

## Package Structure

```text
com.classroom2.app
├── data
│   ├── model
│   │   ├── UserDto.kt
│   │   ├── ClassSessionDto.kt
│   │   ├── AttendanceRecordDto.kt
│   │   ├── QuizDto.kt
│   │   └── QuizAnswerDto.kt
│   ├── repository
│   │   ├── AuthRepositoryImpl.kt
│   │   ├── AttendanceRepositoryImpl.kt
│   │   ├── QuizRepositoryImpl.kt
│   │   ├── InsightRepositoryImpl.kt
│   │   └── GamificationRepositoryImpl.kt
│   └── remote
│       ├── FirebaseService.kt
│       └── FirestorePaths.kt
├── domain
│   ├── model
│   │   ├── User.kt
│   │   ├── UserRole.kt
│   │   ├── ClassSession.kt
│   │   ├── AttendanceRecord.kt
│   │   ├── Quiz.kt
│   │   ├── QuizAnswer.kt
│   │   ├── InsightSummary.kt
│   │   └── Badge.kt
│   └── usecase
│       ├── CreateAttendanceSessionUseCase.kt
│       ├── MarkAttendanceUseCase.kt
│       ├── CreateQuizUseCase.kt
│       ├── SubmitQuizAnswerUseCase.kt
│       ├── GenerateInsightUseCase.kt
│       └── AwardPointsUseCase.kt
├── presentation
│   ├── onboarding
│   │   ├── RoleSelectionScreen.kt
│   │   └── RoleSelectionViewModel.kt
│   ├── professor
│   │   ├── ProfessorDashboardScreen.kt
│   │   └── ProfessorDashboardViewModel.kt
│   ├── student
│   │   ├── StudentDashboardScreen.kt
│   │   └── StudentDashboardViewModel.kt
│   ├── attendance
│   │   ├── ProfessorAttendanceScreen.kt
│   │   ├── StudentScannerScreen.kt
│   │   ├── AttendanceSuccessScreen.kt
│   │   └── AttendanceViewModel.kt
│   ├── quiz
│   │   ├── CreateQuizScreen.kt
│   │   ├── StudentQuizScreen.kt
│   │   ├── QuizResultsScreen.kt
│   │   └── QuizViewModel.kt
│   ├── ai
│   │   ├── AIExplainerScreen.kt
│   │   └── AIExplainerViewModel.kt
│   └── components
│       ├── ClassroomCard.kt
│       ├── PrimaryActionButton.kt
│       ├── StatCard.kt
│       ├── EmptyState.kt
│       ├── LoadingState.kt
│       ├── SuccessCheck.kt
│       └── SectionHeader.kt
├── ui
│   ├── theme
│   │   ├── Color.kt
│   │   ├── Theme.kt
│   │   └── Type.kt
│   └── navigation
│       ├── AppNavGraph.kt
│       └── Routes.kt
└── util
    ├── QRCodeUtil.kt
    ├── TimeUtil.kt
    ├── DemoData.kt
    └── Result.kt
```

## Domain Models

```kotlin
enum class UserRole {
    PROFESSOR,
    STUDENT
}

data class User(
    val id: String = "",
    val name: String = "",
    val role: UserRole = UserRole.STUDENT,
    val points: Int = 0,
    val streak: Int = 0
)

data class ClassSession(
    val id: String = "",
    val professorId: String = "",
    val classId: String = "demo-class",
    val classTitle: String = "Computer Science 101",
    val createdAt: Long = System.currentTimeMillis(),
    val expiresAt: Long = System.currentTimeMillis() + 300000,
    val active: Boolean = true
)

data class AttendanceRecord(
    val id: String = "",
    val sessionId: String = "",
    val studentId: String = "",
    val studentName: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

data class Quiz(
    val id: String = "",
    val sessionId: String = "",
    val professorId: String = "",
    val question: String = "",
    val options: List<String> = emptyList(),
    val correctAnswerIndex: Int = 0,
    val active: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)

data class QuizAnswer(
    val id: String = "",
    val quizId: String = "",
    val studentId: String = "",
    val studentName: String = "",
    val selectedIndex: Int = 0,
    val correct: Boolean = false,
    val submittedAt: Long = System.currentTimeMillis()
)

data class InsightSummary(
    val quizId: String = "",
    val participationRate: Int = 0,
    val correctPercentage: Int = 0,
    val mostSelectedAnswer: String = "",
    val mostMissedAnswer: String = "",
    val confusingTopic: String = "",
    val recommendedExplanation: String = ""
)

data class Badge(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val emoji: String = ""
)
```

## Firestore Structure

```text
users/{userId}

sessions/{sessionId}
sessions/{sessionId}/attendance/{studentId}

quizzes/{quizId}
quizzes/{quizId}/answers/{studentId}

leaderboards/{classId}/students/{studentId}
```

## Firestore Paths

Create a centralized file:

```kotlin
object FirestorePaths {
    const val USERS = "users"
    const val SESSIONS = "sessions"
    const val ATTENDANCE = "attendance"
    const val QUIZZES = "quizzes"
    const val ANSWERS = "answers"
    const val LEADERBOARDS = "leaderboards"
    const val DEFAULT_CLASS_ID = "demo-class"
}
```

## Repositories

### AuthRepository

Responsibilities:

- Create demo professor
- Create demo student
- Store current role locally
- Return current user

For hackathon speed, anonymous auth or local demo identity is acceptable.

### AttendanceRepository

Responsibilities:

- Create attendance session
- Observe active session
- Mark attendance
- Observe attendance records
- End attendance session
- Check duplicate attendance

### QuizRepository

Responsibilities:

- Create quiz
- Observe active quiz
- Submit answer
- Observe quiz answers
- End quiz

### InsightRepository

Responsibilities:

- Calculate participation rate
- Calculate correct answer percentage
- Find most missed answer
- Generate recommended follow-up explanation

### GamificationRepository

Responsibilities:

- Award attendance points
- Award quiz participation points
- Award correct answer points
- Update leaderboard
- Assign badges

## ViewModel State Pattern

Each ViewModel should expose one state object.

Example:

```kotlin
data class AttendanceUiState(
    val isLoading: Boolean = false,
    val activeSession: ClassSession? = null,
    val attendanceRecords: List<AttendanceRecord> = emptyList(),
    val errorMessage: String? = null,
    val successMessage: String? = null
)
```

Use:

```kotlin
private val _uiState = MutableStateFlow(AttendanceUiState())
val uiState: StateFlow<AttendanceUiState> = _uiState.asStateFlow()
```

## Result Wrapper

Create:

```kotlin
sealed class AppResult<out T> {
    data class Success<T>(val data: T) : AppResult<T>()
    data class Error(val message: String) : AppResult<Nothing>()
    data object Loading : AppResult<Nothing>()
}
```

## QR Attendance Logic

QR payload:

```json
{
  "sessionId": "abc123",
  "professorId": "prof456",
  "classId": "demo-class",
  "expiresAt": 1716150000000
}
```

Create model:

```kotlin
data class AttendanceQrPayload(
    val sessionId: String,
    val professorId: String,
    val classId: String,
    val expiresAt: Long
)
```

Validation:

1. Parse QR.
2. Check required fields.
3. Check `expiresAt > currentTime`.
4. Check session active.
5. Check student not already marked present.
6. Save attendance.
7. Award points.
8. Show success.

## Live Quiz Logic

Professor:

1. Creates quiz.
2. Saves quiz with `active = true`.
3. Student dashboard observes active quiz.
4. Students submit one answer.
5. Professor observes answers collection.
6. Results update in real time.

Student answer validation:

1. Student can answer only once.
2. Quiz must be active.
3. Selected index must exist.
4. Correctness calculated from quiz correct index.
5. Points awarded.

## Insight Logic

Input:

- Quiz
- Answers
- Expected class size

Calculations:

```text
participationRate = answers.size / expectedClassSize
correctPercentage = correctAnswers / answers.size
mostSelectedAnswer = option with max selections
mostMissedAnswer = wrong option with max selections
confusingTopic = derived from question keyword
recommendedExplanation = template based on correctness
```

Recommended explanation rules:

- If correct percentage >= 80:
  - “Most students understood this concept. Continue with a harder follow-up question.”
- If correct percentage between 50 and 79:
  - “Some students are confused. Give one example and ask a simpler follow-up.”
- If correct percentage < 50:
  - “Pause and reteach the core concept using a visual example.”

## AI Explainer Logic

If real AI API is available, use it.

If not, use local generated templates.

Function:

```kotlin
fun generateExplanation(concept: String, mode: ExplanationMode): String
```

Modes:

```kotlin
enum class ExplanationMode {
    LIKE_I_AM_12,
    EXAMPLE,
    THREE_BULLETS,
    MINI_QUIZ
}
```

## Demo Mode

Add a DemoData object with:

- Demo professor
- Demo student names
- Demo class
- Demo quiz
- Demo leaderboard

This protects the demo if Firebase data is empty.

## Error Handling

Every important action must show one of:

- Loading state
- Success state
- Error state

Never silently fail.

## Security Rules for MVP

Implement reasonable app-level validation:

- QR expiration
- One attendance per student per session
- One quiz answer per student per quiz
- Active session only
- Active quiz only

Firestore security rules can be simple for hackathon, but README should mention future hardening.

## Required Dependencies

Likely dependencies:

```kotlin
implementation("androidx.navigation:navigation-compose:<latest>")
implementation("com.google.firebase:firebase-firestore-ktx:<latest>")
implementation("com.google.firebase:firebase-auth-ktx:<latest>")
implementation("com.google.firebase:firebase-bom:<latest>")
implementation("androidx.lifecycle:lifecycle-viewmodel-compose:<latest>")
implementation("androidx.lifecycle:lifecycle-runtime-compose:<latest>")
implementation("io.coil-kt:coil-compose:<latest>")
```

For QR:

Use one of:

```kotlin
implementation("com.google.zxing:core:<latest>")
```

For camera scanning:

Use ML Kit or CameraX if time allows.

If QR scanning is difficult, implement:

- Camera screen placeholder
- Demo Scan button
- Manual paste QR field

## Build Checklist

- App compiles
- Firebase connected or local demo fallback works
- Professor and student flows both accessible
- Required features work
- Extra features visible
- Screens polished
- README complete
- Screenshots ready
