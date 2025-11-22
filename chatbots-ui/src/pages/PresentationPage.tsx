import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { ArrowLeft, Code, Brain, MessageSquare, CheckCircle, Zap } from "lucide-react";
const PresentationPage: React.FC = () => {
    const navigate = useNavigate();
    const [currentStep, setCurrentStep] = useState(0);
    const steps = [
        {
            id: 0,
            title: "Welcome to ChatBot Studio",
            description: "Learn how we build intelligent AI-powered chatbots",
            icon: MessageSquare,
            content: (
                <div className="space-y-6">
                    <p className="text-lg text-gray-400">
                        Our chatbots are built using cutting-edge AI technology combined with custom prompts
                        and domain-specific knowledge to create powerful development assistants.
                    </p>
                    <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mt-8">
                        <div className="bg-zinc-900 p-4 rounded-lg border-2 border-zinc-800">
                            <h4 className="font-semibold text-yellow-400 mb-2">🎯 Purpose-Built</h4>
                            <p className="text-sm text-gray-400">Each bot is designed for specific development tasks</p>
                        </div>
                        <div className="bg-zinc-900 p-4 rounded-lg border-2 border-zinc-800">
                            <h4 className="font-semibold text-yellow-400 mb-2">🧠 AI-Powered</h4>
                            <p className="text-sm text-gray-400">Leveraging advanced language models</p>
                        </div>
                        <div className="bg-zinc-900 p-4 rounded-lg border-2 border-zinc-800">
                            <h4 className="font-semibold text-yellow-400 mb-2">⚡ Fast & Efficient</h4>
                            <p className="text-sm text-gray-400">Get instant responses and solutions</p>
                        </div>
                        <div className="bg-zinc-900 p-4 rounded-lg border-2 border-zinc-800">
                            <h4 className="font-semibold text-yellow-400 mb-2">🔧 Customizable</h4>
                            <p className="text-sm text-gray-400">Create your own bots in the Factory</p>
                        </div>
                    </div>
                </div>
            ),
        },
        {
            id: 1,
            title: "Step 1: Define the Purpose",
            description: "Every great bot starts with a clear objective",
            icon: Brain,
            content: (
                <div className="space-y-6">
                    <p className="text-lg text-gray-400">
                        The first step is defining what your bot should do. This shapes everything else.
                    </p>
                    <div className="bg-zinc-900 p-6 rounded-xl border-2 border-zinc-800 space-y-4">
                        <h4 className="font-semibold text-yellow-400 text-lg">Example: Genie Test Generator 🧞</h4>
                        <div className="space-y-3">
                            <div className="flex items-start space-x-3">
                                <CheckCircle className="w-5 h-5 text-yellow-400 mt-1 flex-shrink-0" />
                                <div>
                                    <p className="text-white font-medium">Purpose:</p>
                                    <p className="text-gray-400 text-sm">Generate comprehensive unit tests for Java methods</p>
                                </div>
                            </div>
                            <div className="flex items-start space-x-3">
                                <CheckCircle className="w-5 h-5 text-yellow-400 mt-1 flex-shrink-0" />
                                <div>
                                    <p className="text-white font-medium">Input:</p>
                                    <p className="text-gray-400 text-sm">Java method source code</p>
                                </div>
                            </div>
                            <div className="flex items-start space-x-3">
                                <CheckCircle className="w-5 h-5 text-yellow-400 mt-1 flex-shrink-0" />
                                <div>
                                    <p className="text-white font-medium">Output:</p>
                                    <p className="text-gray-400 text-sm">Complete JUnit test cases with edge cases</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            ),
        },
        {
            id: 2,
            title: "Step 2: Craft the System Prompt",
            description: "The instructions that guide the AI's behavior",
            icon: Code,
            content: (
                <div className="space-y-6">
                    <p className="text-lg text-gray-400">
                        The system prompt is like the bot's personality and expertise. It tells the AI how to think and respond.
                    </p>
                    <div className="bg-zinc-900 p-6 rounded-xl border-2 border-zinc-800">
                        <div className="flex items-center justify-between mb-4">
                            <h4 className="font-semibold text-white">Example System Prompt</h4>
                            <span className="text-xs bg-yellow-400 text-black px-3 py-1 rounded-full font-medium">tester-bot.txt</span>
                        </div>
                        <pre className="text-sm text-gray-300 overflow-x-auto whitespace-pre-wrap">
{`You are an expert Java test generator.
Your task is to analyze Java methods and create comprehensive JUnit test cases.
Guidelines:
- Generate multiple test cases covering normal, edge, and error scenarios
- Use descriptive test method names
- Include assertions for expected behavior
- Add comments explaining the test purpose
- Follow Java and JUnit best practices`}
                        </pre>
                    </div>
                </div>
            ),
        },
        {
            id: 3,
            title: "Step 3: Add Context (RAG)",
            description: "Enhance with domain-specific knowledge",
            icon: Zap,
            content: (
                <div className="space-y-6">
                    <p className="text-lg text-gray-400">
                        RAG (Retrieval-Augmented Generation) allows bots to access specific documents and knowledge bases.
                    </p>
                    <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                        <div className="bg-zinc-900 p-6 rounded-xl border-2 border-zinc-800">
                            <h4 className="font-semibold text-yellow-400 mb-3">📚 Knowledge Base</h4>
                            <ul className="space-y-2 text-sm text-gray-400">
                                <li>• Upload PDF documents</li>
                                <li>• Add text files with FAQs</li>
                                <li>• Include code examples</li>
                                <li>• Company policies</li>
                            </ul>
                        </div>
                        <div className="bg-zinc-900 p-6 rounded-xl border-2 border-zinc-800">
                            <h4 className="font-semibold text-yellow-400 mb-3">🔍 How It Works</h4>
                            <ul className="space-y-2 text-sm text-gray-400">
                                <li>• User asks a question</li>
                                <li>• Relevant docs are retrieved</li>
                                <li>• AI uses context to answer</li>
                                <li>• More accurate responses</li>
                            </ul>
                        </div>
                    </div>
                    <div className="bg-yellow-400/10 border-2 border-yellow-400/30 rounded-lg p-4">
                        <p className="text-gray-300 text-sm">
                            <strong className="text-yellow-400">Example:</strong> The Duck Debugger could access your company's coding standards,
                            common bug patterns, and best practices to provide more relevant debugging advice.
                        </p>
                    </div>
                </div>
            ),
        },
        {
            id: 4,
            title: "Step 4: Configure & Deploy",
            description: "Set parameters and launch your bot",
            icon: MessageSquare,
            content: (
                <div className="space-y-6">
                    <p className="text-lg text-gray-400">
                        Final configuration ensures your bot behaves exactly as needed.
                    </p>
                    <div className="space-y-4">
                        <div className="bg-zinc-900 p-5 rounded-xl border-2 border-zinc-800">
                            <div className="flex items-center justify-between mb-2">
                                <h4 className="font-semibold text-white">Temperature</h4>
                                <span className="text-yellow-400 font-mono">0.7</span>
                            </div>
                            <p className="text-sm text-gray-400">Controls creativity vs. consistency</p>
                        </div>
                        <div className="bg-zinc-900 p-5 rounded-xl border-2 border-zinc-800">
                            <div className="flex items-center justify-between mb-2">
                                <h4 className="font-semibold text-white">Model Selection</h4>
                                <span className="text-yellow-400 font-mono">GPT-4 / Claude</span>
                            </div>
                            <p className="text-sm text-gray-400">Choose the AI model that powers your bot</p>
                        </div>
                        <div className="bg-zinc-900 p-5 rounded-xl border-2 border-zinc-800">
                            <div className="flex items-center justify-between mb-2">
                                <h4 className="font-semibold text-white">Response Format</h4>
                                <span className="text-yellow-400 font-mono">JSON / Text / Code</span>
                            </div>
                            <p className="text-sm text-gray-400">Define how the bot structures its output</p>
                        </div>
                    </div>
                    <div className="bg-yellow-400/10 border-2 border-yellow-400 rounded-lg p-6 text-center">
                        <CheckCircle className="w-12 h-12 text-yellow-400 mx-auto mb-3" />
                        <h4 className="text-white font-semibold text-lg mb-2">Ready to Launch!</h4>
                        <p className="text-gray-400">Your bot is configured and ready to assist users</p>
                    </div>
                </div>
            ),
        },
    ];
    const currentStepData = steps[currentStep];
    const Icon = currentStepData.icon;
    return (
        <div className="min-h-screen bg-black">
            {/* Header */}
            <header className="border-b border-yellow-500/20 bg-black sticky top-0 z-10">
                <div className="max-w-6xl mx-auto px-6 py-4 flex items-center justify-between">
                    <button
                        onClick={() => navigate("/")}
                        className="flex items-center space-x-2 text-gray-400 hover:text-yellow-400 transition-colors"
                    >
                        <ArrowLeft className="w-5 h-5" />
                        <span>Back to Home</span>
                    </button>
                    <div className="text-sm text-gray-500">
                        Step {currentStep + 1} of {steps.length}
                    </div>
                </div>
            </header>
            {/* Main Content */}
            <div className="max-w-6xl mx-auto px-6 py-12">
                {/* Step Header */}
                <div className="text-center mb-12">
                    <div className="w-20 h-20 mx-auto mb-6 rounded-xl bg-yellow-400 flex items-center justify-center">
                        <Icon className="w-10 h-10 text-black" />
                    </div>
                    <h1 className="text-4xl md:text-5xl font-bold text-white mb-4">
                        {currentStepData.title}
                    </h1>
                    <p className="text-xl text-gray-400">
                        {currentStepData.description}
                    </p>
                </div>
                {/* Step Content */}
                <div className="bg-zinc-900/50 border-2 border-zinc-800 rounded-xl p-8 md:p-12 mb-12">
                    {currentStepData.content}
                </div>
                {/* Navigation */}
                <div className="flex items-center justify-between">
                    <button
                        onClick={() => setCurrentStep(Math.max(0, currentStep - 1))}
                        disabled={currentStep === 0}
                        className="px-6 py-3 bg-zinc-900 border-2 border-zinc-800 text-white rounded-lg hover:border-yellow-400 transition-colors disabled:opacity-50 disabled:cursor-not-allowed flex items-center space-x-2"
                    >
                        <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 19l-7-7 7-7" />
                        </svg>
                        <span>Previous</span>
                    </button>
                    {/* Progress Dots */}
                    <div className="flex space-x-2">
                        {steps.map((_, index) => (
                            <button
                                key={`step-${index}`}
                                onClick={() => setCurrentStep(index)}
                                className={`h-3 rounded-full transition-all ${
                                    index === currentStep
                                        ? "bg-yellow-400 w-8"
                                        : index < currentStep
                                        ? "bg-yellow-600 w-3"
                                        : "bg-zinc-800 w-3"
                                }`}
                            />
                        ))}
                    </div>
                    {currentStep < steps.length - 1 ? (
                        <button
                            onClick={() => setCurrentStep(Math.min(steps.length - 1, currentStep + 1))}
                            className="px-6 py-3 bg-yellow-400 text-black font-medium rounded-lg hover:bg-yellow-300 transition-all flex items-center space-x-2"
                        >
                            <span>Next</span>
                            <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 5l7 7-7 7" />
                            </svg>
                        </button>
                    ) : (
                        <button
                            onClick={() => navigate("/dashboard")}
                            className="px-6 py-3 bg-yellow-400 text-black font-medium rounded-lg hover:bg-yellow-300 transition-all flex items-center space-x-2"
                        >
                            <span>Go to Dashboard</span>
                            <CheckCircle className="w-5 h-5" />
                        </button>
                    )}
                </div>
            </div>
        </div>
    );
};
export default PresentationPage;
