# Classroom 2.0 — 2-Minute Demo Script

## Goal

The demo must be tight, clear, and impressive.

Do not explain every feature. Show the professor-student loop.

## Demo Setup

Before presenting:

- Open app on professor device/emulator.
- Open app on student device/emulator if possible.
- Make sure Firebase data is clean or demo mode is ready.
- Have demo QR session ready if needed.
- Use the demo quiz about polymorphism.
- Ensure AI explainer has a polished response.
- Ensure leaderboard has realistic data.

## Demo Script

### 0:00–0:15 — Problem

Say:

> Classrooms still use manual attendance and passive lectures. Professors often do not know who is present or who actually understood the lesson until it is too late.

Show:

- App opening
- Role selection screen

### 0:15–0:30 — Solution

Say:

> We built Classroom 2.0, a native Android app that gives professors QR attendance, live quizzes, AI explanations, and real-time learning insights.

Show:

- Select Professor
- Professor dashboard

### 0:30–0:45 — QR Attendance

Say:

> First, the professor starts attendance. The app creates a session-specific QR code that students can scan in seconds.

Show:

- Tap Start Attendance
- QR code screen
- Active timer
- Empty live attendance state

### 0:45–1:00 — Student Check-In

Say:

> On the student side, scanning the QR instantly marks attendance, prevents duplicates, and rewards participation.

Show:

- Switch to Student
- Tap Scan Attendance
- Scan QR or tap Demo Scan
- Show success screen with points

### 1:00–1:15 — Professor Live Update

Say:

> The professor immediately sees who joined, so attendance is no longer a manual process.

Show:

- Professor attendance screen
- Present count updated
- Student visible in list

### 1:15–1:30 — Live Quiz

Say:

> Next, the professor launches a quick live quiz to check whether the class understood the topic.

Show:

- Professor starts quiz
- Use demo question:
  - “What does polymorphism mean in programming?”
- Student answers
- Professor sees results

### 1:30–1:45 — Insight Dashboard

Say:

> Instead of just showing raw answers, Classroom 2.0 turns quiz data into teaching insight.

Show:

- Correct percentage
- Most missed answer
- Recommended explanation
- Suggested follow-up

### 1:45–1:55 — Extra Features

Say:

> We also added an AI concept explainer and gamification, so students can learn difficult concepts and stay motivated.

Show quickly:

- AI Explainer
- Points / leaderboard

### 1:55–2:00 — Closing

Say:

> Classroom 2.0 turns every class into a real-time, interactive learning experience.

## Backup Demo Script If Something Breaks

If QR scanner fails:

Say:

> For demo reliability, we also built a Demo Scan fallback that uses the same attendance validation logic.

Then tap Demo Scan.

If Firebase live update is slow:

Say:

> The data is stored and synced through Firebase. I’ll refresh the screen to show the saved attendance.

If AI API is not connected:

Say:

> The AI explainer is currently implemented with reliable local educational templates for the hackathon demo, and it is designed to connect to a real AI API next.

If camera permission fails:

Use Demo Scan.

## What Judges Should Remember

They should remember:

1. QR attendance works.
2. Live quiz works.
3. Insight dashboard makes the product smarter.
4. AI explainer adds creativity.
5. Points make students care.
6. UI looks polished.
7. Architecture is clean.
