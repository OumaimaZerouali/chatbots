import React, { useState } from "react";

const GeniePage: React.FC = () => {
    const [code, setCode] = useState("");
    const [generatedTest, setGeneratedTest] = useState("");
    const [pending, setPending] = useState(false);

    async function generateTest() {
        const trimmedCode = code.trim();
        if (!trimmedCode || pending) return;

        setPending(true);
        setGeneratedTest("");

        try {
            const res = await fetch('/api/genie/test', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ code: trimmedCode }),
            });

            const json = await res.json();
            const test = json?.testCode || "No test generated.";
            setGeneratedTest(test);
        } catch (e) {
            setGeneratedTest("Sorry, something went wrong while generating the test.");
        } finally {
            setPending(false);
        }
    }

    return (
        <div className="p-8 flex flex-col h-full bg-black text-white overflow-hidden">
            <h2 className="text-3xl font-bold mb-6 text-yellow-400">🧞 Genie Test Generator</h2>

            {/* Input area */}
            <div className="mb-4">
                <label className="block text-sm font-medium mb-2 text-gray-400">
                    Paste your Java method here:
                </label>
                <textarea
                    className="w-full bg-zinc-900 text-gray-300 placeholder-gray-500 rounded-lg px-4 py-3 text-sm focus:outline-none border-2 border-zinc-800 focus:border-yellow-400 transition disabled:opacity-60"
                    placeholder="public int add(int a, int b) { return a + b; }"
                    value={code}
                    rows={10}
                    onChange={(e) => setCode(e.target.value)}
                    disabled={pending}
                />
            </div>

            <button
                onClick={generateTest}
                disabled={pending || !code.trim()}
                className="bg-yellow-400 hover:bg-yellow-300 text-black px-6 py-3 rounded-lg font-bold transition disabled:opacity-50 disabled:bg-zinc-700 disabled:text-gray-500 mb-6"
            >
                {pending ? "Generating..." : "Generate Test"}
            </button>

            {/* Output area */}
            {generatedTest && (
                <div className="flex-1 overflow-hidden flex flex-col">
                    <label className="block text-sm font-medium mb-2 text-gray-400">
                        Generated Test:
                    </label>
                    <div className="flex-1 bg-zinc-900 rounded-lg p-4 overflow-y-auto border-2 border-zinc-800">
                        <pre className="text-gray-300 text-sm whitespace-pre-wrap break-words">
                            {generatedTest}
                        </pre>
                    </div>
                </div>
            )}

            {pending && !generatedTest && (
                <div className="flex-1 bg-zinc-900 rounded-lg p-4 flex items-center justify-center border-2 border-zinc-800">
                    <div className="text-gray-400">Generating your test...</div>
                </div>
            )}
        </div>
    );
};

export default GeniePage;
