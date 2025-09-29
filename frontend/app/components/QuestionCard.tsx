"use client";

interface Option {
  id: number;
  text: string;
}

interface Question {
  id: number;
  text: string;
  options: Option[];
}

interface QuestionCardProps {
  question: Question;
  selectedOptionId: number;
  onSelect: (optionId: number) => void;
}

export default function QuestionCard({ question, selectedOptionId, onSelect }: QuestionCardProps) {
  return (
    <div className="p-4 rounded shadow bg-white w-full max-w-2xl">
      <p className="font-medium mb-4">{question.text}</p>
      <div className="space-y-2">
        {question.options.map((option) => (
          <button
            key={option.id}
            onClick={() => onSelect(option.id)}
            className={`w-full text-left px-4 py-2 rounded border ${
              selectedOptionId === option.id ? "border-primary bg-primary-light text-white" : "border-gray-300"
            }`}
          >
            {option.text}
          </button>
        ))}
      </div>
    </div>
  );
}
