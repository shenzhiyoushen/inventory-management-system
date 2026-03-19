@echo off
chcp 65001 > nul
title SpringBoot用户管理系统-8088端口

:: ===================== 核心参数配置 =====================
set "JAVA_EXE=../jre/bin/java.exe"
set "JAR_FILE=java-demo-0.0.1-SNAPSHOT.jar"
set "SERVER_PORT=8080"
set "LOG_FILE=startup.log"
set "VISIT_URL=http://localhost:%SERVER_PORT%/demo"

:: ===================== 第一步：前置验证（关键！） =====================
:: 1. 验证JRE是否存在且可执行
echo 【1/5】验证Java运行环境...
if not exist "%JAVA_EXE%" (
    echo ===================== 致命错误 =====================
    echo 未找到JRE！请检查：
    echo 1. 项目目录下是否有jre文件夹
    echo 2. JRE路径是否正确：%JAVA_EXE%
    echo ==============================================
    pause
    exit /b 1
)
:: 验证Java版本（确保是17）
"%JAVA_EXE%" -version > "%LOG_FILE%" 2>&1
if errorlevel 1 (
    echo ===================== 致命错误 =====================
    echo JRE执行失败！可能是JRE损坏或不是64位版本
    echo 请重新提取JDK 17的完整JRE
    echo ==============================================
    pause
    exit /b 1
)

:: 2. 验证Jar包是否存在
echo 【2/5】验证项目Jar包...
if not exist "%JAR_FILE%" (
    echo ===================== 致命错误 =====================
    echo 未找到项目Jar包！请检查：
    echo 1. Jar包名称是否正确：%JAR_FILE%
    echo 2. Jar包是否和脚本在同一目录
    echo ==============================================
    pause
    exit /b 1
)

:: ===================== 第二步：清理8088端口 =====================
echo 【3/5】清理%SERVER_PORT%端口占用...
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

:: ===================== 第三步：启动项目（前台运行，日志实时输出） =====================
echo 【4/5】启动项目（端口：%SERVER_PORT%）...
echo 启动日志会同时输出到 %LOG_FILE% 和控制台
echo ==============================================
:: 前台启动Jar包，日志同时写入文件和控制台（解决日志为空问题）
"%JAVA_EXE%" -jar -Xms512m -Xmx1024m "%JAR_FILE%" --server.port=%SERVER_PORT% > "%LOG_FILE%" 2>&1 | type "%LOG_FILE%"

:: ===================== 第四步：启动成功处理（如果能走到这一步，说明项目正常退出） =====================
:: 检查日志中是否有启动成功标识
findstr /i "Tomcat started on port(s): %SERVER_PORT%" "%LOG_FILE%" > nul
if not errorlevel 1 (
    echo ==============================================
    echo 【5/5】项目启动成功！
    echo 访问地址：%VISIT_URL%
    echo 正在打开浏览器...
    start "" "%VISIT_URL%"
    echo 关闭此窗口将停止项目运行
    echo ==============================================
) else (
    echo ==============================================
    echo 【5/5】项目启动失败！
    echo 请查看日志文件：%LOG_FILE%
    echo 常见原因：数据库连接失败、Jar包损坏、端口仍被占用
    echo ==============================================
)

pause
:: 停止Java进程
taskkill /F /IM java.exe > nul 2>&1
exit /b 0