#-------------------------------------------------
#
# Project created by QtCreator 2010-09-27T07:04:59
#
#-------------------------------------------------

QT       += core gui

TARGET = KnightsQuest
TEMPLATE = app


SOURCES += main.cpp\
        gameboard.cpp \
    land.cpp \
    map.cpp \
    player.cpp \
    type.cpp \
    game.cpp

HEADERS  += gameboard.h \
    general.h \
    land.h \
    map.h \
    player.h \
    type.h \
    game.h

FORMS    += gameboard.ui

RESOURCES += \
    resources.qrc

RC_FILE = KnightsQuest.rc
