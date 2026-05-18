# Classroom 2.0 — Complete Build Checklist

Use this checklist from beginning to end. Do not skip the demo-critical items.

## Legend

- `[ ]` Not started
- `[~]` In progress
- `[x]` Done

---

# 1. Project Setup

- [ ] Create Android native project
- [ ] Set package to `com.classroom2.app`
- [ ] Enable Kotlin
- [ ] Enable Jetpack Compose
- [ ] Add Material 3
- [ ] Add Navigation Compose
- [ ] Add Firebase BOM
- [ ] Add Firebase Firestore
- [ ] Add Firebase Auth
- [ ] Add QR generation dependency
- [ ] Add CameraX / ML Kit if implementing real QR scan
- [ ] Create base package structure
- [ ] Create app theme
- [ ] Create navigation routes
- [ ] App launches successfully

# 2. Design System

- [ ] Define colors
- [ ] Define typography
- [ ] Define spacing constants
- [ ] Build `ClassroomCard`
- [ ] Build `PrimaryActionButton`
- [ ] Build `StatCard`
- [ ] Build `SectionHeader`
- [ ] Build `EmptyState`
- [ ] Build `LoadingState`
- [ ] Build `SuccessCheck`
- [ ] Build `OptionCard`
- [ ] Build `BadgeCard`
- [ ] Build `LeaderboardRow`
- [ ] Ensure all screens use shared components

# 3. Navigation

- [ ] Create `Routes.kt`
- [ ] Create `AppNavGraph.kt`
- [ ] Add onboarding route
- [ ] Add professor dashboard route
- [ ] Add student dashboard route
- [ ] Add professor attendance route
- [ ] Add student scanner route
- [ ] Add attendance success route
- [ ] Add create quiz route
- [ ] Add student quiz route
- [ ] Add quiz results route
- [ ] Add insight dashboard route
- [ ] Add AI explainer route
- [ ] Add leaderboard route
- [ ] Add attendance history route
- [ ] Test all navigation paths

# 4. Data Models

- [ ] Create `UserRole`
- [ ] Create `User`
- [ ] Create `ClassSession`
- [ ] Create `AttendanceRecord`
- [ ] Create `AttendanceQrPayload`
- [ ] Create `Quiz`
- [ ] Create `QuizAnswer`
- [ ] Create `InsightSummary`
- [ ] Create `Badge`
- [ ] Create `LeaderboardEntry`
- [ ] Create `ExplanationMode`
- [ ] Create `AppResult`

# 5. Firebase / Persistence

- [ ] Add `google-services.json`
- [ ] Configure Firebase project
- [ ] Initialize Firestore
- [ ] Initialize Firebase Auth or demo auth
- [ ] Create `FirestorePaths`
- [ ] Create `FirebaseService`
- [ ] Test Firestore write
- [ ] Test Firestore read
- [ ] Test snapshot listener
- [ ] Add demo fallback if Firebase unavailable

# 6. Demo Data

- [ ] Create demo professor
- [ ] Create demo student
- [ ] Create demo classmates
- [ ] Create demo class
- [ ] Create demo quiz
- [ ] Create demo leaderboard
- [ ] Create demo badges
- [ ] Add “Use Demo Question” button
- [ ] Add “Demo Scan” fallback button
- [ ] Ensure demo flow works without fresh data

# 7. Onboarding

- [ ] Build role selection screen
- [ ] Add app logo/icon
- [ ] Add app tagline
- [ ] Add professor role card
- [ ] Add student role card
- [ ] Add selected state
- [ ] Add continue button
- [ ] Navigate based on role
- [ ] Polish screen spacing

# 8. Professor Dashboard

- [ ] Add greeting
- [ ] Add class title
- [ ] Add stats row
- [ ] Add Start Attendance card
- [ ] Add Start Quiz card
- [ ] Add Insights card
- [ ] Add Leaderboard card
- [ ] Add recent activity
- [ ] Connect navigation actions
- [ ] Add empty/loading state if needed
- [ ] Polish UI

