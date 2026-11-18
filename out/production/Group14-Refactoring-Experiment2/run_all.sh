#!/usr/bin/env bash
# Compiles the baseline and each LLM variant, runs the 7 tests, then compares each variant with the baseline on 20,000 random cases.
set -uo pipefail
ROOT="$(cd "$(dirname "$0")" && pwd)"; B="$ROOT/baseline"; W="$(mktemp -d)"
run_driver(){ java -cp "$1" Driver 2>&1; }
mkdir -p "$W/base"; javac -d "$W/base" "$B/OrderService.java" "$B/OrderServiceTest.java" "$ROOT/tools/Driver.java" || exit 1
echo "=== baseline: tests ==="; java -cp "$W/base" OrderServiceTest | tail -1
run_driver "$W/base" > "$W/base.txt"
printf "\n%-22s %-9s %-12s %s\n" variant compiles tests "lines differing from baseline (20,000 cases)"
for d in "$ROOT"/variants/*/; do v=$(basename "$d"); mkdir -p "$W/$v"
  if javac -d "$W/$v" "$d/OrderService.java" "$B/OrderServiceTest.java" "$ROOT/tools/Driver.java" 2>"$W/$v.err"; then
    t=$(java -cp "$W/$v" OrderServiceTest | tail -1); run_driver "$W/$v" > "$W/$v.txt"; n=$(diff "$W/base.txt" "$W/$v.txt" | grep -c '^[<>]')
    printf "%-22s %-9s %-12s %s\n" "$v" yes "$t" "$n"
  else printf "%-22s %-9s\n" "$v" "NO (see compiler output)"; fi
done
