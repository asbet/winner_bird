package com.betulas.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class SurvivorBird extends ApplicationAdapter {
	Texture background;
	Texture bird;
	SpriteBatch batch;
	float birdX=0;
	float birdY=0;
	int gameState=0;
	Random random;
	float velocity=0; //Artan hız integer
	float gravity=0.1f;
	Texture bee;
	Texture bee2;
	Texture bee3;
	float enemyVelocity=2;
	int numberOfEnemies=4;
	int score=0;
	int scoreEnemy=0;
	BitmapFont bitmapFont;
	BitmapFont gameOver;

	
	float [] enemyX=new float[numberOfEnemies];

	float []EnemyOffset=new float[numberOfEnemies];
	float [] EnemyOffset2=new float[numberOfEnemies];
	float [] EnemyOffset3=new float[numberOfEnemies];
	float distance=0;

	Circle birdcircle;
	Circle [] enemyCircles;
	Circle [] enemyCircles2;
	Circle [] enemyCircles3;
	ShapeRenderer shapeRenderer;


	@Override
	public void create () {
		shapeRenderer=new ShapeRenderer(); 
		batch=new SpriteBatch();
		bitmapFont=new BitmapFont();
		bitmapFont.setColor(Color.WHITE);
		bitmapFont.getData().setScale(4);
		gameOver=new BitmapFont();
		gameOver.setColor(Color.BLACK);
		gameOver.getData().setScale(9);

		background=new Texture("Background.png");
		bee=new Texture("enemy.png");
		bee2=new Texture("enemy.png");
		bee3=new Texture("enemy.png");
		bird=new Texture("birds.png");
		birdX=Gdx.graphics.getWidth()/2-bird.getHeight()/2 ;
		birdY=Gdx.graphics.getHeight()/3;
		birdcircle=new Circle();
		enemyCircles=new Circle[numberOfEnemies];
		enemyCircles2=new Circle[numberOfEnemies];
		enemyCircles3=new Circle[numberOfEnemies];
		birdcircle=new Circle();
		//distance farklı grup arıların arasındaki mesafe
		distance=Gdx.graphics.getWidth()/2;
		random=new Random();
		for(int i=0;i<numberOfEnemies;i++){
			EnemyOffset[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()- 200);
			EnemyOffset2[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()- 200);
			EnemyOffset3[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()- 200);
			enemyX[i]=Gdx.graphics.getWidth()-bee.getWidth()/2 + i*distance;
			enemyCircles[i]=new Circle();
			enemyCircles2[i]=new Circle();
			enemyCircles3[i]=new Circle();
		}

	}

	@Override
	public void render () {	
		batch.begin();
		batch.draw(background,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		if(gameState==1) {
			if(enemyX[scoreEnemy]<Gdx.graphics.getWidth()/2-bird.getHeight()/2 ){
				score ++;
				if(scoreEnemy<numberOfEnemies-1){
					scoreEnemy++;
				}else{
					scoreEnemy =0;
				}
			}
			if (Gdx.input.justTouched()) {
				velocity = -14;
			}
			
			for (int i = 0; i < numberOfEnemies; i++) {//
				if(enemyX[i]<Gdx.graphics.getWidth() / 15){
					enemyX[i]=enemyX[i] +numberOfEnemies*distance;
					EnemyOffset[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
					EnemyOffset2[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
					EnemyOffset3[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
				}else{
					enemyX[i] = enemyX[i] - enemyVelocity;
				}


				batch.draw(bee, enemyX[i], Gdx.graphics.getHeight()/2+EnemyOffset[i], Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);
				batch.draw(bee2, enemyX[i], Gdx.graphics.getHeight()/2+EnemyOffset2[i], Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);
				batch.draw(bee3, enemyX[i], Gdx.graphics.getHeight()/2+EnemyOffset3[i], Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);
				//Bee leri x ekseninden sol tarafa kaydırmak istiyeceğiz.
				enemyCircles[i]=new Circle(enemyX[i]+Gdx.graphics.getWidth() / 30,Gdx.graphics.getHeight()/2+EnemyOffset[i]+Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);
				enemyCircles2[i]=new Circle(enemyX[i]+Gdx.graphics.getWidth() / 30,Gdx.graphics.getHeight()/2+EnemyOffset2[i]+Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);
				enemyCircles3[i]=new Circle(enemyX[i]+Gdx.graphics.getWidth() / 30,Gdx.graphics.getHeight()/2+EnemyOffset3[i]+Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);
			}

			if (birdY > 0 ) {
				velocity = velocity + gravity;
				birdY = birdY - velocity;
			}
			else{
				gameState=2;
			}

		}
		else if(gameState==0){
			if(Gdx.input.justTouched()){
				gameState=1;
			}
		} else if(gameState==2){
			gameOver.draw(batch,"Game over! Tap to play again!",100,Gdx.graphics.getHeight()/2);
			if(Gdx.input.justTouched()){
				System.out.println(gameState);
				gameState=1;
				birdY=Gdx.graphics.getHeight()/3;
				for(int i=0;i<numberOfEnemies;i++){
					EnemyOffset[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
					EnemyOffset2[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
					EnemyOffset3[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
					enemyX[i]=Gdx.graphics.getWidth()-bee.getWidth()/2 + i*distance;
					enemyCircles[i]=new Circle();
					enemyCircles2[i]=new Circle();
					enemyCircles3[i]=new Circle();
				}
				velocity=0;
				score=0;
				scoreEnemy=0;

			}
		}

		batch.draw(bird,birdX,birdY,Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);
		bitmapFont.draw(batch,String.valueOf(score),100,200);
		batch.end();
		birdcircle.set(birdX +Gdx.graphics.getWidth()/30,birdY +Gdx.graphics.getHeight()/30,Gdx.graphics.getWidth()/30);
		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.BLACK);
		//shapeRenderer.circle(birdcircle.x,birdcircle.y,birdcircle.radius);

		for (int i=0;i<numberOfEnemies;i++){
			//shapeRenderer.circle(enemyX[i]+Gdx.graphics.getWidth() / 30,Gdx.graphics.getHeight()/2+EnemyOffset[i]+Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);
			//shapeRenderer.circle(enemyX[i]+Gdx.graphics.getWidth() / 30,Gdx.graphics.getHeight()/2+EnemyOffset2[i]+Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);
			//shapeRenderer.circle(enemyX[i]+Gdx.graphics.getWidth() / 30,Gdx.graphics.getHeight()/2+EnemyOffset3[i]+Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);
			if(Intersector.overlaps(birdcircle,enemyCircles[i])||Intersector.overlaps(birdcircle,enemyCircles2[i])||Intersector.overlaps(birdcircle,enemyCircles3[i])){

				gameState=2;
			}

		}
	//	shapeRenderer.end();
	}
	
	@Override
	public void dispose () {

	}
}
