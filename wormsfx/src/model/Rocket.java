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

    public double rocketTime()
    {
        return ((velocityZero / gravity) * (sin(angleZero) + sqrt(sin(angleZero) * sin(angleZero) + constant1)));
    }

    public double rocketPeakX()
    {

        return ((constant2/2) * sin(2*angleZero));
    }

    public double rocketPeakY()
    {
        return ((constant2/2) * (sin(angleZero)*sin(angleZero) + constant1) );
    }

    public double rocketEndX()
    {
        return (constant2 * cos(angleZero) * ( sin(angleZero) + sqrt( sin(angleZero) * sin(angleZero) + constant1 ) ) );
    }

    public double rocketPosY(double posX)
    {
        return ( -1 * gravity / (2*velocityZero*velocityZero*cos(angleZero)*cos(angleZero)) * posX * posX +
                tan(angleZero) + posX + startPointY);
    }

    public double rocketBezierY()
    {
        return ((gravity*rocketTime() - velocityZero * cos(angleZero)) / velocityZero * sin(angleZero)) *
        (constant2 * cos(angleZero) * ( sin(angleZero) + sqrt( sin(angleZero) * sin(angleZero) ) ) ) / 2;
    }

}
