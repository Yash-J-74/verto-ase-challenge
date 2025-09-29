"use client";

import { useEffect, useState } from "react";
import { useParams, useRouter } from "next/navigation";
import QuestionCard from "@/app/components/QuestionCard";
import Timer from "@/app/components/Timer";
import { getQuestions, submitQuizAnswers } from "@/lib/api";

interface Option {
    id: number;
    text: string;
}

interface Question {
    id: number;
    text: string;
    options: Option[];
}

interface Answer {
    questionId: number;
    optionId: number;
}

export default function QuizPage() {
    const [questions, setQuestions] = useState<Question[]>([]);
    const [answers, setAnswers] = useState<Answer[]>([]);
    const [currentIndex, setCurrentIndex] = useState(0);
    const [isSubmitted, setIsSubmitted] = useState(false)

    const params = useParams();
    const router = useRouter();
    const quizId = Number(params.id);

    // Fetch questions on mount
    useEffect(() => {
        if (!quizId) return;

        const fetchQuestions = async () => {
            try {
                const data = await getQuestions(quizId);
                setQuestions(data);
                setAnswers(
                    data.map((q: Question) => ({ questionId: q.id, optionId: 0 }))
                );
            } catch (error) {
                console.error("Failed to fetch questions:", error);
            }
        };

        fetchQuestions();
    }, [quizId]);

    const handleSelect = (optionId: number) => {
        setAnswers((prev) =>
            prev.map((a, i) =>
                i === currentIndex ? { ...a, optionId } : a
            )
        );
    };

    const handleNext = () => setCurrentIndex((prev) => Math.min(prev + 1, questions.length - 1));
    const handlePrevious = () => setCurrentIndex((prev) => Math.max(prev - 1, 0));

    const handleSubmit = async (auto = false) => {
        if (isSubmitted) return;
        setIsSubmitted(true);
        try {
            const validAnswers = answers.filter((a) => a.optionId !== 0);
            const result = await submitQuizAnswers(quizId, validAnswers);
            router.push(`/quiz/${quizId}/result?attemptId=${result.attemptId}&timeUp=${auto}`);
        } catch (err) {
            console.error(err);
            alert("Failed to submit quiz");
        }
    };

    if (!questions.length) {
        return <div className="min-h-screen flex items-center justify-center">Loading quiz...</div>;
    }

    const currentQuestion = questions[currentIndex];

    return (
        <div className="min-h-screen flex flex-col items-center justify-center px-4 space-y-6">
            <Timer initialSeconds={10} onTimeUp={() => handleSubmit(true)} />

            <QuestionCard
                question={currentQuestion}
                selectedOptionId={answers[currentIndex].optionId}
                onSelect={handleSelect}
            />

            <div className="flex w-full max-w-2xl justify-between">
                <button
                    onClick={handlePrevious}
                    disabled={currentIndex === 0}
                    className="px-4 py-2 rounded bg-gray-200 disabled:opacity-50"
                >
                    Previous
                </button>
                {currentIndex < questions.length - 1 ? (
                    <button
                        onClick={handleNext}
                        className="px-4 py-2 rounded bg-primary text-white"
                    >
                        Next
                    </button>
                ) : (
                    <button
                        onClick={() => handleSubmit(false)}
                        className="px-4 py-2 rounded bg-green-600 text-white"
                    >
                        Submit
                    </button>
                )}
            </div>

            <p>
                Question {currentIndex + 1} of {questions.length}
            </p>
        </div>
    );
}
