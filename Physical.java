import static java.lang.Math.*;

abstract class Physical extends Instance {
    protected double dx, dy;
    protected double[] mdx, mdy;
    protected double[][] ddx, ddy; //state, constant force, input force, feedback force
    protected double[][] dds;
    protected double[] key;
    protected double weight;


    protected enum Force {
        Continuous,
        Feedback;
        enum Input {
            Up,
            Down,
            Left,
            Right
        }
    }

    Physical(double x, double y) {
        super(x, y);
    }

    protected void input() {
        double ddx = (this.ddx[Force.Input.Up.ordinal()][0] * key[0] - dds[state][1] * key[1]);
        double ddy = (dds[state][2] * key[2] - dds[state][3] * key[3]);

        if(abs(dx + ddx) < mdx[state]) dx += ddx;
        else if(abs(dx) < mdx[state]) dx = signum(ddx) * mdx[state];

        if(abs(dy + ddy) < mdy[state]) dy += ddy;
        else if(abs(dy) < mdy[state]) dy = signum(ddy) * mdy[state];
    }

    protected void physics() {
        dx += ddx[state][Force.Continuous.ordinal()];
        dy += ddy[state][Force.Continuous.ordinal()];

        dx += ddx[state][Force.Feedback.ordinal()] * signum(dx);
        dy += ddy[state][Force.Feedback.ordinal()] * signum(dy);

        if(abs(dx) < ddx[state][Force.Feedback.ordinal()]) dx = 0;
        if(abs(dy) < ddy[state][Force.Feedback.ordinal()]) dy = 0;
    }

    protected void move() {
        x += dx;
        y += dy;

        int xCenter = (int) round(x / Constants.UNIT), yCenter = (int) round(y / Constants.UNIT);
        double widthAvg = (width + Constants.UNIT) / 2, heightAvg = (height + Constants.UNIT) / 2;

        for(int j = (int) (height / -Constants.UNIT); j <= (int) (height / Constants.UNIT); j++)
            for(int i = (int) (width / -Constants.UNIT); i <= (int) (width / Constants.UNIT); i++) {
                double xBlock = (xCenter + i) * Constants.UNIT, yBlock = (yCenter + j) * Constants.UNIT;
                double xDist = x - xBlock, yDist = y - yBlock;
                if(InstanceManager.getSolidMap()[xCenter + i][yCenter + j] && abs(xDist) < widthAvg && abs(yDist) < heightAvg) {
                    if(abs(xDist - dx) > abs(yDist - dy)) x = xBlock + signum(xDist) * Constants.UNIT;
                    else y = yBlock + signum(yDist) * Constants.UNIT;
                    break;
                }
            }

        for(Instance instance : InstanceManager.getInstanceList())
            if(this != instance && this.isIntersecting(instance))
                this.collidesWith(instance);
    }
}
