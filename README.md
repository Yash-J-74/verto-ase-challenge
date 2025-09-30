# verto-ase-challenge

## ASE Challenge for Verto: Full-Stack Online Quiz Application

A full-stack quiz application built with Spring Boot (backend), SQLite (database), and Next.js + Tailwind (frontend).

Users can take a quiz, submit answers, and view their score and detailed results.

## Getting Started

### Requirements

[Docker](https://www.docker.com/)
 & [Docker Compose](https://docs.docker.com/compose/)
 installed

* Port 8080 available for the backend
* Port 3000 available for the frontend

### Run the App

Clone the repo

``` bash
git clone https://github.com/Yash-J-74/verto-ase-challenge.git
cd verto-ase-challenge
```

Make sure you have Docker and Docker Compose installed.

``` bash
.\start.ps1    # Windows
./start.sh     # Linux/Mac
```

This will:

* Build and start backend on port 8080
* Build and start frontend on port 3000
* Automatically open <http://localhost:3000>

## Project Structure

``` bash
verto-ase-challenge/
│
├── backend/         # Spring Boot + SQLite
│   ├── src/
│   ├── pom.xml
│   └── Dockerfile
│
├── frontend/        # Next.js + Tailwind
│   ├── app/
│   ├── package.json
│   └── Dockerfile
│
├── docker-compose.yml
├── README.md
├── start.ps1               # Startup script (Windows)
└── start.sh                # Startup script (Linux/macOS)
```

## Database

* SQLite used for simplicity.
* Schema includes:
  * Quiz → holds quiz metadata
  * Question → belongs to a quiz
  * Option → belongs to a question, one marked as correct
  * Attempt → represents a user’s quiz attempt (anonymous, no login)
  * AttemptAnswer → stores answers chosen in an attempt

On startup, a sample quiz is inserted with multiple questions via DataInitializer.

## Features Implemented

### Core Features

* Fetch quiz questions (without correct answers)
* Submit quiz answers → score is calculated and saved
* Return results (score, chosen options, correctness, and correct answers)
* Anonymous quiz (no login required)

### Frontend

* Home page to start the quiz
* Quiz page (one question at a time, navigation with Next/Previous)
* Submit button to send all answers
* Results page with score and detailed feedback

### Bonus Features

* Timer shown at the top-right of quiz page
* Auto-submission when time runs out
* Results page highlights correct and incorrect answers

## Assumptions

* Single quiz for now — but schema supports multiple quizzes in the future.
* Anonymous users — no authentication, attempts are saved but not tied to users.
* Each question has exactly one correct option.
* 4 options per question (by convention, not enforced in schema).
* Scoring logic → 1 point per correct answer, total equals number of questions answered.
* Timer → quiz auto-submits when countdown hits zero, with "timeUp" flag in results.

## Tests

* Backend uses JUnit + Spring Boot Integration Test
* Tests run against an in-memory SQLite DB
* You can run tests locally with:

``` bash
./mvnw test
```
