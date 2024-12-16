package controllers;

import java.io.IOException;
import java.util.ArrayList;

import application.Main;
import application.core.*;
import application.entities.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class GameController extends BaseController {
	public static int AMMO_REWARD = 2;
	public static int SCORE_REWARD = 100;
	public static int SCORE_PUNISHMENT = 20;
	public static int DIRECT_DAMAGE = 20;
	public static int INDIRECT_DAMAGE = 7;

    private int health = 100;
    private int score = 0;
    private int ammo = 5;
    
    private String menuStatus;
    
    private double lastAdded = 0;
    private int currentQuestionIndex = 0;
    private Level level;

    private GameLoop gameLoop;
    private Spaceship player;

    @FXML
    private Pane GameContent;

    @FXML
    private Label ScoreLabel;

    @FXML
    private Label AmmoLabel;

    @FXML
    private Label HealthLabel;

    @FXML
    private Button ContinueButton;

    @FXML
    private Button MenuButton;

    @FXML
    private Button PauseButton;

    @FXML
    private VBox PauseMenu;

    @FXML
    private Label MenuLabel;

    @Override
    public void start() {
    	this.SetPauseMenuState(false); 
    	
        this.gameLoop = new GameLoop();

        player = new Spaceship();
        GameContent.getChildren().add(player.getNode());
        this.addGameObject(player);

        GameContent.setFocusTraversable(true);
        GameContent.getScene().setOnKeyPressed(this::handleKeyPress);
        GameContent.getScene().setOnKeyReleased(this::handleKeyRelease);

        // Start game loop
        
        this.setLevel();
        
        gameLoop.setUpdateCallback(() -> {
			updateGame();
		});
        
        gameLoop.start();

        GameContent.requestFocus();
        main.playPersistentSound("game", 0.1, true);
        
        this.menuStatus = "Menu";
    }

    @Override
    public void clean() {
    	main.stopPersistentSound();
    	
        gameLoop.stop();
        gameLoop = null;
    }

    public void addGameObject(GameObject gameObject) {
        gameLoop.addGameObject(gameObject);
    }

    public void removeGameObject(GameObject gameObject) {
        gameLoop.removeGameObject(gameObject);
    }

    private void setLevel() {
    	DataParser data = this.main.getParser();
    	int levelNumber = data.getLevel();
    	int levelIndex = levelNumber - 1;
        	
    	this.level = data.getLevel(levelIndex);
    }
    
    private void progressLevel() {
        double currentTime = gameLoop.getElapsedTime();

        if (currentTime - lastAdded >= level.getSpeed()) {
            if (currentQuestionIndex < level.getQuestions().size()) {
                Question nextQuestion = level.getQuestion(currentQuestionIndex);
                AsteroidBelt asteroidBelt = new AsteroidBelt(nextQuestion);
                this.GameContent.getChildren().add(asteroidBelt.getNode());
                this.addGameObject(asteroidBelt);

                this.lastAdded = currentTime;
                this.currentQuestionIndex += 1;
            } else {
                boolean hasAsteroidBelts = false;

                for (GameObject obj : gameLoop.getGameObjects()) {
                    if (obj instanceof AsteroidBelt) {
                        hasAsteroidBelts = true;
                        break;
                    }
                }

                if (!hasAsteroidBelts) {
                	this.gameLoop.stop();
                	
                	this.menuStatus = "Complete";
                	
                	this.SetPauseMenuState(true);
                }
            }
        }
    }

    private void SetPauseMenuState(boolean state) {
    	this.PauseMenu.setVisible(state);
    	this.PauseButton.setVisible(!state);
    }
    
    public void updateHUD() {
        ScoreLabel.setText("Score: " + score);
        AmmoLabel.setText("Ammo: " + ammo);
        HealthLabel.setText("Health: " + health);
        
		this.MenuLabel.setText(this.menuStatus);

		DataParser data = this.main.getParser();

		if (this.menuStatus.equals("Menu")) {
		    this.ContinueButton.setText("Resume");
		} else if (this.menuStatus.equals("Complete")) {
			
			try {
				data.setWins(data.getWins() + 1);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			int levelNumber = this.level.getLevelNumber();
			
		    
			if (levelNumber == data.getLevelCount()) {
				this.ContinueButton.setText("Replay Final Level");
			} else {
			    this.ContinueButton.setText("Next Level");
			}
			
		} else if (this.menuStatus.equals("Failed")) {
			
			try {
				data.setLosses(data.getLosses() + 1);
			} catch (IOException e) {
				e.printStackTrace();
			}

		    this.ContinueButton.setText("Retry Level");
		}
    }

    private void updateGame() {
        this.checkCollisions();
        this.removeOffScreenObjects();        
        this.progressLevel();  
        
        this.updateHUD();

    }

    private void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.LEFT) {
            player.setMovingLeft(true);
        } else if (event.getCode() == KeyCode.RIGHT) {
            player.setMovingRight(true);
        } else if (event.getCode() == KeyCode.UP) {
            player.setMovingUp(true);
        } else if (event.getCode() == KeyCode.DOWN) {
            player.setMovingDown(true);
        }
    }

    
    private void handleKeyRelease(KeyEvent event) {
        if (event.getCode() == KeyCode.LEFT) {
            this.player.setMovingLeft(false);
        } else if (event.getCode() == KeyCode.RIGHT) {
            this.player.setMovingRight(false);
        } else if (event.getCode() == KeyCode.UP) {
            this.player.setMovingUp(false);
        } else if (event.getCode() == KeyCode.DOWN) {
            this.player.setMovingDown(false);
        } else if (event.getCode() == KeyCode.SPACE) {
            if (ammo > 0) {
                this.shootProjectile();
            }
        } else if (event.getCode() == KeyCode.P) {
        	if (menuStatus.equals("Menu")) {
        		this.PauseAction(null);
        	}
        }
    }
    
    private void checkCollisions() {
        ArrayList<GameObject> objectsToRemove = new ArrayList<>();
        ArrayList<Asteroid> asteroidsToRemove = new ArrayList<>();

		DataParser data = this.main.getParser();
        
        for (GameObject obj1 : new ArrayList<>(gameLoop.getGameObjects())) {
            if (obj1 instanceof AsteroidBelt) {
                AsteroidBelt belt = (AsteroidBelt) obj1;

                for (Asteroid asteroid : new ArrayList<>(belt.getAsteroids())) {
                    boolean isAnswerCorrect = asteroid.getAnswer().isCorrect();

                    if (asteroid.intersects(player)) {
                        if (isAnswerCorrect) {
                            this.main.playSimpleSound("explosion", 0.05);

                            this.damagePlayer(DIRECT_DAMAGE);
                            this.ammo += 1;
                            
                            score += SCORE_REWARD;
                            objectsToRemove.add(belt);
                            GameContent.getChildren().remove(belt.getNode());
                            
                			try {
								data.setCorrect(data.getCorrect() + 1);
							} catch (IOException e) {
								e.printStackTrace();
							}
                            
                            break;
                        } else {
                            this.damagePlayer(DIRECT_DAMAGE);;
                            score -= SCORE_PUNISHMENT;
                            asteroidsToRemove.add(asteroid);
                        }
                    }

                    for (GameObject obj2 : new ArrayList<>(gameLoop.getGameObjects())) {
                        if (obj2 instanceof Projectile && asteroid.intersects(obj2)) {
                            this.main.playSimpleSound("explosion", 0.1);

                            if (isAnswerCorrect) {
                                ammo += AMMO_REWARD;
                                score += SCORE_REWARD;
                                
                    			try {
									data.setCorrect(data.getCorrect() + 1);
								} catch (IOException e) {
									e.printStackTrace();
								}

                                objectsToRemove.add(belt);
                                GameContent.getChildren().remove(belt.getNode());
                                objectsToRemove.add(obj2);
                                GameContent.getChildren().remove(obj2.getNode());
                                break;
                            } else {
                                score -= GameController.SCORE_PUNISHMENT;
                                
                                asteroidsToRemove.add(asteroid);
                                objectsToRemove.add(obj2);
                                
                                GameContent.getChildren().remove(obj2.getNode());
                            }
                        }
                    }
                }

                for (Asteroid asteroid : asteroidsToRemove) {
                    belt.getAsteroids().remove(asteroid);
                    
                    ((Pane) belt.getNode()).getChildren().remove(asteroid.getNode());
                }
                asteroidsToRemove.clear();

                if (belt.getAsteroids().isEmpty()) {
                    objectsToRemove.add(belt);
                    GameContent.getChildren().remove(belt.getNode());
                }
            }
        }

        for (GameObject obj : objectsToRemove) {
            gameLoop.removeGameObject(obj);
            GameContent.getChildren().remove(obj.getNode());
        }
    }

    private void removeOffScreenObjects() {
        ArrayList<GameObject> objectsToRemove = new ArrayList<>();

        for (GameObject obj : gameLoop.getGameObjects()) {
            if (obj instanceof AsteroidBelt) {
            	AsteroidBelt belt = (AsteroidBelt) obj;
                if (belt.getY() > Main.HEIGHT) {
                    objectsToRemove.add(belt);
                    
                    int amount = belt.getAsteroids().size() * 10;
                    this.damagePlayer(amount);
                }
            } else if (obj instanceof Projectile) {
                Projectile projectile = (Projectile) obj;
                if (projectile.getY() + projectile.getHeight() < 0) {
                    objectsToRemove.add(projectile);
                }
            }
        }

        for (GameObject obj : objectsToRemove) {
            gameLoop.removeGameObject(obj);
            GameContent.getChildren().remove(obj.getNode());
            
            if (obj instanceof Asteroid) {
                this.main.playSimpleSound("explosion", 0.15);
            }
            
        }
    }
    
    private void shootProjectile() {
    	if (this.player != null) {
    		if (this.health > 0 && !this.gameLoop.isPaused()) {
    			this.main.playSimpleSound("laserShoot", 0.1);
    			
    			this.ammo -= 1;
    			
    			double x = (player.getX() + Spaceship.WIDTH/2) - Projectile.WIDTH/2 ;
    	        double y = player.getY();

    	        Projectile projectile = new Projectile(x, y);

    	        GameContent.getChildren().add(projectile.getNode());
    	        addGameObject(projectile);
    		}
    	}
    }


    private void damagePlayer(int amount) {
    	this.main.playSimpleSound("hitHurt", 0.2);
        this.health = Math.max(this.health - amount, 0);
        
        if (this.health <= 0) {
        	this.gameLoop.pause();
        	
        	this.SetPauseMenuState(true);
        	
        	this.menuStatus = "Failed";
        }
    }
    
    @FXML
    void ContinueAction(ActionEvent event) {
        this.main.playSimpleSound("blipSelect", 0.05);

    	if (this.menuStatus.equals("Menu")) {
    		this.SetPauseMenuState(false);

    	    this.gameLoop.resume();
    	} else if (this.menuStatus.equals("Complete")) {
    		DataParser data = this.main.getParser();
			int levelNumber = this.level.getLevelNumber();
			
		    
			if (levelNumber < data.getLevelCount()) {
				try {
					data.setLevel(levelNumber + 1);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
    	    this.switchScene("game_area");
    	} else if (this.menuStatus.equals("Failed")) {
    	    this.switchScene("game_area");
    	}
    }

    @FXML
    void MenuAction(ActionEvent event) {
        this.main.playSimpleSound("blipSelect", 0.05);

 		DataParser data = this.main.getParser();
		int levelNumber = this.level.getLevelNumber();
		
	    
		if (levelNumber < data.getLevelCount()) {
			try {
				data.setLevel(levelNumber + 1);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        
    	this.switchScene("menu");
        System.out.println("Returning to menu...");
    }

    @FXML
    void PauseAction(ActionEvent event) {
        this.main.playSimpleSound("blipSelect", 0.05);

    	if (!this.gameLoop.isPaused()) {
    		this.gameLoop.pause();
    		
    		this.SetPauseMenuState(true);
    	}
    }
}
