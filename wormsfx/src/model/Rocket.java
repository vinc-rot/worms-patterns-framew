package model;

import static java.lang.Math.*;

public class Rocket {

    double velocityZero;
    double angleZero;
    double gravity;
    double startPointY;
    double constant1;
    double constant2;

    public Rocket(double velocityZero, double angleZero, double gravity, double startPointY) {
        this.velocityZero = velocityZero;
        this.angleZero = angleZero;
        this.gravity = gravity;
        this.startPointY = startPointY;

        constant1 = (2*startPointY*gravity) / (velocityZero * velocityZero);
        constant2 = (velocityZero*velocityZero) / gravity;
    }

    public int rocketTime()
    {
        return (int) (1000 * (velocityZero / gravity) * (sin(angleZero) + sqrt(sin(angleZero) * sin(angleZero) + constant1)));
    }

    public int rocketPeakX()
    {

        return (int) ((constant2/2) * sin(2*angleZero));
    }

    public int rocketPeakY()
    {
        return (int) ((constant2/2) * (sin(angleZero)*sin(angleZero) + constant1) );
    }

    public int rocketEndX()
    {
        return (int) (constant2 * cos(angleZero) * ( sin(angleZero) + sqrt( sin(angleZero) * sin(angleZero) + constant1 ) ) );
    }

}
