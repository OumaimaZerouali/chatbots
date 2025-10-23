import React, { useState } from "react";

interface GenieResponse {
    testCode: string;
    clarification?: string;
}

export const TestGenerator: React.FC = () => {
    const [codeInput, setCodeInput] = useState("");
    const [response, setResponse] = useState<GenieResponse | null>(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const extractJavaCode = (text: string) => {
        const match = text.match(/```java\n([\s\S]*?)```/);
        return match?.[1].trim() ?? text;
    };

    const handleGenerate = async () => {
        setLoading(true);
        setError(null);
        setResponse(null);

        try {
            const res = await fetch("/api/genie/test", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ code: codeInput }),
            });

            if (!res.ok) throw new Error("Failed to generate test.");

            const data: GenieResponse = await res.json();
            setResponse({
                ...data,
                testCode: data.testCode ? extractJavaCode(data.testCode) : "",
            });
        } catch (err: any) {
            setError(err.message || "Unknown error");
        } finally {
            setLoading(false);
        }
    };

    const handleCopy = () => {
        if (response?.testCode) navigator.clipboard.writeText(response.testCode);
    };

    return (
        <div style={{ maxWidth: 700, margin: "0 auto", padding: 20 }}>
            <h1>🧪 Unit Test Genie</h1>

            <textarea
                style={{ width: "100%", height: 180, padding: 10 }}
                placeholder="Paste your Java method here..."
                value={codeInput}
                onChange={(e) => setCodeInput(e.target.value)}
            />

            <button
                onClick={handleGenerate}
                disabled={loading || !codeInput.trim()}
                style={{ marginTop: 10, padding: "10px 20px" }}
            >
                {loading ? "Generating..." : "Generate Test"}
            </button>

            {error && <p style={{ color: "red", marginTop: 10 }}>{error}</p>}

            {response && (
                <div style={{ marginTop: 20 }}>
                    {response.clarification && (
                        <div
                            style={{
                                backgroundColor: "#fff3cd",
                                padding: 10,
                                border: "1px solid #ffeeba",
                                borderRadius: 5,
                                marginBottom: 15,
                            }}
                        >
                            ⚠️ {response.clarification}
                        </div>
                    )}

                    {response.testCode && (
                        <>
                            <h2>✅ Generated Test</h2>
                            <pre
                                style={{
                                    backgroundColor: "#f4f4f4",
                                    padding: 15,
                                    borderRadius: 5,
                                    overflowX: "auto",
                                    whiteSpace: "pre-wrap",
                                    wordWrap: "break-word",
                                    fontFamily: "monospace",
                                    fontSize: 14,
                                }}
                            >
                {response.testCode}
              </pre>
                            <button onClick={handleCopy} style={{ marginTop: 10 }}>
                                Copy to Clipboard
                            </button>
                        </>
                    )}
                </div>
            )}
        </div>
    );
};

export default TestGenerator;
