import React, { useState, useEffect } from "react";

interface Bot {
    id: string;
    name: string;
    systemPrompt: string;
}

const MyBotsPage: React.FC = () => {
    const [bots, setBots] = useState<Bot[]>([]);
    const [selectedBot, setSelectedBot] = useState<Bot | null>(null);
    const [message, setMessage] = useState("");
    const [pending, setPending] = useState(false);
    const [loading, setLoading] = useState(true);
    const [messages, setMessages] = useState<Array<{ role: string; text: string; time: string }>>([]);
    const [conversationId] = useState(() => `conv-${Date.now()}`);

    function now() {
        return new Date().toLocaleTimeString([], { hour: "2-digit", minute: "2-digit" });
    }

    useEffect(() => {
        fetchBots();
    }, []);

    async function fetchBots() {
        setLoading(true);
        try {
            const res = await fetch('/api/bots');
            const data = await res.json();
            setBots(data || []);
        } catch (e) {
            console.error("Failed to fetch bots", e);
        } finally {
            setLoading(false);
        }
    }

    function selectBot(bot: Bot) {
        setSelectedBot(bot);
        setMessages([
            { role: "assistant", text: `Hi! I'm ${bot.name}. How can I help you?`, time: now() }
        ]);
    }

    async function submit() {
        const text = message.trim();
        if (!text || pending || !selectedBot) return;

        setMessages((prev) => [...prev, { role: "user", text, time: now() }]);
        setMessage("");
        setPending(true);

        try {
            const res = await fetch('/api/chat', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    botId: parseInt(selectedBot.id),
                    conversationId: conversationId,
                    question: text
                }),
            });

            const json = await res.json();
            const reply = json?.message || "...";
            setMessages((prev) => [...prev, { role: "assistant", text: reply, time: now() }]);
        } catch (e) {
            setMessages((prev) => [
                ...prev,
                { role: "assistant", text: "Sorry, something went wrong.", time: now() },
            ]);
        } finally {
            setPending(false);
        }
    }

    return (
        <div className="p-8 flex h-full bg-[#1E2430] text-white rounded-lg overflow-hidden gap-4">
            {/* Bots List Sidebar */}
            <div className="w-80 bg-[#0b1120] rounded-xl p-4 overflow-y-auto border border-gray-800">
                <div className="flex items-center justify-between mb-4">
                    <h3 className="text-xl font-semibold">🤖 My Bots</h3>
                    <button
                        onClick={fetchBots}
                        className="text-purple-400 hover:text-purple-300 text-sm"
                        title="Refresh"
                    >
                        ↻
                    </button>
                </div>

                {loading ? (
                    <div className="text-gray-400 text-sm text-center py-8">Loading bots...</div>
                ) : bots.length === 0 ? (
                    <div className="text-gray-400 text-sm text-center py-8">
                        No bots yet. Create one in the Bot Factory!
                    </div>
                ) : (
                    <div className="space-y-2">
                        {bots.map((bot) => (
                            <div
                                key={bot.id}
                                onClick={() => selectBot(bot)}
                                className={`p-3 rounded-lg cursor-pointer transition ${
                                    selectedBot?.id === bot.id
                                        ? "bg-purple-600"
                                        : "bg-[#1E2430] hover:bg-[#2a3142]"
                                }`}
                            >
                                <div className="font-medium">{bot.name}</div>
                                <div className="text-xs text-gray-400 mt-1 truncate">
                                    ID: {bot.id}
                                </div>
                            </div>
                        ))}
                    </div>
                )}
            </div>

            {/* Chat Area */}
            <div className="flex-1 flex flex-col overflow-hidden">
                {!selectedBot ? (
                    <div className="flex-1 flex items-center justify-center bg-zinc-900 rounded-lg border-2 border-zinc-800">
                        <div className="text-center text-gray-400">
                            <div className="text-4xl mb-4">💬</div>
                            <p>Select a bot from the list to start chatting</p>
                        </div>
                    </div>
                ) : (
                    <>
                        {/* Chat Header */}
                        <div className="bg-zinc-900 rounded-t-lg p-4 border-2 border-zinc-800 border-b-0">
                            <h2 className="text-xl font-bold text-yellow-400">{selectedBot.name}</h2>
                            <p className="text-xs text-gray-400 mt-1">Bot ID: {selectedBot.id}</p>
                        </div>

                        {/* Messages */}
                        <div className="flex-1 bg-zinc-900 p-4 space-y-4 overflow-y-auto border-l-2 border-r-2 border-zinc-800">
                            {messages.map((m, i) => (
                                <div key={i} className={`flex ${m.role === "user" ? "justify-end" : "justify-start"}`}>
                                    <div
                                        className={`p-3 rounded-lg w-fit max-w-md ${
                                            m.role === "user" ? "bg-yellow-400 text-black" : "bg-zinc-800 border-2 border-zinc-700"
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

                        {/* Input Area */}
                        <div className="bg-zinc-900 rounded-b-lg p-4 flex items-center gap-2 border-2 border-zinc-800 border-t-0">
                            <input
                                value={message}
                                onChange={(e) => setMessage(e.target.value)}
                                onKeyPress={(e) => e.key === 'Enter' && submit()}
                                type="text"
                                placeholder="Type your message..."
                                disabled={pending}
                                className="flex-1 bg-black text-gray-300 placeholder-gray-500 rounded-lg px-4 py-3 text-sm focus:outline-none border-2 border-zinc-800 focus:border-yellow-400 transition disabled:opacity-60"
                            />
                            <button
                                onClick={submit}
                                disabled={pending || !message.trim()}
                                className="bg-yellow-400 hover:bg-yellow-300 text-black p-3 rounded-lg flex items-center justify-center disabled:opacity-50 disabled:bg-zinc-700 transition"
                            >
                                <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" viewBox="0 0 24 24"
                                     fill="none" stroke="currentColor">
                                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M5 12h14M12 5l7 7-7 7"/>
                                </svg>
                            </button>
                        </div>
                    </>
                )}
            </div>
        </div>
    );
};

export default MyBotsPage;

