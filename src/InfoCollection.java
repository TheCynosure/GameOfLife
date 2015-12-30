/**
 * Created by john_bachman on 12/15/15.
 */
public class InfoCollection {
    private int neighbors;
    private boolean diseased = false;
    private boolean cured = false;

    public int getNeighbors() {
        return neighbors;
    }

    public boolean isDiseased() {
        return diseased;
    }

    public void addInfo(Info info) {
        neighbors += info.neighbors;
        if(info.diseased) {
            diseased = true;
        }
        if(info.cured) {
            cured = true;
        }
    }

    public void setCured(boolean cured) {
        this.cured = cured;
    }

    public boolean isCured() {
        return cured;
    }
}
