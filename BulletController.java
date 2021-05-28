import java.awt.Graphics2D;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.concurrent.CopyOnWriteArrayList;

public class BulletController implements GameObject{
    private ShipBullet bullet;
    private CopyOnWriteArrayList<ShipBullet> shipBulletArray;
    private int max;
    private Player player;

    public BulletController (String type, Player player){
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
        shipBulletArray = new CopyOnWriteArrayList<ShipBullet>();
    }

    @Override
    public void draw(Graphics2D g2d) {
        for (ShipBullet i : shipBulletArray){
            if (i.getX() > GameUtils.get().getWidth()){
                shipBulletArray.remove(i);
                continue;
            }
            i.draw(g2d);
        }
        
    }

    @Override
    public void update(double deltaTime) {
        if (shipBulletArray.size() > 0){
            for (ShipBullet i : shipBulletArray){
                i.update(deltaTime);
            }

        }
        
    }

    public void addBullet(){
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

    public void removeBullet(ShipBullet bullet){
        shipBulletArray.remove(bullet);
    }

    public CopyOnWriteArrayList<ShipBullet> getshipBulletArray(){
        return shipBulletArray;
    }

    @Override
    public void sendCompressedData(DataOutputStream dataOut) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void receiveCompressedData(DataInputStream dataIn) {
        // TODO Auto-generated method stub
        
    }    

}
