# Classroom 2.0 — Product Specification

## Product Summary

Classroom 2.0 is a native Android app that modernizes classroom interaction by combining QR attendance, live quizzes, AI concept explanations, teaching insights, and student gamification.

## Main Value Proposition

Professors can understand attendance and comprehension in real time. Students can check in, answer quizzes, get explanations, and stay motivated through points.

## Target Users

### Professor

Needs:

- Fast attendance
- Simple quiz creation
- Clear results
- No complicated setup
- Better student engagement
- Insight into student comprehension

Success moment:

> “In 30 seconds, I know who is present and whether the class understood the topic.”

### Student

Needs:

- Fast check-in
- Easy quiz answers
- Help understanding concepts
- Motivation to participate
- Clean and simple app

Success moment:

> “I scanned, answered, learned, and earned points without friction.”

## App Modes

The app starts with role selection:

1. Professor
2. Student

For hackathon speed, the app may use demo users instead of full signup.

## Professor Features

### Professor Dashboard

Purpose:

Give professor quick access to the main classroom actions.

Cards:

- Create Attendance
- Start Live Quiz
- View Insights
- Leaderboard
- Attendance History

Must show:

- Greeting
- Current class title
- Today’s attendance count
- Latest quiz score
- Engagement score

### QR Attendance

Professor can:

- Create active attendance session
- Generate QR code
- See timer
- See live list of students joining
- End session
- Save attendance

Important behavior:

- QR code must be session-specific.
- QR code includes expiration timestamp.
- Students cannot check in twice for same session.
- Professor sees count update live or near-live.

### Live Quiz

Professor can:

- Create question
- Add answer options
- Mark correct answer
- Start quiz
- See live answer count
- End quiz
- Open insight summary

MVP quiz format:

- One multiple-choice question at a time
- Four options
- One correct answer

### Insight Dashboard

After quiz, professor sees:

- Participation rate
- Correct answer percentage
- Most selected answer
- Most missed answer
- Confusing topic
- Recommended explanation
- Engagement summary

This can use deterministic local logic. Full AI is optional.

### Attendance History

Professor sees past sessions:

- Date
- Class title
- Present count
- Attendance percentage
- Option to open details

### Leaderboard

Professor sees:

- Student rank
- Student name
- Points
- Streak
- Badges

## Student Features

### Student Dashboard

Cards:

- Scan Attendance QR
- Join Live Quiz
- Ask AI Explainer
- My Points
- Leaderboard

Must show:

- Greeting
- Points
- Attendance streak
- Latest badge
- Next action

### Scan Attendance QR

Student can:

- Open camera scanner
- Scan QR code
- Get success confirmation

Fallback:

If camera scanning is not implemented or unreliable, include a polished **Demo Scan** button that accepts the latest active session.

### Attendance Success

Show:

- Success animation or large checkmark
- “You are checked in”
- Points earned
- Current streak

### Join Live Quiz

Student can:

- See active quiz
- Select answer
- Submit once
- See confirmation
- See correct/incorrect if professor allows

### AI Concept Explainer

Student can:

- Enter concept
- Choose explanation mode
- Receive explanation

Modes:

- Explain like I am 12
- Give an example
- Summarize in 3 bullets
- Generate mini quiz

MVP behavior:

Use local template responses if no AI API is configured.

Example:

Input: “Polymorphism”

Output:

> Polymorphism means one thing can take many forms. In programming, the same function name can behave differently depending on the object using it.

### Gamification

Students earn points:

- Attendance: +10
- Quiz participation: +5
- Correct answer: +15
- Daily streak bonus: +5

Badges:

- First Check-In
- Quiz Starter
- Sharp Mind
- Streak Hero
- Top 3

## Must-Have Screens

### Onboarding

- Logo
- Tagline
- Role selector
- Continue button

### Professor Screens

1. Professor Dashboard
2. Create Attendance Session
3. QR Attendance Live
4. Create Quiz
5. Live Quiz Results
6. Insight Dashboard
7. Attendance History
8. Leaderboard

### Student Screens

1. Student Dashboard
2. QR Scanner
3. Attendance Success
4. Active Quiz
5. Quiz Submitted
6. AI Explainer
7. Points and Badges
8. Leaderboard

## MVP Boundaries

Do not build full LMS.

Do not build complex professor/student accounts unless everything else is done.

Do not build multi-school support.

Do not build complex analytics.

Do not build chat.

Focus on the real-time classroom loop.

## Success Criteria

The product is successful if judges can understand the value in under 10 seconds and see the full professor-student interaction in under 2 minutes.
