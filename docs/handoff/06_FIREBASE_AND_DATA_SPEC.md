# Classroom 2.0 — Firebase and Data Specification

## Goal

Use Firebase Firestore to power the demo with persistent and live classroom interactions.

## Collections

```text
users
sessions
quizzes
leaderboards
```

Subcollections:

```text
sessions/{sessionId}/attendance
quizzes/{quizId}/answers
leaderboards/{classId}/students
```

## Users Collection

Path:

```text
users/{userId}
```

Example:

```json
{
  "id": "student-demo",
  "name": "Eman",
  "role": "STUDENT",
  "points": 120,
  "streak": 3,
  "createdAt": 1716150000000
}
```

## Sessions Collection

Path:

```text
sessions/{sessionId}
```

Example:

```json
{
  "id": "session-123",
  "professorId": "prof-demo",
  "classId": "demo-class",
  "classTitle": "Computer Science 101",
  "createdAt": 1716150000000,
  "expiresAt": 1716150300000,
  "active": true
}
```

## Attendance Subcollection

Path:

```text
sessions/{sessionId}/attendance/{studentId}
```

Use `studentId` as document ID to prevent duplicates.

Example:

```json
{
  "id": "student-demo",
  "sessionId": "session-123",
  "studentId": "student-demo",
  "studentName": "Eman",
  "timestamp": 1716150100000
}
```

## Quizzes Collection

Path:

```text
quizzes/{quizId}
```

Example:

```json
{
  "id": "quiz-123",
  "sessionId": "session-123",
  "classId": "demo-class",
  "professorId": "prof-demo",
  "question": "What does polymorphism mean in programming?",
  "options": [
    "A variable changing value",
    "One interface having many forms",
    "A loop inside another loop",
    "Storing data permanently"
  ],
  "correctAnswerIndex": 1,
  "active": true,
  "createdAt": 1716150100000
}
```

## Quiz Answers Subcollection

Path:

```text
quizzes/{quizId}/answers/{studentId}
```

Use `studentId` as document ID to prevent duplicate answers.

Example:

```json
{
  "id": "student-demo",
  "quizId": "quiz-123",
  "studentId": "student-demo",
  "studentName": "Eman",
  "selectedIndex": 1,
  "correct": true,
  "submittedAt": 1716150200000
}
```

## Leaderboard Collection

Path:

```text
leaderboards/{classId}/students/{studentId}
```

Example:

```json
{
  "studentId": "student-demo",
  "studentName": "Eman",
  "points": 150,
  "streak": 4,
  "badges": ["First Check-In", "Quiz Starter", "Sharp Mind"],
  "updatedAt": 1716150200000
}
```

## Firestore Path Constants

Create:

```kotlin
object FirestorePaths {
    const val USERS = "users"
    const val SESSIONS = "sessions"
    const val ATTENDANCE = "attendance"
    const val QUIZZES = "quizzes"
    const val ANSWERS = "answers"
    const val LEADERBOARDS = "leaderboards"
    const val STUDENTS = "students"
    const val DEFAULT_CLASS_ID = "demo-class"
}
```

## Real-Time Listeners

### Observe Active Attendance Session

Query:

```text
sessions
where classId == demo-class
where active == true
order by createdAt desc
limit 1
```

### Observe Attendance

Path:

```text
sessions/{sessionId}/attendance
```

Order:

```text
timestamp ascending
```

### Observe Active Quiz

Query:

```text
quizzes
where classId == demo-class
where active == true
order by createdAt desc
limit 1
```

### Observe Quiz Answers

Path:

```text
quizzes/{quizId}/answers
```

Order:

```text
submittedAt ascending
```

### Observe Leaderboard

Path:

```text
leaderboards/demo-class/students
```

Order:

```text
points descending
```

Limit:

```text
10
```

## Repository Pseudocode

### Create Attendance Session

