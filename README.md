# Group 14 – LLM-Assisted Refactoring Experiments

Practical verification for our case study on refactoring (course: Software Design and Analysis, University of Prishtina).
The experiments are inspired by Paper B: *Exploring the Potential of General Purpose LLMs in Automated Software Refactoring: An Empirical Study* (Liu et al., ASE 2025).

Both experiments use the same three prompting strategies:

- **P1 – Generic:** general refactoring instructions.
- **P2 – Extract Method:** instructions naming the refactoring type.
- **P3 – Scoped:** instructions focused on a specific method / subcategories.

## Repository structure

| Path | Content |
|---|---|
| `src/experiment-1-order-service/` | Experiment 1 – `OrderProcessor` (baseline + P1, P2, P3) |
| `tests/` | Unit tests of Experiment 1 |
| `run-tests.sh`, `run-tests.ps1` | Run Experiment 1 (Linux/macOS/Git Bash, Windows PowerShell) |
| `src/experiment-2-order-service/` | Experiment 2 – `OrderService`, GPT-6 and Gemini 3.1 Pro (own README inside) |

## Experiment 1 – OrderProcessor (Elonë Krasniqi)

Five predefined improvements: tax extraction, discount extraction, formatting extraction, duplicate code removal, magic number replacement.

| Prompt | Implemented | Compilation | Tests |
|---|---|---|---|
| P1 – Generic | 3/5 | Passed | 7/7 |
| P2 – Extract Method | 3/5 | Passed | 7/7 |
| P3 – Scoped | 3/5 | Passed | 7/7 |

LLM / version / date: `[to be added]`

Run (from the repository root):
```bash
bash run-tests.sh          # all versions; or: bash run-tests.sh P2
```
```powershell
.\run-tests.ps1            # all versions; or: .\run-tests.ps1 -Version P2
```

## Experiment 2 – OrderService (Ardit Hyseni)

A richer class (tiers, categories, bulk discount) with two LLMs, 7 tests and a 20,000-case differential check.
Results: P1 found all 5 opportunities but changed behaviour in edge cases (`-0.00`, 1-cent differences, immutable list); P2 and P3 behaved identically to the baseline.
Details and table: `src/experiment-2-order-service/README.md`.

```bash
bash src/experiment-2-order-service/run_all.sh
```

Requires JDK 17+.

## Limitations

One or two classes, one run per prompt, our own expected-opportunity lists. Passing all tests does not guarantee complete behavioural equivalence.

## Authors

Elonë Krasniqi and Ardit Hyseni – Faculty of Electrical and Computer Engineering, University of Prishtina.
