# ✅ ToDo.md – AI Chatbot Talk Project

## 🎯 Goal
Create a demo app showing how easy it is to build AI-powered chatbots using Java + OpenAI.  
We will implement 2 fun/useful bots and a small React frontend to interact with them.

---

## 🤖 Bot 1: Unit Test Genie

### ✨ Business Rules:
- User inputs a Java method (as plain text).
- Bot responds with a complete **JUnit test** (JUnit5).
- Bot's personality = **magical & helpful** ("Your test wish is granted 🧪✨").
- It should include:
    - Test class name suggestion.
    - Test method names (arrange-act-assert).
    - Meaningful assertions (best guess).
- If unclear method behavior → genie asks for clarification.
- If user resubmits clarified input → produce better test.

### ✅ Backend Tasks (Java):
- [ ] Define `/api/genie/test` POST endpoint → `{ code: string }`
- [ ] Prompt engineering for genie personality + JUnit generation instructions.
- [ ] Implement OpenAI call using Java SDK or LangChain4j.
- [ ] Return bot response as JSON.
- [ ] Add basic error handling (empty input, too long, invalid code).

### 🧪 Potential Enhancements (Optional):
- [ ] Add support for mocking frameworks (Mockito).
- [ ] Output in code block formatting (```java).
- [ ] Support multiple test generation modes (e.g., "happy path" or "full coverage").

---

## 🦆 Bot 2: Rubber Duck With Attitude

### 😈 Business Rules:
- User explains a bug or confusing behavior.
- Bot replies sarcastically while helping user think through problem.
- Tone = slightly rude but not offensive (funny “senior dev duck”).
- Should encourage self-realization by asking questions.
- If user continues → duck eventually gives suggestions.
- Final fallback: provide a probable fix or hint.

### ✅ Backend Tasks (Java):
- [ ] Define `/api/duck/debug` POST endpoint → `{ explanation: string }`
- [ ] Write system prompt: sarcastic rubber-duck persona + bug reasoning rules.
- [ ] Implement OpenAI call.
- [ ] Return JSON response with duck reply.

### 🦆 Optional Enhancements:
- [ ] Add sarcasm level (`mild`, `spicy`, `brutal`) via parameter.
- [ ] End every message with a random duck emoji 🦆.

---

## 🌐 Frontend (React App)

### 🎯 Business Rules:
- User selects which bot to interact with.
- UI contains:
    - Dropdown: `Select Bot` → `Unit Test Genie` or `Rubber Duck`.
    - Textarea for input.
    - Submit button.
    - Response area (monospace block for code if Genie).
- Must handle loading states.
- Responses shown with formatting (e.g., syntax highlighting for code).

### ✅ Frontend Tasks:
- [ ] Setup React + Vite or CRA.
- [ ] Create `BotSelector` component.
- [ ] Create `ChatInput` and `ChatResponse` components.
- [ ] POST to `/api/genie/test` or `/api/duck/debug` based on bot selection.
- [ ] Display results nicely.
- [ ] Add minimal fun styling (Genie = glowing purple, Duck = yellow/orange).
- [ ] Optional: Chat-like UX with message bubbles.

---

## 🏗️ Infrastructure / Shared Backend Setup

### ✅ Shared Tasks:
- [ ] Create Spring Boot or Quarkus backend project.
- [ ] Add OpenAI/LangChain4j dependency.
- [ ] Configure OpenAI API key via `.env` or config.
- [ ] Create shared `OpenAIService`.
- [ ] CORS enabled for frontend.
- [ ] Implement simple logging.
- [ ] Simple health endpoint `/api/health`.

---

## 🧪 Demo Script (for talk) – TBD

- [ ] Start with Rubber Duck first (funny).
- [ ] Then switch to Genie (useful).
- [ ] Highlight: only changed the system prompt!
- [ ] Show how flexible the concept is (bonus slide with more bot ideas).

---

## 📅 Next Steps
1. Confirm both bot personalities & refine prompts.
2. Decide on Spring Boot vs Quarkus.
3. Start backend scaffolding.
4. Build frontend basic UI.
5. Test both bots.
6. Polish & prepare demo flow.

---

Let’s build something magical and sarcastic at the same time. 🧪🦆
