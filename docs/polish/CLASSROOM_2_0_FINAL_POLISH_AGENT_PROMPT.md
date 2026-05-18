# Classroom 2.0 — Final Polish Agent Prompt

You are now upgrading an already functional Classroom 2.0 Android hackathon app into a beautiful, smooth, judge-winning product.

The app already has the required and extra features implemented. Your mission is not to add random scope. Your mission is to make everything feel premium, reliable, polished, emotionally impressive, and demo-perfect.

## Current State

The app reportedly includes:

- QR attendance with ZXing QR generation, CameraX / ML Kit scanning, demo scan fallback, and paste field.
- Live quiz with one-answer-per-student guard.
- AI Concept Explainer with templated responses.
- Professor Insight Dashboard with deterministic recommendation engine.
- Gamification with points, badges, and live leaderboard.
- Attendance history.
- Role switcher in both dashboards.
- Seed leaderboard data.
- Kotlin 2.0.21, Jetpack Compose, Material 3, MVVM, Navigation Compose.
- Firestore plus in-memory dual backend behind ServiceLocator.
- Placeholder google-services.json that allows zero-config in-memory demo mode.

Known limitations:

- Build has not been verified on emulator/device.
- Gradle wrapper jar may be missing.
- google-services.json is placeholder.

## Goal

Make the project feel like the most polished submission in the hackathon.

The final app should make judges think:

> This is not just a working project. This feels like a real product.

## Non-Negotiable Priorities

1. Do not break existing working features.
2. Do not add large risky features unless all polish is done.
3. Prioritize visible improvements that judges can feel in 2 minutes.
4. Every main screen must look intentional, modern, and consistent.
5. The demo path must be flawless.
6. Empty states, loading states, success states, and error states must be beautiful.
7. The README must make the project look complete and professional.
8. Screenshots must show the best visual moments.
9. If there are compile errors, fix them before anything else.
10. Final output must be on main with clear commits.

---

# Phase 0 — Verify Build First

Before making changes, verify the project compiles.

## Tasks

- Open the project in Android Studio or run Gradle locally.
- If `gradlew` is missing or broken, regenerate wrapper:

```bash
gradle wrapper
```

- Run:

```bash
./gradlew clean
./gradlew assembleDebug
```

If the environment uses Windows:

```bash
gradlew.bat clean
gradlew.bat assembleDebug
```

## Fix Immediately

Fix any:

- Kotlin compiler errors
- Compose API errors
- Material 3 import errors
- Navigation route errors
- Missing dependency errors
- Duplicate class/package errors
- Firebase plugin errors caused by placeholder config
- Permission declaration issues
- CameraX lifecycle issues

## Acceptance Criteria

- Debug APK builds successfully.
- App launches.
- No crash on first screen.
- Navigation between Professor and Student dashboards works.

Commit:

```text
chore: verify and stabilize build
```

---

# Phase 1 — Create a Premium Design System

Build or refine a single design system used everywhere.

## Design Target

Modern education SaaS. Calm, clean, premium, confident.

Think:

- Duolingo energy, but more professional.
- Linear-style spacing and visual calm.
- Google Classroom clarity, but more beautiful.
- Modern fintech cards, but for education.

## Visual Personality

- Trustworthy
- Smooth
- Friendly
- Intelligent
- Fast
- Modern

## Color System

Create a coherent palette in the theme files.

Suggested light palette:

```kotlin
val ClassroomBlue = Color(0xFF3157FF)
val ClassroomIndigo = Color(0xFF243B8A)
val ClassroomPurple = Color(0xFF7C3AED)
val ClassroomGreen = Color(0xFF16A34A)
val ClassroomOrange = Color(0xFFF59E0B)
val ClassroomRed = Color(0xFFEF4444)
val ClassroomBackground = Color(0xFFF7F8FC)
val ClassroomSurface = Color(0xFFFFFFFF)
val ClassroomSurfaceSoft = Color(0xFFF0F3FA)
val ClassroomTextPrimary = Color(0xFF111827)
val ClassroomTextSecondary = Color(0xFF6B7280)
```

