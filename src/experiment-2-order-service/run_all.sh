#!/usr/bin/env bash
# Compiles the baseline and each LLM variant, runs its 7 tests, then compares its output with the baseline on 20,000 random cases.
set -uo pipefail
ROOT="$(cd "$(dirname "$0")" && pwd)"; S="$ROOT/src"; T="$ROOT/tools/Driver.java"; W="$(mktemp -d)"
mkdir -p "$W/base"; sed 's/\bOrderService\b/OrderService/g' "$T" > "$W/base/Driver.java"
javac -d "$W/base" "$S/OrderService.java" "$S/OrderServiceTest.java" "$W/base/Driver.java" || exit 1
echo "=== baseline: tests ==="; java -cp "$W/base" OrderServiceTest | tail -1
java -cp "$W/base" Driver > "$W/base.txt" 2>&1
printf "\n%-24s %-9s %-12s %s\n" variant compiles tests "lines differing from baseline (20,000 cases)"
for f in "$S"/OrderServiceGemini*.java "$S"/OrderServiceGPT*.java; do n=$(basename "$f" .java); case "$n" in *Test) continue;; esac
  mkdir -p "$W/$n"; sed "s/\bOrderService\b/$n/g" "$T" > "$W/$n/Driver.java"
  if javac -d "$W/$n" "$f" "$S/${n}Test.java" "$W/$n/Driver.java" 2>"$W/$n.err"; then
    t=$(java -cp "$W/$n" ${n}Test | tail -1); java -cp "$W/$n" Driver > "$W/$n.txt" 2>&1
    printf "%-24s %-9s %-12s %s\n" "$n" yes "$t" "$(diff "$W/base.txt" "$W/$n.txt" | grep -c '^[<>]')"
  else printf "%-24s %-9s\n" "$n" "NO"; cat "$W/$n.err"; fi
done
