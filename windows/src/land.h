/**
 *  @file
 *  @brief The file contain the Land class
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

#ifndef LAND_H
#define LAND_H

#include <QtGui>
#include <player.h>
#include <type.h>

class Land
{

private:
   // Private variables
   int x;
   int y;
   int id;
   Player *player;
   Type *type;
   QPolygonF polygon;
   QBrush brush;
   QPen pen;
   QGraphicsScene *scene;

public:
    // Constructor & destructors
    Land();
    Land(int x1, int y1, Player *player1, Type *type1, int id1);
    ~Land();

    // public methods
    void draw(QGraphicsScene *scene);
    bool isSelected(QPointF *pos1);
    void selected(void);
    void unselected(void);

    // public Setters
    void setX(int x1);
    void setY(int y1);
    void setId(int id1);
    void setPlayer(Player *player1);
    void setType(Type *type1);

    // public Getters
    Player * getPlayer(void);
    Type * getType(void);
    int getNeigbour(void);
};

#endif // LAND_H
