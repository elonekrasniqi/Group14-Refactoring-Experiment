#!/usr/bin/env bash
set -euo pipefail
ROOT="$(cd "$(dirname "$0")" && pwd)"
selected="${1:-all}"
if [[ "$selected" == all ]]; then versions=(baseline P1 P2 P3); else versions=("$selected"); fi
for v in "${versions[@]}"; do
  case "$v" in baseline|P1|P2|P3) ;; *) echo 'Use baseline, P1, P2, P3 or all' >&2; exit 2;; esac
  dir="$ROOT/build/$v"
  rm -rf "$dir"; mkdir -p "$dir"
  if [[ "$v" == baseline ]]; then cp "$ROOT/src/baseline/OrderProcessor.java" "$dir/OrderProcessor.java"
  else sed "s/public class OrderProcessor$v/public class OrderProcessor/" "$ROOT/src/OrderProcessor$v/OrderProcessor$v.java" > "$dir/OrderProcessor.java"; fi
  cp "$ROOT/tests/OrderProcessorTest.java" "$dir/OrderProcessorTest.java"
  echo "=== $v ==="
  javac -encoding UTF-8 -d "$dir" "$dir/OrderProcessor.java" "$dir/OrderProcessorTest.java"
  java -cp "$dir" OrderProcessorTest
done
