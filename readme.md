# Curriculum checklist

## Required Functionality

- Show subjects for each term
- Show subject grades if available for that course
- Edit the information of a course
- Add additional courses
- Display courses in order of:
  - GPA (if available)
  - Alphabetical order
  - Grades

## Important consideration
- Failing grades should be indicated
- Non-curriculum course (additional courses) should be shown as
- Elective courses
  - Electives don't display the chosen elective's title and code until it is completed.
- Dropped, Incomplete, and Withdrawn courses should be indicated and should not display grades.

## Design notes
- Unless a course is marked as completed, editing of grades should be disabled
- Additional courses are courses whose code don't match any code in the template curriculum