import React, { useState } from "react";

const GeniePage: React.FC = () => {
    const [code, setCode] = useState("");

    return (
        <div className="max-w-3xl mx-auto">
            <h2 className="text-3xl font-semibold mb-6">🧞 Genie Test Generator</h2>
            <textarea
                className="w-full p-4 border rounded-md shadow-sm focus:ring-2 focus:ring-blue-500 focus:outline-none mb-4"
                placeholder="Plak je Java-methode hier..."
                value={code}
                rows={8}
                onChange={(e) => setCode(e.target.value)}
            />
            <button className="px-6 py-3 bg-blue-600 text-white font-semibold rounded-md hover:bg-blue-700 transition">
                Genereer Test
            </button>
        </div>
    );
};

export default GeniePage;
