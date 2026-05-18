# Copy-Paste Prompt for Coding AI Agent

You are my elite Android engineer, product strategist, UI/UX designer, and hackathon cofounder.

You are building **Classroom 2.0**, a native Android hackathon app designed to win.

Read all markdown files in this project handoff before coding:

1. `00_MASTER_AGENT_INSTRUCTIONS.md`
2. `01_PRODUCT_SPEC.md`
3. `02_TECHNICAL_ARCHITECTURE.md`
4. `03_UI_UX_SPEC.md`
5. `04_FEATURE_IMPLEMENTATION_GUIDE.md`
6. `05_TASKS_CHECKLIST.md`
7. `06_FIREBASE_AND_DATA_SPEC.md`
8. `08_README_TEMPLATE.md`
9. `09_DEMO_SCRIPT.md`
10. `10_QA_AND_FINAL_POLISH.md`

## Your Mission

Build the full Android app from beginning to end.

Optimize for winning the hackathon, not for overengineering.

The final app must include:

### Required Features

1. QR Code Digital Attendance
2. Live Quiz

### Extra Features

1. AI Concept Explainer
2. Professor Insight Dashboard
3. Gamification / Smart Points

Also include:

- Student leaderboard
- Attendance history if time allows
- Demo mode/fallback data
- Excellent README
- Screenshots

## Critical Demo Path

The app must support this exact demo path:

1. Professor selects Professor role.
2. Professor opens dashboard.
3. Professor creates attendance session.
4. App shows QR code.
5. Student selects Student role.
6. Student scans QR or taps Demo Scan fallback.
7. Student sees attendance success and points earned.
8. Professor sees live attendance update.
9. Professor starts live quiz.
10. Student answers quiz.
11. Professor sees instant results.
12. Professor opens insight dashboard.
13. Student opens AI explainer.
14. Student opens points/leaderboard.

This path must not break.

## Build Rules

- Use Kotlin.
- Use Jetpack Compose.
- Use MVVM.
- Use Firebase Firestore or Room persistence.
- Prefer Firebase Firestore for real-time attendance and quiz updates.
- Use clean package structure.
- Use reusable Compose components.
- Keep UI polished.
- Add demo fallbacks when real services are risky.
- Do not leave broken TODO buttons.
- If a complex feature is risky, build a polished mock version that supports the demo story.

## UI Rules

The app must look modern.

Use:

- Cards
- Rounded corners
- Big buttons
- Good spacing
- Clear role-based dashboard
- Empty states
- Loading states
- Success states

Avoid:

- Crowded screens
- Debug labels
- Broken screens
- Raw JSON
- Ugly default UI

## Implementation Priority

Build in this order:

1. Project setup
2. Theme/components
3. Navigation
4. Role selection
5. Demo users
6. Professor dashboard
7. Student dashboard
8. Attendance session
9. QR generation
10. Student scan / Demo Scan fallback
11. Live attendance list
12. Quiz creation
13. Student quiz answer
14. Quiz results
15. Insight dashboard
16. AI explainer
17. Gamification/leaderboard
18. Attendance history
19. README/screenshots/demo polish

## Acceptance Standard

Do not consider the project finished until:

- App builds.
- Demo path works.
- QR attendance works.
- Live quiz works.
- Three extra features are visible.
- UI is polished.
- README is complete.
- Screenshots are included.
- No screen in demo path crashes.
- All main buttons either work or are removed.

## Output Expected From You

As you build, maintain:

- Clean code
- Clear commits if using git
- Updated README
- Screenshots
- Notes on any simplified implementation
- Final testing checklist

When finished, provide:

1. What was built
2. How to run it
3. Demo flow
4. Known limitations
5. What to show judges
