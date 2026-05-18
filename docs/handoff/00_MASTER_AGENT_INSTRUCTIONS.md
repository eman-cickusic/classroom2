# Classroom 2.0 — Master AI Agent Instructions

## Mission

You are building **Classroom 2.0**, a native Android hackathon project designed to win the Classroom 2.0 Hackathon.

Your job is to build a polished, demo-ready Android application from beginning to end.

The final app must feel complete, clean, modern, useful, and impressive in a 2-minute demo.

Do not overbuild. Do not add random features. Every feature must support the core product story:

> Classroom 2.0 helps professors turn any class into an interactive, measurable, and AI-assisted learning experience in seconds.

## Winning Standard

The final project should make judges think:

> “This team understood the assignment, built the required features, added creative value, designed it beautifully, documented it clearly, and can actually ship.”

## Required Hackathon Constraints

The project must use:

- Android Native
- Kotlin
- Jetpack Compose preferred
- Firebase or Room database
- Clean architecture / MVVM
- Clear README
- Demo-ready UI

AI tools may be used, but the app itself must show strong implementation quality.

## Required Features

The app must include:

1. **Digital Attendance With QR Code**
   - Professor generates QR code.
   - Student scans QR code.
   - Student is marked present.
   - Attendance history is stored.

2. **Live Quiz**
   - Professor creates a quick live question.
   - Students answer in real time.
   - Professor sees results instantly or near-instantly.

## Extra Features

The app must include at least three original extra features.

Build these three:

1. **AI Concept Explainer**
2. **Professor Insight Dashboard**
3. **Gamification / Smart Points**

Also include these if time allows:

- Attendance history
- Student leaderboard
- Demo mode with fake data
- Dark mode
- Export attendance report

## Core Product Story

Before Classroom 2.0:

- Attendance is manual.
- Students are passive.
- Professors do not know who understands.
- Quizzes are slow.
- Class data is scattered.

After Classroom 2.0:

- Attendance takes seconds.
- Students participate live.
- Professors instantly see understanding.
- AI explains hard concepts.
- Points motivate students.
- The classroom becomes interactive and measurable.

## Non-Negotiable Demo Flow

The demo must work even if some secondary features are simplified.

Build and protect this path first:

1. Professor opens app.
2. Professor creates an attendance session.
3. QR code appears.
4. Student scans QR code or uses demo scan fallback.
5. Student gets success confirmation.
6. Professor sees attendance update.
7. Professor starts a live quiz.
8. Student answers quiz.
9. Professor sees live results.
10. Professor opens insight dashboard.
11. Student opens AI explainer and points screen.

If anything breaks, this path must still work.

## Build Philosophy

Always prioritize:

1. Polished demo path
2. Beautiful UI
3. Reliable required features
4. Clear architecture
5. Good README
6. Then bonus features

Do not prioritize:

- Complex authentication
- Complex class management
- Advanced admin tools
- Unnecessary settings
- Large features that are not visible in the demo
- Perfect backend security beyond reasonable hackathon anti-cheating

## Technology Stack

Use:

- Kotlin
- Jetpack Compose
- Navigation Compose
- Firebase Authentication, preferably anonymous auth for demo
- Firebase Firestore
- Firestore snapshot listeners for live updates
- MVVM
- Repository pattern
- Coil or built-in image handling if needed
- QR generation library
- CameraX / ML Kit barcode scanning if possible
- Demo scan fallback if camera setup takes too long

## App Identity

Name:

**Classroom 2.0**

Tagline:

**Modern classroom interaction in seconds.**

One-sentence pitch:

> Classroom 2.0 helps professors turn any class into an interactive, measurable, and AI-assisted learning experience in seconds.

Sharper pitch:

> Classroom 2.0 replaces boring attendance sheets and passive lectures with QR attendance, live quizzes, AI explanations, and real-time teaching insights.

## Roles

The app has two roles:

### Professor

Professor can:

- Create attendance session
- Generate QR code
- View live attendance
- Start live quiz
- View quiz results
- View insight dashboard
- View leaderboard
- View attendance history

### Student

Student can:

- Scan attendance QR
- Join live quiz
- Submit answer
- Use AI concept explainer
- View points
- View badges
- View leaderboard
- View attendance streak

## UX Rules

The app must feel like modern education SaaS.

Use:

- Clean cards
- Rounded corners
- Strong spacing
- Large buttons
- Clear typography
- Calm background
- One strong accent color
- Green for success
- Orange or purple for energy
- Smooth transitions
- Beautiful empty states
- Loading states
- Success states

Avoid:

- Crowded screens
- Tiny buttons
- Too many colors
- Confusing labels
- Deep hidden features
- Raw JSON shown to users
- Developer-looking UI
- Unfinished screens

## File Organization

Use this package structure:

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

## Implementation Rule

Every screen must be connected to the demo story.

If a screen is not ready, create a polished placeholder instead of leaving it broken.

A polished mock is better than a broken real feature for hackathon judging, especially for AI responses and analytics.

## Done Means Done

The project is done only when:

- App builds successfully.
- Demo flow works from beginning to end.
- Required features are implemented.
- At least three extra features are visible.
- README is complete.
- Screenshots are added.
- Pitch script is ready.
- No obvious crash exists in the demo path.
- UI looks consistent.
- Code is organized.
- Firebase or Room persistence is used.
