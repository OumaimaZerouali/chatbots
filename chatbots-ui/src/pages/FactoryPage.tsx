import React, { useState } from "react";

const FactoryPage: React.FC = () => {
    const [name, setName] = useState("");
    const [personality, setPersonality] = useState("");
    const [purpose, setPurpose] = useState("");
    const [restrictions, setRestrictions] = useState("");
    const [file, setFile] = useState<File | null>(null);
    const [pending, setPending] = useState(false);
    const [response, setResponse] = useState<any>(null);

    async function createBot() {
        if (!name.trim() || pending) return;

        setPending(true);
        setResponse(null);

        try {
            const formData = new FormData();
            formData.append("name", name);
            if (personality.trim()) formData.append("personality", personality);
            if (purpose.trim()) formData.append("purpose", purpose);
            if (restrictions.trim()) formData.append("restrictions", restrictions);
            if (file) formData.append("file", file);

            const res = await fetch('/api/bot-factory', {
                method: 'POST',
                body: formData,
            });

            const json = await res.json();
            setResponse(json);
        } catch (e) {
            setResponse({ message: "Sorry, something went wrong while creating the bot." });
        } finally {
            setPending(false);
        }
    }

    return (
        <div className="p-8 flex flex-col h-full bg-[#1E2430] text-white rounded-lg overflow-hidden">
            <h2 className="text-3xl font-semibold mb-6">Bot Factory</h2>

            <div className="flex-1 overflow-y-auto pr-2">
                {/* Bot Name */}
                <div className="mb-4">
                    <label className="block text-sm font-medium mb-2 text-gray-300">
                        Bot Name <span className="text-red-400">*</span>
                    </label>
                    <input
                        type="text"
                        className="w-full bg-[#0b1120] text-gray-300 placeholder-gray-500 rounded-xl px-4 py-3 text-sm focus:outline-none border border-gray-800 focus:border-purple-600 transition disabled:opacity-60"
                        placeholder="My Custom Bot"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        disabled={pending}
                    />
                </div>

                {/* Personality */}
                <div className="mb-4">
                    <label className="block text-sm font-medium mb-2 text-gray-300">
                        Personality
                    </label>
                    <input
                        type="text"
                        className="w-full bg-[#0b1120] text-gray-300 placeholder-gray-500 rounded-xl px-4 py-3 text-sm focus:outline-none border border-gray-800 focus:border-purple-600 transition disabled:opacity-60"
                        placeholder="Friendly and helpful"
                        value={personality}
                        onChange={(e) => setPersonality(e.target.value)}
                        disabled={pending}
                    />
                </div>

                {/* Purpose */}
                <div className="mb-4">
                    <label className="block text-sm font-medium mb-2 text-gray-300">
                        Purpose
                    </label>
                    <textarea
                        className="w-full bg-[#0b1120] text-gray-300 placeholder-gray-500 rounded-xl px-4 py-3 text-sm focus:outline-none border border-gray-800 focus:border-purple-600 transition disabled:opacity-60"
                        placeholder="What is the purpose of this bot?"
                        value={purpose}
                        rows={3}
                        onChange={(e) => setPurpose(e.target.value)}
                        disabled={pending}
                    />
                </div>

                {/* Restrictions */}
                <div className="mb-4">
                    <label className="block text-sm font-medium mb-2 text-gray-300">
                        Restrictions
                    </label>
                    <textarea
                        className="w-full bg-[#0b1120] text-gray-300 placeholder-gray-500 rounded-xl px-4 py-3 text-sm focus:outline-none border border-gray-800 focus:border-purple-600 transition disabled:opacity-60"
                        placeholder="Any restrictions or limitations?"
                        value={restrictions}
                        rows={3}
                        onChange={(e) => setRestrictions(e.target.value)}
                        disabled={pending}
                    />
                </div>

                {/* File Upload */}
                <div className="mb-6">
                    <label className="block text-sm font-medium mb-2 text-gray-300">
                        Upload Document (Optional)
                    </label>
                    <div className="relative">
                        <input
                            type="file"
                            className="w-full bg-[#0b1120] text-gray-300 rounded-xl px-4 py-3 text-sm focus:outline-none border border-gray-800 focus:border-purple-600 transition disabled:opacity-60 file:mr-4 file:py-2 file:px-4 file:rounded-lg file:border-0 file:text-sm file:font-semibold file:bg-purple-600 file:text-white hover:file:bg-purple-700"
                            onChange={(e) => setFile(e.target.files?.[0] || null)}
                            disabled={pending}
                            accept=".txt,.pdf,.doc,.docx"
                        />
                    </div>
                    {file && (
                        <p className="text-xs text-gray-400 mt-2">
                            Selected: {file.name}
                        </p>
                    )}
                </div>

                <button
                    onClick={createBot}
                    disabled={pending || !name.trim()}
                    className="w-full bg-purple-600 hover:bg-purple-700 px-6 py-3 rounded-xl font-semibold transition disabled:opacity-50 mb-6"
                >
                    {pending ? "Creating Bot..." : "Create Bot"}
                </button>

                {/* Response area */}
                {response && (
                    <div className="mt-6">
                        <label className="block text-sm font-medium mb-2 text-gray-300">
                            Result:
                        </label>
                        <div className="bg-[#0b1120] rounded-xl p-4 border border-gray-800">
                            {response.message && (
                                <p className={`text-sm mb-2 ${response.id ? 'text-green-400' : 'text-yellow-400'}`}>
                                    {response.message}
                                </p>
                            )}
                            {response.id && (
                                <div className="text-gray-300 text-sm space-y-1">
                                    <p><span className="font-semibold">ID:</span> {response.id}</p>
                                    <p><span className="font-semibold">Name:</span> {response.name}</p>
                                    {response.systemPrompt && (
                                        <div className="mt-3">
                                            <p className="font-semibold mb-1">System Prompt:</p>
                                            <pre className="text-xs text-gray-400 whitespace-pre-wrap break-words bg-[#1E2430] p-3 rounded-lg">
                                                {response.systemPrompt}
                                            </pre>
                                        </div>
                                    )}
                                    <div className="mt-4 pt-3 border-t border-gray-700">
                                        <p className="text-green-400 text-sm mb-2">✅ Bot created successfully!</p>
                                        <p className="text-gray-400 text-xs">
                                            Go to <span className="text-purple-400 font-semibold">🤖 My Bots</span> to start chatting with your new bot.
                                        </p>
                                    </div>
                                </div>
                            )}
                        </div>
                    </div>
                )}

                {pending && !response && (
                    <div className="mt-6 bg-[#0b1120] rounded-xl p-4 flex items-center justify-center border border-gray-800">
                        <div className="text-gray-400">Creating your bot...</div>
                    </div>
                )}
            </div>
        </div>
    );
};

export default FactoryPage;

