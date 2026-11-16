# Group 14 – LLM-Assisted Refactoring Experiment

## Overview

This repository contains a small Java refactoring experiment conducted as part of our Software Design and Analysis course project.

The experiment is inspired by Paper B: *Exploring the Potential of General Purpose LLMs in Automated Software Refactoring: An Empirical Study*.

## Experiment

We evaluated three prompting strategies:

- **P1 – Generic:** General refactoring instructions.
- **P2 – Extract Method:** Instructions specifying the refactoring type.
- **P3 – Scoped:** Instructions focused on a specific method.

Five predefined refactoring improvements were evaluated: tax extraction, discount extraction, formatting extraction, duplicate code removal, and magic number replacement.

## Results

| Prompt | Implemented | Compilation | Tests |
|---|---|---|---|
| P1 – Generic | 3/5 | Passed | 7/7 |
| P2 – Extract Method | 3/5 | Passed | 7/7 |
| P3 – Scoped | 3/5 | Passed | 7/7 |

## How to Run

Compile each version separately with the same test class.

For example, from the repository root:

```bash
mkdir out
javac -d out baseline/OrderProcessor.java tests/OrderProcessorTest.java
java -cp out OrderProcessorTest
```

Repeat for P1, P2, and P3, clearing or recreating the `out` directory before each run.

## Limitations

This experiment uses one Java class, one run per prompt, and seven test cases. Passing all tests does not guarantee complete behavioral equivalence.

## Authors

Elonë Krasniqi and Ardit Hyseni

Faculty of Electrical and Computer Engineering, University of Prishtina.