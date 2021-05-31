/**
	This is a template for a Java file.
	
	@author Wilbert Meinard L. Chen (201153)
    @author Lhance Christian S. Wong (205467)
	@version May 31, 2021
**/

/*
	I have not discussed the Java language code in my program 
	with anyone other than my instructor or the teaching assistants 
	assigned to this course.

	I have not used Java language code obtained from another student, 
	or any other unauthorized source, either modified or unmodified.

	If any Java language code or documentation used in my program 
	was obtained from another source, such as a textbook or website, 
	that has been clearly noted with a proper citation in the comments 
	of my program.
*/

import java.awt.Graphics2D;
import java.util.concurrent.CopyOnWriteArrayList;


public class BulletController implements GameObject{
    private CopyOnWriteArrayList<ShipBullet> shipBulletArray;
    private CopyOnWriteArrayList<BossBullet> bossBulletArray;
    private String sDataOut, sDataIn;
    private AudioPlayer ap;
    private int max;
    private Player player;
    private Boss boss;
    private boolean isPlayer;
    private double bossTimer;

    /*
    Instantiates variables if a Player is given  
    */
    public BulletController (Player player){
        switch(player.getShipType()) {
                case 1: //offensive
                    max = 5;
                    break;
                case 2: //balanced
                    max = 3;
                    break;
                case 3: //defensive
                    max = 1;
                    break;
                default:
                    max = 1;
                    break;
        }
        this.player = player;
        isPlayer = true;
        ap = new AudioPlayer();
        shipBulletArray = new CopyOnWriteArrayList<ShipBullet>();

        sDataOut = "";
        sDataIn = "";
        bossTimer = 0;
    }

    /*
    Instantiates variables if a Boss is given  
    */
    public BulletController (Boss boss){
        this.boss = boss;
        isPlayer = false;
        bossBulletArray = new CopyOnWriteArrayList<BossBullet>();
        bossTimer = 0;
        ap = new AudioPlayer();
        sDataOut = "";
        sDataIn = "";
    }

    /*
    Draws all the created bullets,
    all ShipBullets if Player,
    all BossBullets if Boss.
    */
    @Override
    public void draw(Graphics2D g2d) {
        if (isPlayer){
            if (player.isAlive){
                for (ShipBullet i : shipBulletArray){
                    i.draw(g2d);
                }
            }
        }
        else {
            if (boss.isAlive){
                for (BossBullet i : bossBulletArray){
                    i.draw(g2d);
                }
            }
        }
        
    }

    /*
    This moves all the bullets and removes them if they either collide with
    an opposing hitbox or leave window borders.
    It also creates a string of data to be sent to the server
    */
    @Override
    public void update(double deltaTime) {
        bossTimer += deltaTime;

        if (isPlayer){
            sDataOut = "BC_" + String.valueOf(max) + "_";
            if (shipBulletArray.size() > 0){
                for (ShipBullet i : shipBulletArray){
                    if (i.getX() > GameUtils.get().getWidth()){
                        shipBulletArray.remove(i);
                        continue;
                    }
                    i.update(deltaTime);
                    sDataOut = sDataOut.concat(String.format("%.2f_%.2f_",i.getX(),i.getY()));
                }
            }
        } else {
            sDataOut = "BBC_";
            bossTimer += deltaTime;
            if (bossBulletArray.size() > 0){
                for (BossBullet i : bossBulletArray){
                    if (i.getX() < -100){
                        bossBulletArray.remove(i);
                        continue;
                    }
                    i.update(deltaTime);
                    sDataOut = sDataOut.concat(String.format("%.2f_%.2f_%d_",i.getX(),i.getY(),i.getAtkType()));
                }
            }
            if (bossTimer > 3){
                if (boss.getBossHP() > 30){
                    addBullet();
                    sDataOut = sDataOut.concat(String.format("%.2f_%.2f_%d_",boss.getX()+100,boss.getY()+175,1));
                    bossTimer = 0;
                }
                else if (boss.getBossHP() <= 30){
                    addBullet();
                    sDataOut = sDataOut.concat(String.format("%.2f_%.2f_%d_",boss.getX()+100,boss.getY()+175,2));
                    bossTimer = 2;
                }
            }
        }

        readStringData(sDataIn);
    }

