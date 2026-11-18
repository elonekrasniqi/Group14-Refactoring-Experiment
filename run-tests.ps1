
param(
    [ValidateSet('baseline','P1','P2','P3','all')]
    [string]$Version = 'all'
)

$ErrorActionPreference = 'Stop'
$root = Split-Path -Parent $MyInvocation.MyCommand.Path

$versions = if ($Version -eq 'all') {
    @('baseline','P1','P2','P3')
} else {
    @($Version)
}

$utf8NoBom = New-Object System.Text.UTF8Encoding($false)

foreach ($v in $versions) {

    $build = Join-Path $root "build/$v"

    if (Test-Path $build) {
        Remove-Item -Recurse -Force $build
    }

    New-Item -ItemType Directory -Force $build | Out-Null

    if ($v -eq 'baseline') {
        $sourcePath = Join-Path $root 'src/experiment-1-order-service/baseline/OrderProcessor.java'
        $source = [System.IO.File]::ReadAllText($sourcePath)
    } else {
        $sourcePath = Join-Path $root "src/experiment-1-order-service/OrderProcessor$v/OrderProcessor$v.java"
        $source = [System.IO.File]::ReadAllText($sourcePath)

        $source = $source.Replace(
            "public class OrderProcessor$v",
            'public class OrderProcessor'
        )
    }

    # Remove BOM if present
    $source = $source.TrimStart([char]0xFEFF)

    # Write Java source as UTF-8 WITHOUT BOM
    $targetPath = Join-Path $build 'OrderProcessor.java'
    [System.IO.File]::WriteAllText($targetPath, $source, $utf8NoBom)

    Copy-Item `
        (Join-Path $root 'tests/OrderProcessorTest.java') `
        (Join-Path $build 'OrderProcessorTest.java')

    Write-Host "=== $v ==="

    & javac -encoding UTF-8 -d $build `
        (Join-Path $build 'OrderProcessor.java') `
        (Join-Path $build 'OrderProcessorTest.java')

    if ($LASTEXITCODE -ne 0) {
        throw "Compilation failed for $v"
    }

    & java -cp $build OrderProcessorTest

    if ($LASTEXITCODE -ne 0) {
        throw "Tests failed for $v"
    }
}
