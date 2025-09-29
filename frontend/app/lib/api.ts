export const API_URL = process.env.NEXT_PUBLIC_API_URL || "http://localhost:8080";

export async function getQuestions(quizId: number) {
  const res = await fetch(`${API_URL}/api/quiz/${quizId}/questions`);
  return res.json();
}

export async function submitAnswers(quizId: number, answers: any) {
  const res = await fetch(`${API_URL}/api/quiz/${quizId}/submit`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(answers),
  });
  return res.json();
}