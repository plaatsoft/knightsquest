/**
 *  @file
 *  @brief The file contain the Player class methods
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

#include "player.h"

// ------------------------------
// Constructor & DeStructor
// ------------------------------

Player::Player()
{
   id=0;
   name="";

   qDebug() << "Create Player " << name << id;
}

Player::Player(QString name1, int id1)
{
   id=id1;
   name=name1;

   qDebug() << "Create Player " << name << id;
}

Player::~Player() {

   qDebug() << "Destroy Player " << name << id;

}

// ------------------------------
// Setters
// ------------------------------

void Player::setId(int id1) {
   id=id1;
}

void Player::setName(QString name1) {
   name=name1;
}

// ------------------------------
// Getters
// ------------------------------

int Player::getId(void) {
   return id;
}

QString Player::getName(void) {
   return name;
}

QColor Player::getDarkColor()
{
   switch (id) {
     case PLAYER1:
            return Qt::darkGreen;
     case PLAYER2:
            return Qt::darkCyan;
     case PLAYER3:
            return Qt::darkMagenta;
     case PLAYER4:
            return Qt::darkBlue;
     case PLAYER5:
            return QColor(0, 0, 255, 127);
   }
   return Qt::darkGray;
}

QColor Player::getLightColor()
{
   switch (id) {
     case PLAYER1:
            return Qt::green;
     case PLAYER2:
            return Qt::cyan;
     case PLAYER3:
            return Qt::magenta;
     case PLAYER4:
            return Qt::blue;
     case PLAYER5:
            return QColor(0, 255, 255, 127);
   }
   return Qt::gray;
}

// ------------------------------
// The end
// ------------------------------

