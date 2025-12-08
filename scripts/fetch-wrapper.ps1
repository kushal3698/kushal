Param(
    [string]$GradleWrapperVersion = '9.2'
)

# Creates gradle/wrapper and downloads a gradle-wrapper jar from Maven Central
# Usage: .\scripts\fetch-wrapper.ps1

$ErrorActionPreference = 'Stop'

$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Definition
$repoRoot = Resolve-Path "$scriptDir\.."
$wrapperDir = Join-Path $repoRoot 'gradle\wrapper'

if (!(Test-Path $wrapperDir)) {
    New-Item -ItemType Directory -Path $wrapperDir | Out-Null
}

$target = Join-Path $wrapperDir 'gradle-wrapper.jar'

Write-Host "Attempting to download gradle-wrapper.jar for Gradle $GradleWrapperVersion..."

# Try Maven Central artifact
$mavenUrl = "https://repo1.maven.org/maven2/org/gradle/gradle-wrapper/$GradleWrapperVersion/gradle-wrapper-$GradleWrapperVersion.jar"
try {
    Invoke-WebRequest -Uri $mavenUrl -OutFile $target -UseBasicParsing -ErrorAction Stop
    Write-Host "Downloaded gradle-wrapper.jar from Maven Central to $target"
    exit 0
} catch {
    Write-Host "Failed to download from Maven Central: $mavenUrl"
}

# Fallback: try Gradle services (may not host the wrapper jar)
$svcUrl = "https://services.gradle.org/distributions/gradle-wrapper-$GradleWrapperVersion.jar"
try {
    Invoke-WebRequest -Uri $svcUrl -OutFile $target -UseBasicParsing -ErrorAction Stop
    Write-Host "Downloaded gradle-wrapper.jar from Gradle services to $target"
    exit 0
} catch {
    Write-Host "Failed to download from Gradle services: $svcUrl"
}

Write-Host "Unable to automatically fetch gradle-wrapper.jar."
Write-Host "Please generate it locally by running 'gradle wrapper --gradle-version $GradleWrapperVersion' or download the jar and place it at: $target"
exit 1
