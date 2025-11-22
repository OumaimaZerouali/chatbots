import React from "react";

interface SidebarProps {
    selected: string;
    onSelect: (value: string) => void;
}

const Sidebar: React.FC<SidebarProps> = ({ selected, onSelect }) => {
    const menu = [
        { id: "genie", label: "🧞 Genie Test Generator" },
        { id: "duck", label: "🦆 Duck Debugger" },
        { id: "factory", label: "🏭 Bot Factory" },
        { id: "mybots", label: "🤖 My Bots" },
    ];

    return (
        <div className="w-64 bg-zinc-900 border-r-2 border-zinc-800 text-white flex flex-col p-4">
            <h1 className="text-xl font-bold mb-8 text-yellow-400">ChatBot Studio</h1>
            <ul className="space-y-2">
                {menu.map(item => (
                    <li
                        key={item.id}
                        className={`cursor-pointer p-3 rounded-lg transition-all ${
                            selected === item.id 
                                ? "bg-yellow-400 text-black font-medium" 
                                : "hover:bg-zinc-800 hover:text-yellow-400 border-2 border-transparent hover:border-zinc-700"
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
