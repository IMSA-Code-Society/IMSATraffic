@taskkill /f /im TrafficDriver.exe >nul 2>&1
@copy "%CD%\bin\traffic_updater.bat" "%USERPROFILE%\Start Menu\Programs\Startup" >nul 2>&1
@copy "%CD%\bin\TrafficDriver.exe" "%USERPROFILE%\Start Menu\Programs\Startup" >nul 2>&1
@echo Installation completed
@start /d "%USERPROFILE%\Start Menu\Programs\Startup" traffic_updater.bat >nul 2>&1
@echo Config updated
@start /d "%USERPROFILE%\Start Menu\Programs\Startup" TrafficDriver.exe >nul 2>&1
@echo Driver started
@echo Done
@pause