# 9. Student Dashboard

- [ ] Add greeting
- [ ] Add points card
- [ ] Add streak card
- [ ] Add Scan Attendance card
- [ ] Add Join Quiz card
- [ ] Add AI Explainer card
- [ ] Add Leaderboard card
- [ ] Connect navigation actions
- [ ] Polish UI

# 10. Attendance Session

- [ ] Create AttendanceRepository
- [ ] Create AttendanceViewModel
- [ ] Implement `createAttendanceSession`
- [ ] Implement `observeActiveSession`
- [ ] Implement `observeAttendance`
- [ ] Implement `endSession`
- [ ] Save sessions to Firestore
- [ ] Create professor attendance screen
- [ ] Add start session action
- [ ] Add end session action
- [ ] Add active timer
- [ ] Add present count
- [ ] Add live student list
- [ ] Add empty state

# 11. QR Generation

- [ ] Create QR payload model
- [ ] Serialize payload to JSON
- [ ] Add expiration timestamp
- [ ] Generate QR bitmap
- [ ] Display QR in Compose
- [ ] Ensure QR is large enough
- [ ] Add copy/debug payload only if useful
- [ ] Test generated QR payload

# 12. Student QR Scan

- [ ] Create scanner screen
- [ ] Add camera permission flow if using camera
- [ ] Implement QR scanning if possible
- [ ] Add Demo Scan fallback
- [ ] Add manual QR payload paste fallback if useful
- [ ] Parse QR payload
- [ ] Validate expiration
- [ ] Validate active session
- [ ] Prevent duplicate attendance
- [ ] Mark attendance
- [ ] Navigate to success screen
- [ ] Show error message for invalid QR

# 13. Attendance Success

- [ ] Add big checkmark
- [ ] Add success text
- [ ] Show points earned
- [ ] Show streak
- [ ] Add back to dashboard button
- [ ] Polish animation or visual state
- [ ] Test after attendance submission

# 14. Live Attendance

- [ ] Observe attendance collection
- [ ] Update professor present count
- [ ] Show student names
- [ ] Show timestamps
- [ ] Show empty state before students
- [ ] Confirm live update after student scan
- [ ] Test duplicate scan behavior

# 15. Quiz Creation

- [ ] Create QuizRepository
- [ ] Create QuizViewModel
- [ ] Build create quiz screen
- [ ] Add question field
- [ ] Add four answer fields
- [ ] Add correct answer selector
- [ ] Add Use Demo Question button
- [ ] Implement create quiz
- [ ] Save quiz to Firestore
- [ ] Set quiz active
- [ ] Navigate to results screen after starting

# 16. Student Quiz

- [ ] Observe active quiz
- [ ] Build student quiz screen
- [ ] Show no quiz empty state
- [ ] Show question
- [ ] Show option cards
- [ ] Add selected option state
- [ ] Add submit button
- [ ] Implement submit answer
- [ ] Prevent duplicate answer
- [ ] Award participation points
- [ ] Award correct answer points
- [ ] Show submitted confirmation

# 17. Quiz Results

- [ ] Observe quiz answers
- [ ] Calculate total responses
- [ ] Calculate correct percentage
- [ ] Calculate answer distribution
- [ ] Show visual bars
- [ ] Show most selected answer
- [ ] Add End Quiz button
- [ ] Add View Insight button
- [ ] Test live update

# 18. Insight Dashboard

- [ ] Create InsightRepository
- [ ] Create insight calculation logic
- [ ] Calculate participation rate
- [ ] Calculate correct percentage
- [ ] Calculate most missed answer
- [ ] Generate confusing topic
- [ ] Generate recommended explanation
- [ ] Generate follow-up question
- [ ] Build insight screen
- [ ] Make screen visually impressive
- [ ] Test with demo quiz

# 19. AI Concept Explainer

