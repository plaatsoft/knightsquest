/**
 *  @file
 *  @brief The file contain the Game class
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

#ifndef GAME_H
#define GAME_H

#include "map.h"
#include "player.h"

class Game
{

private:
   Map *map;
   Player *players[MAX_PLAYERS];

public:
   // Constructor & Destructor
    Game();
    ~Game();

    // methods
    void draw(QGraphicsScene *scene);
    Player *getPlayer(int id);
};

#endif // GAME_H
