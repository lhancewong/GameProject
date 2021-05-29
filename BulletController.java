import java.awt.Graphics2D;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.concurrent.CopyOnWriteArrayList;


public class BulletController implements GameObject{
    private ShipBullet bullet;
    private CopyOnWriteArrayList<ShipBullet> shipBulletArray, tempArray;
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
                if (i.getX() > GameUtils.get().getWidth()){
                    shipBulletArray.remove(i);
                    continue;
                }
                i.draw(g2d);
            }
        }
        else {
            for (BossBullet i : bossBulletArray){
                if (i.getX() < 0){
                    bossBulletArray.remove(i);
                    continue;
                }
                i.draw(g2d);
            }
        }
        
    }

    @Override
    public void update(double deltaTime) {

        bossTimer += deltaTime;
        if (isPlayer){
            sDataOut = player.getPlayerName() + "BC_";
            if (shipBulletArray.size() > 0){
                for (ShipBullet i : shipBulletArray){
                    i.update(deltaTime);
                    sDataOut = sDataOut.concat(String.format("%.2f_%.2f_",i.getX(),i.getY()));
                }
        }
        }
        else {
            bossTimer += deltaTime;
            if (bossBulletArray.size() > 0){
                for (BossBullet i : bossBulletArray){
                    i.update(deltaTime);
                }
            }
            if (bossTimer > 3){
                if (boss.getBossHP() > 5){
                    addBullet();
                    bossTimer = 0;
                }
                else if (boss.getBossHP() <= 5){
                    addBullet();
                }
            }
        }
        readStringData(sDataIn);
    }

    @Override
    public void readStringData(String s) {
        if(!s.equals("") && !s.equals("p1BC_") && !s.equals("p2BC_")) {
            String[] data = s.split("_");
            shipBulletArray.clear();
            for(int i = 1; i < data.length; i += 2) {
                double x = Double.parseDouble(data[i]);
                double y = Double.parseDouble(data[i+1]);
                //System.out.println("|x:"+x+"|y:"+y+"|");
                shipBulletArray.add(new ShipBullet(x,y));
            }

        } 
        
    }

    public void addBullet(){
        if (isPlayer){
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
        else {
            if (boss.getBossHP() > 5){
                bossBulletArray.add(new BossBullet(boss.getX() + 100, boss.getY() + 175, 1) );
            }
            else if ( boss.getBossHP() <= 5){
                bossBulletArray.add(new BossBullet(boss.getX() + 100, boss.getY() + 175, 2) );
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
    public byte[] getCompressedData() {
        return sDataOut.getBytes();
    }

    @Override
    public void receiveCompressedData(String sDataIn) {
        this.sDataIn = sDataIn;
    }    

}
