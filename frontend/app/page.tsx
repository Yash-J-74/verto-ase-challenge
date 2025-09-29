"use client"; // required for client-side interactivity

import { useRouter } from "next/navigation";

export default function HomePage() {
  const router = useRouter();

  const startQuiz = () => {
    const quizId = 1;
    router.push(`/quiz/${quizId}`);
  };

  return (
    <div className="min-h-screen bg-background flex flex-col items-center justify-center px-4 text-text-primary">
      {/* Title */}
      <h1 className="text-4xl font-bold mb-4 text-primary">
        Welcome to the Quiz!
      </h1>

      {/* Description */}
      <p className="text-lg text-center mb-8 max-w-md">
        Test your knowledge by taking our quick quiz. Click below to get started.
      </p>

      {/* Start Button */}
      <button
        onClick={startQuiz}
        className="bg-primary hover:bg-primary-light text-white font-semibold px-6 py-3 rounded shadow-lg transition duration-300"
      >
        Start Quiz
      </button>
    </div>
  );
}
