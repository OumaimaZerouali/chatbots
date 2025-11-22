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
        <div className="p-8 flex flex-col h-full bg-black text-white overflow-hidden">
            <h2 className="text-3xl font-bold mb-6 text-yellow-400">🦆 Duck Debugger</h2>

            {/* Chat area */}
            <div className="flex-1 p-4 space-y-4 overflow-y-auto bg-zinc-900 rounded-lg border-2 border-zinc-800">
                {messages.map((m, i) => (
                    <div key={i} className={`flex ${m.role === "user" ? "justify-end" : "justify-start"}`}>
                        <div
                            className={`p-3 rounded-lg w-fit max-w-md ${
                                m.role === "user" ? "bg-yellow-400 text-black" : "bg-zinc-800 text-white border-2 border-zinc-700"
                            }`}
                        >
                            <p className="whitespace-pre-wrap break-words">{m.text}</p>
                            <span
                                className={`text-xs block mt-1 ${
                                    m.role === "user" ? "text-black/70 text-right" : "text-gray-500"
                                }`}
                            >
                {m.time}
              </span>
                        </div>
                    </div>
                ))}

                {pending && (
                    <div className="flex justify-start">
                        <div className="bg-zinc-800 border-2 border-zinc-700 p-3 rounded-lg w-fit max-w-md">...</div>
                    </div>
                )}
            </div>

            {/* Input area */}
            <div className="mt-4 flex items-center gap-2">
                <input
                    value={message}
                    onChange={(e) => setMessage(e.target.value)}
                    onKeyDown={(e) => e.key === "Enter" && submit()}
                    type="text"
                    placeholder="Ask me about anything..."
                    disabled={pending}
                    className="flex-1 bg-zinc-900 text-gray-300 placeholder-gray-500 rounded-lg px-4 py-3 text-sm focus:outline-none border-2 border-zinc-800 focus:border-yellow-400 transition disabled:opacity-60"
                />
                <button
                    onClick={submit}
                    disabled={pending || !message.trim()}
                    className="bg-yellow-400 hover:bg-yellow-300 text-black p-3 rounded-lg flex items-center justify-center disabled:opacity-50 disabled:bg-zinc-700"
                >
                    {/* simple send icon */}
                    <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" viewBox="0 0 24 24"
                         fill="none" stroke="currentColor">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M5 12h14M12 5l7 7-7 7"/>
                    </svg>
                </button>
            </div>
        </div>
    );
};

export default DuckPage;