Suggested dark palette:

```kotlin
val ClassroomDarkBackground = Color(0xFF080B12)
val ClassroomDarkSurface = Color(0xFF111827)
val ClassroomDarkSurfaceSoft = Color(0xFF1F2937)
val ClassroomDarkTextPrimary = Color(0xFFF9FAFB)
val ClassroomDarkTextSecondary = Color(0xFF9CA3AF)
```

## Typography

Use Material 3 typography, but tune it:

- Big dashboard titles.
- Clear section headers.
- Friendly body copy.
- No tiny unreadable text.
- Use consistent font weights.

Recommended hierarchy:

- Hero title: 28sp / Bold
- Screen title: 24sp / Bold
- Card title: 18sp / SemiBold
- Body: 15sp / Regular
- Caption: 12sp / Medium

## Shapes

Use consistent rounded corners:

- Large cards: 28dp
- Normal cards: 22dp
- Buttons: 18dp
- Chips: 50 percent / pill shape
- QR container: 32dp

## Spacing

Use consistent spacing tokens:

```kotlin
object ClassroomSpacing {
    val xs = 4.dp
    val sm = 8.dp
    val md = 16.dp
    val lg = 24.dp
    val xl = 32.dp
    val xxl = 48.dp
}
```

## Components To Create / Polish

Create reusable components if they do not already exist:

- `ClassroomScaffold`
- `GradientHeader`
- `ActionCard`
- `MetricCard`
- `PrimaryActionButton`
- `SecondaryActionButton`
- `StatusChip`
- `EmptyStateCard`
- `LoadingCard`
- `SuccessState`
- `DashboardHeroCard`
- `LeaderboardRow`
- `BadgePill`
- `AnimatedCounter`
- `SectionHeader`

## Acceptance Criteria

- Main screens use shared components.
- No screen looks like default Material sample UI.
- Colors, corners, and spacing are consistent.
- Light and dark modes both look polished.

Commit:

```text
style: introduce premium classroom design system
```

---

# Phase 2 — Upgrade Onboarding and Role Selection

The first screen must immediately sell the product.

## Onboarding Goal

Judges should understand the product in 5 seconds.

## Screen Requirements

Create a beautiful landing screen with:

- App name: Classroom 2.0
- Tagline: `Turn every class into a live learning experience.`
- Short subtitle: `QR attendance, live quizzes, AI explanations, and real-time teaching insights.`
- Two large role cards:
  - Professor
  - Student
- Small demo hint: `Choose a role to explore the live classroom flow.`

## Visual Ideas

- Gradient hero background.
- Floating mini cards showing:
  - `92% attendance captured`
  - `Live quiz active`
  - `AI insight ready`
- Smooth fade-in animation.
- Press animation on role cards.

## Acceptance Criteria

- First impression is strong.
- Professor and Student flows are obvious.
- No clutter.
- Works on small and large phones.

Commit:

```text
style: polish onboarding and role selection
```

---

# Phase 3 — Perfect Professor Dashboard

This is one of the most important screenshot screens.

## Goal

Professor should feel powerful and in control.

## Layout

Top hero card:

- Greeting: `Good morning, Professor`
- Main message: `Ready to make today's class interactive?`
- Sub-metrics:
  - Active students
  - Average quiz score
  - Attendance streak / engagement

Primary actions:

- Start Attendance
- Create Live Quiz
- View Insights
- Leaderboard

Recent activity section:

- Last attendance session
- Latest quiz result
- Insight recommendation

## UX Details

- Make buttons large and thumb-friendly.
- Use icons for each action.
- Add subtle motion when dashboard appears.
- Use real or seeded data so dashboard never looks empty.

## Acceptance Criteria

