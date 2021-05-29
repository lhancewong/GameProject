import java.awt.Graphics2D;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.concurrent.CopyOnWriteArrayList;

public class BulletController implements GameObject{
    private ShipBullet bullet;
    private CopyOnWriteArrayList<ShipBullet> shipBulletArray, tempArray;
    private int max;
    private Player player;
    private String sDataOut, sDataIn;

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
        shipBulletArray = new CopyOnWriteArrayList<ShipBullet>();
        tempArray = new CopyOnWriteArrayList<ShipBullet>();

        sDataOut = "";
        sDataIn = "";
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
        sDataOut = player.getPlayerName() + "BC_";
        if (shipBulletArray.size() > 0){
            for (ShipBullet i : shipBulletArray){
                i.update(deltaTime);
                sDataOut = sDataOut.concat(String.format("%.2f_%.2f_",i.getX(),i.getY()));
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

            //shipBulletArray = tempArray;
            //tempArray.clear();

            /* String[] data = s.split("_");
            for (String i : data) {
                i.trim();
                if(i.equals("b0")) {

                }
            } */


            /* int diff = ((data.length-1)/2)-shipBulletArray.size();
            if(diff < 0) { //data has less bullets than controller
                for(int i = -diff; i <= 0; i++) {
                    shipBulletArray.remove(i);
                }
            } else if (diff > 0) { //data has more bullets than controller
                for(int i = diff; i >= 0; i++) {
                    shipBulletArray.add(new ShipBullet(0,0));
                }
            } */
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
    public byte[] getCompressedData() {
        return sDataOut.getBytes();
    }

    @Override
    public void receiveCompressedData(String sDataIn) {
        this.sDataIn = sDataIn;
    }    

}
