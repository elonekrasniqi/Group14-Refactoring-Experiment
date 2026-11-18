#!/usr/bin/env bash
# Compiles the baseline and each LLM variant (each lives in its own package/folder),
# runs its 7 tests, then compares its output with the baseline on 20,000 random cases.
set -uo pipefail
ROOT="$(cd "$(dirname "$0")" && pwd)"; T="$ROOT/tools/Driver.java"; W="$(mktemp -d)"
run() {  # $1=class name  $2=source dir  $3=package
  local n="$1" d="$2" p="$3" o="$W/$1"
  mkdir -p "$o"
  { echo "import $p.$n;"; sed "s/\bOrderService\b/$n/g" "$T"; } > "$o/Driver.java"
  javac -d "$o" "$d/$n.java" "$d/${n}Test.java" "$o/Driver.java" 2>"$W/$n.err" || return 1
  TESTS=$(java -cp "$o" "$p.${n}Test" | tail -1)
  java -cp "$o" Driver > "$W/$n.txt" 2>&1
}
run OrderService "$ROOT/baseline" baseline || { cat "$W/OrderService.err"; exit 1; }
echo "=== baseline: tests ==="; echo "$TESTS"
printf "\n%-24s %-9s %-16s %s\n" variant compiles tests "lines differing from baseline (20,000 cases)"
for f in "$ROOT"/variants/*/OrderService*.java; do
  n=$(basename "$f" .java); case "$n" in *Test) continue;; esac
  d="$(dirname "$f")"; p="variants.$(basename "$d")"
  if run "$n" "$d" "$p"; then
    printf "%-24s %-9s %-16s %s\n" "$n" yes "$TESTS" "$(diff "$W/OrderService.txt" "$W/$n.txt" | grep -c '^[<>]')"
  else printf "%-24s %-9s\n" "$n" "NO"; cat "$W/$n.err"; fi
done