- Looks excellent in screenshot.
- User understands what to tap immediately.
- No empty boring dashboard.

Commit:

```text
style: upgrade professor dashboard experience
```

---

# Phase 4 — Perfect Student Dashboard

The student dashboard should feel fun and motivating.

## Layout

Top hero card:

- Greeting: `Hi, Student`
- Message: `You're on a 4-class learning streak.`
- Points total.
- Badge preview.

Primary actions:

- Scan Attendance QR
- Join Live Quiz
- Ask AI Explainer
- View Leaderboard

Progress area:

- Points progress bar.
- Attendance streak.
- Recent badge earned.

## UX Details

- Make it more energetic than Professor dashboard.
- Use gamification visuals without making it childish.
- Add subtle celebration on points/badges.

## Acceptance Criteria

- Student value is obvious.
- Points and badges are visible immediately.
- The screen feels alive.

Commit:

```text
style: upgrade student dashboard experience
```

---

# Phase 5 — Make QR Attendance Feel Magical

QR attendance is required and must feel fast, trustworthy, and modern.

## Professor QR Screen

Must include:

- Large QR code in a premium card.
- Session title.
- Countdown timer.
- Live present count.
- List of recently checked-in students.
- End session button.
- Anti-cheat microcopy: `Session-specific QR expires automatically.`

## Visual Polish

- QR card should look clean and centered.
- Countdown should be visually obvious.
- Live attendance count should animate.
- New student check-ins should appear smoothly.

## Student Scan Screen

Must include:

- Camera scanner.
- Demo Scan fallback button.
- Paste QR payload field for reliability.
- Clear instruction text.
- Permission handling state.

## Success Screen

After successful attendance:

- Full success state.
- Check icon animation if possible.
- Text: `You're checked in!`
- Points earned: `+10 attendance points`
- Button back to dashboard.

## Error States

Beautiful messages for:

- Expired QR
- Already checked in
- Invalid QR
- Session ended
- Camera permission missing

## Acceptance Criteria

- Attendance can be demoed without camera using fallback.
- Real camera path works if possible.
- Success state looks screenshot-worthy.
- Professor live count updates.

Commit:

```text
style: polish QR attendance flow
```

---

# Phase 6 — Make Live Quiz Feel Real-Time and Clear

The quiz must be simple but exciting.

## Professor Create Quiz Screen

Fields:

- Question
- Four options
- Correct answer selector
- Start quiz button

Improve with:

- Clean form cards.
- Character guidance.
- Example placeholder question.
- Validation messages.

## Student Quiz Screen

Must include:

- Active question card.
- Four large option cards.
- Selected option state.
- Submit button.
- Confirmation after submit.

## Professor Results Screen

Must include:

- Total responses.
- Correct percentage.
- Answer distribution bars.
- Most missed answer.
- Live updating results.
- End quiz button.

## Visual Polish

- Animate bars when results load/update.
- Use clear correct/incorrect colors.
- Make results readable from a distance.

## Acceptance Criteria

- Quiz flow works end-to-end.
- One answer per student guard remains intact.
- Results screen looks impressive.
- Results are easy to explain in demo.

Commit:

```text
style: polish live quiz experience
```

---

# Phase 7 — Make Insight Dashboard Judge-Winning

This should be the smartest-looking screen.

## Goal

Show that Classroom 2.0 does not only collect answers. It helps professors teach better.

## Required Insight Cards

- Participation rate
- Correct answer percentage
- Most missed answer
- Confusing topic
- Suggested follow-up explanation
- Engagement summary

## Suggested Copy

Main insight:

> Most students understood the core idea, but 38% missed the application question. Try giving one concrete example before moving on.

Follow-up action:

> Recommended next step: explain polymorphism with a real-world analogy, then ask a 30-second check-in question.

## Visual Ideas

- Big intelligence card at top.
- Metrics in beautiful cards.
- Recommendation card with AI sparkle icon.
- `Teaching Insight Ready` chip.

