package com.beryl.testgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import java.util.Iterator;

public class MainTestGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture playerImg, bulletImg;
        Sound laser, hit;
        Music bgMusic;
	OrthographicCamera camera;
        Rectangle player;
        Array<Rectangle> bullets;
        long lastBullet;
        
        public void createPlayer(){
            player = new Rectangle();
            player.x = 1300/2 - 150/2;
            player.y = 0;
            player.width = 100;
            player.height = 100;
        }
        
        public void spawnBullet(){
            Rectangle bullet=new Rectangle();
            bullet.width = 75;
            bullet.height = 300;
            bullet.x = MathUtils.random(0, 1300-75);
            bullet.y = 720;
            bullets.add(bullet);
            lastBullet = TimeUtils.nanoTime();
            //laser.play();
        }
        
	@Override
	public void create () {
		batch = new SpriteBatch();
                camera = new OrthographicCamera();
                camera.setToOrtho(false, 1300, 720);
    
		playerImg = new Texture("26450.png");
                bulletImg = new Texture("redLaserRay.png");
                
                laser = Gdx.audio.newSound(Gdx.files.internal("Laser_Gun-Mike_Koenig-1975537935.mp3"));
                hit = Gdx.audio.newSound(Gdx.files.internal("punch_or_whack_-Vladimir-403040765.mp3"));
                bgMusic = Gdx.audio.newMusic(Gdx.files.internal("Bloodborne DLC OST - Ludwig the Holy Blade.mp3"));
             
                bgMusic.setLooping(true);
                bgMusic.play();
                
                createPlayer();
                bullets = new Array<Rectangle>();
                spawnBullet();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                camera.update();
                
                batch.setProjectionMatrix(camera.combined);
                
		batch.begin();
		batch.draw(playerImg, player.x, player.y);
                for(Rectangle bullet : bullets){
                    batch.draw(bulletImg, bullet.x, bullet.y);
                }
		batch.end();
                
                if(Gdx.input.isTouched()){
                    Vector3 touchPos = new Vector3();
                    touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                    camera.unproject(touchPos);
                    player.x=touchPos.x - 150/2;
                }
                
                if(Gdx.input.isKeyPressed(Keys.LEFT)) player.x -= 400 * Gdx.graphics.getDeltaTime();
                if(Gdx.input.isKeyPressed(Keys.RIGHT)) player.x += 400 * Gdx.graphics.getDeltaTime();
                
                if(player.x<0){
                    player.x=0;
                }
                if(player.x>1300-150){
                    player.x=1300-150;
                }
                
                if(TimeUtils.nanoTime() - lastBullet > MathUtils.random(200000000, 1000000000)){
                    spawnBullet();
                }
                
                Iterator<Rectangle> iter = bullets.iterator();
                while(iter.hasNext()){
                    Rectangle bullet = iter.next();
                    bullet.y -= 500*Gdx.graphics.getDeltaTime();
                    if(bullet.y + 300 < 0){
                        iter.remove();
                    }
                    if(bullet.overlaps(player)){
                        hit.play();
                        iter.remove();
                    }
                }
                
                
	}
	
	@Override
	public void dispose () {
		batch.dispose();
                laser.dispose();
                hit.dispose();
                bulletImg.dispose();
                bgMusic.dispose();
		playerImg.dispose();
	}
}
