import React, { useState } from "react";
import Sidebar from "../components/Sidebar";
import GeniePage from "../pages/GeniePage";
import DuckPage from "../pages/DuckPage.tsx";

const Dashboard: React.FC = () => {
    const [selected, setSelected] = useState("genie");

    return (
        <div className="flex h-screen bg-gray-100">
            <Sidebar selected={selected} onSelect={setSelected} />
            <main className="flex-1 overflow-y-auto">
                {selected === "genie" && <GeniePage />}
                {selected === "duck" && <DuckPage />}
            </main>
        </div>
    );
};

export default Dashboard;
