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
    private int max;
    private Player player;
    private Boss boss;
    private boolean isPlayer;
    private double bossTimer;

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
        shipBulletArray = new CopyOnWriteArrayList<ShipBullet>();

        sDataOut = "";
        sDataIn = "";
        bossTimer = 0;
    }

    public BulletController (Boss boss){
        this.boss = boss;
        isPlayer = false;
        bossBulletArray = new CopyOnWriteArrayList<BossBullet>();
        bossTimer = 0;

        sDataOut = "";
        sDataIn = "";
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (isPlayer){
            for (ShipBullet i : shipBulletArray){
                i.draw(g2d);
            }
        }
        else {
            for (BossBullet i : bossBulletArray){
                i.draw(g2d);
            }
        }
        
    }

    @Override
    public void update(double deltaTime) {
        bossTimer += deltaTime;

        if (isPlayer){
            sDataOut = "BC_";
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

    @Override
    public void readStringData(String s) {
        if(!s.equals("") && !s.equals("p1BC_") && 
           !s.equals("p2BC_") && !s.equals("BBC_") ) {
            if(isPlayer) {
                String[] data = s.split("_");
                shipBulletArray.clear();
                for(int i = 1; i < data.length; i += 2) {
                    double x = Double.parseDouble(data[i]);
                    double y = Double.parseDouble(data[i+1]);
                    shipBulletArray.add(new ShipBullet(x,y));
                }

            } else {
                String[] data = s.split("_");
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
                }
            }
        }
        else {
            if(boss.isAlive) {
                if (boss.getBossHP() > 30){
                    bossBulletArray.add(new BossBullet(boss.getX() + 100, boss.getY() + 175, 1) );
                }
                else if ( boss.getBossHP() <= 30){
                    bossBulletArray.add(new BossBullet(boss.getX() + 100, boss.getY() + 175, 2) );
                }
            }
        }


    }

    public void removeShipBullet(ShipBullet bullet){
        shipBulletArray.remove(bullet);
    }

    public void removeBossBullet(BossBullet bullet){
        bossBulletArray.remove(bullet);
    }

    public CopyOnWriteArrayList<ShipBullet> getshipBulletArray(){
        return shipBulletArray;
    }

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
