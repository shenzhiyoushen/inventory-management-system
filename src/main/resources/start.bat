@echo off
chcp 65001 > nul
title SpringBoot-8080

:: ========== 核心修复：用绝对路径替代相对路径 ==========
:: 获取脚本所在的绝对目录（解决子进程路径解析错误）
set "SCRIPT_DIR=%~dp0"
:: JRE绝对路径（脚本目录的上级目录 → jre → bin → java.exe）
set "JAVA_EXE=%SCRIPT_DIR%..\jre\bin\java.exe"
:: 其他配置（Jar包用脚本目录的绝对路径，避免路径问题）
set "JAR_FILE=%SCRIPT_DIR%java-demo-0.0.1-SNAPSHOT.jar"
set "SERVER_PORT=8080"
set "LOG_FILE=%SCRIPT_DIR%startup.log"  :: 日志文件绝对路径
set "VISIT_URL=http://localhost:%SERVER_PORT%/demo"
set "DELAY_SEC=8"

:: 禁用干扰环境变量
set JAVA_TOOL_OPTIONS=

:: ========== 前置验证（绝对路径版） ==========
echo 【1/4】验证运行环境...
:: 1. 检查JRE是否存在（绝对路径）
if not exist "%JAVA_EXE%" (
    echo [ERROR] 未找到JRE：%JAVA_EXE%
    echo 请确认jre文件夹在脚本目录的上级目录！
    pause
    exit /b 1
)
:: 2. 检查Jar包是否存在（绝对路径）
if not exist "%JAR_FILE%" (
    echo [ERROR] 未找到Jar包：%JAR_FILE%
    echo 请确认Jar包和脚本在同一目录！
    pause
    exit /b 1
)
:: 3. 清理端口占用
:: ===================== 第二步：清理8088端口 =====================
echo 【2/4】清理%SERVER_PORT%端口占用...
for /f "tokens=5" %%a in ('netstat -ano ^| findstr ":%SERVER_PORT%" ^| findstr "LISTENING"') do (
    if not "%%a"=="4" (
        echo 结束占用进程 PID: %%a
        taskkill /F /PID %%a > nul 2>&1
    ) else (
        echo ===================== 警告 =====================
        echo 系统进程（PID=4）占用%SERVER_PORT%端口！
        echo 请修改脚本中的SERVER_PORT为其他端口（如8089）
        echo ==============================================
        pause
        exit /b 1
    )
)


:: ========== 核心：异步启动Jar包（修复语法+绝对路径） ==========
echo 【3/4】异步启动项目（端口：%SERVER_PORT%）...
echo 日志将写入：%LOG_FILE%
:: 清空旧日志（绝对路径）
echo. > "%LOG_FILE%"

:: 修复后的启动命令（绝对路径+正确转义+后台常驻）
:: cmd /k 替代 cmd /c：保证子进程常驻（/c执行完就关，/k保持进程）

start /B cmd /k "%JAVA_EXE% -jar -Dfile.encoding=UTF-8 -Xms512m -Xmx1024m %JAR_FILE% --server.port=%SERVER_PORT%" > "%LOG_FILE%" 2>&1

:: ========== 延迟5秒打开浏览器 ==========
echo 【4/4】等待%DELAY_SEC%秒后打开浏览器...
timeout /t %DELAY_SEC% /nobreak > nul

:: 打开浏览器（解决路径/空格问题）

echo 正在打开浏览器访问："%VISIT_URL%"

start "" "%VISIT_URL%"

:: 提示信息
echo ==============================================
echo ✅ 项目已后台启动（绝对路径版）
echo 📝 日志文件：%LOG_FILE%
echo ❌ 停止项目请执行：taskkill /F /IM java.exe
echo ==============================================

:: 保持窗口打开（可选）
pause > nul
exit /b 0