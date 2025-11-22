import React from "react";
import { useNavigate } from "react-router-dom";
import { Sparkles, Bug, Factory, Bot, Presentation } from "lucide-react";

const LandingPage: React.FC = () => {
    const navigate = useNavigate();

    const menuItems = [
        {
            id: "genie",
            title: "Genie Test Generator",
            description: "Automatically generate unit tests for your Java code with AI assistance",
            icon: Sparkles,
            color: "from-purple-500 to-pink-500",
            emoji: "🧞",
        },
        {
            id: "duck",
            title: "Duck Debugger",
            description: "Your friendly rubber duck to help you debug and understand your code",
            icon: Bug,
            color: "from-yellow-500 to-orange-500",
            emoji: "🦆",
        },
        {
            id: "factory",
            title: "Bot Factory",
            description: "Create and customize your own AI chatbots with ease",
            icon: Factory,
            color: "from-blue-500 to-cyan-500",
            emoji: "🏭",
        },
        {
            id: "mybots",
            title: "My Bots",
            description: "View and manage all your created chatbots in one place",
            icon: Bot,
            color: "from-green-500 to-emerald-500",
            emoji: "🤖",
        },
        {
            id: "presentation",
            title: "Presentation Mode",
            description: "Learn how we build these amazing chatbots with an interactive guide",
            icon: Presentation,
            color: "from-red-500 to-pink-500",
            emoji: "🎭",
        },
    ];

    const handleCardClick = (id: string) => {
        if (id === "presentation") {
            navigate("/presentation");
        } else {
            navigate(`/dashboard?tab=${id}`);
        }
    };

    return (
        <div className="min-h-screen bg-gradient-to-br from-gray-900 via-gray-800 to-gray-900">
            {/* Header */}
            <header className="text-center py-16 px-4">
                <h1 className="text-5xl md:text-6xl font-bold text-white mb-4">
                    Welcome to <span className="bg-gradient-to-r from-purple-400 to-pink-400 bg-clip-text text-transparent">ChatBot Studio</span>
                </h1>
                <p className="text-xl text-gray-300 max-w-2xl mx-auto">
                    Your all-in-one platform for AI-powered development tools and chatbot creation
                </p>
            </header>

            {/* Menu Grid */}
            <div className="max-w-7xl mx-auto px-4 pb-16">
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                    {menuItems.map((item) => {
                        const Icon = item.icon;
                        return (
                            <button
                                key={item.id}
                                onClick={() => handleCardClick(item.id)}
                                className="group cursor-pointer transform transition-all duration-300 hover:scale-105 w-full text-left"
                            >
                                <div className="bg-zinc-900 border-2 border-zinc-800 rounded-xl p-6 h-full hover:border-yellow-400 transition-all duration-300 hover:shadow-2xl hover:shadow-yellow-500/10">
                                    {/* Icon and Emoji */}
                                    <div className="flex items-center justify-between mb-4">
                                        <div className="w-16 h-16 rounded-lg bg-yellow-400 flex items-center justify-center text-3xl shadow-lg">
                                            {item.emoji}
                                        </div>
                                        <Icon className="w-8 h-8 text-gray-600 group-hover:text-yellow-400 transition-colors" />
                                    </div>

                                    {/* Title */}
                                    <h3 className="text-2xl font-bold text-white mb-3 group-hover:text-yellow-400 transition-all">
                                        {item.title}
                                    </h3>

                                    {/* Description */}
                                    <p className="text-gray-400 leading-relaxed group-hover:text-gray-300 transition-colors">
                                        {item.description}
                                    </p>

                                    {/* Arrow indicator */}
                                    <div className="mt-6 flex items-center text-yellow-400 transition-colors">
                                        <span className="text-sm font-medium">Get started</span>
                                        <svg
                                            className="w-4 h-4 ml-2 transform group-hover:translate-x-2 transition-transform"
                                            fill="none"
                                            stroke="currentColor"
                                            viewBox="0 0 24 24"
                                        >
                                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 5l7 7-7 7" />
                                        </svg>
                                    </div>
                                </div>
                            </button>
                        );
                    })}
                </div>
            </div>

            {/* Footer */}
            <footer className="text-center py-8 text-gray-600 text-sm border-t border-yellow-500/20">
                <p>Powered by AI • Built with <span className="text-yellow-400">❤️</span></p>
            </footer>
        </div>
    );
};

export default LandingPage;

