/********************************************************************************
** Form generated from reading UI file 'gameboard.ui'
**
** Created: Tue 12. Oct 06:49:28 2010
**      by: Qt User Interface Compiler version 4.7.0
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_GAMEBOARD_H
#define UI_GAMEBOARD_H

#include <QtCore/QVariant>
#include <QtGui/QAction>
#include <QtGui/QApplication>
#include <QtGui/QButtonGroup>
#include <QtGui/QGraphicsView>
#include <QtGui/QHeaderView>
#include <QtGui/QLabel>
#include <QtGui/QMainWindow>
#include <QtGui/QPushButton>
#include <QtGui/QWidget>

QT_BEGIN_NAMESPACE

class Ui_Gameboard
{
public:
    QWidget *centralWidget;
    QGraphicsView *graphicsView;
    QLabel *label;
    QLabel *label_2;
    QLabel *label_3;
    QLabel *label_4;
    QLabel *label_5;
    QPushButton *nextTurnButton;

    void setupUi(QMainWindow *Gameboard)
    {
        if (Gameboard->objectName().isEmpty())
            Gameboard->setObjectName(QString::fromUtf8("Gameboard"));
        Gameboard->resize(640, 528);
        centralWidget = new QWidget(Gameboard);
        centralWidget->setObjectName(QString::fromUtf8("centralWidget"));
        graphicsView = new QGraphicsView(centralWidget);
        graphicsView->setObjectName(QString::fromUtf8("graphicsView"));
        graphicsView->setGeometry(QRect(10, 20, 41, 41));
        label = new QLabel(centralWidget);
        label->setObjectName(QString::fromUtf8("label"));
        label->setGeometry(QRect(10, 480, 46, 13));
        label_2 = new QLabel(centralWidget);
        label_2->setObjectName(QString::fromUtf8("label_2"));
        label_2->setGeometry(QRect(10, 490, 46, 13));
        label_3 = new QLabel(centralWidget);
        label_3->setObjectName(QString::fromUtf8("label_3"));
        label_3->setGeometry(QRect(10, 500, 46, 13));
        label_4 = new QLabel(centralWidget);
        label_4->setObjectName(QString::fromUtf8("label_4"));
        label_4->setGeometry(QRect(10, 510, 46, 13));
        label_5 = new QLabel(centralWidget);
        label_5->setObjectName(QString::fromUtf8("label_5"));
        label_5->setGeometry(QRect(10, 470, 46, 13));
        nextTurnButton = new QPushButton(centralWidget);
        nextTurnButton->setObjectName(QString::fromUtf8("nextTurnButton"));
        nextTurnButton->setGeometry(QRect(270, 480, 75, 23));
        Gameboard->setCentralWidget(centralWidget);

        retranslateUi(Gameboard);

        QMetaObject::connectSlotsByName(Gameboard);
    } // setupUi

    void retranslateUi(QMainWindow *Gameboard)
    {
        Gameboard->setWindowTitle(QApplication::translate("Gameboard", "Gameboard", 0, QApplication::UnicodeUTF8));
        label->setText(QApplication::translate("Gameboard", "TextLabel", 0, QApplication::UnicodeUTF8));
        label_2->setText(QApplication::translate("Gameboard", "TextLabel", 0, QApplication::UnicodeUTF8));
        label_3->setText(QApplication::translate("Gameboard", "TextLabel", 0, QApplication::UnicodeUTF8));
        label_4->setText(QApplication::translate("Gameboard", "TextLabel", 0, QApplication::UnicodeUTF8));
        label_5->setText(QApplication::translate("Gameboard", "TextLabel", 0, QApplication::UnicodeUTF8));
        nextTurnButton->setText(QApplication::translate("Gameboard", "Next Turn", 0, QApplication::UnicodeUTF8));
    } // retranslateUi

};

namespace Ui {
    class Gameboard: public Ui_Gameboard {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_GAMEBOARD_H