## Acceptance Criteria

- This screen looks like a product differentiator.
- It clearly communicates value beyond requirements.
- It is included in README screenshots.

Commit:

```text
style: elevate professor insight dashboard
```

---

# Phase 8 — Make AI Explainer Feel Useful and Safe

The AI Explainer can be templated, but it should feel intentional.

## Student AI Screen

Fields:

- Concept input.
- Mode selector chips:
  - Simple
  - Example
  - 3 Bullets
  - Mini Quiz
- Generate button.
- Result card.
- Save / Copy button if easy.

## Improve Templates

For each mode, make output structured and helpful.

### Simple Mode

```text
Think of [concept] like [analogy]. In simple terms, it means [simple definition]. You use it when [context].
```

### Example Mode

```text
Example: [clear classroom example]
Why it matters: [one sentence]
Common mistake: [one sentence]
```

### 3 Bullets Mode

```text
- [Main idea]
- [When to use it]
- [What to remember]
```

### Mini Quiz Mode

```text
Quick check:
Question: [question]
A. [option]
B. [option]
C. [option]
Answer: [answer]
```

## UI Polish

- Result card should feel like an AI answer.
- Add loading shimmer or short animation.
- Add friendly empty state.

## Acceptance Criteria

- Easy to demo in 10 seconds.
- Output looks useful, not random.
- No API dependency required for demo.

Commit:

```text
style: polish AI concept explainer
```

---

# Phase 9 — Make Gamification Feel Rewarding

Gamification is one of the visible creativity features.

## Student Points Screen

Show:

- Total points.
- Progress to next badge.
- Attendance streak.
- Badges earned.
- Leaderboard position.

## Leaderboard Screen

Show:

- Top 5 students.
- Current user highlighted.
- Points.
- Badges / streak indicator.

## Badges

Use at least 5 badges:

1. First Check-In
2. Quiz Starter
3. Quick Thinker
4. Perfect Answer
5. Streak Builder

## Visual Polish

- Use medal icons or emoji if no custom icons.
- Use subtle rank styling.
- Avoid empty screen by using seed data.

## Acceptance Criteria

- Leaderboard is always populated.
- Current user progress feels rewarding.
- Good screenshot screen.

Commit:

```text
style: polish gamification and leaderboard
```

---

# Phase 10 — Add Motion and Microinteractions

Do this only after the build is stable.

## Add Motion To

- Screen entrance transitions.
- Dashboard cards appearing.
- Attendance success state.
- Quiz result bars.
- Leaderboard rows.
- AI result appearing.
- Button press states.

## Compose APIs To Use

- `AnimatedVisibility`
- `animateFloatAsState`
- `animateIntAsState`
- `updateTransition`
- `Crossfade`
- `Modifier.animateContentSize()`

## Rules

- Motion should be subtle.
- Do not make the app feel slow.
- Do not add complex animation libraries.
- Avoid animations that can crash or glitch during demo.

## Acceptance Criteria

- App feels smoother.
- No janky distracting animation.
- Demo path feels modern.

Commit:

```text
style: add smooth product microinteractions
```

---

# Phase 11 — Perfect Empty, Loading, and Error States

Judges notice polish in edge states.

## Required States

For all major screens, add:

- Loading state.
- Empty state.
- Error state.
- Success state where relevant.

## Empty State Examples

Attendance:

> No students yet. Keep the QR visible and check-ins will appear live.

Quiz:

> No responses yet. Students will appear here as soon as they answer.

AI Explainer:

> Type a concept and choose a mode to get a simple explanation.

Leaderboard:

> Points will appear after students attend and answer quizzes.

## Error State Examples

QR:

> This QR code expired. Ask your professor to generate a fresh one.

Quiz:

> This quiz has ended. Wait for the next live question.

Network:

> Live sync is unavailable, but demo mode is still ready.

## Acceptance Criteria

