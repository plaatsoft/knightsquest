/**
 *  @file
 *  @brief The file contain the Land methods
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

#include "land.h"
#include "map.h"

// ------------------------------
// Constructor & DeSctructor
// ------------------------------

Land::Land()
{
   x=0;
   y=0;
   id=0;
   player=NULL;
   type=NULL;

   qDebug() << "Create Land";
}

Land::Land(int x1, int y1, Player *player1, Type *type1, int id1)
{
   x=x1;
   y=y1;
   id=id1;
   player=player1;
   type=type1;

   qDebug() << "Create Land" << id << x << y << type << player;
}

Land::~Land()
{
   x=0;
   y=0;
   player=NULL;
   type=NULL;
   id=0;

   qDebug() << "Destroy Land";
}

// ------------------------------
// Setters
// ------------------------------

void Land::setX(int x1) {
   x=x1;
}

void Land::setY(int y1) {
   y=y1;
}

void Land::setId(int id1) {
   id=id1;
}

void Land::setPlayer(Player *player1) {
   player=player1;
}

void Land::setType(Type *type1) {
   type=type1;
}

// ------------------------------
// Owner
// ------------------------------

void Land::draw(QGraphicsScene *scene1)
{
   int xOffset=10+(x*10);
   int yOffset=0;
   if (y%2) {
        xOffset=30+(x*10);
   }

   // Create Polygon
   polygon  << QPoint(0+(x*30)+xOffset,  20+(y*10)+yOffset)
            << QPoint(10+(x*30)+xOffset, 10+(y*10)+yOffset)
            << QPoint(20+(x*30)+xOffset, 10+(y*10)+yOffset)
            << QPoint(30+(x*30)+xOffset, 20+(y*10)+yOffset)
            << QPoint(20+(x*30)+xOffset, 30+(y*10)+yOffset)
            << QPoint(10+(x*30)+xOffset, 30+(y*10)+yOffset);

   // Store screne
   scene=scene1;

   // Create Brush   
   brush.setStyle(Qt::SolidPattern);
   brush.setColor(player->getDarkColor());

   // Add Polygon on screen
   scene->addPolygon(polygon,pen,brush);

   type->draw(scene, (x*30)+xOffset+8, (y*10)+yOffset+12 );
}

bool Land::isSelected(QPointF *pos1) {

   bool result;
   QPointF tmp;
   tmp.setX(pos1->x());
   tmp.setY(pos1->y());

   result= polygon.containsPoint(tmp,Qt::OddEvenFill);

   return result;
}

void Land::selected(void)
{
   // Create Brush
   QBrush brush;
   brush.setStyle(Qt::SolidPattern);
   brush.setColor(player->getLightColor());

   // Create Pen
   QPen pen;
   pen.setColor(Qt::black);

   // Add Polygon on screen
   scene->addPolygon(polygon,pen,brush);

   // Add Image on screen
   /*if (image!=NULL) {
      scene->removeItem(image);
   }
   scene->addItem(image);*/
}

void Land::unselected(void)
{
   // Create Brush
   QBrush brush;
   brush.setStyle(Qt::SolidPattern);
   brush.setColor(player->getDarkColor());

   // Create Pen
   QPen pen;
   pen.setColor(Qt::black);

   // Add Polygon on screen
   scene->addPolygon(polygon,pen,brush);

   // Add Image on screen
   /*if (image!=NULL) {
      scene->removeItem(image);
   }
   scene->addItem(image);*/
}

Player * Land::getPlayer(void) {
   return player;
}

Type * Land::getType(void) {
   return type;
}

int Land::getNeigbour(void)
{
    int returnValue=0;

    // Init random method
    QTime midnight(0, 0, 0);
    qsrand(midnight.secsTo(QTime::currentTime()));
    int value=qrand() % 5;
    qDebug() << "Random" << player << value;

    switch (value)
    {
       case 0:
          returnValue=id-16;
          break;

       case 1:
          returnValue=id-30;
          break;

       case 2:
          returnValue=id-17;
          break;

       case 3:
          returnValue=id+14;
          break;

       case 4:
          returnValue=id+30;
          break;

       case 5:
          returnValue=id+15;
          break;
    }

    // Check for location out-site map
    if (returnValue<0) {
       returnValue=0;
    }

    return returnValue;
}

// ------------------------------
// The End
// ------------------------------
