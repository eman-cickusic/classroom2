# Classroom 2.0 — QA and Final Polish Checklist

## Goal

Before submission, test the app like a judge will see it.

The demo path must be smooth, fast, and reliable.

## Build Checks

- [ ] App builds without errors
- [ ] App launches without crash
- [ ] No missing Firebase config error in demo build
- [ ] No obvious Gradle issue
- [ ] No screen has broken imports
- [ ] No screen has unresolved TODO code
- [ ] Release or debug APK can be installed

## Navigation Checks

- [ ] Role selection opens
- [ ] Professor role navigates correctly
- [ ] Student role navigates correctly
- [ ] Back navigation does not break demo
- [ ] Dashboard buttons work
- [ ] No main button leads to blank screen
- [ ] No crash when switching roles

## Professor Flow QA

### Dashboard

- [ ] Dashboard looks polished
- [ ] Main actions are visible
- [ ] Stats render properly
- [ ] No raw placeholder text

### Attendance

- [ ] Professor can create session
- [ ] QR code appears
- [ ] Timer appears
- [ ] Present count appears
- [ ] Empty state appears before students
- [ ] Student appears after check-in
- [ ] End session works

### Quiz

- [ ] Professor can create quiz
- [ ] Demo question button works
- [ ] Correct answer selector works
- [ ] Quiz starts
- [ ] Results screen opens
- [ ] Answers appear
- [ ] Correct percentage is calculated
- [ ] Results visualization looks good

### Insights

- [ ] Insight dashboard opens
- [ ] Participation rate shown
- [ ] Correct percentage shown
- [ ] Most missed answer shown
- [ ] Recommended explanation shown
- [ ] Screen looks impressive

## Student Flow QA

### Dashboard

- [ ] Student dashboard looks polished
- [ ] Points visible
- [ ] Streak visible
- [ ] Scan attendance button works
- [ ] Join quiz button works
- [ ] AI explainer button works
- [ ] Leaderboard button works

### Attendance

- [ ] Scanner screen opens
- [ ] Demo Scan works
- [ ] Invalid QR shows error
- [ ] Expired QR shows error
- [ ] Duplicate scan shows already checked in
- [ ] Success screen appears
- [ ] Points earned shown

### Quiz

- [ ] Student can see active quiz
- [ ] Student can select option
- [ ] Student can submit
- [ ] Student cannot submit twice
- [ ] Confirmation appears
- [ ] Points update if implemented

### AI Explainer

- [ ] Empty input handled
- [ ] Concept input works
- [ ] Mode chips work
- [ ] Explanation appears
- [ ] Polymorphism example looks good
- [ ] Result card looks polished

### Gamification

- [ ] Points card visible
- [ ] Badges visible
- [ ] Leaderboard visible
- [ ] Current student highlighted if possible

## Data QA

- [ ] Users saved or demo-loaded
- [ ] Sessions saved
- [ ] Attendance saved
- [ ] Quiz saved
- [ ] Answers saved
- [ ] Leaderboard saved or demo-loaded
- [ ] Duplicate attendance blocked
- [ ] Duplicate quiz answer blocked

## UI Polish QA

- [ ] Consistent colors
- [ ] Consistent button style
- [ ] Consistent card style
- [ ] Consistent spacing
- [ ] Text readable
- [ ] No overlapping content
- [ ] No clipped text
- [ ] Works on emulator
- [ ] Works on common phone size
- [ ] Looks good in screenshots
- [ ] No debug logs visible in UI
- [ ] No “Lorem ipsum”
- [ ] No unfinished TODO labels

## README QA

- [ ] README title added
- [ ] Tagline added
- [ ] Problem explained
- [ ] Solution explained
- [ ] Required features listed
- [ ] Extra features listed
- [ ] Tech stack listed
- [ ] Architecture listed
- [ ] Firebase setup explained
- [ ] Screenshots added
- [ ] Demo flow added
- [ ] Future improvements added
- [ ] AI tools used section added

## Screenshot QA

Capture these:

- [ ] Professor dashboard
- [ ] QR attendance
- [ ] Student success
- [ ] Live quiz
- [ ] Quiz results
- [ ] Insight dashboard
- [ ] AI explainer
- [ ] Leaderboard

Screenshots should be:

- Clear
- Cropped properly
- No debug UI
- No broken data
- Visually consistent

## Demo QA

Practice full demo three times.

### Run 1

- [ ] Start timer
- [ ] Complete demo under 2 minutes
- [ ] Note slow parts

### Run 2

- [ ] Improve transitions
- [ ] Remove unnecessary explanation
- [ ] Confirm no crashes

### Run 3

- [ ] Final practice
- [ ] Use exact script
- [ ] Confirm confidence

## Demo Data Reset

Before final demo:

- [ ] Clear old sessions if needed
- [ ] Create fresh active session
- [ ] Use predictable demo student
- [ ] Use demo quiz
- [ ] Ensure leaderboard has data
- [ ] Ensure AI explainer has default concept

## Final Submission Checklist

- [ ] Code pushed
- [ ] README pushed
- [ ] Screenshots pushed
- [ ] App builds
- [ ] Demo path tested
- [ ] Pitch script ready
- [ ] Backup screenshots ready
- [ ] Team knows who clicks what
- [ ] Team knows what to say if scanner or Firebase is slow

## Definition of Done

The app is done when:

- The required features work.
- The three extra features are visible.
- The UI is polished.
- The README is strong.
- The 2-minute demo is smooth.
- A judge can understand the value immediately.
