package Game;

import IO.Input;
import graphics.Sprite;
import graphics.SpriteSheet;
import graphics.TextureAtlas;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Bullet {
    private float x;
    private float y;
    private float speed;
    private boolean active;
    public static final int SPRITE_SCALE = 8;
    public static final int SPRITES_PER_HEADING = 1;
    public boolean isActive(){
        return  active;
    }

    private enum Heading {
        NORTH(20*SPRITE_SCALE, 6*SPRITE_SCALE, 1*SPRITE_SCALE,1*SPRITE_SCALE),
        EAST(23*SPRITE_SCALE, 6*SPRITE_SCALE, 1*SPRITE_SCALE,1*SPRITE_SCALE),
        SOUTH(22*SPRITE_SCALE, 6*SPRITE_SCALE, 1*SPRITE_SCALE,1*SPRITE_SCALE),
        WEST(21*SPRITE_SCALE, 6*SPRITE_SCALE, 1*SPRITE_SCALE,1*SPRITE_SCALE);

        private int x,y,h,w;
        Heading(int x, int y, int h, int w){
            this.x = x;
            this.y = y;
            this.h = h;
            this.w = w;
        }

        protected BufferedImage texture(TextureAtlas atlas){
            return atlas.cut(x,y,w,h);
        }
    }

    private Bullet.Heading heading;
    private Map<Bullet.Heading, Sprite> spriteMap;
    private float scale;

    public Bullet(float scale, float speed, TextureAtlas atlas){


        //this.heading = heading;
        spriteMap = new HashMap<Bullet.Heading, Sprite>();
        this.scale = scale;
        this.speed = speed;
        this.active = false;


        for(Bullet.Heading h : Bullet.Heading.values()){
            SpriteSheet sheet = new SpriteSheet(h.texture(atlas),SPRITES_PER_HEADING,5 );
            Sprite sprite = new Sprite(sheet,scale);
            spriteMap.put(h, sprite);

    }
    }


    public void update(){
        if(heading == Heading.NORTH){
            y -=speed;
        }else if(heading == Heading.SOUTH){
            y +=speed;
        }else if(heading == Heading.EAST){
            x +=speed;
        }else if (heading == Heading.WEST){
           x -=speed;
        }



      if(y<0){
          deactivate();
      }else if (y>= Game.HEIGTH - SPRITE_SCALE*scale){
          deactivate();
      }
      if(x<0){
          deactivate();
      }else if (x>= Game.WIDTH - SPRITE_SCALE*scale){
          deactivate();
      }

    }

    public void render(Graphics2D g){
        spriteMap.get(heading).render(g, x, y);
    }

    public void activate (float x, float y, String head){
        this.active = true;
        this.x = x;
        this.y = y;
        switch (head){
            case "NORTH": heading = Heading.NORTH;
            break;
            case "WEST": heading = Heading.WEST;
            break;
            case "EAST": heading = Heading.EAST;
            break;
            case "SOUTH": heading = Heading.SOUTH;
        }

    }

    public void deactivate(){
        active = false;
    }

}
