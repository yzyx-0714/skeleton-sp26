import edu.princeton.cs.algs4.StdRandom;

import java.awt.*;
import java.util.Map;

public class Particle {
    public ParticleFlavor flavor;
    public int lifespan;

    public static final int PLANT_LIFESPAN = 150;
    public static final int FLOWER_LIFESPAN = 75;
    public static final int FIRE_LIFESPAN = 10;
    public static final Map<ParticleFlavor, Integer> LIFESPANS =
            Map.of(ParticleFlavor.FLOWER, FLOWER_LIFESPAN,
                   ParticleFlavor.PLANT, PLANT_LIFESPAN,
                   ParticleFlavor.FIRE, FIRE_LIFESPAN);

    public Particle(ParticleFlavor flavor) {
        this.flavor = flavor;
        //这是Intelli的修复功能教我可以用getOrDefault来设置默认值-1，如果map里有此键则获得此键对应的值
        this.lifespan = LIFESPANS.getOrDefault(this.flavor, -1);
    }

    public void decrementLifespan() {
        if (this.lifespan > 0) {
            this.lifespan -= 1;
        }
        if (this.lifespan == 0) {
            this.flavor = ParticleFlavor.EMPTY;
            this.lifespan = -1;
        }
    }


    public Color color() {
        if (flavor == ParticleFlavor.EMPTY) {
            return Color.BLACK;
        } else if (flavor == ParticleFlavor.SAND) {
            return Color.YELLOW;
        } else if (flavor == ParticleFlavor.BARRIER) {
            return Color.GRAY;
        } else if (flavor == ParticleFlavor.WATER) {
            return Color.BLUE;
        } else if (flavor == ParticleFlavor.FOUNTAIN) {
            return Color.CYAN;
        } else if (flavor == ParticleFlavor.PLANT) {
            double ratio = (double) Math.max(0, Math.min(lifespan, PLANT_LIFESPAN)) / PLANT_LIFESPAN;
            int g = 120 + (int) Math.round((255 - 120) * ratio);
            return new Color(0, g, 0);
        } else if (flavor == ParticleFlavor.FIRE) {
            double ratio = (double) Math.max(0, Math.min(lifespan, FIRE_LIFESPAN)) / FIRE_LIFESPAN;
            int r = (int) Math.round(255 * ratio);
            return new Color(r, 0, 0);
        } else if (flavor == ParticleFlavor.FLOWER) {
            double ratio = (double) Math.max(0, Math.min(lifespan, FLOWER_LIFESPAN)) / FLOWER_LIFESPAN;
            int r = 120 + (int) Math.round((255 - 120) * ratio);
            int g = 70 + (int) Math.round((141 - 70) * ratio);
            int b = 80 + (int) Math.round((161 - 80) * ratio);
            return new Color(r, g, b);

        }
        return Color.GRAY;
    }

    public void moveInto(Particle other) {
        other.flavor = this.flavor;
        other.lifespan = this.lifespan;

        this.flavor = ParticleFlavor.EMPTY;
        this.lifespan = -1;
    }

    public void fall(Map<Direction, Particle> neighbors) {
        Particle p = neighbors.get(Direction.DOWN);
        if (p.flavor == ParticleFlavor.EMPTY) {
            moveInto(p);
        }
    }

    public void flow(Map<Direction, Particle> neighbors) {
        int num = StdRandom.uniformInt(3);
        if (num == 0) {
            return;
        }
        if (num == 1 && neighbors.get(Direction.LEFT).flavor == ParticleFlavor.EMPTY) {
                moveInto(neighbors.get(Direction.LEFT));
        }
        if (num == 2 && neighbors.get(Direction.RIGHT).flavor == ParticleFlavor.EMPTY) {
                moveInto(neighbors.get(Direction.RIGHT));
        }
    }

    public void grow(Map<Direction, Particle> neighbors) {
        int num = StdRandom.uniformInt(10);
        Direction[] direction = {Direction.UP, Direction.LEFT, Direction.RIGHT, Direction.DOWN};
        if (num < direction.length) {
            Direction this_Direction = direction[num];
            Particle p = neighbors.get(this_Direction);
            if (p.flavor == ParticleFlavor.EMPTY) {
                p.flavor = this.flavor;
                p.lifespan = LIFESPANS.get(p.flavor);
            }
        }
    }

    public void burn(Map<Direction, Particle> neighbors) {
        Direction[] direction = {Direction.UP, Direction.LEFT, Direction.RIGHT, Direction.DOWN};
        for (int i = 0; i < direction.length; i++) {
            int num = StdRandom.uniformInt(10);
            Particle p = neighbors.get(direction[i]);
            if (p.flavor == ParticleFlavor.PLANT || p.flavor == ParticleFlavor.FLOWER) {
                if (num < 4) {
                    p.flavor = ParticleFlavor.FIRE;
                    p.lifespan = FIRE_LIFESPAN;
                }
            }
        }
    }

    public void action(Map<Direction, Particle> neighbors) {
        if (this.flavor == ParticleFlavor.EMPTY) {
            return;
        }
        if (this.flavor != ParticleFlavor.BARRIER) {
            fall(neighbors);
        }
        if (this.flavor == ParticleFlavor.WATER) {
            flow(neighbors);
        }
        if (this.flavor == ParticleFlavor.PLANT) {
            grow(neighbors);
        }
        if (this.flavor == ParticleFlavor.FIRE) {
            burn(neighbors);
        }
    }
}