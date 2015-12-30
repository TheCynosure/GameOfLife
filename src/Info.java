/**
 * Created by john_bachman on 12/15/15.
 */
public class Info {
    public int neighbors = 0;
    public boolean diseased, cured = false;

    public Info(int neighbors, boolean diseased, boolean cured) {
        this.neighbors = neighbors;
        this.diseased = diseased;
        this.cured = cured;
    }
}
