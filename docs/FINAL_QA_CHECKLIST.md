# Classroom 2.0 — Final Demo QA Checklist

Walk this before submitting. Anything unchecked is a blocker.

## Build

- [ ] `./gradlew assembleDebug` passes.
- [ ] App launches on emulator/device.
- [ ] No crash on onboarding.
- [ ] No broken icons/resources.
- [ ] No missing permissions.

## One-Device Demo

- [ ] Professor role opens.
- [ ] Student role opens.
- [ ] Role switcher works from both dashboards.
- [ ] Demo Mode works without Firebase.
- [ ] No internet required for core demo.

## Attendance

- [ ] Professor starts attendance session.
- [ ] QR displays clearly inside the premium 32dp card.
- [ ] Countdown timer chip is visible and updates.
- [ ] Demo Scan works.
- [ ] Paste payload works.
- [ ] Student receives the animated success confirmation.
- [ ] Points are awarded.
- [ ] Duplicate attendance is blocked with friendly copy.
- [ ] Expired QR is blocked with friendly copy.
- [ ] Professor present count animates as students check in.
- [ ] Attendance history shows the seed sessions plus newly ended ones.

## Quiz

- [ ] Professor creates a quiz.
- [ ] "Use demo question" preloads the polymorphism quiz.
- [ ] Empty fields show the friendly validation banner.
- [ ] Student sees the active quiz with one-answer-only chip.
- [ ] Student can select an answer.
- [ ] Student can submit.
- [ ] Duplicate answer is blocked with friendly copy.
- [ ] Professor sees the response count.
- [ ] Professor sees the correct percentage in the green hero.
- [ ] Results bars animate to their final widths.
- [ ] Correct option is highlighted in green with the "Correct" chip.
- [ ] Insight dashboard shows recommendation.

## AI Explainer

- [ ] "Like I'm 12" mode renders the analogy + plain definition + when-to-use template.
- [ ] "Example" mode renders example + why-it-matters + common-mistake.
- [ ] "3 bullets" mode renders three bullets.
- [ ] "Mini quiz" mode renders Q + 3 options + answer.
- [ ] Empty input shows the friendly nudge.
- [ ] Loading shimmer card appears for ~550ms.
- [ ] Result card has the sparkle avatar + mode label + copy button.

## Gamification

- [ ] Points update after attendance (+15).
- [ ] Points update after quiz participation (+5).
- [ ] Correct answer bonus works (+15 total).
- [ ] First Check-In badge appears after first scan.
- [ ] Quiz Starter badge appears after first answer.
- [ ] Perfect Answer + Quick Thinker badges appear after first correct answer.
- [ ] Streak Builder badge appears at 3-class streak.
- [ ] Leaderboard is never empty.
- [ ] Podium card highlights current student.
- [ ] Class ranking rows stagger in.

## Design

- [ ] Onboarding gradient + floating mini cards render.
- [ ] Professor dashboard hero + action cards look polished.
- [ ] Student dashboard hero shows animated points + streak + progress bar.
- [ ] QR screen has the lock-icon anti-cheat microcopy.
- [ ] Success screen has the three-stage animated entrance.
- [ ] Quiz results screen has animated bars.
- [ ] Insight dashboard has the AI suggestion card.
- [ ] AI screen result card looks like a chat reply.
- [ ] Leaderboard podium renders.
- [ ] Dark mode is not broken (system dark theme).
- [ ] No raw error messages anywhere.
- [ ] No blank screens.

## README & Docs

- [ ] Problem is clear.
- [ ] Solution is clear.
- [ ] Required features listed.
- [ ] Extra features listed.
- [ ] Tech stack listed.
- [ ] Demo mode explained with comparison table.
- [ ] 12-step demo flow included.
- [ ] Judging criteria mapping included.
- [ ] Screenshot grid renders (or placeholders linked).
- [ ] `docs/PITCH_SCRIPT.md` exists and is rehearsed.
- [ ] `docs/FINAL_QA_CHECKLIST.md` (this file) is all checked.

## Final Pitch

- [ ] Demo is under 2 minutes.
- [ ] No step requires typing a long value (the paste-payload affordance is a backup, not the main path).
- [ ] No step depends on perfect camera behavior (Demo Scan is the primary path).
- [ ] Presenter knows the exact tap path by heart.
- [ ] Closing sentence is memorized: "Classroom 2.0 replaces passive classrooms with interactive, measurable, AI-assisted learning."

## Final Submission Standard

The app is ready only when it feels:

- Functional
- Beautiful
- Smooth
- Reliable
- Easy to demo
- Easy to understand
- **Better than a normal class project.**
