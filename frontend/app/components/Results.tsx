"use client";

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
  results: AnswerResult[];
}

export default function Results({ response }: { response: SubmitResponse }) {
  return (
    <div className="w-full max-w-2xl space-y-4">
      {response.results.map((r) => (
        <div
          key={r.questionId}
          className={`p-4 rounded shadow ${
            r.correct ? "bg-green-100" : "bg-red-100"
          }`}
        >
          <p className="font-medium">{r.questionText}</p>
          <p>
            Your answer: <span className="font-semibold">{r.chosenOption}</span>
          </p>
          {!r.correct && (
            <p>
              Correct answer: <span className="font-semibold">{r.correctOption}</span>
            </p>
          )}
        </div>
      ))}
    </div>
  );
}
