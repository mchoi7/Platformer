import static java.lang.Math.*;

abstract class Physical extends Instance {
    protected double dx, dy;
    protected double[] mdx, mdy;
    protected double[][] ddx, ddy; //state, constant force, input force, feedback force
    protected double[] key;
    protected double[] dds;


    protected enum Force {
        Constant,
        Input,
        Feedback
    }

    Physical(double x, double y) {
        super(x, y);
    }

    protected void input() {
        double ddx = this.ddx[state][Force.Input.ordinal()] * (dds[0] * key[0] - dds[1] * key[1]);
        double ddy = this.ddy[state][Force.Input.ordinal()] * (dds[2] * key[2] - dds[3] * key[3]);

        if(abs(dx + ddx) < mdx[state]) dx += ddx;
        else if(abs(dx) < mdx[state]) dx = signum(ddx) * mdx[state];

        if(abs(dy + ddy) < mdy[state]) dy += ddy;
        else if(abs(dy) < mdy[state]) dy = signum(ddy) * mdy[state];
    }

    protected void physics() {
        double ddx = this.ddx[state][Force.Constant.ordinal()];
        double ddy = this.ddy[state][Force.Constant.ordinal()];

        dx += ddx;
        dy += ddy;

        ddx = this.ddx[state][Force.Feedback.ordinal()] * signum(dx);
        ddy = this.ddy[state][Force.Feedback.ordinal()] * signum(dy);

        dx += ddx;
        dy += ddy;

        if(abs(dx) < abs(ddx)) dx = 0;
        if(abs(dy) < abs(ddy)) dy = 0;
    }

    protected void move() {

    }
}
