@taskkill /f /im TrafficDriver.exe >nul 2>&1
@del "%USERPROFILE%\Start Menu\Programs\Startup\traffic_updater.bat" >nul 2>&1
@del "%USERPROFILE%\Start Menu\Programs\Startup\TrafficDriver.exe" >nul 2>&1
@del "%USERPROFILE%\Start Menu\Programs\Startup\TrafficDriver.config" >nul 2>&1
@echo Uninstall finished
@pause