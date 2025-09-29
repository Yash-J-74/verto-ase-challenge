export const API_URL = process.env.NEXT_PUBLIC_API_URL || "http://localhost:8080";

export async function getQuestions(quizId: number) {
  const res = await fetch(`${API_URL}/api/quiz/${quizId}/questions`);
  return res.json();
}

export async function submitQuizAnswers(quizId: number, answers: { questionId: number; optionId: number }[]) {
  const res = await fetch(`${API_URL}/api/quiz/${quizId}/submit`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ answers }),
  });

  if (!res.ok) {
    throw new Error(`Failed to submit answers: ${res.statusText}`);
  }

  return res.json();
}

export async function getAttemptResults(quizId: number, attemptId: number) {
  const res = await fetch(
    `${API_URL}/api/quiz/${quizId}/attempt/${attemptId}`
  );
  if (!res.ok) throw new Error("Failed to fetch attempt results");
  return res.json();
}