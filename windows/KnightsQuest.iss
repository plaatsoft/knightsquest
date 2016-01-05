;
; RedSquare windows installer configuration file.
;
; The file can be processed with "Inno Setup Compiler v5.3.8"
;
; All Copyrights Reserved (c) PlaatSoft 2008-2011

[Setup]
AppPublisher=PlaatSoft
AppPublisherURL=http://www.plaatsoft.nl
AppName=KnightsQuest
AppVerName=PlaatSoft KnightsQuest v0.20
AppVersion=0.20
AppCopyright=Copyright (C) 2008-2011 PlaatSoft

DefaultDirName={pf}\KnightsQuest
DefaultGroupName=PlaatSoft
UninstallDisplayIcon={app}\KnightsQuest.exe
Compression=lzma
SolidCompression=yes
OutputDir=release\

[Files]
Source: "src\release\KnightsQuest.exe"; DestDir: "{app}"
Source: "src\release\libgcc_s_dw2-1.dll"; DestDir: "{app}"
Source: "src\release\mingwm10.dll"; DestDir: "{app}"
Source: "src\release\QtCore4.dll"; DestDir: "{app}"
Source: "src\release\QtGui4.dll"; DestDir: "{app}"
Source: "src\release\QtNetwork4.dll"; DestDir: "{app}"
Source: "src\release\QtXml4.dll"; DestDir: "{app}"
Source: "src\maps\map01.xml"; DestDir: "{app}\maps"
Source: "src\snd\click.wav"; DestDir: "{app}\snd"
Source: "src\release\license.txt"; DestDir: "{app}"; Flags: isreadme

[Icons]
Name: "{group}\KnightsQuest\KnightsQuest"; Filename: "{app}\KnightsQuest.exe"
Name: "{commondesktop}\PlaatSoft KnightsQuest"; Filename: "{app}\KnightsQuest.exe"
Name: "{group}\KnightsQuest\Uninstaller"; Filename: "{uninstallexe}"

[Registry]
Root: HKCU; Subkey: "Software\PlaatSoft\KnightsQuest"; ValueName: "target"; ValueData: {app}; ValueType: string;  Flags: uninsdeletekeyifempty
Root: HKCU; Subkey: "Software\PlaatSoft\KnightsQuest"; ValueName: "username"; ValueData: {username}; ValueType: string;  Flags: uninsdeletekeyifempty

