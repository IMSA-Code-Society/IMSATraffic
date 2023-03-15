@taskkill /f /im TrafficDriver.exe >nul 2>&1
@copy "%CD%\bin\traffic_updater.bat" "%USERPROFILE%\Start Menu\Programs\Startup" >nul 2>&1
@copy "%CD%\bin\TrafficDriver.exe" "%USERPROFILE%\Start Menu\Programs\Startup" >nul 2>&1
@echo Installation finished
@pause