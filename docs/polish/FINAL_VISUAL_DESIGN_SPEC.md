# Classroom 2.0 — Final Visual Design Spec

This file defines the target visual style for the final polished app.

## Product Feel

Classroom 2.0 should feel like a premium modern education SaaS product, not a school assignment.

Keywords:

- Clean
- Calm
- Fast
- Intelligent
- Friendly
- Premium
- Reliable

## Design Principles

1. One primary action per screen.
2. Big readable cards.
3. Clear visual hierarchy.
4. Soft backgrounds, strong accent color.
5. Rounded corners everywhere.
6. Smooth transitions, never flashy.
7. No clutter.
8. No raw technical language in UI.
9. Demo data should make screens feel alive.
10. Every screenshot should look intentional.

## Screen-by-Screen Visual Target

### Onboarding

- Big gradient hero.
- Product name and tagline.
- Two beautiful role cards.
- Floating mini metric cards.
- Should immediately communicate: attendance, quizzes, AI insights.

### Professor Dashboard

- Calm, powerful, professional.
- Hero card with class summary.
- Large action cards.
- Recent insight preview.
- Avoid empty dashboard.

### Student Dashboard

- Friendly and motivating.
- Points card visible near top.
- Streak and badge preview.
- Action cards for scan, quiz, AI.

### QR Attendance

- QR code centered in premium white card.
- Countdown timer visible.
- Present count big and animated.
- Recent check-ins below.
- Session status chip.

### Student Scan Success

- Full-screen success moment.
- Large check icon.
- `You're checked in!`
- `+10 points earned`
- Smooth celebration but not too much.

### Live Quiz

- Question as central focus.
- Options as large tappable cards.
- Selected state obvious.
- Submit button sticky or clearly visible.

### Quiz Results

- Big correct percentage.
- Animated answer bars.
- Participation count.
- Clear correct answer indicator.

### Insight Dashboard

- Should look like the smartest screen.
- Top recommendation card.
- Metrics below.
- Teaching suggestion with sparkle/AI chip.

### AI Explainer

- Clean input.
- Mode chips.
- Result card that feels like an assistant response.
- Copy/save action if possible.

### Leaderboard

- Top 3 visually special.
- Current student highlighted.
- Points and badges visible.

## UI Copy Rules

Use friendly, short text.

Prefer:

- `Start attendance`
- `Live quiz`
- `Ask AI`
- `Insight ready`
- `You're checked in!`
- `Great answer!`
- `Try explaining this before moving on.`

Avoid:

- `Submit payload`
- `Firestore document created`
- `Operation failed`
- `Null response`
- `Invalid object`

## Button Rules

Primary buttons:

- Filled.
- Strong accent.
- Big height, ideally 52dp+.
- Rounded.

Secondary buttons:

- Tonal or outlined.
- Still rounded.

Danger buttons:

- Only for end session/end quiz.
- Do not overuse red.

## Cards

All main cards should have:

- Rounded corners.
- Comfortable padding.
- Clear title.
- Optional icon.
- Short supporting text.

## Motion Rules

Use motion to communicate state changes:

- Success appears.
- Count updates.
- Results bars grow.
- Cards fade/slide in.

Avoid:

- Long delays.
- Bouncy distracting effects.
- Motion on every tiny element.

## Final Visual Test

Take screenshots of every main screen. Put them side-by-side. The app passes only if it looks like one coherent product.
