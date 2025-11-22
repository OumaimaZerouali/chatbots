import React, { useState, useEffect } from "react";
import { useSearchParams, useNavigate } from "react-router-dom";
import { Home } from "lucide-react";
import Sidebar from "../components/Sidebar";
import GeniePage from "../pages/GeniePage";
import DuckPage from "../pages/DuckPage.tsx";
import FactoryPage from "../pages/FactoryPage";
import MyBotsPage from "../pages/MyBotsPage";

const Dashboard: React.FC = () => {
    const [searchParams] = useSearchParams();
    const navigate = useNavigate();
    const tabParam = searchParams.get("tab");
    const [selected, setSelected] = useState(tabParam || "genie");

    useEffect(() => {
        if (tabParam) {
            setSelected(tabParam);
        }
    }, [tabParam]);

    return (
        <div className="flex h-screen bg-black">
            <Sidebar selected={selected} onSelect={setSelected} />
            <main className="flex-1 overflow-y-auto relative">
                {/* Back to Home button */}
                <button
                    onClick={() => navigate("/")}
                    className="absolute top-4 right-4 z-10 flex items-center space-x-2 px-4 py-2 bg-zinc-900 border-2 border-zinc-800 text-white rounded-lg hover:border-yellow-400 hover:text-yellow-400 transition-colors shadow-lg"
                >
                    <Home className="w-4 h-4" />
                    <span className="text-sm">Home</span>
                </button>

                {selected === "genie" && <GeniePage />}
                {selected === "duck" && <DuckPage />}
                {selected === "mybots" && <MyBotsPage />}
                {selected === "factory" && <FactoryPage />}
            </main>
        </div>
    );
};

export default Dashboard;