- No raw exception text shown to users.
- No blank screens.
- All error messages are human-friendly.

Commit:

```text
ux: improve loading empty and error states
```

---

# Phase 12 — Demo Mode Must Be Bulletproof

Hackathon demos fail when they rely too much on perfect setup.

## Requirements

The app must be demoable without:

- Real Firebase config.
- Real multiple devices.
- Camera working.
- Internet connection.

## Demo Mode Behavior

If Firebase config is placeholder:

- Use in-memory backend.
- Show a subtle `Demo Mode` chip.
- Seed sample students.
- Seed leaderboard.
- Allow Demo Scan.
- Allow switching Professor/Student role on same device.

## Demo Flow Must Work On One Device

1. Choose Professor.
2. Start attendance.
3. Copy/show payload or use Demo Scan route.
4. Switch to Student.
5. Demo Scan / paste QR.
6. Attendance success.
7. Switch Professor.
8. See attendance update.
9. Start quiz.
10. Switch Student.
11. Answer quiz.
12. Switch Professor.
13. See results and insight.

## Acceptance Criteria

- One-device demo works.
- No external dependency blocks judging.
- Demo Mode is clearly documented.

Commit:

```text
feat: harden one-device demo mode
```

---

# Phase 13 — Final README Upgrade

The README must look like a winning submission page.

## README Must Include

- Title and tagline.
- Short product GIF/screenshot placeholder.
- Problem.
- Solution.
- Feature table.
- Demo flow.
- Screenshots gallery.
- Tech stack.
- Architecture.
- Firebase/demo mode explanation.
- How to run.
- Project structure.
- AI tools used.
- Judging criteria mapping.
- Future improvements.

## Add This Judging Criteria Section

```markdown
## Why Classroom 2.0 Stands Out

| Judging Area | How We Address It |
|---|---|
| Required Features | QR attendance and live quiz are implemented end-to-end. |
| Creativity | AI explanations, professor insights, gamification, badges, and leaderboard. |
| Technical Quality | Kotlin, Jetpack Compose, MVVM, repositories, Firebase/in-memory backend abstraction. |
| UX/UI | Polished role-based dashboards, modern cards, smooth states, clear demo flow. |
| Completeness | Demo mode, attendance history, seeded data, README, and screenshot-ready screens. |
```

## Screenshots

Create `/screenshots` and include placeholder references even if images are not present yet:

```markdown
## Screenshots

| Professor Dashboard | QR Attendance | Live Quiz |
|---|---|---|
| ![](screenshots/professor_dashboard.png) | ![](screenshots/qr_attendance.png) | ![](screenshots/live_quiz.png) |

| Insight Dashboard | AI Explainer | Leaderboard |
|---|---|---|
| ![](screenshots/insight_dashboard.png) | ![](screenshots/ai_explainer.png) | ![](screenshots/leaderboard.png) |
```

## Acceptance Criteria

- README looks professional even before screenshots are added.
- Demo mode instructions are clear.
- Judges can understand the app quickly.

Commit:

```text
docs: upgrade README for final judging
```

---

# Phase 14 — Screenshot and Presentation Polish

## Screenshots To Capture

Capture these from emulator/device:

1. `screenshots/professor_dashboard.png`
2. `screenshots/qr_attendance.png`
3. `screenshots/student_success.png`
4. `screenshots/live_quiz.png`
5. `screenshots/quiz_results.png`
6. `screenshots/insight_dashboard.png`
7. `screenshots/ai_explainer.png`
8. `screenshots/leaderboard.png`

## Screenshot Rules

- Use seeded/demo data.
- Use light mode unless dark mode looks better.
- Avoid screenshots with empty states unless intentionally showing polish.
- Make sure status bar does not look distracting.
- Use consistent device size.

## Optional

Create a short screen recording of the 2-minute demo path.

Commit:

```text
docs: add final screenshot gallery
```

---

# Phase 15 — Final QA Checklist

