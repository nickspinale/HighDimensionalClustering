package spinacht;

import spinacht.data.Database;

/**
 * Simple class wrapping all parameters of a SUBCLU run: epsilon, minPts, and a database.
 */
public class Params {

    private final double eps;
    private final int minPts;
    private final Database db;

    public Params(double eps, int minPts, Database db) {
        this.eps = eps;
        this.minPts = minPts;
        this.db = db;
    }

    public double getEps() {
        return this.eps;
    }

    public int getMinPts() {
        return this.minPts;
    }

    public Database getDatabase() {
        return this.db;
    }

}
