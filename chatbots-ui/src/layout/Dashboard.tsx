import React, { useState } from "react";
import Sidebar from "../components/Sidebar";
import GeniePage from "../pages/GeniePage";
import DuckPage from "../pages/DuckPage.tsx";
import FactoryPage from "../pages/FactoryPage";
import MyBotsPage from "../pages/MyBotsPage";

const Dashboard: React.FC = () => {
    const [selected, setSelected] = useState("genie");

    return (
        <div className="flex h-screen bg-gray-100">
            <Sidebar selected={selected} onSelect={setSelected} />
            <main className="flex-1 overflow-y-auto">
                {selected === "genie" && <GeniePage />}
                {selected === "duck" && <DuckPage />}
                {selected === "mybots" && <MyBotsPage />}
                {selected === "factory" && <FactoryPage />}
            </main>
        </div>
    );
};

export default Dashboard;
