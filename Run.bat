@echo off
REM Set the paths for TestNG XML file, Allure results, and report
set TESTNG_XML_FILE=test/testng.xml
set RESULTS_DIR=allure-results
set REPORT_DIR=allure-report

REM Run TestNG tests
echo Running tests...
pause
mvn test
pause
REM Generate Allure report
allure generate --single-file --clean -o allure-report
REM Check if the report was generated successfully
if exist %REPORT_DIR%\index.html (
    echo Report generated successfully.
) else (
    echo Failed to generate report.
    exit /b 1
)



echo Allure report generated and saved as %REPORT_DIR%\allure-report.html.
pause