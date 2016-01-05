/**
 *  @file
 *  @brief The file contain the Map class methods
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

#include <QtGui>

#include "general.h"
#include "map.h"
#include "gameboard.h"

// ------------------------------
// Constructor & DeStructor
// ------------------------------

Map::Map(Player *players[MAX_PLAYERS])
{
   qDebug() << "Create Map";

   initMap("/maps/map01.xml", players);
}

Map::~Map()
{
   qDebug() << "Destroy Map";
}

// ------------------------------
// Load xml map and process data
// ------------------------------

void Map::draw(QGraphicsScene *scene)
{
   for (int i=0; i<(MAX_VERT*MAX_HORZ); i++) {
      land[i]->draw(scene);
   }
}

void Map::initMap(QString filename, Player *players[MAX_PLAYERS])
{
   // Create directorty + filename
   QSettings settings("PlaatSoft", APPL_NAME);
   QString target = settings.value("target", "").toString();
   qDebug() << target;
   QString fileName = target+filename;

   // Open xml file
   QFile file(fileName);
   if (!file.open(QFile::ReadOnly)) {
      qDebug() << fileName << "not found!";
   }

   // Parse XML data
   QXmlStreamReader reader(&file);
   int y=0;
   int i=0;
   while (!reader.atEnd()) {
      reader.readNext();
      if (reader.isStartElement()) {
          if(reader.name() == "line") {
             //QString id=reader.attributes().value("id").toString();
             QString line=reader.attributes().value("data").toString();

             for (int x=0; x<MAX_HORZ; x++){
               land[i]=new Land(x, y, getPlayer([line[x]), getType(line[x]), i);
               i++;
             }
             y++;
         }
      }
   }
}

Player * Map::getPlayer(QCharRef input) {

   switch ( input.toAscii() )
   {
     // Kingdom 1 (Human)
     case '1':
         return players[0];

     // Kingdom 2 (Computer)
     case '2':
         return players[1];

     // Kingdom 3 (Computer)
     case '3':
         return players[2];

     // Kingdom 4 (Computer)
     case '4':
         return players[3];

     // Kingdom 5 (Computer)
     case '5':
         return players[4];

     // No player
     default:
        return NULL;
   }
}

Type * Map::getType(QCharRef input) {

   switch ( input.toAscii() )
   {
     // Land
     case '1':
         return new Type(LAND_GRASS);

     // Sea
     default:
         return new Type(LAND_SEA);
   }
}

// ------------------------------
// The End
// ------------------------------
