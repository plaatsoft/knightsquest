/**
 *  @file
 *  @brief The file contain the Type class methods
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

#include "type.h"

// ------------------------------
// Constructor & DeStructor
// ------------------------------

Type::Type(int type1)
{
   image=NULL;
   type=type1;

   qDebug() << "Create Type" << type1;
}

Type::~Type()
{
   qDebug() << "Destroy Type";
}

// ------------------------------
// Other
// ------------------------------

void Type::draw(QGraphicsScene *scene, int x, int y)
{
   QString filename="";

   switch (type) {

    case LAND_SEA:
           filename=":/images/sea.png";
           break;

    case LAND_GRASS:
           filename=":/images/grass.png";
           break;

    case LAND_FOREST:
           filename=":/images/tree.png";
           break;

    case LAND_VILLAGE:
          filename=":/images/village.png";
          break;

    case LAND_CASTLE:
          filename=":/images/castle.png";
          break;

    case LAND_FARMER:
          filename=":/images/farmer.png";
          break;

    case LAND_KNIGHT:
          filename=":/images/knight.png";
          break;

    case LAND_KING:
          filename=":/images/king.png";
          break;

    default:
          image=NULL;
          return;
   }

   // Remove previous image from screen
   if (image!=NULL) {
      scene->removeItem(image);
      delete image;
   }

   // Add image to screen
   image= new QGraphicsPixmapItem(QPixmap(filename));
   image->setPos(QPoint(x,y));
   scene->addItem(image);
}

// ------------------------------
// Setters and Getters
// ------------------------------

void Type::setType(int type1) {
   type=type1;
}

int Type::getType(void) {
   return type;
}

// ------------------------------
// The End
// ------------------------------
