/**
 *  @file
 *  @brief The file contain the Gameboard methods
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

#include "gameboard.h"
#include "ui_gameboard.h"

#include "general.h"
#include "map.h"

// ------------------------------
// Constructor & DeSctructor
// ------------------------------

Gameboard::Gameboard(QWidget *parent) : QMainWindow(parent),  ui(new Ui::Gameboard)
{
    click=false;
    move=false;

    // Init random method
    QTime midnight(0, 0, 0);
    qsrand(midnight.secsTo(QTime::currentTime()));

    ui->setupUi(this);

    // Init grab mouse events
    //grabMouse();

    // Add background screen
    scene = new QGraphicsScene();
    scene->addPixmap(QPixmap(":/images/background2.png"));

    // Init game
    game = new Game();
    //game->draw(scene);

    ui->graphicsView->setScene(scene);
    ui->graphicsView->setGeometry(0,0,SCREEN_WIDTH+2,SCREEN_HEIGHT+2);

    // Set fix windows form size.
    setMinimumSize(SCREEN_WIDTH+2,SCREEN_HEIGHT+2);
    setMaximumSize(SCREEN_WIDTH+2,SCREEN_HEIGHT+2);

    // Set Windows Title
    char tmp[100];
    sprintf(tmp,"%s v%s", APPL_NAME, APPL_VERSION);
    setWindowTitle(tmp);

    // Init statemachine
    timer = new QTimer(this);
    QObject::connect(timer, SIGNAL(timeout()), this, SLOT(statemachine()));
    timer->start(15);
}

Gameboard::~Gameboard()
{
    delete ui;
}

// ------------------------------
// State Machine
// ------------------------------

void Gameboard::statemachine() {

   QPointF pos = mapFromGlobal(cursor->pos());

   ui->label->setText("x="+QString::number(pos.x()));
   ui->label_2->setText("y="+QString::number(pos.y()));

   if (click) {
      ui->label_3->setText("click");
   } else {
       ui->label_3->setText("");
   }

   if (move) {
      ui->label_4->setText("move");
   } else {
      ui->label_4->setText("");
   }
}

void Gameboard::landSelect(int nr) {

   /*if (land[nr]->getOwner()==HUMAN_PLAYER_ID)
   {
      QSound::play("snd/click.wav");
      land[nr]->selected();
   }*/
}

void Gameboard::playerAct() {

   /*for(int player=HUMAN_PLAYER_ID+1; player<=MAX_PLAYERS; player++) {
      for (int id=0; id<(MAX_VERT*MAX_HORZ); id++) {
         if ((land[id]!=NULL) && (land[id]->getOwner()==player)) {
            qDebug() << id;
            int cell=land[id]->getNeigbour();
            qDebug() << cell;
            if ((cell!=0) && (land[cell]!=NULL) && (land[cell]->getOwner()==0)) {
               land[cell]->setOwner(player);
               land[cell]->unselected();
               qDebug() << id << cell;
               break;
            }
            //qDebug() << id;
         }
      }
   }*/
}

// ------------------------------
// User actions
// ------------------------------

void Gameboard::mouseMoveEvent(QMouseEvent *event)
{
   move=true;
}

void Gameboard::mousePressEvent(QMouseEvent *event)
{
    QPointF pos = mapFromGlobal(cursor->pos());

    /*if (event->button() == Qt::LeftButton && !click) {
        click = true;
        for (int i=0; i<maxLand; i++) {
           if((land[i]!=NULL) && (land[i]->isSelected(&pos))){
              ui->label_5->setText(QString::number(i));
              landSelect(i);
           }
        }
    }*/
}

void Gameboard::mouseReleaseEvent(QMouseEvent *event)
{
    if (event->button() == Qt::LeftButton && click) {
        click = false;
        move = false;
    }
}

void Gameboard::on_nextTurnButton_clicked()
{
   qDebug() << "Next Turn";
   playerAct();
}

// ------------------------------
// The End
// ------------------------------




