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
        <div className="p-8 flex flex-col h-full bg-[#1E2430] text-white rounded-lg overflow-hidden">
            <h2 className="text-3xl font-semibold mb-6">🧞 Genie Test Generator</h2>

            {/* Input area */}
            <div className="mb-4">
                <label className="block text-sm font-medium mb-2 text-gray-300">
                    Paste your Java method here:
                </label>
                <textarea
                    className="w-full bg-[#0b1120] text-gray-300 placeholder-gray-500 rounded-xl px-4 py-3 text-sm focus:outline-none border border-gray-800 focus:border-purple-600 transition disabled:opacity-60"
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
                className="bg-purple-600 hover:bg-purple-700 px-6 py-3 rounded-xl font-semibold transition disabled:opacity-50 mb-6"
            >
                {pending ? "Generating..." : "Generate Test"}
            </button>

            {/* Output area */}
            {generatedTest && (
                <div className="flex-1 overflow-hidden flex flex-col">
                    <label className="block text-sm font-medium mb-2 text-gray-300">
                        Generated Test:
                    </label>
                    <div className="flex-1 bg-[#0b1120] rounded-xl p-4 overflow-y-auto border border-gray-800">
                        <pre className="text-gray-300 text-sm whitespace-pre-wrap break-words">
                            {generatedTest}
                        </pre>
                    </div>
                </div>
            )}

            {pending && !generatedTest && (
                <div className="flex-1 bg-[#0b1120] rounded-xl p-4 flex items-center justify-center border border-gray-800">
                    <div className="text-gray-400">Generating your test...</div>
                </div>
            )}
        </div>
    );
};

export default GeniePage;