```kotlin
suspend fun createAttendanceSession(professorId: String): ClassSession {
    val sessionId = firestore.collection("sessions").document().id
    val now = System.currentTimeMillis()

    val session = ClassSession(
        id = sessionId,
        professorId = professorId,
        classId = "demo-class",
        classTitle = "Computer Science 101",
        createdAt = now,
        expiresAt = now + 5 * 60 * 1000,
        active = true
    )

    firestore.collection("sessions")
        .document(sessionId)
        .set(session)
        .await()

    return session
}
```

### Mark Attendance

```kotlin
suspend fun markAttendance(sessionId: String, student: User): AppResult<AttendanceRecord> {
    val sessionRef = firestore.collection("sessions").document(sessionId)
    val session = sessionRef.get().await().toObject(ClassSession::class.java)

    if (session == null) return AppResult.Error("Session not found")
    if (!session.active) return AppResult.Error("Session is no longer active")
    if (session.expiresAt < System.currentTimeMillis()) return AppResult.Error("QR code expired")

    val attendanceRef = sessionRef
        .collection("attendance")
        .document(student.id)

    val existing = attendanceRef.get().await()

    if (existing.exists()) {
        return AppResult.Error("You are already checked in")
    }

    val record = AttendanceRecord(
        id = student.id,
        sessionId = sessionId,
        studentId = student.id,
        studentName = student.name,
        timestamp = System.currentTimeMillis()
    )

    attendanceRef.set(record).await()

    return AppResult.Success(record)
}
```

### Submit Quiz Answer

```kotlin
suspend fun submitAnswer(
    quiz: Quiz,
    student: User,
    selectedIndex: Int
): AppResult<QuizAnswer> {
    if (!quiz.active) return AppResult.Error("Quiz is closed")
    if (selectedIndex !in quiz.options.indices) return AppResult.Error("Invalid answer")

    val answerRef = firestore
        .collection("quizzes")
        .document(quiz.id)
        .collection("answers")
        .document(student.id)

    val existing = answerRef.get().await()

    if (existing.exists()) {
        return AppResult.Error("You already answered this quiz")
    }

    val answer = QuizAnswer(
        id = student.id,
        quizId = quiz.id,
        studentId = student.id,
        studentName = student.name,
        selectedIndex = selectedIndex,
        correct = selectedIndex == quiz.correctAnswerIndex,
        submittedAt = System.currentTimeMillis()
    )

    answerRef.set(answer).await()

    return AppResult.Success(answer)
}
```

## Demo Data Seeder

Create a `DemoData.kt`.

Include:

```kotlin
object DemoData {
    val professor = User(
        id = "prof-demo",
        name = "Professor Ada",
        role = UserRole.PROFESSOR
    )

    val student = User(
        id = "student-demo",
        name = "Eman",
        role = UserRole.STUDENT,
        points = 120,
        streak = 3
    )

    val classmates = listOf(
        User(id = "student-1", name = "Lejla", role = UserRole.STUDENT, points = 160, streak = 5),
        User(id = "student-2", name = "Amar", role = UserRole.STUDENT, points = 145, streak = 4),
        User(id = "student-3", name = "Sara", role = UserRole.STUDENT, points = 132, streak = 3),
        User(id = "student-4", name = "Tarik", role = UserRole.STUDENT, points = 115, streak = 2),
        User(id = "student-5", name = "Hana", role = UserRole.STUDENT, points = 105, streak = 2)
    )
}
```

## Firestore Security Rules for Hackathon

Simple development rules:

```text
rules_version = '2';

service cloud.firestore {
  match /databases/{database}/documents {
    match /{document=**} {
      allow read, write: if true;
    }
  }
}
```

Mention in README:

> For hackathon demo speed, Firestore rules are permissive. In production, rules should validate authenticated roles, session ownership, and one response per student.

## Production Hardening Notes

Future improvements:

- Require Firebase Auth
- Validate professor ownership of sessions
- Validate student enrollment
- Use server timestamps
- Use Cloud Functions for trusted point updates
- Rotate QR codes every 30–60 seconds
- Add location proximity if needed
- Add device integrity checks if needed
