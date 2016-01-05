/**
 *  @file
 *  @brief The file contain the Game methods
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

#include "game.h"

// ------------------------------
// Constructor & DeSctructor
// ------------------------------

Game::Game()
{
   // Create Players
   players[0] = new Player("Player 1 (Human)", 1);
   players[1] = new Player("Player 2 (Computer)", 2);
   players[2] = new Player("Player 3 (Computer)", 3);
   players[3] = new Player("Player 4 (Computer)", 4);
   players[4] = new Player("Player 5 (Computer)", 5);

   // Create map grid
   map = new Map(players);
}

Game::~Game()
{
}

// ------------------------------
// Other
// ------------------------------

void Game::draw(QGraphicsScene *scene)
{
   map->draw(scene);
}

// ------------------------------
// Getters
// ------------------------------

Player *Game::getPlayer(int id)
{
  for (int i=0; i<MAX_PLAYERS; i++)
  {
     if (players[i]->getId()==id)
     {
        return players[i];
     }
  }
  return NULL;
}

// ------------------------------
// The End
// ------------------------------
