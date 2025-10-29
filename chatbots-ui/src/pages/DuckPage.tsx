import React, {useState} from "react";

const DuckPage: React.FC = () => {
    const [message, setMessage] = useState("");
    const [pending, setPending] = useState(false);
    const [messages, setMessages] = useState([
        {role: "assistant", text: "Hello, how can I help you today?", time: now()},
    ]);

    function now() {
        return new Date().toLocaleTimeString([], {hour: "2-digit", minute: "2-digit"});
    }

    async function submit() {
        const text = message.trim();
        if (!text || pending) return;

        setMessages((prev) => [...prev, {role: "user", text, time: now()}]);
        setMessage("");
        setPending(true);

        try {
            const res = await fetch('/api/duck/debug', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ conversationId: Math.random(), requestMessage: text }),
            })

            const json = await res.json();
            const reply = json?.responseMessage || "...";
            setMessages((prev) => [...prev, {role: "assistant", text: reply, time: now()}]);
        } catch (e) {
            setMessages((prev) => [
                ...prev,
                {role: "assistant", text: "Sorry, something went wrong.", time: now()},
            ]);
        } finally {
            setPending(false);
        }
    }

    return (
        <div className="p-8 flex flex-col h-full bg-[#1E2430] text-white rounded-lg overflow-hidden">
            {/* Chat area */}
            <div className="flex-1 p-4 space-y-4 overflow-y-auto">
                {messages.map((m, i) => (
                    <div key={i} className={`flex ${m.role === "user" ? "justify-end" : "justify-start"}`}>
                        <div
                            className={`p-3 rounded-xl w-fit max-w-md ${
                                m.role === "user" ? "bg-purple-600" : "bg-[#1e1b4b]"
                            }`}
                        >
                            <p className="whitespace-pre-wrap">{m.text}</p>
                            <span
                                className={`text-xs block mt-1 ${
                                    m.role === "user" ? "text-gray-300 text-right" : "text-gray-400"
                                }`}
                            >
                {m.time}
              </span>
                        </div>
                    </div>
                ))}

                {pending && (
                    <div className="flex justify-start">
                        <div className="bg-[#1e1b4b] p-3 rounded-lg w-fit max-w-md">...</div>
                    </div>
                )}
            </div>

            {/* Input area */}
            <div className="p-4 flex items-center gap-2">
                <input
                    value={message}
                    onChange={(e) => setMessage(e.target.value)}
                    type="text"
                    placeholder="Ask me about anything..."
                    disabled={pending}
                    className="flex-1 bg-[#0b1120] text-gray-300 placeholder-gray-500 rounded-xl px-4 py-3 text-sm focus:outline-none border border-gray-800 focus:border-purple-600 transition disabled:opacity-60"
                />
                <button
                    onClick={submit}
                    disabled={pending || !message.trim()}
                    className="bg-purple-600 hover:bg-purple-700 p-3 rounded-xl flex items-center justify-center disabled:opacity-50"
                >
                    {/* simple send icon */}
                    <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5 text-white" viewBox="0 0 24 24"
                         fill="none" stroke="currentColor">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M5 12h14M12 5l7 7-7 7"/>
                    </svg>
                </button>
            </div>
        </div>
    );
};

export default DuckPage;