Before declaring done, test every item.

## Build

- [ ] `./gradlew assembleDebug` passes.
- [ ] App launches.
- [ ] No crash on first open.
- [ ] No missing resource errors.
- [ ] No broken imports.

## Navigation

- [ ] Role selection works.
- [ ] Professor dashboard opens.
- [ ] Student dashboard opens.
- [ ] Role switcher works.
- [ ] Back navigation is sane.

## Attendance

- [ ] Professor can start session.
- [ ] QR appears.
- [ ] Countdown appears.
- [ ] Demo Scan works.
- [ ] Paste payload works.
- [ ] Student gets success confirmation.
- [ ] Duplicate attendance is blocked.
- [ ] Expired QR is blocked.
- [ ] Professor sees attendance update.
- [ ] Attendance history records session.

## Quiz

- [ ] Professor can create quiz.
- [ ] Validation blocks empty quiz.
- [ ] Student can answer.
- [ ] Duplicate answer is blocked.
- [ ] Professor sees results.
- [ ] Correct percentage is accurate.
- [ ] Results bars render correctly.
- [ ] Insight dashboard uses quiz result data.

## AI Explainer

- [ ] Empty input validation works.
- [ ] Simple mode works.
- [ ] Example mode works.
- [ ] 3 bullets mode works.
- [ ] Mini quiz mode works.
- [ ] Loading/result state looks good.

## Gamification

- [ ] Attendance gives points.
- [ ] Quiz participation gives points.
- [ ] Correct answer gives points.
- [ ] Badges appear.
- [ ] Leaderboard updates or shows seeded data.
- [ ] Current user is visible.

## Design

- [ ] No ugly default UI remains.
- [ ] Main screens have consistent spacing.
- [ ] Buttons are consistent.
- [ ] Cards are consistent.
- [ ] Typography is readable.
- [ ] Dark mode does not look broken.
- [ ] Loading/empty/error states are polished.

## Demo

- [ ] One-device demo path works.
- [ ] Demo takes under 2 minutes.
- [ ] No typing-heavy step slows down demo.
- [ ] Screenshots are added.
- [ ] README is updated.

---

# Final 2-Minute Pitch Script

Use this exact script or improve slightly.

## 0:00 - 0:15 Problem

Classrooms still rely on manual attendance and passive lectures. Professors often do not know who is actually present or who understood the lesson until it is too late.

## 0:15 - 0:30 Solution

We built Classroom 2.0, a native Android app that turns any class into a live learning experience with QR attendance, live quizzes, AI explanations, and real-time teaching insights.

## 0:30 - 1:15 Demo

Here is the professor dashboard. In one tap, the professor starts attendance and gets a session-specific QR code. The student scans it and is checked in instantly. The professor sees attendance update live.

Now the professor launches a live quiz. The student answers from their phone, and the professor immediately sees the class result distribution.

## 1:15 - 1:40 Extras

The app goes beyond the required features. Students can use the AI Concept Explainer to understand difficult topics. Professors get an Insight Dashboard that recommends what to explain next. Students also earn points and badges, creating motivation and engagement.

## 1:40 - 1:55 Technical Quality

Classroom 2.0 is built with Kotlin, Jetpack Compose, Material 3, MVVM architecture, Navigation Compose, and a Firebase/in-memory backend abstraction, so it works both as a real app and as a reliable hackathon demo.

## 1:55 - 2:00 Closing

Classroom 2.0 replaces passive classrooms with interactive, measurable, AI-assisted learning.

---

# Final Definition of Done

The project is done only when all of these are true:

- App builds successfully.
- Required features work end-to-end.
- Three extra features are visible and demo-ready.
- One-device demo mode works.
- Main screens look beautiful.
- Empty/error/success states are polished.
- README is professional.
- Screenshots are included.
- 2-minute demo script is ready.
- There are no obvious crashes in the demo path.

Do not stop at functional. Stop only when it feels impressive.
