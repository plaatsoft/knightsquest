/**
 *  @file
 *  @brief The file contain the Gameboard class
 *  @author wplaat
 *
 *  Copyright (C) 2008-2010 PlaatSoft
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, version 2.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

#ifndef GAMEBOARD_H
#define GAMEBOARD_H

#include <QtGui>

#include "general.h"
#include "game.h"

namespace Ui {
    class Gameboard;
}

class Gameboard : public QMainWindow
{
    Q_OBJECT

private:
    Ui::Gameboard *ui;
    QGraphicsScene *scene;
    QCursor *cursor;
    QTimer *timer;
    Game *game;

    bool click;
    bool move;

    // private methods
    void landSelect(int nr);
    void playerAct(void);

private slots:
    void on_nextTurnButton_clicked();

protected:
    void mousePressEvent(QMouseEvent *event);
    void mouseReleaseEvent(QMouseEvent *event);
    void mouseMoveEvent(QMouseEvent *event);

 public Q_SLOTS:
      void statemachine();

public:
    explicit Gameboard(QWidget *parent = 0);
    ~Gameboard();
};

#endif // GAMEBOARD_H
