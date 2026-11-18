# Experiment 2 – LLM-assisted refactoring on a richer class (`OrderService`)

Inspired by Paper B (Liu et al., *Exploring the potential of general purpose LLMs in automated software refactoring*, ASE 2025).
Complements Experiment 1 (`OrderProcessor`, simpler class) in this repository.

## Setup
- **Input:** `src/OrderService.java` – a deliberately smelly class (long method, duplicated code, magic numbers, tiers and categories).
- **Safety check 1:** 7 unit tests (`src/OrderServiceTest.java`); each variant has an identical copy that only differs in the class name (`src/OrderService<Model>P<n>Test.java`).
- **Safety check 2:** `tools/Driver.java` compares each variant with the baseline on 20,000 random cases (negative prices, quantity 0–15, three categories, three tiers).
- **LLMs:** GPT-6 and Gemini 3.1 Pro (names as shown by the apps), date: `[dd/mm/yyyy]`.
- **Prompts:** `prompts/` – P1 generic, P2 type named (Extract Method), P3 scoped to `buildInvoice` and two subcategories. Each run used a new chat.
- **Expected opportunities (our list, 5):** tax helper, discount helper, formatting helpers, duplicated `trim().toUpperCase()`, magic-number constants.

## Results
| Model · prompt | Found (of 5) | In scope | Compiles | 7 tests | Behaviour check (20,000 cases) |
|---|---|---|---|---|---|
| GPT-6 · P1 | 5/5 | 5/5 | yes | PASS | 376 cases print `Discount: -0.00` instead of `0.00` |
| Gemini 3.1 Pro · P1 | 5/5 | 5/5 | yes | PASS | `-0.00`; 289 lines differ by 1 cent; `names()` returns an immutable list |
| GPT-6 · P2 | 4/5 | 4/4 | yes | PASS | no difference |
| Gemini 3.1 Pro · P2 | 4/5 | 4/4 | yes | PASS | no difference |
| GPT-6 · P3 | 3/5 | 3/3 | yes | PASS | no difference |
| Gemini 3.1 Pro · P3 | 3/5 | 3/3 | yes | PASS | no difference |

Causes (edge cases): `subtotal * 0` gives `-0.0` for a negative subtotal; Gemini P1 replaced `line - line * 0.05` with `line * (1.0 - 0.05)`; `.toList()` returns an unmodifiable list.
"In scope" counts only what the prompt asked for (P2: four Extract Method opportunities; P3: three inside `buildInvoice`).

## Reproduce
```bash
bash run_all.sh      # needs JDK 17+ (Linux/macOS/Git Bash)
```
Raw LLM answers are in `answers/`. The code from each answer is in `src/` with the class renamed so that all files compile together (`OrderServiceGeminiP1`, …, `OrderServiceGPTP3`); only the class name was changed. In IntelliJ mark `src` as Sources Root and run any `*Test` class.

## Limitations
One class, one run per prompt, two models, our own expected list, 7 tests. The behaviour differences appear only in edge cases.
