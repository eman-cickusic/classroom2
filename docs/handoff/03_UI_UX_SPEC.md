# Classroom 2.0 — UI/UX Specification

## Design Goal

The app should feel like a polished modern education SaaS product, not a student prototype.

## Visual Direction

Use:

- Clean dashboard cards
- Rounded corners
- Strong spacing
- Minimal text
- Big clear actions
- Calm background
- Strong accent color
- Modern Material 3 components
- Icons where helpful
- Smooth transitions
- Empty states
- Success states

## Suggested Color Palette

Use a trustworthy education palette.

```kotlin
val ClassroomIndigo = Color(0xFF4F46E5)
val ClassroomBlue = Color(0xFF2563EB)
val ClassroomGreen = Color(0xFF22C55E)
val ClassroomOrange = Color(0xFFF97316)
val ClassroomPurple = Color(0xFF9333EA)
val ClassroomBackground = Color(0xFFF8FAFC)
val ClassroomSurface = Color(0xFFFFFFFF)
val ClassroomTextPrimary = Color(0xFF0F172A)
val ClassroomTextSecondary = Color(0xFF64748B)
```

Dark mode optional:

```kotlin
val DarkBackground = Color(0xFF020617)
val DarkSurface = Color(0xFF0F172A)
val DarkTextPrimary = Color(0xFFF8FAFC)
val DarkTextSecondary = Color(0xFF94A3B8)
```

## Typography

Use Material 3 typography.

Guidelines:

- Screen title: large and bold
- Section heading: medium and bold
- Card title: medium and semibold
- Body text: readable, not too small
- Buttons: clear action verbs

## Spacing

Use consistent spacing:

- Screen horizontal padding: 20dp or 24dp
- Card padding: 16dp or 20dp
- Section gap: 24dp
- Card gap: 12dp or 16dp
- Button height: 52dp or 56dp
- Corner radius: 20dp or 24dp

## Reusable Components

Create these components.

### ClassroomCard

Purpose:

Standard card used across dashboard and content screens.

Properties:

- title
- subtitle
- icon or emoji
- action
- content

### PrimaryActionButton

Purpose:

Main CTA button.

Examples:

- Start Attendance
- Generate QR
- Submit Answer
- Ask AI
- Start Quiz

### StatCard

Purpose:

Display metrics.

Examples:

- Present: 24
- Correct: 82%
- Points: 130
- Streak: 5

### EmptyState

Purpose:

Make empty screens feel intentional.

Examples:

- “No active quiz yet”
- “No students checked in yet”
- “No attendance history yet”

### LoadingState

Purpose:

Show progress while Firebase loads.

### SuccessCheck

Purpose:

Student attendance success and answer submitted screens.

### SectionHeader

Purpose:

Reusable title plus optional action.

## Onboarding Screen

Content:

- Logo or icon
- App name: Classroom 2.0
- Tagline: Modern classroom interaction in seconds
- Role cards:
  - Professor
  - Student
- Continue button

UX:

- Role cards should be large and obvious.
- Selected role should be visually highlighted.
- No login complexity in MVP.

## Professor Dashboard

Top:

- Greeting: “Good morning, Professor”
- Class name: “Computer Science 101”
- Subtitle: “Ready to make today’s class interactive?”

Stats row:

- Present today
- Latest quiz score
- Engagement

Action cards:

1. Start Attendance
2. Start Live Quiz
3. View Insights
4. Leaderboard

Bottom:

- Recent activity

## Professor Attendance Screen

Header:

- “QR Attendance”
- Active timer

Main:

- Large QR code centered
- Session status card
- Present count
- Live student list

Actions:

- End Session
- Copy QR payload / Demo code optional

Empty state:

- “Waiting for students to scan...”

Live list item:

- Student name
- Time checked in
- Success check icon

## Student Scanner Screen

If camera works:

- Full scanner preview
- Top instruction: “Scan your professor’s QR code”

If camera is not ready:

- Polished placeholder
- Demo Scan button
- Manual paste field optional

After scan:

- Validate QR
- Navigate to success screen

## Attendance Success Screen

Use a big success moment.

Content:

- Large checkmark
- “You are checked in”
- “+10 points earned”
- “Current streak: 3 classes”
- Button: Back to Dashboard

This must look satisfying in the demo.

## Create Quiz Screen

Fields:

- Question
- Option A
- Option B
- Option C
- Option D
- Correct answer selector

Actions:

- Start Quiz
- Use Demo Question

Demo question:

> What does polymorphism mean in programming?

Options:

1. A variable changing value
2. One interface having many forms
3. A loop inside another loop
4. Storing data permanently

Correct:

2

## Student Quiz Screen

Content:

- Question
- Four large option cards
- Submit button
- Progress / status

Rules:

- Option card selected state must be obvious.
- Student can submit only once.
- Show confirmation after submit.

## Quiz Results Screen

Professor view:

- Total answers
- Correct percentage
- Bar chart-like result cards
- Most selected answer
- Button: View Teaching Insight

Use simple bars with Compose Box widths.

## Insight Dashboard

This is one of the most important screens for winning.

Show:

- Correct percentage
- Participation rate
- Most missed answer
- Confusing topic
- Recommended explanation
- Suggested follow-up question

Make it feel smart.

Example:

```text
Class Understanding: 62%

Students struggled most with:
"One interface having many forms"

Recommended next step:
Pause for 60 seconds and explain polymorphism using an animal sound example.
```

## AI Explainer Screen

Content:

- Input field: “Enter a concept”
- Mode chips:
  - Like I’m 12
  - Example
  - 3 bullets
  - Mini quiz
- Button: Explain
- Result card

Default demo concept:

> Polymorphism

Result card should look beautiful.

## Points / Badges Screen

Content:

- Points card
- Streak card
- Badges grid
- Leaderboard preview

Badges:

- First Check-In
- Quiz Starter
- Sharp Mind
- Streak Hero

## Leaderboard Screen

List:

1. Student name
2. Points
3. Streak
4. Badge emoji

Highlight current student.

## Empty States

Examples:

### No Attendance

```text
No students yet

Share the QR code and students will appear here in real time.
```

### No Quiz

```text
No live quiz

Start a quick question to check class understanding.
```

### No History

```text
No saved sessions

Your completed attendance sessions will appear here.
```

## Loading States

Avoid plain spinners only.

Use:

- Skeleton card placeholders
- “Loading classroom data...”
- Smooth fade if possible

## Success States

Use:

- Big green check
- Short message
- Points earned
- Next action

## Screenshots Required for README

Capture:

1. Professor dashboard
2. QR attendance screen
3. Student scan success
4. Live quiz screen
5. Quiz results / insight dashboard
6. AI explainer
7. Leaderboard / points

## UI Polish Checklist

- No default ugly screens
- Consistent button styles
- Consistent card styles
- No text cut off
- No tiny tap targets
- No broken navigation
- No empty raw screens
- No placeholder “TODO”
- No debug labels visible
- Demo data looks realistic
