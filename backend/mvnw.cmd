@echo off
where mvn >nul 2>nul
if %errorlevel%==0 (
  mvn %*
  exit /b %errorlevel%
)
where docker >nul 2>nul
if not %errorlevel%==0 (
  echo Maven veya Docker bulunamadi. README kurulum adimlarina bakin.
  exit /b 1
)
docker run --rm -v "%CD%:/workspace" -w /workspace maven:3.9.9-eclipse-temurin-21 mvn %*
