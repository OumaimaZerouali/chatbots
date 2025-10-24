import React, { useState } from "react";
import Sidebar from "../components/Sidebar";
import GeniePage from "../pages/GeniePage";

const Dashboard: React.FC = () => {
    const [selected, setSelected] = useState("genie");

    return (
        <div className="flex h-screen bg-gray-100">
            <Sidebar selected={selected} onSelect={setSelected} />
            <main className="flex-1 p-8 overflow-y-auto">
                {selected === "genie" && <GeniePage />}
            </main>
        </div>
    );
};

export default Dashboard;
