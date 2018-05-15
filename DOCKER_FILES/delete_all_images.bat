@echo off
rem # Delete all stopped containers
FOR /f "tokens=*" %%i IN ('docker ps -aq') DO docker rm %%i
rem Delete all dangling (unused) images
FOR /f "tokens=*" %%i IN ('docker images --format "{{.ID}}"') DO docker rmi %%i