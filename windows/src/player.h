/**
 *  @file
 *  @brief The file contain the Player class
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

#ifndef PLAYER_H
#define PLAYER_H

#include <QtGui>

enum {
   PLAYER1=1,   // Kingdom 1 (Human)
   PLAYER2=2,   // Kingdom 2 (Computer)
   PLAYER3=3,   // Kingdom 3 (Computer)
   PLAYER4=4,   // Kingdom 4 (Computer)
   PLAYER5=5,   // Kingdom 5 (Computer)
};

class Player
{
private:
    int id;
    QString name;

public:
    // Constructor & Destructor
    Player();
    Player(QString name1, int id1);
    ~Player();

    // Setters
    void setId(int id1);
    void setName(QString name1);

    // Getters
    int getId();
    QString getName(void);
    QColor getDarkColor();
    QColor getLightColor();
};

#endif // PLAYER_H
