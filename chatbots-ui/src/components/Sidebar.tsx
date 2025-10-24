import React from "react";

interface SidebarProps {
    selected: string;
    onSelect: (value: string) => void;
}

const Sidebar: React.FC<SidebarProps> = ({ selected, onSelect }) => {
    const menu = [
        { id: "genie", label: "🧞 Genie Test Generator" },
        { id: "duck", label: "🦆 Duck Debugger" },
    ];

    return (
        <div className="w-64 bg-gray-900 text-white flex flex-col p-4">
            <h1 className="text-xl font-bold mb-8">ChatBot Dashboard</h1>
            <ul className="space-y-2">
                {menu.map(item => (
                    <li
                        key={item.id}
                        className={`cursor-pointer p-3 rounded ${
                            selected === item.id ? "bg-gray-700" : "hover:bg-gray-800"
                        }`}
                        onClick={() => onSelect(item.id)}
                    >
                        {item.label}
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default Sidebar;