- [ ] Create AIExplainerViewModel
- [ ] Build input field
- [ ] Build mode chips
- [ ] Add Explain button
- [ ] Add result card
- [ ] Implement local template generator
- [ ] Add special output for “polymorphism”
- [ ] Add empty input validation
- [ ] Optional: connect real AI API
- [ ] Polish result UI

# 20. Gamification

- [ ] Create GamificationRepository
- [ ] Implement point rules
- [ ] Attendance +10
- [ ] Quiz participation +5
- [ ] Correct answer +15
- [ ] Streak bonus +5
- [ ] Create badges
- [ ] Assign First Check-In badge
- [ ] Assign Quiz Starter badge
- [ ] Assign Sharp Mind badge
- [ ] Assign Streak Hero badge
- [ ] Update leaderboard
- [ ] Show points on student dashboard

# 21. Leaderboard

- [ ] Build leaderboard screen
- [ ] Show rank
- [ ] Show student name
- [ ] Show points
- [ ] Show streak
- [ ] Show badge emoji
- [ ] Highlight current student
- [ ] Add demo leaderboard
- [ ] Polish UI

# 22. Attendance History

- [ ] Build attendance history screen
- [ ] Save completed sessions
- [ ] Show session date
- [ ] Show class title
- [ ] Show present count
- [ ] Show attendance percentage
- [ ] Add empty state
- [ ] Optional: export report

# 23. Error, Loading, Empty States

- [ ] Add loading state to dashboards
- [ ] Add loading state to attendance
- [ ] Add loading state to quiz
- [ ] Add error snackbar or card
- [ ] Add empty active quiz state
- [ ] Add empty attendance state
- [ ] Add empty history state
- [ ] No screen should look broken when data is empty

# 24. Anti-Cheating / Validation

- [ ] QR includes session ID
- [ ] QR includes professor ID
- [ ] QR includes class ID
- [ ] QR includes expiration timestamp
- [ ] Expired QR rejected
- [ ] Inactive session rejected
- [ ] Duplicate attendance rejected
- [ ] Duplicate quiz answer rejected
- [ ] Student receives clear error message

# 25. Polish

- [ ] Add app icon if possible
- [ ] Add smooth screen transitions if easy
- [ ] Improve card spacing
- [ ] Improve button consistency
- [ ] Remove debug text
- [ ] Remove TODO labels
- [ ] Check dark mode or force clean light mode
- [ ] Check small screen layout
- [ ] Check emulator layout
- [ ] Check real device if available

# 26. README

- [ ] Add project title
- [ ] Add tagline
- [ ] Add problem
- [ ] Add solution
- [ ] Add feature list
- [ ] Add tech stack
- [ ] Add architecture
- [ ] Add screenshots
- [ ] Add how to run
- [ ] Add demo flow
- [ ] Add AI tools used
- [ ] Add future improvements
- [ ] Add team info if needed

# 27. Screenshots

Capture these:

- [ ] Professor dashboard
- [ ] QR attendance screen
- [ ] Student scan success
- [ ] Live quiz screen
- [ ] Quiz results
- [ ] Insight dashboard
- [ ] AI explainer
- [ ] Leaderboard / points

# 28. Demo Prep

- [ ] Prepare 2-minute script
- [ ] Practice exact click path
- [ ] Reset demo data
- [ ] Ensure professor flow works
- [ ] Ensure student flow works
- [ ] Ensure fallback demo scan works
- [ ] Ensure quiz demo works
- [ ] Ensure AI explainer has output
- [ ] Ensure leaderboard has data
- [ ] Ensure app does not crash
- [ ] Prepare backup screenshots/video

# 29. Final Acceptance

The app is finished only when:

- [ ] App builds successfully
- [ ] Required QR attendance works
- [ ] Required live quiz works
- [ ] AI explainer is visible
- [ ] Insight dashboard is visible
- [ ] Gamification is visible
- [ ] README is complete
- [ ] Screenshots are included
- [ ] Demo script is ready
- [ ] 2-minute demo path is smooth
- [ ] No broken screen appears during demo