    /*
    This receives data from the server which contains the 
    bullet locations of the ShipBullets and BossBullets.
    */
    @Override
    public void readStringData(String s) {
        String[] data = s.split("_");
        /* if(!s.equals("") && !s.equals("p1BC_") && 
           !s.equals("p2BC_") && !s.equals("BBC_") ) { */
        if (data.length > 2){
            if(isPlayer) {
                max = Integer.parseInt(data[1]);
                shipBulletArray.clear();
                for(int i = 2; i < data.length; i += 2) {
                    double x = Double.parseDouble(data[i]);
                    double y = Double.parseDouble(data[i+1]);
                    shipBulletArray.add(new ShipBullet(x,y));
                }

            } else {
                bossBulletArray.clear();
                for(int i = 1; i < data.length; i += 3) {
                    double x = Double.parseDouble(data[i]);
                    double y = Double.parseDouble(data[i+1]);
                    int z = Integer.parseInt(data[i+2]);
                    bossBulletArray.add(new BossBullet(x,y,z));
                }
            }
        }
    }

    /*
    If Player, This checks if the player already has created the max amount of bullets possible
    for its corresponding ship type. If not, it will create a ShipBullet.
    If Boss, it will make a corresponding BossBullet depending on its current health.
    */
    public void addBullet(){
        if (isPlayer){
            if(player.isAlive) {
                if (shipBulletArray.size() < max){
                    switch(player.getShipType()) {
                        case 1: //offensive
                            shipBulletArray.add(new ShipBullet(player.getX() + 180, player.getY() + 37));
                            break;
                        case 2: //balanced
                            shipBulletArray.add(new ShipBullet(player.getX() + 130, player.getY() + 50));
                            break;
                        case 3: //defensive
                            shipBulletArray.add(new ShipBullet(player.getX() + 130, player.getY() + 55));
                            break;
                        default:
                            shipBulletArray.add(new ShipBullet(player.getX() + 130, player.getY() + 50));
                            break;
                    }
                    ap.playSound("sounds/shoot.wav",GameUtils.get().getIsMaster());
                }
            }
        }
        else {
            if(boss.isAlive) {
                if (boss.getBossHP() > 100){
                    bossBulletArray.add(new BossBullet(boss.getX() + 100, boss.getY() + 175, 1) );
                    ap.playSound("sounds/fireball.wav", GameUtils.get().getIsMaster());
                }
                else if ( boss.getBossHP() <= 100){
                    bossBulletArray.add(new BossBullet(boss.getX() + 100, boss.getY() + 175, 2) );
                    ap.playSound("sounds/fireball.wav", GameUtils.get().getIsMaster());
                }
            }
        }


    }

    /*
    This sets the new maximum amount of bullets the Player can shoot
    depending on the ship type.
    */
    public void setNewMax(int shipType){
        switch(shipType) {
                case 1: //offensive
                    max = 5;
                    break;
                case 2: //balanced
                    max = 3;
                    break;
                case 3: //defensive
                    max = 1;
                    break;
                default:
                    max = 1;
                    break;
        }
    }

    /*
    Removes a ShipBullet
    */
    public void removeShipBullet(ShipBullet bullet){
        shipBulletArray.remove(bullet);
    }

    /*
    Removes a BossBullet
    */
    public void removeBossBullet(BossBullet bullet){
        bossBulletArray.remove(bullet);
    }

    /*
    This returns the shipBulletArray.
    */
    public CopyOnWriteArrayList<ShipBullet> getshipBulletArray(){
        return shipBulletArray;
    }

    /*
    returns bossBulletArray
    */
    public CopyOnWriteArrayList<BossBullet> getbossBulletArray(){
        return bossBulletArray;
    }


    @Override
    public String getCompressedData() {
        return sDataOut;
    }


    @Override
    public void receiveCompressedData(String sDataIn) {
        this.sDataIn = sDataIn;
    }    

}
