//package com.example.demo;
//
//public abstract class PowerUp extends ActiveActorDestructible {
//    private static final String IMAGE_LOCATION = "/com/example/demo/images/";
//    private static final int IMAGE_HEIGHT = 40;
//    private static final double VERTICAL_VELOCITY = -5;
//
//    public PowerUp(String imageName, double initialXPos, double initialYPos) {
//        super(imageName, IMAGE_HEIGHT, initialXPos, initialYPos);
//    }
//
//    @Override
//    public void updatePosition() {
//        moveVertically(VERTICAL_VELOCITY);
//    }
//
//
//    @Override
//    public void updateActor() {
//        updatePosition();
//
//        if (getLayoutX() < -50) {
//            destroy();
//        }
//    }
//
//    @Override
//    public void takeDamage() {
//        destroy();
//    }
//}