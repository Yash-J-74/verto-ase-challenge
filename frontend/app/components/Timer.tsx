"use client";

import { useEffect, useState } from "react";

interface TimerProps {
  initialSeconds: number;
  onTimeUp?: () => void; // callback when timer reaches zero
}

export default function Timer({ initialSeconds, onTimeUp }: TimerProps) {
  const [timeLeft, setTimeLeft] = useState(initialSeconds);

  useEffect(() => {
    if (timeLeft <= 0) {
      onTimeUp?.();
      return;
    }
    const timer = setInterval(() => setTimeLeft(prev => prev - 1), 1000);
    return () => clearInterval(timer);
  }, [timeLeft, onTimeUp]);

  const minutes = Math.floor(timeLeft / 60);
  const seconds = timeLeft % 60;

  return (
    <div className="absolute top-4 right-4 bg-gray-200 text-gray-800 px-3 py-1 rounded font-mono">
      {minutes}:{seconds.toString().padStart(2, "0")}
    </div>
  );
}
