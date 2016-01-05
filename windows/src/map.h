/**
 *  @file
 *  @brief The file contain the Map class
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

#ifndef MAP_H
#define MAP_H

#include <QtGui>

#include "general.h"
#include "land.h"

class Map
{
  private:
    int cell[MAX_HORZ][MAX_VERT];
    Land *land[MAX_LAND];

    // private methods
    void initMap(QString filename, Player *players[MAX_PLAYERS]);
    Player * getPlayer(QCharRef input);
    Type * getType(QCharRef input);

 public:
    // Constructor & Destructor
     Map(Player *players[MAX_PLAYERS]);
     ~Map();

     // public methods
     void draw(QGraphicsScene *scene1);

};

#endif // MAP_H
