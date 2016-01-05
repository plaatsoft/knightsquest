/**
 *  @file
 *  @brief The file contain the Type class
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

#ifndef TYPE_H
#define TYPE_H

#include <QtGui>

enum {
  // Land types
  LAND_SEA=0,
  LAND_GRASS=1,
  LAND_FOREST=2,
  LAND_VILLAGE=3,
  LAND_CASTLE=4,

  // People types
  LAND_FARMER=5,
  LAND_KNIGHT=6,
  LAND_KING=7
};

class Type
{
  private:
     QGraphicsPixmapItem *image;
     int type;

  public:
     // Constructor & destructors
     Type(int type1);
     ~Type();

     // Other
     void draw(QGraphicsScene *scene,int x, int y);

     // Getters / Setters
     void setType(int type1);
     int getType(void);
};

#endif // TYPE_H
