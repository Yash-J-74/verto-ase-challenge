"use client";

import { useEffect, useState } from "react";
import { useSearchParams, useParams, useRouter } from "next/navigation";
import { getAttemptResults } from "@/lib/api";
import Results from "@/app/components/Results";

interface AnswerResult {
  questionId: number;
  questionText: string;
  chosenOption: string;
  correctOption: string;
  correct: boolean;
}

interface SubmitResponse {
  score: number;
  total: number;
  attemptId: number;
  results: AnswerResult[];
}

export default function ResultPage() {
  const [response, setResponse] = useState<SubmitResponse | null>(null);
  const searchParams = useSearchParams();
  const params = useParams();
  const router = useRouter();

  const quizId = Number(params.id);
  const attemptId = Number(searchParams.get("attemptId"));
  const timeUp = searchParams.get("timeUp") === "true";

  useEffect(() => {
    if (!attemptId) return;
    getAttemptResults(quizId, attemptId)
      .then(setResponse)
      .catch(console.error);
  }, [quizId, attemptId]);

  if (!response) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <p>Loading results...</p>
      </div>
    );
  }

  return (
    <div className="min-h-screen flex flex-col items-center justify-center px-4">
      {timeUp && (
        <p className="mb-4 text-red-600 font-semibold text-lg">
          Time's up! Your quiz was automatically submitted.
        </p>
      )}

      <h1 className="text-3xl font-bold mb-4 text-primary">Quiz Results</h1>
      <p className="text-lg mb-6">
        You scored <span className="text-primary font-bold">{response.score}</span> out of {response.total}
      </p>

      <Results response={response} />

      <button
        onClick={() => router.push("/")}
        className="mt-8 bg-primary hover:bg-primary-light text-white font-semibold px-6 py-3 rounded shadow-lg transition duration-300"
      >
        Back to Home
      </button>
    </div>
  );
}
